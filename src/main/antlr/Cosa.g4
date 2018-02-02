grammar Cosa;
tmp: TMP ;
TMP: .* ;
module: NEWLINE* funcdef (NEWLINE funcdef)* NEWLINE* EOF ;
funcdef: '(def' WHITESPACE* (WHITESPACE+ (IDENT))* ')' ;
//func: '(' FUNCDEF ')' ;
id : IDENT
   | QUOTEDSTRING ;
IDENT : [a-zA-Z0-9]+;
//ADD : '+';
//SUB : '-';
//MUL : '*';
//DIV : '/';
QUOTEDSTRING : '"' ('\\''"' | ~'"')* '"' ;
WHITESPACE : [ \r\n]+ ;
NEWLINE : [\r\n]+ ;
INT     : [0-9]+ ;

// (def main
//     (Strings args) (Int)
//     (if (> args.length 0)
//         (print (args.get 1))
//         (print "No parameters")))
