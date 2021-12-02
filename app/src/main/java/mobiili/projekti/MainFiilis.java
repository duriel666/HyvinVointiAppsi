package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainFiilis extends AppCompatActivity {

    Button tallenna;
    SeekBar fiilisAsteikko;
    TextView fiilisArvo;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fiilis);
        getSupportActionBar().hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);

        image = (ImageView)  findViewById(R.id.fiilisKuva);
        image.setImageResource(R.drawable.ok);
        fiilisAsteikko = (SeekBar) findViewById(R.id.fiilisAsteikko);
        fiilisArvo = (TextView) findViewById(R.id.fiilisArvo);
        fiilisArvo.setText("-  " + fiilisAsteikko.getProgress() + "  -");

        fiilisAsteikko.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int arvo = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                arvo = i;

                if (i < -33) {
                    image.setImageResource(R.drawable.sad);
                }
                if (i >= -33 & i <= 33) {
                    image.setImageResource(R.drawable.ok);
                }
                if (i > 33) {
                    image.setImageResource(R.drawable.happy);
                }
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
}