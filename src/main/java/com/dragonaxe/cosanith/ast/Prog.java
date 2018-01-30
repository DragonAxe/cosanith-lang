package com.dragonaxe.cosanith.ast;

import com.dragonaxe.cosanith.VarManager;

import java.util.LinkedList;
import java.util.List;

public class Prog {

    public List<Opp> lines;

    public Prog() {
        lines = new LinkedList<Opp>();
    }

    // Expression: 5+(4*3)
    // %a = mul i32 4, 3
    // %b = add i32 5, %a
    //
    public String toLLVM() {
        StringBuilder sb = new StringBuilder();
        sb.append("target triple = \"x86_64-unknown-linux-gnu\"\n" +
                "\n" +
                "@str = internal constant [4 x i8] c\"%d\\0A\\00\"\n" +
                "\n" +
                "declare i32 @printf(i8*, ...)\n" +
                "\n" +
                "define i32 @main() {\n");

        for (Opp line : lines) {
            String outName = VarManager.getNewVar();
            sb.append(line.toLLVM(outName));

            sb.append("    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str, i32 0, i32 0), i32 %");
            sb.append(outName);
            sb.append(")\n");
        }

        sb.append("    ret i32 0\n" +
                "}\n");
        return sb.toString();
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
