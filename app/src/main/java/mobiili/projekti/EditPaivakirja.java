package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditPaivakirja extends AppCompatActivity {

    Button poista, peruuta;
    ImageButton takaisin, koti, asetukset;
    int numero = 0, numero2 = 0, index = 0, index2 = 0, pknum = 0, poisto = 0;
    List boxilista = new ArrayList();
    TextView testi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paivakirja);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> paivakirja = DB.getPaivakirja(tunnus);

        LinearLayout layoutEP = findViewById(R.id.editPaivakirjaLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(10);

        paivakirja.clear();
        paivakirja.addAll(DB.getPaivakirja(tunnus));
        index = paivakirja.size();

        pknum = index - 3;
        numero = 1;
        index2 = (index + 1) / 3;
        final CheckBox[] p = new CheckBox[index];
        if (index > 0) {
            int laskuri = 1;
            for (int i = 0; i < index2; i++) {
                numero2 = Integer.parseInt(paivakirja.get(pknum + 1));
                p[numero] = new CheckBox(this);
                p[numero].setText("Merkintä:  " + laskuri + " / " + index2 + "\n"
                        + paivakirja.get(pknum) + "\n\n" + paivakirja.get(pknum + 2));
                p[numero].setLayoutParams(params);
                p[numero].setPadding(dp(10), 0, dp(10), 10);
                p[numero].setBackgroundColor(Color.LTGRAY);
                p[numero].setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
                p[numero].setGravity(Gravity.CENTER_VERTICAL);
                layoutEP.addView(p[numero]);
                boxilista.add(numero2);
                pknum -= 3;
                numero += 1;
                laskuri += 1;
            }
        }

        testi = findViewById(R.id.otsikkoEditPaivakirja);
        poista = findViewById(R.id.poistaPaivakirja);
        numero = 1;
        poista.setOnClickListener(v -> {
            for (int i = 0; i < index2; i++) {
                if (p[numero].isChecked()) {
                    poisto = (int) boxilista.get(numero - 1);
                    DB.deletePaivakirja(tunnus, poisto);
                    Intent intent = new Intent(this, EditPaivakirja.class);
                    intent.putExtra("tunnus", tunnus);
                    startActivity(intent);
                }
                numero += 1;
            }
        });

        peruuta = findViewById(R.id.peruuta1);
        peruuta.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPaivakirja.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        takaisin = findViewById(R.id.takaisin);
        takaisin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPaivakirja.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        koti = findViewById(R.id.koti);
        koti.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        asetukset = findViewById(R.id.asetukset);
        asetukset.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainAsetukset.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });
    }

    /*public boolean isSelected() { //TODO jotaki
        return selected.isSelected();
    }*/

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}