package albedo.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.adbonnin.cz2128.JsonException;
import fr.adbonnin.cz2128.JsonProvider;
import io.micronaut.core.annotation.Blocking;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

@Blocking
public abstract class JsonProviderService {

    public static final long DEFAULT_LOCK_TIMEOUT = 2000;

    private final ConcurrentMap<Path, JsonProviderLock> jsonProviders = new ConcurrentHashMap<>();

    protected abstract JsonProvider createJsonProvider(Path path);

    public JsonProvider getJsonProvider(Path path) {
        return getJsonProvider(path, DEFAULT_LOCK_TIMEOUT);
    }

    public JsonProvider getJsonProvider(Path path, long lockTimeout) {
        return jsonProviders.computeIfAbsent(path, (p) -> {
            final JsonProvider jsonProvider = createJsonProvider(p);
            return new JsonProviderLock(jsonProvider, lockTimeout);
        });
    }

    private static class JsonProviderLock implements JsonProvider {

        private final ReentrantReadWriteLock.ReadLock readLock;

        private final ReentrantReadWriteLock.WriteLock writeLock;

        private final JsonProvider jsonProvider;

        private final long lockTimeout;

        public JsonProviderLock(JsonProvider jsonProvider, long lockTimeout) {
            this.jsonProvider = requireNonNull(jsonProvider);
            this.lockTimeout = lockTimeout;

            final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            this.readLock = lock.readLock();
            this.writeLock = lock.writeLock();
        }

        @Override
        public <T> T withParser(ObjectMapper mapper, Function<JsonParser, ? extends T> function) {
            try {
                if (readLock.tryLock(lockTimeout, TimeUnit.MILLISECONDS)) {
                    try {
                        return jsonProvider.withParser(mapper, function);
                    }
                    finally {
                        readLock.unlock();
                    }
                }
                else {
                    throw new JsonException("Reader lock can't be acquired.");
                }
            }
            catch (InterruptedException e) {
                throw new JsonException(e);
            }
        }

        @Override
        public <T> T withGenerator(ObjectMapper mapper, BiFunction<JsonParser, JsonGenerator, ? extends T> function) {
            try {
                if (writeLock.tryLock(lockTimeout, TimeUnit.MILLISECONDS)) {
                    try {
                        return jsonProvider.withGenerator(mapper, function);
                    }
                    finally {
                        writeLock.unlock();
                    }
                }
                else {
                    throw new JsonException("Write lock can't be acquired.");
                }
            }
            catch (InterruptedException e) {
                throw new JsonException(e);
            }
        }
    }
}
