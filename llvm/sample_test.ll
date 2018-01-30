target triple = "x86_64-unknown-linux-gnu"

@str = internal constant [4 x i8] c"%d\0A\00"

declare i32 @printf(i8*, ...)

define i32 @main() {
    %ac = add i32 3, 9
    %ab = mul i32 5, %ac
    %aa = add i32 %ab, 10
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str, i32 0, i32 0), i32 %aa)
    %af = add i32 2, 3
    %ae = add i32 %af, 4
    %ad = add i32 %ae, 5
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str, i32 0, i32 0), i32 %ad)
    ret i32 0
}