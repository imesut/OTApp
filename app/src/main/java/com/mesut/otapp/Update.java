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




        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int randomBreakpoint = (int)(100 * Math.random());
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {

                            final PseudoSDK anObj = new PseudoSDK(0);
                            while (!isInterrupted() && anObj.progress <= 100) {
                                Thread.sleep(1000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(PseudoSDK.progress(randomBreakpoint,anObj.progress)){
                                            progressPercentage.setText("%"+String.valueOf(anObj.progress));
                                        }
                                        else {
                                            progressPercentage.setText("%"+String.valueOf(anObj.progress) + "BOOM");
                                        }
                                    }
                                });
                                anObj.progress++;
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                };

                t.start();
            }
        });

        //Dialog Box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Diyalog Mesajı Buraya gelecek").setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Update.this, Management.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setMessage("Override tetx here");
        //dialog.show();
    }
}
