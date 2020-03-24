package com.example.mjcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView displayHandle;
    private String operation = "";
    private double firstNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayHandle = findViewById(R.id.result);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onSaveInstanceState (@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putDouble("display", getNumbers());
        outState.putString("operation", operation);
        outState.putDouble("firstNumber", firstNumber);
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setNumbers(savedInstanceState.getDouble("display"));
        operation = savedInstanceState.getString("operation");
        firstNumber = savedInstanceState.getDouble("firstNumber");
    }

    public void keyClicked(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();
        String display = displayHandle.getText().toString();

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
                if(display.equals("0")){
                    display = "";
                }
                display += key;
                displayHandle.setText(display);
                break;
            case ",":
                if(!display.contains(".")){
                    display += ".";
                    displayHandle.setText(display);
                }
                break;
            case "+":
            case "-":
            case "x":
            case "/":
            case "%":
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
            case "+/-":
                changeSign();
                break;
            case "log10":
                log10();
                break;
            case "x!":
                factorial();
                break;
            case "SQRT(x)":
                sqrt();
                break;
            case "x^3":
                pow(3);
                break;
            case "x^2":
                pow(2);
                break;
        }
    }

    private void pow(int i) {
        double num = Math.pow(getNumbers(), i);
        setNumbers(num);
    }

    private void sqrt() {
        double num = Math.sqrt(getNumbers());
        setNumbers(num);
    }

    private void factorial() {
        double size = getNumbers();
        double num = 1;
        while(size > 0){
            num *= size;
            size--;
        }
        setNumbers(num);
    }

    private void log10() {
        double num = Math.log10(getNumbers());
        setNumbers(num);
    }

    private void changeSign() {
        double num = (-1)*getNumbers();
        setNumbers(num);
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
            case "%":
                result = firstNumber % secondNumber;
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
