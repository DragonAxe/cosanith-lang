package com.dragonaxe.cosanith.ast;

import com.dragonaxe.cosanith.VarManager;

public class Opp extends Expr {

    public enum OppType {
        ADD, SUB, MUL, DIV
    }

    public OppType opptype;
    public Expr a;
    public Expr b;

    public int result () {
        if (opptype == null || a == null || b == null) {
            return -1;
        }
        int ia;
        int ib;
        if (a instanceof Opp) {
            ia = ((Opp)a).result();
        } else {
            ia = ((Int)a).getInt();
        }
        if (b instanceof Opp) {
            ib = ((Opp)b).result();
        } else {
            ib = ((Int)b).getInt();
        }
        switch (opptype) {
            case ADD: return ia + ib;
            case SUB: return ia - ib;
            case MUL: return ia * ib;
            case DIV: return ia / ib;
            default: return -1;
        }
    }

    public String toLLVM(String outVarName) {
        StringBuilder sb = new StringBuilder();
        String paramA;
        String paramB;

        if (a instanceof Int) {
            paramA = ((Int)a).toString();
        } else {
            String name1 = VarManager.getNewVar();
            paramA = "%" + name1;
            sb.append(((Opp)a).toLLVM(name1));
        }
        if (b instanceof Int) {
            paramB = ((Int)b).toString();
        } else {
            String name2 = VarManager.getNewVar();
            paramB = "%" + name2;
            sb.append(((Opp)b).toLLVM(name2));
        }

        sb.append("    %");
        sb.append(outVarName);
        sb.append(" = ");
        switch (opptype) {
            case ADD: sb.append("add"); break;
            case SUB: sb.append("sub"); break;
            case MUL: sb.append("mul"); break;
            case DIV: sb.append("div"); break;
        }
        sb.append(" i32 ");
        sb.append(paramA);
        sb.append(", ");
        sb.append(paramB);
        sb.append("\n");
        return sb.toString();
    }

    private String iToVarName(int i) {
        char one = (char)('a'+(i%26));
        char two = (char)('a'+(i/26));
        return "v" + two + one;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (opptype == null) {
            sb.append("nullopp");
        } else {
            switch (opptype) {
                case ADD: sb.append('+'); break;
                case SUB: sb.append('-'); break;
                case MUL: sb.append('*'); break;
                case DIV: sb.append('/'); break;
            }
        }
        sb.append(" ");
        if (a == null) {
            sb.append("null");
        } else {
            sb.append(a.toString());
        }
        sb.append(" ");
        if (b == null) {
            sb.append("null");
        } else {
            sb.append(b.toString());
        }
        sb.append(")");
        return sb.toString();
    }
}
