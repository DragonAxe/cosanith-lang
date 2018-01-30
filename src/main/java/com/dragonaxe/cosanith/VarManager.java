package com.dragonaxe.cosanith;

public class VarManager {

    private static int i = 0;

    public static String getNewVar() {
        char one = (char)('a'+(i%26));
        char two = (char)('a'+(i/26));
        i++;
        return "" + two + one;
    }

}
