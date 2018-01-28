import ast.Int;
import ast.Opp;
import ast.Prog;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class Main extends HelloBaseListener {

    public static void main(String[] args) {

        try {
            String input = "5*(3+9)+(10)\n2+3+4+5\n";
            CharStream cs = CharStreams.fromString(input);
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

    }

    public static Prog currentProg;
    public Stack<Opp> currentExpr;
    public Stack<Integer> currentParam;

    @Override
    public void enterProg(HelloParser.ProgContext ctx) {
        System.out.println("New Program: " + ctx.getText());
        currentProg = new Prog();
        currentExpr = new Stack<Opp>();
        currentParam = new Stack<Integer>();
    }

    @Override
    public void exitProg(HelloParser.ProgContext ctx) {
        System.out.print(currentProg);
    }

    @Override
    public void enterExpr(HelloParser.ExprContext ctx) {
        System.out.println("New Expression: " + ctx.getText() + " ChildCount:" + ctx.getChildCount()
            + " RuleIndex:" + ctx.getRuleIndex());
        if (ctx.getChildCount() == 3 && ctx.getChild(1).getChildCount() == 0) {
            System.out.println("Pushed Opp");
            currentExpr.push(new Opp());
            currentParam.push(0);
        }
    }

    @Override
    public void exitExpr(HelloParser.ExprContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.getChild(1).getChildCount() == 0) {
            Opp opp = currentExpr.pop();
            currentParam.pop();
            System.out.println("Popped Opp: " + opp);
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
        System.out.println("New Terminal: " + node.getText());
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
        System.out.println(opp);
    }
}
