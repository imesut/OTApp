package com.mesut.otapp;


public class PseudoSDK {
    // data member private yapılacak ve getter setter method yazılacak
    public  int progress;

    public PseudoSDK(){}

    public PseudoSDK(int i){
        progress = i;
    }

    public static int LOCAL_VERSION = 3;
    public static int SERVER_VERSION;
    public static boolean forced = false;

    public static boolean connected = false;

    public static int timeoutPrb = 5;
    public static int hashPrb = 5;
    public static int reconnectPrb = 5;
    public static int updateFailPrb = 5;
    public static int speedofTime = 1;

    public static boolean progress(int randomInt, int progress){
        return !(randomInt == progress);
    }

    public static int check_for_update(){
        // Update Version to
        if (SERVER_VERSION > LOCAL_VERSION){
            return SERVER_VERSION;
        }
        // Forced Update
        else if(forced){
            if(SERVER_VERSION==LOCAL_VERSION){
                return -1;
            }
            return 0;
        }
        // No update
        else{
            return -1;
        }
    }
}
