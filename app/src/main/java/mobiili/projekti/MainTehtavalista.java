package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainTehtavalista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tehtavalista);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> tehtavalista = DB.getTehtavalista(tunnus);

        LinearLayout layouttehtavalista = (LinearLayout) findViewById(R.id.tehtavaLayout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = dp(10);

        tehtavalista.clear();
        tehtavalista.addAll(DB.getTehtavalista(tunnus));
        int index = tehtavalista.size();
        for (int i = 0; i < index; i++) {
            int numero = i;
            int index2 = Integer.parseInt(tehtavalista.get(i));
            i += 1;
            String tehtava = tehtavalista.get(i);
            TextView tl = new TextView(this); // tähän tarvitaan joku map? mihin toisena
            // tietona numero ja toisena merkintä ja checkbox perään
            //CheckBox
            tl.setLayoutParams(params);
            tl.setPadding(dp(10), 0, dp(10), 10);
            tl.setBackgroundColor(Color.LTGRAY);
            tl.setTextSize((TypedValue.COMPLEX_UNIT_SP), 20);
            tl.setGravity(Gravity.CENTER_VERTICAL);
            tl.setText("merkinta :"+numero+" / "+index+" - "+tehtava);
            layouttehtavalista.addView(tl);
        }

    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        int num2 = Math.round(num1);
        return num2;
    }
}