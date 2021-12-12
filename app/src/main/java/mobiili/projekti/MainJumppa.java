package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import java.util.Objects;

public class MainJumppa extends AppCompatActivity {

    ImageButton takaisin,koti,asetukset;
    WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jumppa);

        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        String path1 = "<iframe width=\"320\" height=\"250\" src=\"https://www.youtube.com/embed/r3GGSfaKMJ8\"" +
                " title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write;" +
                " encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";

        //koko keho venyttely 30min https://www.youtube.com/watch?v=lJUIDBBqJOE
        //koko keho workout 20 min https://www.youtube.com/watch?v=vuZU4SSsGkE
        // HIIT 10 min https://www.youtube.com/watch?v=uHx6nbdSlAg
        //jalkareeni 12min https://www.youtube.com/watch?v=Fu_oExrPX68
        //Good morning reeni 10 min https://www.youtube.com/watch?v=diPRDW6CxWM
        // abs & yoga 10min https://www.youtube.com/watch?v=6En0LPb4Hfw


        webView1 = findViewById(R.id.jumppavideo1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.setWebViewClient(new WebViewClient());
        webView1.loadData(path1, "text/html", "utf-8");



        takaisin = findViewById(R.id.takaisin);
        takaisin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
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
