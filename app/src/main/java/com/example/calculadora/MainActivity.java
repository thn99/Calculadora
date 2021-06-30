package com.example.calculadora;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculadora.databinding.ActivityMainBinding;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    private StringBuilder expression;
    private TextView calcView;
    private String calcResult;
    private boolean restartFlag; // usava para indicar quando apagar o texto ao digitar

    // Constantes p/ manutenção da activity
    private final String intentAction = "RECEBER_RETORNAR_OPCAO";
    private final int OPTIONS_ACTIVITY_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        calcView = findViewById(R.id.calcView);
        expression = new StringBuilder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionsIt:
                Intent intent = new Intent(intentAction);
                startActivityForResult(intent, OPTIONS_ACTIVITY_REQUEST_CODE);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String calcOption = data.getStringExtra(OptionsActivity.RETORNO);
        if(requestCode == OPTIONS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            if(calcOption != null){
                if(calcOption.equals("ADVANCED")){
                    activityMainBinding.BTpercent.setVisibility(View.VISIBLE);
                    activityMainBinding.BTpow.setVisibility(View.VISIBLE);
                    activityMainBinding.BTsquare.setVisibility(View.VISIBLE);
                } else {
                    activityMainBinding.BTpercent.setVisibility(View.INVISIBLE);
                    activityMainBinding.BTpow.setVisibility(View.INVISIBLE);
                    activityMainBinding.BTsquare.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void onButtonClick(View view){
        try {
            Button button = (Button) view;
            String textButton = (String) button.getText();

            if (!textButton.matches("C|=")) { // se o botão não for limpar o texto
                if(calcView.getText().toString().matches("(.*\\d+)$|^$")) { // se o último botão == número
                    if(restartFlag == true) { // lógica para limpar a tela ao iniciar uma nova operação
                        calcView.setText(""); // limpa automaticamente
                        restartFlag = false;
                    }
                    calcView.setText(calcView.getText() + textButton);
                    expression.append(textButton);
                }
                else if (calcView.getText().toString().matches("(.*\\D+)$")) { // se o último botão !== numero
                    if(!textButton.matches("\\D")){ // se o botão clicado == numero
                        if(restartFlag == true) { // lógica para limpar a tela ao iniciar uma nova operação
                            calcView.setText(""); // limpa automaticamente
                            restartFlag = false;
                        }
                        calcView.setText(calcView.getText() + textButton);
                        expression.append(textButton);
                    }
                }

            } else {
                if(textButton.equals("C")){ // só esvazia a lógica
                    if (calcView.length() > 0) calcView.setText(""); // limpa o texto
                    if (expression.length() > 0) expression.setLength(0); // esvazia o stringbuilder
                } else { // faz as contas e esvazia a lógica (=)
                    if (calcView.length() > 0 && expression.length() > 0){
                        Expression mathExpression = new Expression(expression.toString()); // cria a expressão
                        double calculo = mathExpression.calculate(); // faz o cálculo
                        calcResult = String.valueOf(calculo); // converte em string para setar na calculadora
                        calcView.setText(calcResult);
                        expression.setLength(0);
                        restartFlag = true;
                    }
                }
            }
        } catch (Error e){
            System.out.println(e);
        }

    }

}