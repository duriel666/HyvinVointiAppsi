package mobiili.projekti;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainUni extends AppCompatActivity {

    Button tallenna;
    EditText nukuttuH, nukuttuMin, tavoiteH, tavoiteMin;
    TextView nukuttuToday;
    int unitavoiteh = 8, unitavoitemin = 0, nukuttuH2 = 0, nukuttuMin2 = 0, tavoiteH2 = 0,
            tavoiteMin2 = 0, min1 = 0, h1 = 0, num1 = 0, num2 = 0;
    int minuutitnukuttu = 0, minuutittavoite = 0;
    ImageButton takaisin, koti, asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_uni);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> uni = DB.getUni(tunnus);
        final ArrayList<String> uniMuisti = DB.getUniMuisti(tunnus);

        uniMuisti.clear();
        uniMuisti.addAll(DB.getUniMuisti(tunnus));
        unitavoiteh = Integer.parseInt(uniMuisti.get(0));
        unitavoitemin = Integer.parseInt(uniMuisti.get(1));
        int nukuttuh = Integer.parseInt(uniMuisti.get(2)), nukuttumin = Integer.parseInt(uniMuisti.get(3));

        tallenna = findViewById(R.id.tallennaUni);
        nukuttuH = findViewById(R.id.uniAikaH);
        nukuttuH.setText(Integer.toString(nukuttuh));
        nukuttuMin = findViewById(R.id.uniAikaMin);
        nukuttuMin.setText(Integer.toString(nukuttumin));
        tavoiteH = findViewById(R.id.uniAikaHb);
        tavoiteH.setText(Integer.toString(unitavoiteh));
        tavoiteMin = findViewById(R.id.uniAikaMinb);
        tavoiteMin.setText(Integer.toString(unitavoitemin));

        uni.clear();
        uni.addAll(DB.getUni(tunnus));
        int index = uni.size();
        int index2 = index / 2;
        h1 = 0;
        for (int i = 0; i < index2; i++) {
            num1 = Integer.parseInt(uni.get(i));
            h1 += num1;
            num2 = Integer.parseInt((uni.get(i + 1)));
            min1 += num2;
            i += 1;
        }
        minuutitnukuttu = (h1 * 60) + min1;
        minuutittavoite = (unitavoiteh * 60) + unitavoitemin;
        nukuttuToday = findViewById(R.id.uniNukuttu);
        if (minuutitnukuttu <= 0 || minuutittavoite <= 0) {
            nukuttuToday.setText("Päivän tavoitteesta nukuttu: 0 %");
        } else {
            int prosentti = minuutitnukuttu * 100 / minuutittavoite;
            nukuttuToday.setText("Päivän tavoitteesta nukuttu: " + prosentti + " %");
            ProgressBar pb = findViewById(R.id.uniProgress);
            pb.setProgress(prosentti);
        }
        tallenna.setOnClickListener(v ->
        {
            if (nukuttuH == null) {
                nukuttuH2 = 0;
            } else {
                nukuttuH2 = Integer.parseInt(nukuttuH.getText().toString());
            }
            if (nukuttuMin == null) {
                nukuttuMin2 = 0;
            } else {
                nukuttuMin2 = Integer.parseInt(nukuttuMin.getText().toString());
            }
            if (tavoiteH == null) {
                tavoiteH2 = 0;
            } else {
                tavoiteH2 = Integer.parseInt(tavoiteH.getText().toString());
            }
            if (tavoiteMin == null) {
                tavoiteMin2 = 0;
            } else {
                tavoiteMin2 = Integer.parseInt(tavoiteMin.getText().toString());
            }

            DB.addUni(tunnus, nukuttuH2, nukuttuMin2);
            DB.setUniMuisti(tunnus, tavoiteH2, tavoiteMin2, nukuttuH2, nukuttuMin2);
            Intent intent = new Intent(this, MainUni.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            Toast.makeText(this, "Tallennettu", Toast.LENGTH_SHORT).show();
            overridePendingTransition(
                    R.anim.f_in, R.anim.f_out
            );
        }); //TODO tarkista ettei ole nukuttu yli 24tuntia

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