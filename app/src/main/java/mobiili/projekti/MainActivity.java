package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                Toast.makeText(MainActivity.this, "Kirjoita tunnus ja salasana", Toast.LENGTH_SHORT).show();
            else {
                Boolean checksalasana = DB.checksalasana(t, s);
                if (checksalasana) {
                    Toast.makeText(MainActivity.this, "Kirjautuminen onnistui", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    intent.putExtra("tunnus", t);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Väärä tunnus tai salasana", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg = findViewById(R.id.buttonRek);
        reg.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, regPage.class)));
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}