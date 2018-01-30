package com.dragonaxe.cosanith.ast;

public class Int extends Expr {

    private int a;

    public Int(int a) {
        this.a = a;
    }

    public Int(String s) {
        try {
            a = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Error converting string to number for Int");
        }
    }

    public int getInt() {
        return a;
    }

    @Override
    public String toString() {
        return "" + a;
    }
}
