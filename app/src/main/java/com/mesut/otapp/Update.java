package com.mesut.otapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class Update extends AppCompatActivity {
    int i = 1;
    int j = 0;
    boolean phase = true;
    public boolean waitForProcess = false;
    public int sleepDuration = 1000;
    public boolean stop = false;

    public void update_wait(int fn, TextView textview, Thread counter, Builder builder, TextView progress){
        AlertDialog dialog = builder.create();
        dialog.setTitle(R.string.error);
        switch (fn){
            case 1:
                //Download update
                textview.setText(R.string.updateState1);
                sleepDuration = 3000;
                break;
            case 2:
                //Check the update(hash) text
                textview.setText(R.string.updateState2);
                sleepDuration = 1000;
                break;
            case 3:
                //Download check result
                sleepDuration = 500;
                if ((int)(100 * Math.random()) < PseudoSDK.hashPrb){
                    waitForProcess = true;
                    dialog.setMessage(getString(R.string.updateState3fail));
                    dialog.show();
                }
                //success message
                else {
                    textview.setText(R.string.updateState3);
                }
                break;
            case 4:
                sleepDuration = 3000;
                //Transmission Started
                textview.setText(R.string.updateState4);
                waitForProcess = true;
                counter.start();
                break;
            case 5:
                //Applying Update
                //counter.interrupt();
                sleepDuration = 6000;
                textview.setText(R.string.updateState5);
                break;
            case 6:
                //Waiting for restart
                progress.setVisibility(View.INVISIBLE);
                sleepDuration = 4000;
                break;
            case 7:
                //WeWALK reconnected
                sleepDuration = 1000;
                if ((int)(100 * Math.random()) < PseudoSDK.reconnectPrb){
                    waitForProcess = true;
                    dialog.setMessage(getString(R.string.updateState7fail));
                    dialog.show();
                }
                textview.setText(R.string.updateState7);
                break;
            case 8:
                //Checking Installed Update
                if ((int)(100 * Math.random()) < PseudoSDK.updateFailPrb){
                    textview.setText(R.string.updateState8fail);
                    dialog.setMessage(getString(R.string.updateState8fail));
                    dialog.setTitle(R.string.error);
                    dialog.show();
                }
                else{
                    textview.setText(R.string.updateState8);
                    dialog.setTitle("Güncelleme Tamamlandı.");
                    dialog.setMessage("WeWALK'unuzu kullanmaya devam edebilirsiniz...");
                    dialog.show();
                }
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();

        TextView tips = (TextView)findViewById(R.id.tips);
        final TextView progressPercentage = (TextView)findViewById(R.id.progressPercentage);
        final TextView stepInfo = (TextView)findViewById(R.id.stepInfo);

        //Dialog Box
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Thread.currentThread().interrupt();
                Intent intent = new Intent(Update.this, Management.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        final AlertDialog dialog = builder.create();

        //Dummy Progress
        int rb = 100;
        int err = 0;
        if ((int)(100 * Math.random()) < PseudoSDK.timeoutPrb) {
            rb = (int) (100 * Math.random());
            err = 1;
        }
        final int randomBreakpoint = rb;
        final int error = err;
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    j = 0;
                    while (!Thread.currentThread().isInterrupted() && j <= randomBreakpoint) {
                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(PseudoSDK.progress(randomBreakpoint,j)){
                                    progressPercentage.setText("%"+String.valueOf(j));
                                    j++;
                                }
                                else {
                                    if (error == 0){
                                        progressPercentage.setText("Güncelleme WeWALK'a Başarıyla iletildi");
                                        PseudoSDK.LOCAL_VERSION = PseudoSDK.SERVER_VERSION;
                                        stop = true;
                                        waitForProcess = false;
                                    }
                                    else{
                                        progressPercentage.setText("%"+j+" Hata: "+String.valueOf(error));
                                        dialog.setMessage(getString(R.string.timeoutError));
                                        dialog.setTitle(R.string.error);
                                        dialog.show();
                                        stop = true;
                                    }
                                }
                            }
                        });
                        if (stop){
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        final Thread main_update = new Thread() {
            @Override
            public void run() {
                try {
                    i=1;
                    while (phase) {
                        sleepDuration = sleepDuration + (int)(2000*Math.random());
                        if(waitForProcess){
                            Thread.sleep(20);
                        }
                        else {
                            Thread.sleep(sleepDuration);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!waitForProcess) {
                                    update_wait(i, stepInfo, t, builder, progressPercentage);
                                    i++;
                                }
                                //phase = false;
                            }
                            });
                        if (i == 9) {
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                }

            }
        };
        main_update.start();
    }
}