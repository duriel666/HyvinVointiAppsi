package mobiili.projekti;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;

public class MainAsetukset extends AppCompatActivity {

    Button tallenna, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_asetukset);
        getSupportActionBar().hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> asetukset = DB.getAsetukset(tunnus);

        asetukset.clear();
        asetukset.addAll(DB.getAsetukset(tunnus));
        int vesi = Integer.parseInt(asetukset.get(0)), uni = Integer.parseInt(asetukset.get(1)),
                fiilis = Integer.parseInt(asetukset.get(2));

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch1 = (Switch) findViewById(R.id.switch1);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch2 = (Switch) findViewById(R.id.switch2);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch3 = (Switch) findViewById(R.id.switch3);

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

        tallenna = (Button) findViewById(R.id.tallenna1);

        tallenna.setOnClickListener(v -> {
            Intent intent = new Intent(MainAsetukset.this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            int s1, s2, s3;
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
            DB.setAsetukset(tunnus, s1, s2, s3);
            startActivity(intent);
        });

        logout = (Button) findViewById(R.id.tallenna2);
        logout.setOnClickListener(v -> {
            startActivity(new Intent(MainAsetukset.this, MainActivity.class));
        });

    }
}