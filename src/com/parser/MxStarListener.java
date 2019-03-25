// Generated from C:/Users/lbn187/Desktop/IDEA/compiler_v0/src\MxStar.g4 by ANTLR 4.7.2
package com.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxStarParser}.
 */
public interface MxStarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableDefine}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefine(MxStarParser.VariableDefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableDefine}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefine(MxStarParser.VariableDefineContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#classPart}.
	 * @param ctx the parse tree
	 */
	void enterClassPart(MxStarParser.ClassPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#classPart}.
	 * @param ctx the parse tree
	 */
	void exitClassPart(MxStarParser.ClassPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#functionPart}.
	 * @param ctx the parse tree
	 */
	void enterFunctionPart(MxStarParser.FunctionPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#functionPart}.
	 * @param ctx the parse tree
	 */
	void exitFunctionPart(MxStarParser.FunctionPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#thetype}.
	 * @param ctx the parse tree
	 */
	void enterThetype(MxStarParser.ThetypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#thetype}.
	 * @param ctx the parse tree
	 */
	void exitThetype(MxStarParser.ThetypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#allthetype}.
	 * @param ctx the parse tree
	 */
	void enterAllthetype(MxStarParser.AllthetypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#allthetype}.
	 * @param ctx the parse tree
	 */
	void exitAllthetype(MxStarParser.AllthetypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#basicthetype}.
	 * @param ctx the parse tree
	 */
	void enterBasicthetype(MxStarParser.BasicthetypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#basicthetype}.
	 * @param ctx the parse tree
	 */
	void exitBasicthetype(MxStarParser.BasicthetypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MxStarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MxStarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtblock}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtblock(MxStarParser.StmtblockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtblock}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtblock(MxStarParser.StmtblockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtif}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtif(MxStarParser.StmtifContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtif}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtif(MxStarParser.StmtifContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtwhile}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtwhile(MxStarParser.StmtwhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtwhile}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtwhile(MxStarParser.StmtwhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtfor}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtfor(MxStarParser.StmtforContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtfor}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtfor(MxStarParser.StmtforContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtbreak}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtbreak(MxStarParser.StmtbreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtbreak}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtbreak(MxStarParser.StmtbreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtcontinue}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtcontinue(MxStarParser.StmtcontinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtcontinue}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtcontinue(MxStarParser.StmtcontinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtreturn}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtreturn(MxStarParser.StmtreturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtreturn}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtreturn(MxStarParser.StmtreturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtexpression}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtexpression(MxStarParser.StmtexpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtexpression}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtexpression(MxStarParser.StmtexpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtvariable}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtvariable(MxStarParser.StmtvariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtvariable}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtvariable(MxStarParser.StmtvariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtblank}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmtblank(MxStarParser.StmtblankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtblank}
	 * labeled alternative in {@link MxStarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmtblank(MxStarParser.StmtblankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprnull}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprnull(MxStarParser.ExprnullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprnull}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprnull(MxStarParser.ExprnullContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprbrackets}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprbrackets(MxStarParser.ExprbracketsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprbrackets}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprbrackets(MxStarParser.ExprbracketsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprstring}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprstring(MxStarParser.ExprstringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprstring}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprstring(MxStarParser.ExprstringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprprefix}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprprefix(MxStarParser.ExprprefixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprprefix}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprprefix(MxStarParser.ExprprefixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprassign}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprassign(MxStarParser.ExprassignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprassign}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprassign(MxStarParser.ExprassignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprthis}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprthis(MxStarParser.ExprthisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprthis}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprthis(MxStarParser.ExprthisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprnew}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprnew(MxStarParser.ExprnewContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprnew}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprnew(MxStarParser.ExprnewContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expror}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpror(MxStarParser.ExprorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expror}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpror(MxStarParser.ExprorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprsuffix}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprsuffix(MxStarParser.ExprsuffixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprsuffix}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprsuffix(MxStarParser.ExprsuffixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprfalse}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprfalse(MxStarParser.ExprfalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprfalse}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprfalse(MxStarParser.ExprfalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprsmember}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprsmember(MxStarParser.ExprsmemberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprsmember}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprsmember(MxStarParser.ExprsmemberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprand}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprand(MxStarParser.ExprandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprand}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprand(MxStarParser.ExprandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprfunction}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprfunction(MxStarParser.ExprfunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprfunction}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprfunction(MxStarParser.ExprfunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expridentifier}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpridentifier(MxStarParser.ExpridentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expridentifier}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpridentifier(MxStarParser.ExpridentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprinteger}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprinteger(MxStarParser.ExprintegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprinteger}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprinteger(MxStarParser.ExprintegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprbinary}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprbinary(MxStarParser.ExprbinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprbinary}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprbinary(MxStarParser.ExprbinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprtrue}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprtrue(MxStarParser.ExprtrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprtrue}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprtrue(MxStarParser.ExprtrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprexpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprexpr(MxStarParser.ExprexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprexpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprexpr(MxStarParser.ExprexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(MxStarParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(MxStarParser.CreatorContext ctx);
}