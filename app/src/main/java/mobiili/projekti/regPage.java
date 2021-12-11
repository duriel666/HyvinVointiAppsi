package mobiili.projekti;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class regPage extends AppCompatActivity {

    EditText tunnus, salasana1, salasana2;
    Button reg;
    DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tunnus = findViewById(R.id.tunnusreg);
        salasana1 = findViewById(R.id.salasanareg1);
        salasana2 = findViewById(R.id.salasanareg2);
        DB = new DataBase(this);

        reg = findViewById(R.id.buttonreg);
        reg.setOnClickListener(view -> {
            String t = tunnus.getText().toString();
            String s1 = salasana1.getText().toString();
            String s2 = salasana2.getText().toString();

            if (t.equals("") || s1.equals("") || s2.equals(""))
                Toast.makeText(regPage.this, "Täytä kaikki kohdat", Toast.LENGTH_SHORT).show();
            else if (s1.equals(s2)) {
                Boolean checktunnus = DB.checktunnus(t);
                if (!checktunnus) {
                    Boolean insert = DB.insertData(t, s1);
                    if (insert) {
                        Toast.makeText(regPage.this, "Tunnus luotu", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(regPage.this, "Rekisteröityminen epäonnistui", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(regPage.this, "Käyttäjätunnus varattu", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(regPage.this, "Salasanat eivät täsmää", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return Math.round(num1);
    }
}