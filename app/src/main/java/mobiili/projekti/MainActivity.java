package mobiili.projekti;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText tunnus, salasana;
    Button kirjaudu, reg;
    DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tunnus = findViewById(R.id.tunnusEdit);
        salasana = findViewById(R.id.salasanaEdit);
        DB = new DataBase(this);

        kirjaudu = findViewById(R.id.buttonKirjaudu);
        kirjaudu.setOnClickListener(v -> {
            String t = tunnus.getText().toString();
            String s = salasana.getText().toString();

            if (t.equals("") || s.equals(""))
                Toast.makeText(this, "Kirjoita tunnus ja salasana", Toast.LENGTH_SHORT).show();
            else {
                Boolean checksalasana = DB.checksalasana(t, s);
                if (checksalasana) {
                    Toast.makeText(this, "Kirjautuminen onnistui", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainPage.class);
                    intent.putExtra("tunnus", t);
                    startActivity(intent);
                    overridePendingTransition(
                            R.anim.r_in, R.anim.l_out
                    );
                } else {
                    Toast.makeText(this, "Väärä tunnus tai salasana", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg = findViewById(R.id.buttonRek);
        reg.setOnClickListener(v -> startActivity(new Intent(this, regPage.class)));
        overridePendingTransition(
                R.anim.l_in, R.anim.r_out
        );
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}