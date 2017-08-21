package com.mesut.otapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Management extends AppCompatActivity {
    // Define of Visual Items
    Button updateButton;
    TextView updateInfoText;
    ImageButton preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Test Initializations for speed up
        PseudoSDK.connected = true;
        PseudoSDK.forced = true;


        if (PseudoSDK.connected) {
            setContentView(R.layout.activity_management_connected);
            // Visual Item Parameters
            updateButton = (Button) findViewById(R.id.updateButton);
            updateInfoText = (TextView) findViewById(R.id.updateInfoText);
            updateInfoText.setText("WeWALK Cihazınızın Yazılım Sürümü: v" + String.valueOf(PseudoSDK.LOCAL_VERSION));

            if (PseudoSDK.check_for_update() != -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("WeWALK cihazı yazılımı güncellemesi bulundu.").setTitle("Güncelleme Bulundu...");
                builder.setPositiveButton("Yükleyelim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Management.this, Update.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateButton.setVisibility(View.VISIBLE);
                        if (PseudoSDK.check_for_update() > 0) {
                            updateInfoText.setText("WeWALK cihaz yazılımınız için yeni güncelleme(Sürüm: " + String.valueOf(PseudoSDK.check_for_update()) + ") bulundu.");
                        } else if (PseudoSDK.check_for_update() == 0) {
                            updateInfoText.setText("WeWALK cihaz yazılımınız için güncelleme Bulundu.");
                        }
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                updateInfoText.setText("WeWALK cihazınız yazılımınız (v" + String.valueOf(PseudoSDK.LOCAL_VERSION) + ") güncel.");
            }
        }else{
            setContentView(R.layout.activity_management_unconnected);
        }
    }

    //Activity Go To Function
    public void goToUpdate(View view) {
        Intent intent = new Intent(this, Update.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    //Activity Go To Function
    public void goToSimulation(View view) {
        Intent intent = new Intent(this, Simulation.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}

