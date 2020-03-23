package com.example.mjcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView displayHandle;
    private String operation = "";
    private double firstNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayHandle = (TextView) findViewById(R.id.result);
    }


    public void keyClicked(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();

        switch(key){
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                String display = displayHandle.getText().toString();
                if(display.equals("0")){
                    display = "";
                }
                display += key;
                displayHandle.setText(display);
                break;
            case "+":
            case "-":
            case "x":
            case "/":
                if(operation.equals("")){
                    firstNumber = getNumbers();
                } else {
                    if(getNumbers() != 0){
                        calculate();
                        firstNumber = getNumbers();
                    }
                }
                setOperation(key);
                clearDisplay();
                break;
            case "=":
                calculate();
                clearFirstNumber();
                clearOperation();
                break;
            case "AC":
                clearFirstNumber();
                clearOperation();
                clearDisplay();
                break;
        }
    }

    private void calculate() {
        double result = 0;
        double secondNumber = getNumbers();

        switch(operation){
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "x":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = firstNumber / secondNumber;
                break;
        }

        setNumbers(result);
    }

    private double getNumbers() {
        return Double.parseDouble(displayHandle.getText().toString());
    }

    private void setNumbers(double text) {
        if(text == (long) text) {
            displayHandle.setText(String.valueOf((long)text));
        } else {
            displayHandle.setText(String.valueOf(text));
        }
    }

    private void setOperation(String key) {
        operation = key;
    }

    private void clearDisplay() {
        displayHandle.setText("0");
    }

    private void clearOperation() {
        operation = "";
    }

    private void clearFirstNumber() {
        firstNumber = 0;
    }
}
