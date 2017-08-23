package com.mesut.otapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.ObjectInputValidation;

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
        final SeekBar speedOfTime = (SeekBar)findViewById(R.id.speedofTimeSeekBar);
        final TextView speedofTimeRatio = (TextView)findViewById(R.id.speedofTimeRatioText);
        Button submit = (Button)findViewById(R.id.submit);

        forced.setChecked(PseudoSDK.forced);
        connected.setChecked(PseudoSDK.connected);
        serverVersion.setText(String.valueOf(PseudoSDK.SERVER_VERSION));
        timeoutPrb.setText(String.valueOf(PseudoSDK.timeoutPrb));
        hashPrb.setText(String.valueOf(PseudoSDK.hashPrb));
        reconnectPrb.setText(String.valueOf(PseudoSDK.reconnectPrb));
        updateFailPrb.setText(String.valueOf(PseudoSDK.updateFailPrb));
        speedOfTime.setProgress(PseudoSDK.speedofTime);
        speedofTimeRatio.setText("1/"+String.valueOf(PseudoSDK.speedofTime+1));

        speedOfTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speedofTimeRatio.setText("1/"+String.valueOf(i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PseudoSDK.forced = forced.isChecked();
                PseudoSDK.connected = connected.isChecked();

                String sv = serverVersion.getText().toString();
                String to = timeoutPrb.getText().toString();
                String hs = hashPrb.getText().toString();
                String rc = reconnectPrb.getText().toString();
                String uf = updateFailPrb.getText().toString();

                //Set empty places to value zero
                String[] editTexts = {sv, to, hs, rc, uf};
                for (int k=0; k <= 4; k++){
                    if (editTexts[k].equals("")){
                        editTexts[k] = "0";
                    }
                }
                PseudoSDK.SERVER_VERSION = Integer.parseInt(editTexts[0]);
                PseudoSDK.timeoutPrb = Integer.parseInt(editTexts[1]);
                PseudoSDK.hashPrb = Integer.parseInt(editTexts[2]);
                PseudoSDK.reconnectPrb = Integer.parseInt(editTexts[3]);
                PseudoSDK.updateFailPrb = Integer.parseInt(editTexts[4]);
                PseudoSDK.speedofTime = speedOfTime.getProgress();
                Intent intent = new Intent(Simulation.this, Management.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
