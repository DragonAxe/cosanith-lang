

grammar Hello;
prog:  (expr NEWLINE)* ;
expr:  expr (MUL|DIV) expr
    |  expr (ADD|SUB) expr
    |  INT
    |  '(' expr ')'
    ;
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
NEWLINE : [\r\n]+ ;
INT     : [0-9]+ ;
