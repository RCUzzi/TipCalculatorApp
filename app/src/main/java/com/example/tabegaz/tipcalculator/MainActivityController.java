package com.example.tabegaz.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivityController extends AppCompatActivity {

    private EditText amountEditText;
    private TextView percentTextView;
    private SeekBar percentSeekView;
    private TextView tipTextView;
    private TextView totalTextView;
    private  int tipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentSeekView = findViewById(R.id.percentSeekView);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        amountEditText.setText("");
        percentTextView.setText("0%");
        tipTextView.setText("$0.00");
        totalTextView.setText("$0.00");

        amountEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                calculateTip();
                return true;
            }
        });

        percentSeekView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percentTextView.setText(""+progress + "%");
            tipPercent = progress;
            calculateTip();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //calculateTip();
            }
        });
    }

    public void calculateTip()
    {
        double bill, tipAmount, totalBill, tax;
        try {
            bill = Double.parseDouble(amountEditText.getText().toString());
        } catch (Exception e) {
            bill = 0.0;
        }
        tipAmount = (bill * tipPercent)/100;
        tax = (bill*4)/100; // georgia's sales tax on prepared food
        totalBill = tipAmount + bill + tax;
        NumberFormat  currecnyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        tipTextView.setText(currecnyFormat.format(tipAmount));
        totalTextView.setText(currecnyFormat.format(totalBill));
    }

    public void addToTotal(View view) {
        double previousAmount;
        double amount = Double.parseDouble(view.getTag().toString());
        try {
            previousAmount = Double.parseDouble(amountEditText.getText().toString());
        } catch (Exception e) {
            previousAmount = 0.0;
        }
        previousAmount += amount;
        amountEditText.setText(Double.toString(previousAmount));
    }

    public void clearTotal(View view) {
        amountEditText.setText("");
        calculateTip();
    }
}
