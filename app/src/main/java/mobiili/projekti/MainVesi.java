
package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainVesi extends AppCompatActivity {

    Button tallenna;
    EditText juo, vesimaara, vesitavoitemaara;
    DataBase DB;
    TextView juotu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vesi);
        getSupportActionBar().hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        final DataBase DB = new DataBase(this);
        final ArrayList<String> vesiToday = DB.getVesiToday(tunnus);
        final ArrayList<String> vesiMuisti = DB.getVesiMuisti(tunnus);

        vesiMuisti.clear();
        vesiMuisti.addAll(DB.getVesiMuisti(tunnus));
        int vesitavoitemaaranum = Integer.parseInt(vesiMuisti.get(0)), vesimaaranum = Integer.parseInt(vesiMuisti.get(1));

        vesimaara = findViewById(R.id.vesiMaara);
        vesimaara.setText(Integer.toString(vesimaaranum));
        vesitavoitemaara = findViewById(R.id.vesiTavoiteMaara);
        vesitavoitemaara.setText(Integer.toString(vesitavoitemaaranum));

        vesiToday.clear();
        vesiToday.addAll(DB.getVesiToday(tunnus));
        int index2 = vesiToday.size();
        int num2 = 0;
        for (int i = 0; i < index2; i++) {
            int num1 = Integer.parseInt(vesiToday.get(i));
            num2 += num1;
        }
        juotu = findViewById(R.id.vesiJuotu);
        String num3 = Integer.toString(num2);
        juotu.setText(num3);

        tallenna = findViewById(R.id.tallennaVesi);
        juo = findViewById(R.id.vesiMaara);
        tallenna.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainVesi.this, MainPage.class);
            int juo2 = Integer.parseInt(juo.getText().toString());
            int maara = Integer.parseInt(vesimaara.getText().toString());
            int tavoite = Integer.parseInt(vesitavoitemaara.getText().toString());
            DB.addVesi(tunnus, juo2);
            DB.setVesiMuisti(tunnus, tavoite, maara);
            intent.putExtra("tunnus", tunnus);
            startActivity(intent);
        });
    }
}