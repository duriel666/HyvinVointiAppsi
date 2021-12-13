package mobiili.projekti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainAsetukset extends AppCompatActivity {

    Button tallenna, logout;
    ImageButton takaisin, koti, asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_asetukset);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> asetukset = DB.getAsetukset(tunnus);

        asetukset.clear();
        asetukset.addAll(DB.getAsetukset(tunnus));
        int vesi = Integer.parseInt(asetukset.get(0)), uni = Integer.parseInt(asetukset.get(1)),
                fiilis = Integer.parseInt(asetukset.get(2)), tehtava = Integer.parseInt(asetukset.get(3)),
                paivakirja = Integer.parseInt(asetukset.get(4)), uusi1 = Integer.parseInt(asetukset.get(5)),
                uusi2 = Integer.parseInt(asetukset.get(6));

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch1 = findViewById(R.id.switch1);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch2 = findViewById(R.id.switch2);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch3 = findViewById(R.id.switch3);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch4 = findViewById(R.id.switch4);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch5 = findViewById(R.id.switch5);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch6 = findViewById(R.id.switch6);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch7 = findViewById(R.id.switch7);

        if (vesi == 1) {
            switch1.setChecked(true);
        }
        if (vesi != 1) {
            switch1.setChecked(false);
        }
        if (uni == 1) {
            switch2.setChecked(true);
        }
        if (uni != 1) {
            switch2.setChecked(false);
        }
        if (fiilis == 1) {
            switch3.setChecked(true);
        }
        if (fiilis != 1) {
            switch3.setChecked(false);
        }
        if (tehtava == 1) {
            switch4.setChecked(true);
        }
        if (tehtava != 1) {
            switch4.setChecked(false);
        }
        if (paivakirja == 1) {
            switch5.setChecked(true);
        }
        if (paivakirja != 1) {
            switch5.setChecked(false);
        }
        if (uusi1 == 1) {
            switch6.setChecked(true);
        }
        if (uusi1 != 1) {
            switch6.setChecked(false);
        }
        if (uusi2 == 1) {
            switch7.setChecked(true);
        }
        if (uusi2 != 1) {
            switch7.setChecked(false);
        }

        tallenna = findViewById(R.id.tallenna1);
        tallenna.setOnClickListener(v -> {
            int s1, s2, s3, s4, s5, s6, s7;
            if (switch1.isChecked()) {
                s1 = 1;
            } else {
                s1 = 0;
            }
            if (switch2.isChecked()) {
                s2 = 1;
            } else {
                s2 = 0;
            }
            if (switch3.isChecked()) {
                s3 = 1;
            } else {
                s3 = 0;
            }
            if (switch4.isChecked()) {
                s4 = 1;
            } else {
                s4 = 0;
            }
            if (switch5.isChecked()) {
                s5 = 1;
            } else {
                s5 = 0;
            }
            if (switch6.isChecked()) {
                s6 = 1;
            } else {
                s6 = 0;
            }
            if (switch7.isChecked()) {
                s7 = 1;
            } else {
                s7 = 0;
            }
            DB.setAsetukset(tunnus, s1, s2, s3, s4, s5, s6, s7);
            Intent intent = new Intent(this, MainAsetukset.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.f_in, R.anim.f_out
            );
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            int exit = 0;
            intent.putExtra("exit", exit);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.d_in, R.anim.u_out
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
                    R.anim.d_in, R.anim.u_out
            );
        });
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}