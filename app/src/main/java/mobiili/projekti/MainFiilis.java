package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainFiilis extends AppCompatActivity {

    Button tallenna;
    SeekBar fiilisAsteikko;
    TextView fiilisArvo;
    ImageView image;
    ImageButton takaisin, koti, asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fiilis);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> fiilisToday = DB.getFiilisToday(tunnus);

        image = findViewById(R.id.fiilisKuva);
        image.setImageResource(R.drawable.ok);
        fiilisAsteikko = findViewById(R.id.fiilisAsteikko);
        fiilisArvo = findViewById(R.id.fiilisArvo);
        fiilisArvo.setText("-  " + fiilisAsteikko.getProgress() + "  -");

        fiilisToday.clear();
        fiilisToday.addAll(DB.getFiilisToday(tunnus));
        int index2 = fiilisToday.size(), num2 = 0, num3 = 0;
        for (int i = 0; i < index2; i++) {
            int num1 = Integer.parseInt(fiilisToday.get(i));
            num2 += num1;
        }
        if (index2 > 0) {
            num3 = num2 / index2;
        }
        if (index2 > 0) {
            String fiilisNyt = fiilisToday.get(index2 - 1);
            int kohta = Integer.parseInt(fiilisNyt);
            fiilisAsteikko.setProgress(kohta);
            fiilisArvo.setText("-  " + fiilisAsteikko.getProgress() + "  -");
            naama(kohta);
        } else {
            fiilisAsteikko.setProgress(0);
            fiilisArvo.setText("-  " + fiilisAsteikko.getProgress() + "  -");
        }

        TextView fiilisTanaan = findViewById(R.id.fiilisTanaan);
        String fiilisT = Integer.toString(num3);
        if (index2 > 0) {
            fiilisTanaan.setText("Päivän kokonaisfiilis: " + fiilisT);
        } else {
            fiilisTanaan.setText("Päivän kokonaisfiilis: -");
        }
        fiilisAsteikko.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int arvo = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                arvo = i;
                naama(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                fiilisArvo.setText("-  " + arvo + "  -");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                fiilisArvo.setText("-  " + arvo + "  -");
            }
        });

        ProgressBar fiilisEilen = findViewById(R.id.fiilisEilen);
        ProgressBar fiilisTp = findViewById(R.id.fiilisTp);
        ArrayList<String> historia = DB.getFiilisHistoria(tunnus);
        int index = historia.size();
        if (index > 0) {
            fiilisEilen.setProgress(Integer.parseInt(historia.get(0)) + 100);
            if (Integer.parseInt(historia.get(0)) < 100) {
                fiilisEilen.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            }
            if (Integer.parseInt(historia.get(0)) > 100) {
                fiilisEilen.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            }
            if (index > 1) {
                fiilisTp.setProgress(Integer.parseInt(historia.get(1)) + 100);
                if (Integer.parseInt(historia.get(1)) < 100) {
                    fiilisTp.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                }
                if (Integer.parseInt(historia.get(1)) > 100) {
                    fiilisTp.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
                }
            } else {
                fiilisTp.setProgress(0);
            }
        } else {
            fiilisEilen.setProgress(0);
        }

        tallenna = findViewById(R.id.tallennaFiilis);
        tallenna.setOnClickListener(v ->
        {
            DB.addFiilis(tunnus, fiilisAsteikko.getProgress());
            Toast.makeText(this, "Fiilis tallennettu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainFiilis.class);
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

    public void naama(int kohta) {
        if (kohta < -33) {
            image.setImageResource(R.drawable.sad);
        }
        if (kohta >= -33 & kohta <= 33) {
            image.setImageResource(R.drawable.ok);
        }
        if (kohta > 33) {
            image.setImageResource(R.drawable.happy);
        }
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}