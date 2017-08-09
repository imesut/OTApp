package com.mesut.otapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Management extends AppCompatActivity {
    // Define of Visual Items
    Button updateButton;
    TextView updateInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        // Visual Item Parameters
        updateButton = (Button) findViewById(R.id.updateButton);
        updateInfoText = (TextView) findViewById(R.id.updateInfoText);
        updateInfoText.setText("1.faz");

    }

    //Activity Pass Function
    public void goToUpdate(View view) {
        Intent intent = new Intent(this, Update.class);
        startActivity(intent);
    }
}

