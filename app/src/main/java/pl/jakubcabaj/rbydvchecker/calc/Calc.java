package pl.jakubcabaj.rbydvchecker.calc;

import android.widget.EditText;
import android.widget.TextView;

import java.util.function.Function;

import pl.jakubcabaj.rbydvchecker.model.Pokemon;

public class Calc {
    public static void calculate(Pokemon p, int level, Function<Pokemon, Integer> statGetter, EditText sourceText, TextView resultText) {
        String statText = sourceText.getText().toString();
        int providedStat;
        try {
            providedStat = Integer.parseInt(statText);
        } catch (Exception e) {
            providedStat = 0;
        }
        if (providedStat == 0) {
            resultText.setText("0");
            return;
        }
        int pokemonBaseStat = statGetter.apply(p);
        int minStat = 15;
        int maxStat = 0;
        for (int i = 0; i < 16; i++) {
            int calc = (int) Math.floor(((2.0 * pokemonBaseStat + i + i) * level / 100.0)) + 5;
            if (calc == providedStat) {
                minStat = Math.min(minStat, i);
                maxStat = Math.max(maxStat, i);
            }
        }
        if (maxStat < minStat) {
            resultText.setText("Bad Stat");
            return;
        }
        if (minStat == maxStat) {
            resultText.setText(minStat+"");
            return;
        }
        resultText.setText(minStat+"-"+maxStat);
    }

    public static void calculateHP(Pokemon p, int level, EditText sourceText, TextView resultText) {
        String statText = sourceText.getText().toString();
        int providedStat;
        try {
            providedStat = Integer.parseInt(statText);
        } catch (Exception e) {
            providedStat = 0;
        }
        if (providedStat == 0) {
            resultText.setText("0");
            return;
        }
        int pokemonBaseStat = p.hp;
        int minStat = 15;
        int maxStat = 0;
        for (int i = 0; i < 16; i++) {
            int calc = (int) Math.floor((2.0 * pokemonBaseStat + i + i) * level / 100.0) + level + 10;
            if (calc == providedStat) {
                minStat = Math.min(minStat, i);
                maxStat = Math.max(maxStat, i);
            }
        }
        if (maxStat < minStat) {
            resultText.setText("Bad Stat");
        }
        if (minStat == maxStat) {
            resultText.setText(minStat+"");
        }
        resultText.setText(minStat+"-"+maxStat);
    }
}
