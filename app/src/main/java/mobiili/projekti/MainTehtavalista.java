package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

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
import android.widget.Toast;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainTehtavalista extends AppCompatActivity {

    ImageButton takaisin, koti, asetukset;
    int tnum = 0, numero = 0, index2 = 0, numero2 = 0, tehty = 0, muokkaa = 0;
    List boxilista = new ArrayList();
    Button tallenna;
    EditText tehtava;
    String tehtavam, aika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tehtavalista);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> tehtavalista = DB.getTehtavalista(tunnus);

        LinearLayout layouttl = findViewById(R.id.tehtavaLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = dp(10);

        tehtavalista.clear();
        tehtavalista.addAll(DB.getTehtavalista(tunnus));
        int index = tehtavalista.size();

        tehtava = findViewById(R.id.tehtavalistamerkinta);

        tnum = index - 4;
        numero = 1;
        index2 = (index + 1) / 4;
        final CheckBox[] p = new CheckBox[index];
        if (index > 0) {
            int laskuri = 1;
            for (int i = 0; i < index2; i++) {
                numero2 = Integer.parseInt(tehtavalista.get(tnum + 1));
                p[numero] = new CheckBox(this);
                p[numero].setText("Tehtävä:  " + laskuri + " / " + index2 + "\n" + tehtavalista.get(tnum + 2));
                p[numero].setLayoutParams(params);
                p[numero].setPadding(dp(10), 0, dp(10), 10);
                p[numero].setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
                p[numero].setGravity(Gravity.CENTER_VERTICAL);
                layouttl.addView(p[numero]);
                boxilista.add(numero2);
                if (Integer.parseInt(tehtavalista.get(tnum + 3)) == 1) {
                    p[numero].setBackgroundColor(Color.DKGRAY);
                    p[numero].setChecked(true);
                } else {
                    p[numero].setBackgroundColor(Color.LTGRAY);
                    p[numero].setChecked(false);
                }
                tnum -= 4;
                numero += 1;
                laskuri += 1;
            }
        }

        tallenna = findViewById(R.id.tallennaTehtava);
        numero = 1;
        tallenna.setOnClickListener(v -> {
            for (int i = 0; i < index2; i++) {
                if (p[numero].isChecked()) {
                    muokkaa = (int) boxilista.get(numero - 1);
                    tehty = 1;
                    DB.muokkaaTehtavalista(tunnus, muokkaa, tehty);
                } else {
                    muokkaa = (int) boxilista.get(numero - 1);
                    tehty = 0;
                    DB.muokkaaTehtavalista(tunnus, muokkaa, tehty);
                }
                numero += 1;
            }
            if (index2 > 0) {
                numero = Integer.parseInt(tehtavalista.get(index - 3)) + 1;
            } else {
                numero = 1;
            }
            if (tehtava.getText().toString().equals("")) {
                Toast.makeText(this, "Tallennettu", Toast.LENGTH_SHORT).show();
            } else {
                DB.addTehtavalista(tunnus, numero, tehtava.getText().toString(), tehty);
            }
            Intent intent = new Intent(this, MainTehtavalista.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            Toast.makeText(this, "Tehtävä lisätty ja tallennettu", Toast.LENGTH_SHORT).show();
            overridePendingTransition(
                    R.anim.f_in, R.anim.f_out
            );
        });

        takaisin = findViewById(R.id.takaisin);
        takaisin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.l_in, R.anim.r_out
            );
        });

        koti = findViewById(R.id.koti);
        koti.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.d_in, R.anim.u_out
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