package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class MainPaivakirja extends AppCompatActivity {

    Button tallenna, muokkaa;
    EditText merkinta;
    int numero = 0, pknum = 0, index2 = 0, index = 0;
    ImageButton takaisin, koti, asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paivakirja);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> paivakirja = DB.getPaivakirja(tunnus);

        merkinta = findViewById(R.id.paivakirjamerkinta);

        LinearLayout layoutPaivakirja = findViewById(R.id.paivakirjaLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(10);

        paivakirja.clear();
        paivakirja.addAll(DB.getPaivakirja(tunnus));
        index = paivakirja.size();

        pknum = index - 3;
        index2 = (index + 1) / 3;
        numero = 1;
        final TextView[] p = new TextView[index];
        if (index > 0) {
            int laskuri = 1;
            for (int i = 0; i < index2; i++) {
                p[numero] = new TextView(this);
                p[numero].setText("Merkintä:  " + laskuri + " / " + index2 + "\n"
                        + paivakirja.get(pknum) + "\n\n" + paivakirja.get(pknum + 2));
                p[numero].setLayoutParams(params);
                p[numero].setPadding(dp(10), 0, dp(10), 10);
                p[numero].setBackgroundColor(Color.LTGRAY);
                p[numero].setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
                p[numero].setGravity(Gravity.CENTER_VERTICAL);
                layoutPaivakirja.addView(p[numero]);
                pknum -= 3;
                numero += 1;
                laskuri += 1;
            }
        }

        tallenna = findViewById(R.id.tallennaPaivakirja);
        tallenna.setOnClickListener(v ->
        {
            int index2 = paivakirja.size();
            if (index2 > 0) {
                numero = Integer.parseInt(paivakirja.get(index - 2)) + 1;
            } else {
                numero = 1;
            }
            if (merkinta.getText().toString().equals("")) {
                Toast.makeText(this, "Merkintä tyhjä", Toast.LENGTH_SHORT).show();
            } else {
                DB.addPaivakirja(tunnus, numero, merkinta.getText().toString());
                Intent intent = new Intent(this, MainPaivakirja.class);
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
                Toast.makeText(this, "Merkintä poistettu!", Toast.LENGTH_SHORT).show();
                overridePendingTransition(
                        R.anim.f_in, R.anim.f_out
                );
            }
        });

        muokkaa = findViewById(R.id.editPaivakirja);
        muokkaa.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditPaivakirja.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.r_in, R.anim.l_out
            );
        });

        takaisin = findViewById(R.id.takaisin);
        takaisin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.d_in, R.anim.u_out
            );
        });

        koti = findViewById(R.id.koti);
        koti.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.l_in, R.anim.r_out
            );
        });

        asetukset = findViewById(R.id.asetukset);
        asetukset.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainAsetukset.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.u_in, R.anim.d_out
            );
        });

    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}