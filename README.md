# Cosanith Lang

![Cosanith The Dragon Logo](CosanithTheDragon.png)

This is my attempt to create a parenthesis based language
that compiles to llvm intermediate representation.

## Why the name Cosanith?
I basically went to [fantacynamegenerators.com/dragon_names][1]
and found a name I liked, then added a bit to the end to make
it a little more unique, and ended up with 'Cosanith'.

[1]: http://www.fantasynamegenerators.com/dragon_names.php

## Sample llvm ir Output

The following is the sample input and output to my compiler as of 2018-01-29:

Input:

    100+2*34
    (10*8)-(2+3)

Output:

    target triple = "x86_64-unknown-linux-gnu"

    @str = internal constant [4 x i8] c"%d\0A\00"

    declare i32 @printf(i8*, ...)

    define i32 @main() {
        %ab = mul i32 2, 34
        %aa = add i32 100, %ab
        call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str, i32 0, i32 0), i32 %aa)
        %ad = mul i32 10, 8
        %ae = add i32 2, 3
        %ac = sub i32 %ad, %ae
        call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str, i32 0, i32 0), i32 %ac)
        ret i32 0
    }


## Old Sample Output
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
