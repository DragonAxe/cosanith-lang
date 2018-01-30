package com.dragonaxe.cosanith;

import com.dragonaxe.cosanith.antlr.HelloBaseListener;
import com.dragonaxe.cosanith.antlr.HelloLexer;
import com.dragonaxe.cosanith.antlr.HelloParser;
import com.dragonaxe.cosanith.ast.Int;
import com.dragonaxe.cosanith.ast.Opp;
import com.dragonaxe.cosanith.ast.Prog;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class Main extends HelloBaseListener {

    // Note: These paths are relative to the project directory.
    public static String inputFileName = "./src/main/llvm/input.txt";
    public static String outputFileName = "./src/main/llvm/output.ll";

    public static void main(String[] args) {

        if (args.length == 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        }

        try {
            try {
                // String input = "5*(3+9)+(10)\n2+3+4+5\n";
                System.out.println("Reading from file: " + inputFileName);
                System.out.println("----");
                CharStream cs = CharStreams.fromFileName(inputFileName);
                // CharStream cs = CharStreams.fromString(input);
                HelloLexer lexer = new HelloLexer(cs);
                HelloParser hp = new HelloParser(new CommonTokenStream(lexer));
                HelloParser.ProgContext pc = hp.prog();
                ParseTreeWalker walker = new ParseTreeWalker();
                Main listener = new Main();
                walker.walk(listener, pc);
            } catch (RuntimeException e) {
                System.err.println("Error: " + e);
                System.err.println("Current Progress: " + currentProg);
                throw e;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Prog currentProg;
    public Stack<Opp> currentExpr;
    public Stack<Integer> currentParam;

    @Override
    public void enterProg(HelloParser.ProgContext ctx) {
        // System.out.println("New Program: " + ctx.getText());
        currentProg = new Prog();
        currentExpr = new Stack<Opp>();
        currentParam = new Stack<Integer>();
    }

    @Override
    public void exitProg(HelloParser.ProgContext ctx) {
        System.out.print(currentProg);
        System.out.println("----");
        System.out.println("llvm ir written to file: " + outputFileName);
        String llvmProg = currentProg.toLLVM();
        try {
            PrintWriter out = new PrintWriter(outputFileName);
            out.print(llvmProg);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterExpr(HelloParser.ExprContext ctx) {
        // System.out.println("New Expression: " + ctx.getText() + " ChildCount:" + ctx.getChildCount()
        //     + " RuleIndex:" + ctx.getRuleIndex());
        if (ctx.getChildCount() == 3 && ctx.getChild(1).getChildCount() == 0) {
            // System.out.println("Pushed Opp");
            currentExpr.push(new Opp());
            currentParam.push(0);
        }
    }

    @Override
    public void exitExpr(HelloParser.ExprContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.getChild(1).getChildCount() == 0) {
            Opp opp = currentExpr.pop();
            currentParam.pop();
            // System.out.println("Popped Opp: " + opp);
            if (currentExpr.empty()) {
                currentProg.lines.add(opp);
            } else {
                switch (currentParam.peek()) {
                    case 0: currentExpr.peek().a = opp; break;
                    case 2: currentExpr.peek().b = opp; break;
                }
                int tmp = currentParam.pop() + 1;
                currentParam.push(tmp);
            }
        }
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // System.out.println("New Terminal: " + node.getText());
        if (node.getText().equals("(") || node.getText().equals(")") || node.getText().equals("\n")) {
            return;
        }
        Opp opp = currentExpr.peek();
        switch (currentParam.peek()) {
            case 0: opp.a = new Int(node.getText()); break;
            case 2: opp.b = new Int(node.getText()); break;
            case 1:
                switch (node.getSymbol().getType()) {
                    case HelloLexer.ADD: opp.opptype = Opp.OppType.ADD; break;
                    case HelloLexer.SUB: opp.opptype = Opp.OppType.SUB; break;
                    case HelloLexer.MUL: opp.opptype = Opp.OppType.MUL; break;
                    case HelloLexer.DIV: opp.opptype = Opp.OppType.DIV; break;
                    default:
                        throw new RuntimeException("Expected +, -, *, /. Got "
                                + HelloLexer.ruleNames[node.getSymbol().getType()-1]);
                }
                break;
            default:
                throw new RuntimeException("Expected parameter to Opp");
        }
        int tmp = currentParam.pop() + 1;
        currentParam.push(tmp);
        // System.out.println(opp);
    }
}
