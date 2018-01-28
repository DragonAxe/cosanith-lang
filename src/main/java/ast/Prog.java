package ast;

import java.util.LinkedList;
import java.util.List;

public class Prog {

    public List<Opp> lines;

    public Prog() {
        lines = new LinkedList<Opp>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Opp line : lines) {
            sb.append(line.toString());
            sb.append(" = ");
            sb.append(line.result());
            sb.append("\n");
        }
        return sb.toString();
    }
}
