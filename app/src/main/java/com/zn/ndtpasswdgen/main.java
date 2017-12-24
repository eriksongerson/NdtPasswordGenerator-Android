package com.zn.ndtpasswdgen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class main extends AppCompatActivity {

    int Length = 12;

    Button generateButton;
    Button copyToClipboardButton;
    CheckBox symbolsCheckBox;
    CheckBox numbersCheckBox;
    CheckBox lowerCaseCheckBox;
    CheckBox upperCaseCheckBox;
    TextView symbolsEditText;
    TextView generatedPasswordTextView;
    SeekBar lengthOfPassSeekBar;
    TextView lengthOfPassTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upperCaseCheckBox = (CheckBox) findViewById(R.id.upperCaseCheckBox);
        lowerCaseCheckBox = (CheckBox) findViewById(R.id.lowerCaseCheckBox);
        numbersCheckBox = (CheckBox) findViewById(R.id.numbersCheckBox);
        symbolsCheckBox = (CheckBox) findViewById(R.id.symbolsCheckBox);
        symbolsEditText = (TextView) findViewById(R.id.symbolsEditText);

        lengthOfPassSeekBar = (SeekBar) findViewById(R.id.lengthOfPassSeekBar);
        lengthOfPassTextView = (TextView) findViewById(R.id.lengthOfPassTextView);

        generateButton = (Button) findViewById(R.id.generateButton);
        generatedPasswordTextView = (TextView) findViewById(R.id.generatedPasswordTextView);

        copyToClipboardButton = (Button) findViewById(R.id.copyToClipboardButton);

        upperCaseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CheckIfChecked();
            }
        });

        lowerCaseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CheckIfChecked();
            }
        });

        numbersCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CheckIfChecked();
            }
        });

        symbolsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(symbolsCheckBox.isChecked()){
                    symbolsEditText.setEnabled(true);
                }
                else{
                    symbolsEditText.setEnabled(false);
                }
                CheckIfChecked();
            }
        });



        lengthOfPassSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("PROGRESS", i + ": " + seekBar.getProgress());
                Length = seekBar.getProgress() + 8;
                lengthOfPassTextView.setText((Length + ""));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Generate();
            }
        });

        copyToClipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password:", generatedPasswordTextView.getText());
                clipboard.setPrimaryClip(clip);
                copyToClipboardButton.setText("Copied!");
                copyToClipboardButton.setEnabled(true);
                Toast.makeText(main.this, "Copied!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CheckIfChecked(){
        if(!lowerCaseCheckBox.isChecked() && !upperCaseCheckBox.isChecked() && !numbersCheckBox.isChecked() && !symbolsCheckBox.isChecked()) {
            generateButton.setEnabled(false);
        } else{
            generateButton.setEnabled(true);
        }
    }

    private void Generate(){
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        StringBuilder alphabet = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        StringBuilder symbols = new StringBuilder(symbolsEditText.getText());

        for (int i = 0; i < Length; i++) {
            int work = random.nextInt(4);
            if (checkThis(work)) {
                switch (work) {
                    case 0: {
                        password.append(Character.toUpperCase(alphabet.charAt(random.nextInt(alphabet.length()))));
                        break;
                    }
                    case 1: {
                        password.append(alphabet.charAt(random.nextInt(alphabet.length())));
                        break;
                    }
                    case 2: {
                        password.append(random.nextInt(10));
                        break;
                    }
                    case 3: {
                        password.append(symbols.charAt(random.nextInt(symbols.length())));
                        break;
                    }
                }
            }
            else{
                i--;
            }
        }

        generatedPasswordTextView.setText(password.toString());
    }

    private boolean checkThis(int number){
        if(number == 0){
            if(upperCaseCheckBox.isChecked()) return true;
        }
        if(number == 1){
            if(lowerCaseCheckBox.isChecked()) return true;
        }
        if(number == 2){
            if(numbersCheckBox.isChecked()) return true;
        }
        if(number == 3){
            if(symbolsCheckBox.isChecked()) return true;
        }
        return false;
    }

}
