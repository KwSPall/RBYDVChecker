package pl.jakubcabaj.rbydvchecker.importer;

import android.content.res.AssetManager;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pl.jakubcabaj.rbydvchecker.model.Pokemon;

public class PokemonImporter {

    public static List<Pokemon> readFile(String csv, AssetManager assetManager) throws IOException {
        InputStream inputStream = assetManager.open(csv);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Pokemon.class).withColumnSeparator(';');
        MappingIterator<Pokemon> pokemonIterator = mapper.readerFor(Pokemon.class).with(schema).readValues(inputStream);
        return pokemonIterator.readAll();
    }
}
