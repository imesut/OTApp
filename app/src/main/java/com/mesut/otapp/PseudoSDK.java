package com.mesut.otapp;


public class PseudoSDK {
    public  int progress;

    public PseudoSDK(){}

    public PseudoSDK(int i){
        progress = i;
    }

    public static int LOCAL_VERSION = 1;
    public static int SERVER_VERSION;
    public static boolean forced = false;

    public static boolean connected = false;

    public static int timeoutPrb = 50;
    public static int hashPrb = 50;
    public static int reconnectPrb = 50;
    public static int updateFailPrb = 50;
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
