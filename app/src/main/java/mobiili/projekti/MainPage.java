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
    ImageButton takaisin, koti, asetukset;
    int h1 = 0, minuutittavoite = 0, minuutitnukuttu = 0, min1 = 0;

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
        final ArrayList<String> uniData = DB.getUni(tunnus);
        final ArrayList<String> uniMuisti = DB.getUniMuisti(tunnus);

        LinearLayout etuSivu = findViewById(R.id.etuLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(200));
        params.bottomMargin = dp(10);

        inspiroivaQuote = findViewById(R.id.otsikkoPaasivu);

        sivuAsetukset.clear();
        sivuAsetukset.addAll(DB.getAsetukset(tunnus));
        int vesiAsetus = Integer.parseInt(sivuAsetukset.get(0)), uniAsetus = Integer.parseInt(sivuAsetukset.get(1)),
                fiilisAsetus = Integer.parseInt(sivuAsetukset.get(2)), tehtavaAsetus = Integer.parseInt(sivuAsetukset.get(3)),
                paivakirjaAsetus = Integer.parseInt(sivuAsetukset.get(4)), uusi1Asetus = Integer.parseInt(sivuAsetukset.get(5)),
                uusi2Asetus = Integer.parseInt(sivuAsetukset.get(6));

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

            uniData.clear();
            uniData.addAll(DB.getUni(tunnus));
            uniMuisti.clear();
            uniMuisti.addAll((DB.getUniMuisti(tunnus)));
            int unitavoiteh = Integer.parseInt(uniMuisti.get(0)), unitavoitemin = Integer.parseInt(uniMuisti.get(1));
            int indexu = uniData.size(); //TODO nukuttuh ja nukuttumin jotaki käyttöä tänne?
            int indexu2 = indexu / 2;
            h1 = 0;
            min1 = 0;
            for (int i = 0; i < indexu2; i++) {
                int num1 = Integer.parseInt(uniData.get(i));
                h1 += num1;
                int num2 = Integer.parseInt((uniData.get(i + 1)));
                min1 += num2;
                i += 1;
            }
            minuutitnukuttu = (h1 * 60) + min1;
            minuutittavoite = (unitavoiteh * 60) + unitavoitemin;
            if (minuutitnukuttu <= 0 || minuutittavoite <= 0) {
                uni.setText("Uni\n\nValmiina päivän tavoitteesta: - %\nTänään nukuttu: 0 h 0 min");
            } else {
                uni.setText("Uni\n\nValmiina päivän tavoitteesta: " + minuutitnukuttu * 100 / minuutittavoite + " %" +
                        "\nTänään nukuttu: " + minuutitnukuttu + " min");
            }

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
            } else {
                fiilis.setText("Fiilis\n\nFiilis nyt: ?\nPäivän fiilis: ?");
            }

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
        if (uusi1Asetus == 1) {
            TextView uusi1 = new TextView(this);
            uusi1.setLayoutParams(params);
            uusi1.setPadding(dp(10), 0, dp(10), 0);
            uusi1.setBackgroundColor(Color.LTGRAY);
            uusi1.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            uusi1.setGravity(Gravity.CENTER_VERTICAL);
            etuSivu.addView(uusi1);

            uusi1.setText("Jumppa");
            uusi1.setOnClickListener(v -> {
                Intent intent = new Intent(MainPage.this, MainJumppa.class);
                intent.putExtra("tunnus", tunnus);
                startActivity(intent);
            });
        }
        if (uusi2Asetus == 1) {
            TextView uusi2 = new TextView(this);
            uusi2.setLayoutParams(params);
            uusi2.setPadding(dp(10), 0, dp(10), 0);
            uusi2.setBackgroundColor(Color.LTGRAY);
            uusi2.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            uusi2.setGravity(Gravity.CENTER_VERTICAL);
            etuSivu.addView(uusi2);

            uusi2.setText("Uusi2"); //TODO tuohon tekstiä ja alla olevat intent ja start pois kommenteista
            //TODO MainUusix.class tilalle .java uudesta
            uusi2.setOnClickListener(v -> {
                //TODO Intent intent = new Intent(MainPage.this, MainUusi2.class);
                //TODO intent.putExtra("tunnus", tunnus);
                //TODO startActivity(intent);
            });
        }

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(() -> {
            quotelista.clear();
            quotelista.addAll(DB.randomquotelista());
            Random random = new Random();
            int index = random.nextInt(quotelista.size());
            String rQuote = quotelista.get(index);
            inspiroivaQuote.setText(rQuote);
            swipeRefresh.setRefreshing(false);
        });

        takaisin = findViewById(R.id.takaisin);
        takaisin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        koti = findViewById(R.id.koti);
        koti.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });

        asetukset = findViewById(R.id.asetukset);
        asetukset.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainAsetukset.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}