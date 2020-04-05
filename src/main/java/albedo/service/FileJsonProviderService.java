package albedo.service;

import fr.adbonnin.cz2128.JsonProvider;
import fr.adbonnin.cz2128.json.provider.FileJsonProvider;

import javax.inject.Singleton;
import java.nio.file.Path;

@Singleton
public class FileJsonProviderService extends JsonProviderService {

    @Override
    protected JsonProvider createJsonProvider(Path path) {
        return new FileJsonProvider(path);
    }
}
