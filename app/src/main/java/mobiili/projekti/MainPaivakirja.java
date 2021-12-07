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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class MainPaivakirja extends AppCompatActivity {

    Button tallenna;
    EditText merkinta;
    int numero = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paivakirja);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> paivakirja = DB.getPaivakirja(tunnus);

        merkinta = (EditText) findViewById(R.id.paivakirjamerkinta);

        LinearLayout layoutPaivakirja = (LinearLayout) findViewById(R.id.paivakirjaLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = dp(10);

        paivakirja.clear();
        paivakirja.addAll(DB.getPaivakirja(tunnus));
        int index = paivakirja.size(), num2 = 0, num3 = 0;

        final TextView[] p = new TextView[index];
        if (index > 0) {
            int pknum = index - 3;
            int index2 = index / 3;
            numero = 1;
            for (int i = 1; i <= index2; i++) { // 5, 9 ja 13 poistaa ensimmäisen näkyvistä
                p[numero] = new TextView(this);
                p[numero].setText("Merkintä:  " + paivakirja.get(pknum + 1) + " / " + index2 + "\n"
                        + paivakirja.get(pknum) + "\n\n" + paivakirja.get(pknum + 2));
                p[numero].setLayoutParams(params);
                p[numero].setPadding(dp(10), 0, dp(10), 10);
                p[numero].setBackgroundColor(Color.LTGRAY);
                p[numero].setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
                p[numero].setGravity(Gravity.CENTER_VERTICAL);
                layoutPaivakirja.addView(p[numero]);
                pknum -= 3;
                numero += 1;
            }
        }

        tallenna = (Button) findViewById(R.id.tallennaPaivakirja);
        tallenna.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainPaivakirja.this, MainPage.class);
            int index2 = paivakirja.size();
            if (index2 > 0) {
                numero = Integer.parseInt(paivakirja.get(index - 2)) + 1;
            } else {
                numero = 1;
            }
            // tarkistus onko tekstiä
            DB.addPaivakirja(tunnus, numero, merkinta.getText().toString());
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}