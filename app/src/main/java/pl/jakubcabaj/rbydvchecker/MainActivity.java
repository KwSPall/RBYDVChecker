package pl.jakubcabaj.rbydvchecker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.jakubcabaj.rbydvchecker.calc.Calc;
import pl.jakubcabaj.rbydvchecker.importer.PokemonImporter;
import pl.jakubcabaj.rbydvchecker.model.Pokemon;

public class MainActivity extends AppCompatActivity {

    List<Pokemon> pokemonList;
    Map<String, Pokemon> pokemonMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            pokemonList = PokemonImporter.readFile("gen1.csv", getAssets() );
        } catch (IOException e) {
            return;
        }
        setContentView(R.layout.activity_main);
        preparePokemonList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        Button clickButton = findViewById(R.id.calcButton);
        clickButton.setOnClickListener((view) -> {
            //get Selected Pokemon
            String pokemonName = ((AutoCompleteTextView) findViewById(R.id.autoCompleteTextView)).getText().toString();
            if (!pokemonMap.containsKey(pokemonName)) {
                return;
            }
            //get Level
            int level;
            String pokemonLevel = ((EditText) findViewById(R.id.levelText)).getText().toString();
            try {
                level = Integer.parseInt(pokemonLevel);
            } catch (Exception e) {
                level = 0;
            }
            if (level > 100 || level < 1) {
                return;
            }
            Calc.calculateHP(pokemonMap.get(pokemonName), level, findViewById(R.id.hpText), findViewById(R.id.hpResult));
            Calc.calculate(pokemonMap.get(pokemonName), level, (p -> p.attack), findViewById(R.id.atkText), findViewById(R.id.atkResult));
            Calc.calculate(pokemonMap.get(pokemonName), level, (p -> p.defense), findViewById(R.id.defText), findViewById(R.id.defResult));
            Calc.calculate(pokemonMap.get(pokemonName), level, (p -> p.speed), findViewById(R.id.speedText), findViewById(R.id.speedResult));
            Calc.calculate(pokemonMap.get(pokemonName), level, (p -> p.special), findViewById(R.id.specialText), findViewById(R.id.specialResult));
        });
    }

    private void preparePokemonList() {
        String[] pokemonArray = pokemonList.stream().map((p) -> p.name).toArray(String[]::new);
        pokemonList.forEach(pokemon -> pokemonMap.put(pokemon.name, pokemon));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, pokemonArray);

        AutoCompleteTextView actv = findViewById(R.id.autoCompleteTextView);
        actv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
