package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainPage extends AppCompatActivity {

    SwipeRefreshLayout swipeRefresh;
    TextView inspiroivaQuote,fiilis,uni,vesi;
    ImageButton asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> quotelista = DB.randomquotelista();
        final ArrayList<String> fiilisToday = DB.getFiilisToday(tunnus);

        LinearLayout etuSivu = (LinearLayout) findViewById(R.id.etuLayout);
        LinearLayout a = new LinearLayout(this);
        a.setOrientation(LinearLayout.VERTICAL);

        inspiroivaQuote = findViewById(R.id.otsikkoPaasivu);
        fiilis = findViewById(R.id.view2);
        vesi = findViewById(R.id.view1);

        fiilisToday.clear();
        fiilisToday.addAll(DB.getFiilisToday(tunnus));
        int index2 = fiilisToday.size();
        int num2 = 0;
        int num3 = 0;
        for (int i = 0; i < index2; i++) {
            int num1 = Integer.parseInt(fiilisToday.get(i));
            num2 += num1;
        }
        if (index2 > 0) {
            num3 = num2 / index2;
        }
        String fiilisT = Integer.toString(num3);
        fiilis.setText(fiilisT);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                quotelista.clear();
                quotelista.addAll(DB.randomquotelista());
                Random random = new Random();
                int index = random.nextInt(quotelista.size());
                String rQuote = quotelista.get(index);
                inspiroivaQuote.setText(rQuote);
                swipeRefresh.setRefreshing(false);
            }
        });

        /*
        TextView vesi = new TextView(this);
        TextView uni = new TextView(this);
        TextView joku = new TextView(this);
        TextView joku2 = new TextView(this);

        a.addView(vesi);
        a.addView(uni);
        a.addView(joku);
        a.addView(joku2);
        etuSivu.addView(a);*/

        asetukset = (ImageButton) findViewById(R.id.asetukset);

        asetukset.setOnClickListener(v -> {
            Intent intent = new Intent(MainPage.this, SettingsActivity.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        fiilis.setOnClickListener(v -> {
            Intent intent = new Intent(MainPage.this, MainFiilis.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        vesi.setOnClickListener(v -> {
            Intent intent = new Intent(MainPage.this, MainVesi.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });
    }
}