package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainPaivakirja extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paivakirja);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> paivakirja = DB.getPaivakirja(tunnus);

        LinearLayout layoutpaivakirja = (LinearLayout) findViewById(R.id.paivakirjaLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = dp(10);

        paivakirja.clear();
        paivakirja.addAll(DB.getPaivakirja(tunnus));
        paivakirja.clear();
        paivakirja.addAll(DB.getTehtavalista(tunnus));
        int index = paivakirja.size();
        for (int i = 0; i < index; i++) {
            int numero = i;
            int index2 = Integer.parseInt(paivakirja.get(i));
            i += 1;
            String paivays = paivakirja.get(i);
            i += 1;
            String merkinta = paivakirja.get(i);
            TextView pkm = new TextView(this);
            // tähän tarvitaan joku map? mihin yhtenä
            // tietona numero ja toisena päiväys ja kolmantena päiväkirjateksti
            pkm.setLayoutParams(params);
            pkm.setPadding(dp(10), 0, dp(10), 10);
            pkm.setBackgroundColor(Color.LTGRAY);
            pkm.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            pkm.setGravity(Gravity.CENTER_VERTICAL);
            pkm.setText("merkinta :"+numero+" / "+index+" - "+paivays+"\n"+merkinta);
            layoutpaivakirja.addView(pkm);
        }
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        int num2 = Math.round(num1);
        return num2;
    }
}