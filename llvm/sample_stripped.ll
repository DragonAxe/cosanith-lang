target triple = "x86_64-unknown-linux-gnu"

@str = internal constant [4 x i8] c"%d\0A\00"

declare i32 @printf(i8*, ...)

define i32 @main() {
    %a = mul i32 4, 3
    %b = add i32 5, %a
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str, i32 0, i32 0), i32 %b)
    ret i32 0
}

