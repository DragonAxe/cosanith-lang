# Cosanith Lang

This is my attempt to create a parenthesis based language
that compiles to llvm intermediate representation.

Here is some sample output as of 2018-01-27:

    New Program: 5*(3+9)+(10)
    2+3+4+5
    
    New Expression: 5*(3+9)+(10) ChildCount:3 RuleIndex:1
    Pushed Opp
    New Expression: 5*(3+9) ChildCount:3 RuleIndex:1
    Pushed Opp
    New Expression: 5 ChildCount:1 RuleIndex:1
    New Terminal: 5
    (nullopp 5 null)
    New Terminal: *
    (* 5 null)
    New Expression: (3+9) ChildCount:3 RuleIndex:1
    New Terminal: (
    New Expression: 3+9 ChildCount:3 RuleIndex:1
    Pushed Opp
    New Expression: 3 ChildCount:1 RuleIndex:1
    New Terminal: 3
    (nullopp 3 null)
    New Terminal: +
    (+ 3 null)
    New Expression: 9 ChildCount:1 RuleIndex:1
    New Terminal: 9
    (+ 3 9)
    Popped Opp: (+ 3 9)
    New Terminal: )
    Popped Opp: (* 5 (+ 3 9))
    New Terminal: +
    (+ (* 5 (+ 3 9)) null)
    New Expression: (10) ChildCount:3 RuleIndex:1
    New Terminal: (
    New Expression: 10 ChildCount:1 RuleIndex:1
    New Terminal: 10
    (+ (* 5 (+ 3 9)) 10)
    New Terminal: )
    Popped Opp: (+ (* 5 (+ 3 9)) 10)
    New Terminal: 
    
    New Expression: 2+3+4+5 ChildCount:3 RuleIndex:1
    Pushed Opp
    New Expression: 2+3+4 ChildCount:3 RuleIndex:1
    Pushed Opp
    New Expression: 2+3 ChildCount:3 RuleIndex:1
    Pushed Opp
    New Expression: 2 ChildCount:1 RuleIndex:1
    New Terminal: 2
    (nullopp 2 null)
    New Terminal: +
    (+ 2 null)
    New Expression: 3 ChildCount:1 RuleIndex:1
    New Terminal: 3
    (+ 2 3)
    Popped Opp: (+ 2 3)
    New Terminal: +
    (+ (+ 2 3) null)
    New Expression: 4 ChildCount:1 RuleIndex:1
    New Terminal: 4
    (+ (+ 2 3) 4)
    Popped Opp: (+ (+ 2 3) 4)
    New Terminal: +
    (+ (+ (+ 2 3) 4) null)
    New Expression: 5 ChildCount:1 RuleIndex:1
    New Terminal: 5
    (+ (+ (+ 2 3) 4) 5)
    Popped Opp: (+ (+ (+ 2 3) 4) 5)
    New Terminal: 
    
    (+ (* 5 (+ 3 9)) 10) = 70
    (+ (+ (+ 2 3) 4) 5) = 14
    
    Process finished with exit code 0
