package com.example.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculadora.databinding.SecondActivityBinding;

public class OptionsActivity extends AppCompatActivity {

    SecondActivityBinding activityOptionsBinding;
    public static String RETORNO = "RETORNO";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOptionsBinding = SecondActivityBinding.inflate(getLayoutInflater());
        setContentView(activityOptionsBinding.getRoot());
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        String selectedOption = activityOptionsBinding.calcOptionsRg.getCheckedRadioButtonId() == R.id.basicCalcRb
                ? "BASIC" : "ADVANCED";
        intent.putExtra(RETORNO, selectedOption);
        setResult(RESULT_OK, intent);

        finish();
    }
}
