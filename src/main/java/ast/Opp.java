package ast;

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
