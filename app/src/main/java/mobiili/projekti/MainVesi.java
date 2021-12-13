
package mobiili.projekti;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainVesi extends AppCompatActivity {

    Button tallenna;
    EditText juo, vesimaara, vesitavoitemaara;
    TextView juotu;
    int prosentti = 0;
    ImageButton takaisin, koti, asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vesi);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> vesiToday = DB.getVesiToday(tunnus);
        final ArrayList<String> vesiMuisti = DB.getVesiMuisti(tunnus);

        vesiMuisti.clear();
        vesiMuisti.addAll(DB.getVesiMuisti(tunnus));
        int vesitavoitemaaranum = Integer.parseInt(vesiMuisti.get(0)), vesimaaranum = Integer.parseInt(vesiMuisti.get(1));

        vesimaara = findViewById(R.id.vesiMaara);
        vesimaara.setText(Integer.toString(vesimaaranum));
        vesitavoitemaara = findViewById(R.id.vesiTavoiteMaara);
        vesitavoitemaara.setText(Integer.toString(vesitavoitemaaranum));

        vesiToday.clear();
        vesiToday.addAll(DB.getVesiToday(tunnus));
        juotu = findViewById(R.id.vesiJuotu);
        ProgressBar pb = findViewById(R.id.vesiProg);
        int index = vesiToday.size();
        if (index > 0) {
            int num2 = 0;
            for (int i = 0; i < index; i++) {
                int num1 = Integer.parseInt(vesiToday.get(i));
                num2 += num1;
            }

            prosentti = (100 * num2) / vesitavoitemaaranum;
            juotu.setText(num2 + " ml - " + prosentti + " %");
            pb.setProgress(prosentti);
        } else {
            juotu.setText("0 ml - 0 %");
        }
        if (prosentti < 50) {
            pb.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            pb.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (prosentti < 50) {
            pb.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            pb.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        tallenna = findViewById(R.id.tallennaVesi);
        juo = findViewById(R.id.vesiMaara);
        tallenna.setOnClickListener(v -> //TODO historia mistä voi poistaa?
        {
            int juo2 = Integer.parseInt(juo.getText().toString());
            int maara = Integer.parseInt(vesimaara.getText().toString());
            int tavoite = Integer.parseInt(vesitavoitemaara.getText().toString());
            DB.addVesi(tunnus, juo2);
            DB.setVesiMuisti(tunnus, tavoite, maara);
            Intent intent = new Intent(this, MainVesi.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
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