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
