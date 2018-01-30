	.text
	.file	"sample_test.ll"
	.globl	main                    # -- Begin function main
	.p2align	4, 0x90
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# BB#0:
	movl	$17, %eax
	retq
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cfi_endproc
                                        # -- End function
	.type	.LC0,@object            # @.LC0
	.section	.rodata,"a",@progbits
.LC0:
	.asciz	"%d\n"
	.size	.LC0, 4


	.section	".note.GNU-stack","",@progbits
