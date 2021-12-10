package mobiili.projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;

import java.util.Objects;

public class EmptyTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_template);
        //TODO seuraavat kolme riviä alhaalla oleva dp metodi omaan pohjaan
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent siirto = getIntent();
        String tunnus = siirto.getExtras().getString("tunnus");

        //TODO nämä koodit kopiodaan uuteen pohjaan
        //TODO omat koodit tänne
    }

    public int dp(float num) {
        float num1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        int num2 = Math.round(num1);
        return num2;
    }
}