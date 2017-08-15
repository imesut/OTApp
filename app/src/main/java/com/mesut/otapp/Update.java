package com.mesut.otapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class Update extends AppCompatActivity {
    int i = 1;
    boolean phase = true;
    public boolean stop = false;

    public int update_wait(int i){
        int sleep = 500;
        return sleep;
    }

    public void update_step(int i){
        //If logics
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Diyalog Mesajı Buraya gelecek").setTitle(R.string.dialog_title);

        //Dialog Box - Return to Management Activity Button
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
        if (Math.random() > 0.95) {
            rb = (int) (100 * Math.random());
            err = (int)(5*Math.random())+1;
        }
        final int randomBreakpoint = rb;
        final int error = err;
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    final PseudoSDK anObj = new PseudoSDK(0);
                    while (!Thread.currentThread().isInterrupted() && anObj.progress <= randomBreakpoint) {
                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(PseudoSDK.progress(randomBreakpoint,anObj.progress)){
                                    progressPercentage.setText("%"+String.valueOf(anObj.progress));
                                    anObj.progress++;
                                }
                                else {
                                    if (error == 0){
                                        progressPercentage.setText("Başarıyla iletildi");
                                        dialog.setMessage("Güncelleme Tamamlandı.");
                                        dialog.show();
                                        PseudoSDK.LOCAL_VERSION = PseudoSDK.SERVER_VERSION;
                                        stop = true;
                                    }
                                    else{
                                        progressPercentage.setText("%"+anObj.progress+" Hata: "+String.valueOf(error));
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
                    while (phase) {
                        Thread.sleep(0);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Download update
                                stepInfo.setText(R.string.updateState1);
                                Log.d("status", "yazı1");
                                //Wait for download
                                try {
                                    Thread.sleep(800);
                                }catch (InterruptedException e){}
                                //show hash check message
                                Log.d("status", "sleep1");
                                stepInfo.setText(R.string.updateState2);
                                Log.d("status", "yazı2");
                                //Wait for hash check
                                try {
                                    Thread.sleep(400);
                                }catch (InterruptedException e){}
                                Log.d("status", "sleep2");
                                //Check fail message
                                int rnd = (int)(100 * Math.random());
                                if (rnd < PseudoSDK.hashPrb){
                                    stepInfo.setText(R.string.updateState3fail);
                                }
                                //Check success message
                                else{
                                    Log.d("status", "check");
                                    stepInfo.setText(R.string.updateState3);
                                    Log.d("status", "yazı3");
                                    try {
                                        Thread.sleep(200);
                                    }catch (InterruptedException e){}
                                    Log.d("status", "sleep3");
                                    //Start Transmission
                                    stepInfo.setText(R.string.updateState4);
                                    Log.d("status", "yazı4");
                                    t.start();
                                    Log.d("status", "update başlangıcı");
                                }
                                phase = false;
                                Log.d("scenario", "rnd: " + String.valueOf(rnd) + " hashPrb: " + String.valueOf(PseudoSDK.hashPrb) + String.valueOf(phase));
                            }
                        });
                        break;
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        main_update.start();
    }
}