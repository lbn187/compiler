// Generated from C:/Users/lbn187/Desktop/IDEA/compiler_v0/src\MxStar.g4 by ANTLR 4.7.2
package com.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxStarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxStarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableDefine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDefine(MxStarParser.VariableDefineContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassPart(MxStarParser.ClassPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionPart(MxStarParser.FunctionPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#thetype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThetype(MxStarParser.ThetypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#allthetype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllthetype(MxStarParser.AllthetypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#basicthetype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicthetype(MxStarParser.BasicthetypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxStarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtblock}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtblock(MxStarParser.StmtblockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtif}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtif(MxStarParser.StmtifContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtwhile}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtwhile(MxStarParser.StmtwhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtfor}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtfor(MxStarParser.StmtforContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtbreak}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtbreak(MxStarParser.StmtbreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtcontinue}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtcontinue(MxStarParser.StmtcontinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtreturn}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtreturn(MxStarParser.StmtreturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtexpression}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtexpression(MxStarParser.StmtexpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtvariable}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtvariable(MxStarParser.StmtvariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtblank}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtblank(MxStarParser.StmtblankContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprnull}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprnull(MxStarParser.ExprnullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprbrackets}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprbrackets(MxStarParser.ExprbracketsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprstring}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprstring(MxStarParser.ExprstringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprprefix}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprprefix(MxStarParser.ExprprefixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprassign}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprassign(MxStarParser.ExprassignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprthis}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprthis(MxStarParser.ExprthisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprnew}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprnew(MxStarParser.ExprnewContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expror}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpror(MxStarParser.ExprorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprsuffix}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprsuffix(MxStarParser.ExprsuffixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprfalse}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprfalse(MxStarParser.ExprfalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprsmember}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprsmember(MxStarParser.ExprsmemberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprand}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprand(MxStarParser.ExprandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprfunction}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprfunction(MxStarParser.ExprfunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expridentifier}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpridentifier(MxStarParser.ExpridentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprinteger}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprinteger(MxStarParser.ExprintegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprbinary}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprbinary(MxStarParser.ExprbinaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprtrue}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprtrue(MxStarParser.ExprtrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprexpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprexpr(MxStarParser.ExprexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(MxStarParser.CreatorContext ctx);
}