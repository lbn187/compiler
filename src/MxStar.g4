grammar MxStar;
program: (variableDefine|classPart|functionPart)* EOF;
variableDefine: thetype name=Identifier ('=' expression)? ';';
classPart: 'class' name=Identifier '{' (variableDefine|functionPart)* '}';
functionPart: thetype? name=Identifier (('(' (parameter (',' parameter)*)? ')')|('()')) block;
parameter: thetype name=Identifier;
thetype: allthetype ('['']')*;
allthetype: basicthetype | name=Identifier;
basicthetype: 'int' | 'bool' | 'string' | 'void';
block: '{' stmt* '}';
stmt: block                                                                          # stmtblock
		| 'if' '(' expression ')' stmt ('else' stmt)?                                # stmtif
		| 'while' '(' expression ')' stmt                                            # stmtwhile
		| 'for' '(' pre=expression? ';' mid=expression? ';' suc=expression? ')' stmt # stmtfor
		| 'break' ';'                                                                # stmtbreak
		| 'continue' ';'                                                             # stmtcontinue
		| 'return' expression? ';'                                                   # stmtreturn
		| expression ';'                                                             # stmtexpression
		| variableDefine                                                             # stmtvariable
		| ';'                                                                        # stmtblank
		;
expression: '(' expression ')'                                                       # exprbrackets
			| name=Identifier                                                        # expridentifier
			| name=ConstInteger                                                      # exprinteger
			| name=ConstString                                                       # exprstring
			| 'null'                                                                 # exprnull
			| 'true'                                                                 # exprtrue
			| 'false'                                                                # exprfalse
			| 'this'                                                                 # exprthis
            | 'new' creator                                                          # exprnew
            | name=Identifier  (('(' (expression (',' expression)*)? ')')|('()'))    # exprfunction
			| expression '[' expression ']'                                          # exprexpr
			| expression '.' expression                                              # exprsmember
			| op=('!'|'~'|'+'|'-'|'++'|'--') expression                              # exprprefix
			| expression op=('++'|'--')                                              # exprsuffix
			| expression op=('*'|'/'|'%') expression                                 # exprbinary
			| expression op=('+'|'-') expression                                     # exprbinary
			| expression op=('<<'|'>>') expression                                   # exprbinary
			| expression op=('<'|'<='|'>'|'>=') expression                           # exprbinary
			| expression op=('=='|'!=') expression                                   # exprbinary
			| expression op='&' expression                                           # exprbinary
			| expression op='^' expression                                           # exprbinary
			| expression op='|' expression                                           # exprbinary
			| expression op='&&' expression                                          # exprbinary
			| expression op='||' expression                                          # exprbinary
			| <assoc=right> expression '=' expression                                # exprassign
			;
creator: allthetype (('['expression']')+ ('['']')*)
        | name=Identifier (('(' ')')|('()'))?
        ;
Identifier: [a-zA-Z][a-zA-Z0-9_]*;
ConstString: '"'Char*?'"';
ConstInteger: [1-9][0-9]* | '0';
fragment Char: ~["\\\n]|'\\'["n\\];
WS: [ \t\r\n]+ -> skip;
Comment: '//' .*? '\r'? '\n' ->skip;
Comment2: '/*' .*? '*/' -> skip;
