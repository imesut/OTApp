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
    public boolean waitForProcess = false;
    public int sleepDuration = 1000;
    public boolean stop = false;

    public void update_wait(int fn, TextView textview, Thread counter){
        switch (fn){
            case 1:
                //Download update
                textview.setText(R.string.updateState1);
                //Log.d("status", "yazı1");
                sleepDuration = 3000;
                break;
            case 2:
                textview.setText(R.string.updateState2);
                //Log.d("status", "yazı2");
                sleepDuration = 2000;
                break;
            case 3:
                int rnd = (int)(100 * Math.random());
                if (rnd < PseudoSDK.hashPrb){
                    textview.setText(R.string.updateState3fail);
                }
                //Check success message
                else {
                    textview.setText(R.string.updateState3);
                }
                break;
            case 4:
                //Start Transmission
                textview.setText(R.string.updateState4);
                waitForProcess = true;
                counter.start();
                break;
            case 5:
                counter.interrupt();
                textview.setText(R.string.updateState5);
                break;
            default:
                textview.setText("Default of Case");
                sleepDuration = 1000;
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
                    anObj.progress = 0;
                    while (!Thread.currentThread().isInterrupted() && anObj.progress <= randomBreakpoint) {
                        Log.d("running", "thread running");
                        Log.d("iterasyon", String.valueOf(anObj.progress));
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
                                        //dialog.setMessage("Güncelleme Tamamlandı.");
                                        //dialog.show();
                                        PseudoSDK.LOCAL_VERSION = PseudoSDK.SERVER_VERSION;
                                        stop = true;
                                        waitForProcess = false;
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
                    i=1;
                    while (phase) {
                        //Log.d("waitforprocess", String.valueOf(waitForProcess));
                        //Log.d("i", String.valueOf(i));
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
                                    //Log.d("sleepDuration", String.valueOf(sleepDuration));
                                    update_wait(i, stepInfo, t);
                                    //Log.d("Run", String.valueOf(i));
                                    i++;
                                    if (i == 6) {
                                        i = 1;
                                    }
                                }
                                //phase = false;
                            }
                            });

                        //break
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        main_update.start();
    }
}