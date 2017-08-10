package com.mesut.otapp;

import android.content.Context;

import java.io.File;


public class PseudoSDK {
    // data member private yapılacak ve getter setter method yazılacak
    public  int progress;

    public PseudoSDK(){}

    public PseudoSDK(int i){
        progress = i;
    }

    public static int LOCAL_VERSION = 4;

    public static boolean progress(int randomInt, int progress){
        return !(randomInt == progress);
    }

    public static int check_for_update(){
        int SERVER_VERSION = 5;
        boolean SV_forced = false;
        // Update Version to
        if (SERVER_VERSION > LOCAL_VERSION){
            return SERVER_VERSION;
        }
        // Forced Update
        else if(SV_forced){
            return 0;
        }
        // No update
        else{
            return -1;
        }
    }

    File file = new File("version.txt");
    file.


}
