package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainFiilis extends AppCompatActivity {

    Button tallenna;
    SeekBar fiilisAsteikko;
    TextView fiilisArvo;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fiilis);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> fiilisToday = DB.getFiilisToday(tunnus);

        image = (ImageView) findViewById(R.id.fiilisKuva);
        image.setImageResource(R.drawable.ok);
        fiilisAsteikko = (SeekBar) findViewById(R.id.fiilisAsteikko);
        fiilisArvo = (TextView) findViewById(R.id.fiilisArvo);
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
        String fiilisT = Integer.toString(num3);
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

        tallenna = (Button) findViewById(R.id.tallennaFiilis);
        tallenna.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainFiilis.this, MainPage.class);
            DB.addFiilis(tunnus, fiilisAsteikko.getProgress());
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
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
        int num2 = Math.round(num1);
        return num2;
    }
}