package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ResourcesFileLoader implements Loader {

    private final URL resource;

    private static final Type GSON_TYPE = new TypeToken<List<Measurement>>(){}.getType();

    public ResourcesFileLoader(String fileName) throws FileProcessException {
        resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new FileProcessException(fileName + " - not found");
        }
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try {
            return new Gson().fromJson(Files.readString(Path.of(resource.toURI())), GSON_TYPE);
        } catch (URISyntaxException | IOException e) {
            throw new FileProcessException(e);
        }
    }
}
