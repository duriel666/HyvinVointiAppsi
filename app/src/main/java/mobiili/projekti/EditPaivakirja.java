package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class EditPaivakirja extends AppCompatActivity {

    Button tallenna, peruuta;
    EditText merkinta;
    int numero = 0;
    ImageButton takaisin, koti, asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paivakirja);
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
        params.bottomMargin = dp(10);

        paivakirja.clear();
        paivakirja.addAll(DB.getPaivakirja(tunnus));
        int index = paivakirja.size(), num2 = 0, num3 = 0;

        final CheckBox[] p = new CheckBox[index];
        if (index > 0) {
            int pknum = index - 3;
            int index2 = index / 3;
            numero = 1;
            for (int i = 1; i <= index2; i++) {
                p[numero] = new CheckBox(this);
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
            Intent intent = new Intent(mobiili.projekti.EditPaivakirja.this, MainPage.class);
            int index2 = paivakirja.size();
            if (index2 > 0) {
                numero = Integer.parseInt(paivakirja.get(index - 2)) + 1;
            } else {
                numero = 1;
            }
            if (merkinta.getText().toString().equals("")) {
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            } else {
                DB.addPaivakirja(tunnus, numero, merkinta.getText().toString());
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            }
        });

        peruuta = (Button) findViewById(R.id.peruuta1);
        peruuta.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditPaivakirja.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        takaisin = (ImageButton) findViewById(R.id.takaisin);
        takaisin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPaivakirja.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        koti = (ImageButton) findViewById(R.id.koti);
        koti.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        asetukset = (ImageButton) findViewById(R.id.asetukset);
        asetukset.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainAsetukset.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}