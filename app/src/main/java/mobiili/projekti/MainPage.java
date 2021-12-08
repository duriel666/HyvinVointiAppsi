package mobiili.projekti;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainPage extends AppCompatActivity {

    SwipeRefreshLayout swipeRefresh;
    TextView inspiroivaQuote;
    ImageButton asetukset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> quotelista = DB.randomquotelista();
        final ArrayList<String> vesiToday = DB.getVesiToday(tunnus);
        final ArrayList<String> fiilisToday = DB.getFiilisToday(tunnus);
        final ArrayList<String> sivuAsetukset = DB.getAsetukset(tunnus);
        final ArrayList<String> vesiMuisti = DB.getVesiMuisti(tunnus);

        LinearLayout etuSivu = (LinearLayout) findViewById(R.id.etuLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(200));
        params.topMargin = dp(10);

        inspiroivaQuote = findViewById(R.id.otsikkoPaasivu);

        sivuAsetukset.clear();
        sivuAsetukset.addAll(DB.getAsetukset(tunnus));
        int vesiAsetus = Integer.parseInt(sivuAsetukset.get(0)), uniAsetus = Integer.parseInt(sivuAsetukset.get(1)),
                fiilisAsetus = Integer.parseInt(sivuAsetukset.get(2)), tehtavaAsetus = Integer.parseInt(sivuAsetukset.get(3)),
                paivakirjaAsetus = Integer.parseInt(sivuAsetukset.get(4));

        if (vesiAsetus == 1) {
            TextView vesi = new TextView(this);
            vesi.setLayoutParams(params);
            vesi.setPadding(dp(10), 0, dp(10), 0);
            vesi.setBackgroundColor(Color.LTGRAY);
            vesi.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            vesi.setGravity(Gravity.CENTER_VERTICAL);
            etuSivu.addView(vesi);

            vesiToday.clear();
            vesiToday.addAll(DB.getVesiToday(tunnus));
            int index2 = vesiToday.size(), num2 = 0, num3 = 0;
            for (int i = 0; i < index2; i++) {
                int num1 = Integer.parseInt(vesiToday.get(i));
                num2 += num1;
            }

            vesiMuisti.clear();
            vesiMuisti.addAll(DB.getVesiMuisti(tunnus));
            int vesitavoitemaaranum = Integer.parseInt(vesiMuisti.get(0));

            if (vesitavoitemaaranum > 0 && num2 > 0) {
                num3 = ((num2 * 100) / vesitavoitemaaranum);
            }
            vesi.setText("Vesi\n\nValmiina päivän tavoitteesta: " + num3 + "%\n" +
                    "Tänään juotu: " + num2 + "ml / " + vesitavoitemaaranum + "ml");

            vesi.setOnClickListener(v -> {
                Intent intent = new Intent(MainPage.this, MainVesi.class);
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            });
        }

        if (uniAsetus == 1) {
            TextView uni = new TextView(this);
            uni.setLayoutParams(params);
            uni.setPadding(dp(10), 0, dp(10), 0);
            uni.setBackgroundColor(Color.LTGRAY);
            uni.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            uni.setGravity(Gravity.CENTER_VERTICAL);
            etuSivu.addView(uni);

            uni.setText("Uni\n\nValmiina päivän tavoitteesta: \nTänään nukuttu: ");

            uni.setOnClickListener(v -> {
                Intent intent = new Intent(MainPage.this, MainUni.class);
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            });
        }

        if (fiilisAsetus == 1) {
            TextView fiilis = new TextView(this);
            fiilis.setLayoutParams(params);
            fiilis.setPadding(dp(10), 0, dp(10), 0);
            fiilis.setBackgroundColor(Color.LTGRAY);
            fiilis.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            fiilis.setGravity(Gravity.CENTER_VERTICAL);
            etuSivu.addView(fiilis);

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
                fiilis.setText("Fiilis\n\nFiilis nyt: " + fiilisNyt + "\nPäivän fiilis: " + fiilisT);
            } else
                fiilis.setText("Fiilis\n\nFiilis nyt: \nPäivän fiilis: ");

            fiilis.setOnClickListener(v -> {
                Intent intent = new Intent(MainPage.this, MainFiilis.class);
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            });
        }

        if (tehtavaAsetus == 1) {
            TextView tehtava = new TextView(this);
            tehtava.setLayoutParams(params);
            tehtava.setPadding(dp(10), 0, dp(10), 0);
            tehtava.setBackgroundColor(Color.LTGRAY);
            tehtava.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            tehtava.setGravity(Gravity.CENTER_VERTICAL);
            etuSivu.addView(tehtava);

            tehtava.setText("Tehtävälista");

            tehtava.setOnClickListener(v -> {
                Intent intent = new Intent(MainPage.this, MainTehtavalista.class);
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            });
        }

        if (paivakirjaAsetus == 1) {
            TextView paivakirja = new TextView(this);
            paivakirja.setLayoutParams(params);
            paivakirja.setPadding(dp(10), 0, dp(10), 0);
            paivakirja.setBackgroundColor(Color.LTGRAY);
            paivakirja.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            paivakirja.setGravity(Gravity.CENTER_VERTICAL);
            etuSivu.addView(paivakirja);

            paivakirja.setText("Päiväkirja");

            paivakirja.setOnClickListener(v -> {
                Intent intent = new Intent(MainPage.this, MainPaivakirja.class);
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            });
        }

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

        asetukset = (ImageButton) findViewById(R.id.asetukset);

        asetukset.setOnClickListener(v -> {
            Intent intent = new Intent(MainPage.this, MainAsetukset.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        int num2 = Math.round(num1);
        return num2;
    }
}