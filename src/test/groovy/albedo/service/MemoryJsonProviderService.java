package albedo.service;

import fr.adbonnin.cz2128.JsonProvider;
import fr.adbonnin.cz2128.json.provider.MemoryJsonProvider;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

import javax.inject.Singleton;
import java.nio.file.Path;

@Requires(env = Environment.TEST)
@Replaces(FileJsonProviderService.class)
@Singleton
public class MemoryJsonProviderService extends JsonProviderService {

    @Override
    protected JsonProvider createJsonProvider(Path path) {
        return new MemoryJsonProvider();
    }
}
