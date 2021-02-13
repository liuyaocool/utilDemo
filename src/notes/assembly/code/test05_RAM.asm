assume cs:code

; 更灵活定位内存地址

; bx 偏移地址寄存器
; si
; di

code segment

	start:		mov bx,0
				mov si,0

				mov ax,ds:[si]

				inc si
				mov ax,ds:[bx+si]

				mov di,0
				mov ax,ds:[bx+di]

				mov bx,2
				mov al,ds:[bx+5]

			mov ax,4c00H
			int 21h
code ends

end start