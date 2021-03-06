package com.example.mjcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                choiceNumber(key);
                break;
            case ",":
                choicePoint();
                break;
            case "+":
            case "-":
            case "x":
            case "/":
                choiceOperation(key);
                break;
            case "%":
                choicePercent();
                break;
            case "=":
                choiceEqual();
                break;
            case "AC":
                clearAll();
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
                pow(0.5);
                break;
            case "x^3":
                pow(3);
                break;
            case "x^2":
                pow(2);
                break;
        }
    }

    private void choicePercent() {
        double secondNumber = getNumbers();

        double result;

        if(operation.equals("x") || operation.equals("/")){
            result = secondNumber / 100;
        } else {
            result = (firstNumber * secondNumber) / 100;
        }

        setNumbers(result);
    }

    private void clearAll() {
        clearFirstNumber();
        clearOperation();
        clearDisplay();
    }

    private void choiceEqual() {
        if(!operation.equals("")){
            calculate();
            clearFirstNumber();
            clearOperation();
        }
    }

    private void choiceOperation(String key) {
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
    }

    private void choicePoint() {
        String display = displayHandle.getText().toString();

        if(!display.contains(".")){
            display += ".";
            displayHandle.setText(display);
        }
    }

    private void choiceNumber(String key) {
        String display = displayHandle.getText().toString();

        if(display.equals("0")){
            display = "";
        }
        display += key;

        displayHandle.setText(display);
    }

    private void pow(double i) {
        double num = getNumbers();

        if(num < 0 && i < 1) {
            makeToast("Nie można pierwiastkować liczby ujemnej!");
        } else {
            num = Math.pow(getNumbers(), i);
        }

        setNumbers(num);
    }

    private void factorial() {
        double size = getNumbers();

        if(size < 0){
            makeToast("Nie można silnii z ujemnych");
        } else if (size != (long) size){
            makeToast("Nie można silnii z przecinkowych");
        } else {
            double num = 1;
            while(size > 0){
                num *= size;
                size--;
            }
            setNumbers(num);
        }
    }

    private void log10() {
        double num = getNumbers();

        if(num < 0){
            makeToast("Nie można logarytmować liczby ujemnej!");
        } else if(num == 0){
            makeToast("Nie można zero");
        } else {
            num = Math.log10(getNumbers());
        }

        setNumbers(num);
    }

    private void makeToast(String txt){
        Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT).show();
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
                if(secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    Toast.makeText(getApplicationContext(),"Nie można dzielić przez 0!",Toast.LENGTH_SHORT).show();
                }
                break;
        }

        setNumbers(result);
    }

    private double getNumbers() {
        return Double.parseDouble(displayHandle.getText().toString());
    }

    private void setNumbers(double numbers) {
        if(Double.isInfinite(numbers) || Double.isNaN(numbers)) {
            makeToast("Przekroczyłeś zakres zmiennej double");
        } else {
            if(numbers == (long) numbers) {
                displayHandle.setText(String.valueOf((long)numbers));
            } else {
                displayHandle.setText(String.valueOf(numbers));
            }
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
