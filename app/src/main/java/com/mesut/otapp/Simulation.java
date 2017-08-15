package com.mesut.otapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Simulation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        final Switch connected = (Switch)findViewById(R.id.connected);
        final Switch forced = (Switch)findViewById(R.id.forced);
        final EditText serverVersion = (EditText)findViewById(R.id.serverVersion);
        EditText timeoutPrb = (EditText)findViewById(R.id.timeoutPrb);
        EditText hashPrb = (EditText)findViewById(R.id.hashPrb);
        EditText reconnect = (EditText)findViewById(R.id.reconnect);
        EditText updateFailPrb = (EditText)findViewById(R.id.updateFailPrb);
        Button submit = (Button)findViewById(R.id.submit);

        serverVersion.setText(String.valueOf(PseudoSDK.SERVER_VERSION));
        forced.setChecked(PseudoSDK.forced);
        connected.setChecked(PseudoSDK.connected);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PseudoSDK.forced = forced.isChecked();
                PseudoSDK.connected = connected.isChecked();
                PseudoSDK.SERVER_VERSION = Integer.parseInt(serverVersion.getText().toString());
                Intent intent = new Intent(Simulation.this, Management.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
