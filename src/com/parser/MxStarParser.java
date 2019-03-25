// Generated from C:/Users/lbn187/Desktop/IDEA/compiler_v0/src\MxStar.g4 by ANTLR 4.7.2
package com.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxStarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, Identifier=51, ConstString=52, 
		ConstInteger=53, WS=54, Comment=55;
	public static final int
		RULE_program = 0, RULE_variableDefine = 1, RULE_classPart = 2, RULE_functionPart = 3, 
		RULE_parameter = 4, RULE_thetype = 5, RULE_allthetype = 6, RULE_basicthetype = 7, 
		RULE_block = 8, RULE_stmt = 9, RULE_expression = 10, RULE_creator = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "variableDefine", "classPart", "functionPart", "parameter", 
			"thetype", "allthetype", "basicthetype", "block", "stmt", "expression", 
			"creator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "';'", "'class'", "'{'", "'}'", "'('", "','", "')'", "'()'", 
			"'['", "']'", "'int'", "'bool'", "'string'", "'void'", "'if'", "'else'", 
			"'while'", "'for'", "'break'", "'continue'", "'return'", "'null'", "'true'", 
			"'false'", "'this'", "'new'", "'.'", "'!'", "'~'", "'+'", "'-'", "'++'", 
			"'--'", "'*'", "'/'", "'%'", "'<<'", "'>>'", "'<'", "'<='", "'>'", "'>='", 
			"'=='", "'!='", "'&'", "'^'", "'|'", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "Identifier", "ConstString", "ConstInteger", "WS", 
			"Comment"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MxStar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxStarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxStarParser.EOF, 0); }
		public List<VariableDefineContext> variableDefine() {
			return getRuleContexts(VariableDefineContext.class);
		}
		public VariableDefineContext variableDefine(int i) {
			return getRuleContext(VariableDefineContext.class,i);
		}
		public List<ClassPartContext> classPart() {
			return getRuleContexts(ClassPartContext.class);
		}
		public ClassPartContext classPart(int i) {
			return getRuleContext(ClassPartContext.class,i);
		}
		public List<FunctionPartContext> functionPart() {
			return getRuleContexts(FunctionPartContext.class);
		}
		public FunctionPartContext functionPart(int i) {
			return getRuleContext(FunctionPartContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << Identifier))) != 0)) {
				{
				setState(27);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(24);
					variableDefine();
					}
					break;
				case 2:
					{
					setState(25);
					classPart();
					}
					break;
				case 3:
					{
					setState(26);
					functionPart();
					}
					break;
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDefineContext extends ParserRuleContext {
		public Token name;
		public ThetypeContext thetype() {
			return getRuleContext(ThetypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDefineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDefine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVariableDefine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVariableDefine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVariableDefine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDefineContext variableDefine() throws RecognitionException {
		VariableDefineContext _localctx = new VariableDefineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_variableDefine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			thetype();
			setState(35);
			((VariableDefineContext)_localctx).name = match(Identifier);
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(36);
				match(T__0);
				setState(37);
				expression(0);
				}
			}

			setState(40);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassPartContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public List<VariableDefineContext> variableDefine() {
			return getRuleContexts(VariableDefineContext.class);
		}
		public VariableDefineContext variableDefine(int i) {
			return getRuleContext(VariableDefineContext.class,i);
		}
		public List<FunctionPartContext> functionPart() {
			return getRuleContexts(FunctionPartContext.class);
		}
		public FunctionPartContext functionPart(int i) {
			return getRuleContext(FunctionPartContext.class,i);
		}
		public ClassPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classPart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterClassPart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitClassPart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitClassPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassPartContext classPart() throws RecognitionException {
		ClassPartContext _localctx = new ClassPartContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(T__2);
			setState(43);
			((ClassPartContext)_localctx).name = match(Identifier);
			setState(44);
			match(T__3);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << Identifier))) != 0)) {
				{
				setState(47);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(45);
					variableDefine();
					}
					break;
				case 2:
					{
					setState(46);
					functionPart();
					}
					break;
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(52);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionPartContext extends ParserRuleContext {
		public Token name;
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public ThetypeContext thetype() {
			return getRuleContext(ThetypeContext.class,0);
		}
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public FunctionPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionPart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterFunctionPart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitFunctionPart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFunctionPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionPartContext functionPart() throws RecognitionException {
		FunctionPartContext _localctx = new FunctionPartContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functionPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(54);
				thetype();
				}
				break;
			}
			setState(57);
			((FunctionPartContext)_localctx).name = match(Identifier);
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				{
				{
				setState(58);
				match(T__5);
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << Identifier))) != 0)) {
					{
					setState(59);
					parameter();
					setState(64);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(60);
						match(T__6);
						setState(61);
						parameter();
						}
						}
						setState(66);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(69);
				match(T__7);
				}
				}
				break;
			case T__8:
				{
				{
				setState(70);
				match(T__8);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(73);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public Token name;
		public ThetypeContext thetype() {
			return getRuleContext(ThetypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			thetype();
			setState(76);
			((ParameterContext)_localctx).name = match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThetypeContext extends ParserRuleContext {
		public AllthetypeContext allthetype() {
			return getRuleContext(AllthetypeContext.class,0);
		}
		public ThetypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thetype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterThetype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitThetype(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitThetype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThetypeContext thetype() throws RecognitionException {
		ThetypeContext _localctx = new ThetypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_thetype);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			allthetype();
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(79);
				match(T__9);
				setState(80);
				match(T__10);
				}
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AllthetypeContext extends ParserRuleContext {
		public Token name;
		public BasicthetypeContext basicthetype() {
			return getRuleContext(BasicthetypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public AllthetypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_allthetype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterAllthetype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitAllthetype(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAllthetype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AllthetypeContext allthetype() throws RecognitionException {
		AllthetypeContext _localctx = new AllthetypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_allthetype);
		try {
			setState(88);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
			case T__12:
			case T__13:
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(86);
				basicthetype();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				((AllthetypeContext)_localctx).name = match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BasicthetypeContext extends ParserRuleContext {
		public BasicthetypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicthetype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterBasicthetype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitBasicthetype(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBasicthetype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BasicthetypeContext basicthetype() throws RecognitionException {
		BasicthetypeContext _localctx = new BasicthetypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_basicthetype);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(T__3);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__5) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << Identifier) | (1L << ConstString) | (1L << ConstInteger))) != 0)) {
				{
				{
				setState(93);
				stmt();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(99);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	 
		public StmtContext() { }
		public void copyFrom(StmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StmtexpressionContext extends StmtContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StmtexpressionContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtexpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtexpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtexpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtblockContext extends StmtContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StmtblockContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtblock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtblock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtblock(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtwhileContext extends StmtContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public StmtwhileContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtwhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtwhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtwhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtforContext extends StmtContext {
		public ExpressionContext pre;
		public ExpressionContext mid;
		public ExpressionContext suc;
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public StmtforContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtfor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtfor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtfor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtifContext extends StmtContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public StmtifContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtif(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtif(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtif(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtcontinueContext extends StmtContext {
		public StmtcontinueContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtcontinue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtcontinue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtcontinue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtreturnContext extends StmtContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StmtreturnContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtreturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtreturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtreturn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtblankContext extends StmtContext {
		public StmtblankContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtblank(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtblank(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtblank(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtbreakContext extends StmtContext {
		public StmtbreakContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtbreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtbreak(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtbreak(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtvariableContext extends StmtContext {
		public VariableDefineContext variableDefine() {
			return getRuleContext(VariableDefineContext.class,0);
		}
		public StmtvariableContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterStmtvariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitStmtvariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStmtvariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_stmt);
		int _la;
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new StmtblockContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				block();
				}
				break;
			case 2:
				_localctx = new StmtifContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				match(T__15);
				setState(103);
				match(T__5);
				setState(104);
				expression(0);
				setState(105);
				match(T__7);
				setState(106);
				stmt();
				setState(109);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(107);
					match(T__16);
					setState(108);
					stmt();
					}
					break;
				}
				}
				break;
			case 3:
				_localctx = new StmtwhileContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(111);
				match(T__17);
				setState(112);
				match(T__5);
				setState(113);
				expression(0);
				setState(114);
				match(T__7);
				setState(115);
				stmt();
				}
				break;
			case 4:
				_localctx = new StmtforContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(117);
				match(T__18);
				setState(118);
				match(T__5);
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << Identifier) | (1L << ConstString) | (1L << ConstInteger))) != 0)) {
					{
					setState(119);
					((StmtforContext)_localctx).pre = expression(0);
					}
				}

				setState(122);
				match(T__1);
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << Identifier) | (1L << ConstString) | (1L << ConstInteger))) != 0)) {
					{
					setState(123);
					((StmtforContext)_localctx).mid = expression(0);
					}
				}

				setState(126);
				match(T__1);
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << Identifier) | (1L << ConstString) | (1L << ConstInteger))) != 0)) {
					{
					setState(127);
					((StmtforContext)_localctx).suc = expression(0);
					}
				}

				setState(130);
				match(T__7);
				setState(131);
				stmt();
				}
				break;
			case 5:
				_localctx = new StmtbreakContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(132);
				match(T__19);
				setState(133);
				match(T__1);
				}
				break;
			case 6:
				_localctx = new StmtcontinueContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(134);
				match(T__20);
				setState(135);
				match(T__1);
				}
				break;
			case 7:
				_localctx = new StmtreturnContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(136);
				match(T__21);
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << Identifier) | (1L << ConstString) | (1L << ConstInteger))) != 0)) {
					{
					setState(137);
					expression(0);
					}
				}

				setState(140);
				match(T__1);
				}
				break;
			case 8:
				_localctx = new StmtexpressionContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(141);
				expression(0);
				setState(142);
				match(T__1);
				}
				break;
			case 9:
				_localctx = new StmtvariableContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(144);
				variableDefine();
				}
				break;
			case 10:
				_localctx = new StmtblankContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(145);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprnullContext extends ExpressionContext {
		public ExprnullContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprnull(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprnull(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprnull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprbracketsContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprbracketsContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprbrackets(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprbrackets(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprbrackets(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprstringContext extends ExpressionContext {
		public Token name;
		public TerminalNode ConstString() { return getToken(MxStarParser.ConstString, 0); }
		public ExprstringContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprstring(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprstring(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprstring(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprprefixContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprprefixContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprprefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprprefix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprprefix(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprassignContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExprassignContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprassign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprassign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprassign(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprthisContext extends ExpressionContext {
		public ExprthisContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprthis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprthis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprthis(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprnewContext extends ExpressionContext {
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public ExprnewContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprnew(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprnew(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprnew(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprorContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExprorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExpror(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExpror(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExpror(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprsuffixContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprsuffixContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprsuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprsuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprsuffix(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprfalseContext extends ExpressionContext {
		public ExprfalseContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprfalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprfalse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprfalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprsmemberContext extends ExpressionContext {
		public Token name;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public ExprsmemberContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprsmember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprsmember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprsmember(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprandContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExprandContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprfunctionContext extends ExpressionContext {
		public ExpressionContext name;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExprfunctionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprfunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprfunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprfunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpridentifierContext extends ExpressionContext {
		public Token name;
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public ExpridentifierContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExpridentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExpridentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExpridentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprintegerContext extends ExpressionContext {
		public Token name;
		public TerminalNode ConstInteger() { return getToken(MxStarParser.ConstInteger, 0); }
		public ExprintegerContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprinteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprinteger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprinteger(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprbinaryContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExprbinaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprbinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprbinary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprbinary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprtrueContext extends ExpressionContext {
		public ExprtrueContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprtrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprtrue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprtrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprexprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExprexprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				{
				_localctx = new ExprbracketsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(149);
				match(T__5);
				setState(150);
				expression(0);
				setState(151);
				match(T__7);
				}
				break;
			case Identifier:
				{
				_localctx = new ExpridentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(153);
				((ExpridentifierContext)_localctx).name = match(Identifier);
				}
				break;
			case ConstInteger:
				{
				_localctx = new ExprintegerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(154);
				((ExprintegerContext)_localctx).name = match(ConstInteger);
				}
				break;
			case ConstString:
				{
				_localctx = new ExprstringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(155);
				((ExprstringContext)_localctx).name = match(ConstString);
				}
				break;
			case T__22:
				{
				_localctx = new ExprnullContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(156);
				match(T__22);
				}
				break;
			case T__23:
				{
				_localctx = new ExprtrueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(157);
				match(T__23);
				}
				break;
			case T__24:
				{
				_localctx = new ExprfalseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(158);
				match(T__24);
				}
				break;
			case T__25:
				{
				_localctx = new ExprthisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(159);
				match(T__25);
				}
				break;
			case T__26:
				{
				_localctx = new ExprnewContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(160);
				match(T__26);
				setState(161);
				creator();
				}
				break;
			case T__28:
			case T__29:
			case T__30:
			case T__31:
			case T__32:
			case T__33:
				{
				_localctx = new ExprprefixContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(162);
				((ExprprefixContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33))) != 0)) ) {
					((ExprprefixContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(163);
				expression(13);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(227);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(225);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(166);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(167);
						((ExprbinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__34) | (1L << T__35) | (1L << T__36))) != 0)) ) {
							((ExprbinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(168);
						expression(12);
						}
						break;
					case 2:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(169);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(170);
						((ExprbinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__30 || _la==T__31) ) {
							((ExprbinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(171);
						expression(11);
						}
						break;
					case 3:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(172);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(173);
						((ExprbinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__37 || _la==T__38) ) {
							((ExprbinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(174);
						expression(10);
						}
						break;
					case 4:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(175);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(176);
						((ExprbinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42))) != 0)) ) {
							((ExprbinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(177);
						expression(9);
						}
						break;
					case 5:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(178);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(179);
						((ExprbinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__43 || _la==T__44) ) {
							((ExprbinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(180);
						expression(8);
						}
						break;
					case 6:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(181);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(182);
						((ExprbinaryContext)_localctx).op = match(T__45);
						setState(183);
						expression(7);
						}
						break;
					case 7:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(184);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(185);
						((ExprbinaryContext)_localctx).op = match(T__46);
						setState(186);
						expression(6);
						}
						break;
					case 8:
						{
						_localctx = new ExprbinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(187);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(188);
						((ExprbinaryContext)_localctx).op = match(T__47);
						setState(189);
						expression(5);
						}
						break;
					case 9:
						{
						_localctx = new ExprandContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(190);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(191);
						((ExprandContext)_localctx).op = match(T__48);
						setState(192);
						expression(4);
						}
						break;
					case 10:
						{
						_localctx = new ExprorContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(193);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(194);
						((ExprorContext)_localctx).op = match(T__49);
						setState(195);
						expression(3);
						}
						break;
					case 11:
						{
						_localctx = new ExprassignContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(196);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(197);
						match(T__0);
						setState(198);
						expression(1);
						}
						break;
					case 12:
						{
						_localctx = new ExprfunctionContext(new ExpressionContext(_parentctx, _parentState));
						((ExprfunctionContext)_localctx).name = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(199);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(213);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
						case T__5:
							{
							{
							setState(200);
							match(T__5);
							setState(209);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << Identifier) | (1L << ConstString) | (1L << ConstInteger))) != 0)) {
								{
								setState(201);
								expression(0);
								setState(206);
								_errHandler.sync(this);
								_la = _input.LA(1);
								while (_la==T__6) {
									{
									{
									setState(202);
									match(T__6);
									setState(203);
									expression(0);
									}
									}
									setState(208);
									_errHandler.sync(this);
									_la = _input.LA(1);
								}
								}
							}

							setState(211);
							match(T__7);
							}
							}
							break;
						case T__8:
							{
							{
							setState(212);
							match(T__8);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						}
						break;
					case 13:
						{
						_localctx = new ExprexprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(215);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(216);
						match(T__9);
						setState(217);
						expression(0);
						setState(218);
						match(T__10);
						}
						break;
					case 14:
						{
						_localctx = new ExprsmemberContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(220);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(221);
						match(T__27);
						setState(222);
						((ExprsmemberContext)_localctx).name = match(Identifier);
						}
						break;
					case 15:
						{
						_localctx = new ExprsuffixContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(223);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(224);
						((ExprsuffixContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__32 || _la==T__33) ) {
							((ExprsuffixContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(229);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CreatorContext extends ParserRuleContext {
		public Token name;
		public AllthetypeContext allthetype() {
			return getRuleContext(AllthetypeContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_creator);
		try {
			int _alt;
			setState(247);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(230);
				allthetype();
				{
				setState(235); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(231);
						match(T__9);
						setState(232);
						expression(0);
						setState(233);
						match(T__10);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(237); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(243);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(239);
						match(T__9);
						setState(240);
						match(T__10);
						}
						} 
					}
					setState(245);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(246);
				((CreatorContext)_localctx).name = match(Identifier);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		case 11:
			return precpred(_ctx, 16);
		case 12:
			return precpred(_ctx, 15);
		case 13:
			return precpred(_ctx, 14);
		case 14:
			return precpred(_ctx, 12);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\39\u00fc\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\7\2\36\n\2\f\2\16\2!\13\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\5\3)\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\7\4\62\n\4\f\4\16\4"+
		"\65\13\4\3\4\3\4\3\5\5\5:\n\5\3\5\3\5\3\5\3\5\3\5\7\5A\n\5\f\5\16\5D\13"+
		"\5\5\5F\n\5\3\5\3\5\5\5J\n\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\7\7T\n\7"+
		"\f\7\16\7W\13\7\3\b\3\b\5\b[\n\b\3\t\3\t\3\n\3\n\7\na\n\n\f\n\16\nd\13"+
		"\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13p\n\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13{\n\13\3\13\3\13\5\13\177\n\13"+
		"\3\13\3\13\5\13\u0083\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13"+
		"\u008d\n\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u0095\n\13\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00a7\n\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\7\f\u00cf\n\f\f\f\16\f\u00d2\13\f\5\f\u00d4\n\f\3\f\3\f\5\f\u00d8"+
		"\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u00e4\n\f\f\f\16\f\u00e7"+
		"\13\f\3\r\3\r\3\r\3\r\3\r\6\r\u00ee\n\r\r\r\16\r\u00ef\3\r\3\r\7\r\u00f4"+
		"\n\r\f\r\16\r\u00f7\13\r\3\r\5\r\u00fa\n\r\3\r\2\3\26\16\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\2\n\3\2\16\21\3\2\37$\3\2%\'\3\2!\"\3\2()\3\2*-\3\2./"+
		"\3\2#$\2\u0128\2\37\3\2\2\2\4$\3\2\2\2\6,\3\2\2\2\b9\3\2\2\2\nM\3\2\2"+
		"\2\fP\3\2\2\2\16Z\3\2\2\2\20\\\3\2\2\2\22^\3\2\2\2\24\u0094\3\2\2\2\26"+
		"\u00a6\3\2\2\2\30\u00f9\3\2\2\2\32\36\5\4\3\2\33\36\5\6\4\2\34\36\5\b"+
		"\5\2\35\32\3\2\2\2\35\33\3\2\2\2\35\34\3\2\2\2\36!\3\2\2\2\37\35\3\2\2"+
		"\2\37 \3\2\2\2 \"\3\2\2\2!\37\3\2\2\2\"#\7\2\2\3#\3\3\2\2\2$%\5\f\7\2"+
		"%(\7\65\2\2&\'\7\3\2\2\')\5\26\f\2(&\3\2\2\2()\3\2\2\2)*\3\2\2\2*+\7\4"+
		"\2\2+\5\3\2\2\2,-\7\5\2\2-.\7\65\2\2.\63\7\6\2\2/\62\5\4\3\2\60\62\5\b"+
		"\5\2\61/\3\2\2\2\61\60\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2"+
		"\2\64\66\3\2\2\2\65\63\3\2\2\2\66\67\7\7\2\2\67\7\3\2\2\28:\5\f\7\298"+
		"\3\2\2\29:\3\2\2\2:;\3\2\2\2;I\7\65\2\2<E\7\b\2\2=B\5\n\6\2>?\7\t\2\2"+
		"?A\5\n\6\2@>\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2CF\3\2\2\2DB\3\2\2\2"+
		"E=\3\2\2\2EF\3\2\2\2FG\3\2\2\2GJ\7\n\2\2HJ\7\13\2\2I<\3\2\2\2IH\3\2\2"+
		"\2JK\3\2\2\2KL\5\22\n\2L\t\3\2\2\2MN\5\f\7\2NO\7\65\2\2O\13\3\2\2\2PU"+
		"\5\16\b\2QR\7\f\2\2RT\7\r\2\2SQ\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2"+
		"V\r\3\2\2\2WU\3\2\2\2X[\5\20\t\2Y[\7\65\2\2ZX\3\2\2\2ZY\3\2\2\2[\17\3"+
		"\2\2\2\\]\t\2\2\2]\21\3\2\2\2^b\7\6\2\2_a\5\24\13\2`_\3\2\2\2ad\3\2\2"+
		"\2b`\3\2\2\2bc\3\2\2\2ce\3\2\2\2db\3\2\2\2ef\7\7\2\2f\23\3\2\2\2g\u0095"+
		"\5\22\n\2hi\7\22\2\2ij\7\b\2\2jk\5\26\f\2kl\7\n\2\2lo\5\24\13\2mn\7\23"+
		"\2\2np\5\24\13\2om\3\2\2\2op\3\2\2\2p\u0095\3\2\2\2qr\7\24\2\2rs\7\b\2"+
		"\2st\5\26\f\2tu\7\n\2\2uv\5\24\13\2v\u0095\3\2\2\2wx\7\25\2\2xz\7\b\2"+
		"\2y{\5\26\f\2zy\3\2\2\2z{\3\2\2\2{|\3\2\2\2|~\7\4\2\2}\177\5\26\f\2~}"+
		"\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082\7\4\2\2\u0081\u0083"+
		"\5\26\f\2\u0082\u0081\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2\2\2"+
		"\u0084\u0085\7\n\2\2\u0085\u0095\5\24\13\2\u0086\u0087\7\26\2\2\u0087"+
		"\u0095\7\4\2\2\u0088\u0089\7\27\2\2\u0089\u0095\7\4\2\2\u008a\u008c\7"+
		"\30\2\2\u008b\u008d\5\26\f\2\u008c\u008b\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\u0095\7\4\2\2\u008f\u0090\5\26\f\2\u0090\u0091\7"+
		"\4\2\2\u0091\u0095\3\2\2\2\u0092\u0095\5\4\3\2\u0093\u0095\7\4\2\2\u0094"+
		"g\3\2\2\2\u0094h\3\2\2\2\u0094q\3\2\2\2\u0094w\3\2\2\2\u0094\u0086\3\2"+
		"\2\2\u0094\u0088\3\2\2\2\u0094\u008a\3\2\2\2\u0094\u008f\3\2\2\2\u0094"+
		"\u0092\3\2\2\2\u0094\u0093\3\2\2\2\u0095\25\3\2\2\2\u0096\u0097\b\f\1"+
		"\2\u0097\u0098\7\b\2\2\u0098\u0099\5\26\f\2\u0099\u009a\7\n\2\2\u009a"+
		"\u00a7\3\2\2\2\u009b\u00a7\7\65\2\2\u009c\u00a7\7\67\2\2\u009d\u00a7\7"+
		"\66\2\2\u009e\u00a7\7\31\2\2\u009f\u00a7\7\32\2\2\u00a0\u00a7\7\33\2\2"+
		"\u00a1\u00a7\7\34\2\2\u00a2\u00a3\7\35\2\2\u00a3\u00a7\5\30\r\2\u00a4"+
		"\u00a5\t\3\2\2\u00a5\u00a7\5\26\f\17\u00a6\u0096\3\2\2\2\u00a6\u009b\3"+
		"\2\2\2\u00a6\u009c\3\2\2\2\u00a6\u009d\3\2\2\2\u00a6\u009e\3\2\2\2\u00a6"+
		"\u009f\3\2\2\2\u00a6\u00a0\3\2\2\2\u00a6\u00a1\3\2\2\2\u00a6\u00a2\3\2"+
		"\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00e5\3\2\2\2\u00a8\u00a9\f\r\2\2\u00a9"+
		"\u00aa\t\4\2\2\u00aa\u00e4\5\26\f\16\u00ab\u00ac\f\f\2\2\u00ac\u00ad\t"+
		"\5\2\2\u00ad\u00e4\5\26\f\r\u00ae\u00af\f\13\2\2\u00af\u00b0\t\6\2\2\u00b0"+
		"\u00e4\5\26\f\f\u00b1\u00b2\f\n\2\2\u00b2\u00b3\t\7\2\2\u00b3\u00e4\5"+
		"\26\f\13\u00b4\u00b5\f\t\2\2\u00b5\u00b6\t\b\2\2\u00b6\u00e4\5\26\f\n"+
		"\u00b7\u00b8\f\b\2\2\u00b8\u00b9\7\60\2\2\u00b9\u00e4\5\26\f\t\u00ba\u00bb"+
		"\f\7\2\2\u00bb\u00bc\7\61\2\2\u00bc\u00e4\5\26\f\b\u00bd\u00be\f\6\2\2"+
		"\u00be\u00bf\7\62\2\2\u00bf\u00e4\5\26\f\7\u00c0\u00c1\f\5\2\2\u00c1\u00c2"+
		"\7\63\2\2\u00c2\u00e4\5\26\f\6\u00c3\u00c4\f\4\2\2\u00c4\u00c5\7\64\2"+
		"\2\u00c5\u00e4\5\26\f\5\u00c6\u00c7\f\3\2\2\u00c7\u00c8\7\3\2\2\u00c8"+
		"\u00e4\5\26\f\3\u00c9\u00d7\f\22\2\2\u00ca\u00d3\7\b\2\2\u00cb\u00d0\5"+
		"\26\f\2\u00cc\u00cd\7\t\2\2\u00cd\u00cf\5\26\f\2\u00ce\u00cc\3\2\2\2\u00cf"+
		"\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d4\3\2"+
		"\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00cb\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4"+
		"\u00d5\3\2\2\2\u00d5\u00d8\7\n\2\2\u00d6\u00d8\7\13\2\2\u00d7\u00ca\3"+
		"\2\2\2\u00d7\u00d6\3\2\2\2\u00d8\u00e4\3\2\2\2\u00d9\u00da\f\21\2\2\u00da"+
		"\u00db\7\f\2\2\u00db\u00dc\5\26\f\2\u00dc\u00dd\7\r\2\2\u00dd\u00e4\3"+
		"\2\2\2\u00de\u00df\f\20\2\2\u00df\u00e0\7\36\2\2\u00e0\u00e4\7\65\2\2"+
		"\u00e1\u00e2\f\16\2\2\u00e2\u00e4\t\t\2\2\u00e3\u00a8\3\2\2\2\u00e3\u00ab"+
		"\3\2\2\2\u00e3\u00ae\3\2\2\2\u00e3\u00b1\3\2\2\2\u00e3\u00b4\3\2\2\2\u00e3"+
		"\u00b7\3\2\2\2\u00e3\u00ba\3\2\2\2\u00e3\u00bd\3\2\2\2\u00e3\u00c0\3\2"+
		"\2\2\u00e3\u00c3\3\2\2\2\u00e3\u00c6\3\2\2\2\u00e3\u00c9\3\2\2\2\u00e3"+
		"\u00d9\3\2\2\2\u00e3\u00de\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e7\3\2"+
		"\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\27\3\2\2\2\u00e7\u00e5"+
		"\3\2\2\2\u00e8\u00ed\5\16\b\2\u00e9\u00ea\7\f\2\2\u00ea\u00eb\5\26\f\2"+
		"\u00eb\u00ec\7\r\2\2\u00ec\u00ee\3\2\2\2\u00ed\u00e9\3\2\2\2\u00ee\u00ef"+
		"\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f5\3\2\2\2\u00f1"+
		"\u00f2\7\f\2\2\u00f2\u00f4\7\r\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00f7\3\2"+
		"\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00fa\3\2\2\2\u00f7"+
		"\u00f5\3\2\2\2\u00f8\u00fa\7\65\2\2\u00f9\u00e8\3\2\2\2\u00f9\u00f8\3"+
		"\2\2\2\u00fa\31\3\2\2\2\35\35\37(\61\639BEIUZboz~\u0082\u008c\u0094\u00a6"+
		"\u00d0\u00d3\u00d7\u00e3\u00e5\u00ef\u00f5\u00f9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}