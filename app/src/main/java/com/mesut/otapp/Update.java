package com.mesut.otapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class Update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();

        TextView tips = (TextView)findViewById(R.id.tips);
        final TextView progressPercentage = (TextView)findViewById(R.id.progressPercentage);
        TextView stepInfo = (TextView)findViewById(R.id.stepInfo);
        Button temp = (Button)findViewById(R.id.temp);

        //Dialog Box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Diyalog Mesajı Buraya gelecek").setTitle(R.string.dialog_title);

        //Dialog Box - Return to Management Activity Button
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Update.this, Management.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        final AlertDialog dialog = builder.create();

        //Dummy Progress
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rb = 100;
                int err = 0;
                if (Math.random() > 0.95) {
                    rb = (int) (100 * Math.random());
                    err = (int)(5*Math.random())+1;
                }
                final int randomBreakpoint = rb;
                final int error = err;
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {

                            final PseudoSDK anObj = new PseudoSDK(0);
                            while (!isInterrupted() && anObj.progress <= randomBreakpoint) {
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
                                                PseudoSDK.LOCAL_VERSION = 10;
                                            }
                                            else{
                                                progressPercentage.setText("%"+anObj.progress+" Hata: "+String.valueOf(error));
                                            }
                                        }
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                };
                t.start();
            }
        });
    }
}
