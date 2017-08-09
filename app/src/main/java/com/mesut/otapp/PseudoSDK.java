package com.mesut.otapp;

public class PseudoSDK {
    // data member private yapılacak ve getter setter method yazılacak
    public  int progress;

    public PseudoSDK(){}

    public PseudoSDK(int i){
        progress = i;
    }

    public static boolean progress(int randomInt, int progress){
        return !(randomInt == progress);
    }
}
