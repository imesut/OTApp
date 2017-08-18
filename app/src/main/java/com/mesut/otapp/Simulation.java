package com.mesut.otapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Simulation extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Simulation.this, Management.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0, 0);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        final Switch connected = (Switch)findViewById(R.id.connected);
        final Switch forced = (Switch)findViewById(R.id.forced);
        final EditText serverVersion = (EditText)findViewById(R.id.serverVersion);
        final EditText timeoutPrb = (EditText)findViewById(R.id.timeoutPrb);
        final EditText hashPrb = (EditText)findViewById(R.id.hashPrb);
        final EditText reconnectPrb = (EditText)findViewById(R.id.reconnect);
        final EditText updateFailPrb = (EditText)findViewById(R.id.updateFailPrb);
        Button submit = (Button)findViewById(R.id.submit);

        forced.setChecked(PseudoSDK.forced);
        connected.setChecked(PseudoSDK.connected);
        serverVersion.setText(String.valueOf(PseudoSDK.SERVER_VERSION));
        timeoutPrb.setText(String.valueOf(PseudoSDK.timeoutPrb));
        hashPrb.setText(String.valueOf(PseudoSDK.hashPrb));
        reconnectPrb.setText(String.valueOf(PseudoSDK.reconnectPrb));
        updateFailPrb.setText(String.valueOf(PseudoSDK.updateFailPrb));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PseudoSDK.forced = forced.isChecked();
                PseudoSDK.connected = connected.isChecked();
                PseudoSDK.SERVER_VERSION = Integer.parseInt(serverVersion.getText().toString());
                PseudoSDK.timeoutPrb = Integer.parseInt(timeoutPrb.getText().toString());
                PseudoSDK.hashPrb = Integer.parseInt(hashPrb.getText().toString());
                PseudoSDK.reconnectPrb = Integer.parseInt(reconnectPrb.getText().toString());
                PseudoSDK.updateFailPrb = Integer.parseInt(updateFailPrb.getText().toString());
                Intent intent = new Intent(Simulation.this, Management.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
