// Generated from /home/dragonaxe/Dropbox/Programming/java/cosanith/src/main/antlr/Cosa.g4 by ANTLR 4.7
package com.dragonaxe.cosanith.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CosaParser}.
 */
public interface CosaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CosaParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CosaParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CosaParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CosaParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CosaParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(CosaParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link CosaParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(CosaParser.FuncContext ctx);
}