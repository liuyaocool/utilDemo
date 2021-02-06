# 环境准备

## DOSBox

- www.dosbox.com → Latest Version: 0.74-3 点进去 下载 → 安装
- 创建文件夹d:\asm 
- 将debug.exe link.exe masm.exe放到此文件夹
- 输入 mount c: d:\asm
- 输入 c: 
- 快速设置
  - dosbox 开启时有个黑窗口，里边有个配置文件： C:\Users\Administrator\AppData\Local\DOSBox\dosbox-0.74-3.conf
  - 在末尾 [autoexec]  后
  - 输入 mount c: d:\asm 回车
  - 输入 c:  回车
- 输入debug 就可以使用了

## winxp

- link.exe masm.exe 拷贝到xp（可加环境变量快速启动）
- 安装 sublime text 32位编辑器

# 汇编基础知识

## 字节型

1byte 内存中最小单元 如：0A是1个字节

- 1byte = 8bit    1个字节 = 2个16进制位 = 8个2进制位 （0100 0101）
- 1kb = 1024byte

## 字形

2byte 

## debug

- 黑色界面是内存
  - 主内存（内存条）：绝大多数指令和数据的存放位置
  - CPU中存放了一部分指令和数据
- u命令   0AE4:0144 750C	JNZ	0152  查看机器指令 和 汇编指令
  - 0AE4:0144 --内存编号
  - 0144  --编号信息
  - 705C  --机器指令
  - JNZ 0152  --汇编指令
- d命令  查看内存数据
- e命令
- r命令  查看寄存器的值

## CPU

### 总线 

一根代表1bit

- 地址总线：寻址（内存）能力 一次性寻址的大小
- 数据总线：一次性传送数据的能力
- 控制总线：



## 内存空间

### 内存条 

内存中一部分地址

### 显存

内存中不同于内存条的一部分地址

### RAM ROM  

- RAM：读/写，断电数据丢失
- ROM：读，断电数据还在，一般用在启动计算机上面

### 端口

端口号 60H	

input out 指令 	就是端口的指令

### 鼠标、键盘等外设

cpu通过端口访问这些外设

端口（port  港口 装货和卸货）：读写数据

外设上有芯片，可以存储指令和数据

cpu读取外设指令的过程：

- 键盘按下A → 通过芯片传送给电脑端口
- 端口——主板三根总线——CPU
- CPU则可以通过上行方式拿到端口的指令和数据

# 寄存器

存放 地址信息 和 数据信息 的地方

## 8O86CPU

16位 可以存放两个字节 有14个寄存器  AX BX CX DX SI DI SP BP IP CS SS DS ED PSW

## 通用寄存器

存放数据的 数据寄存器 AX BX CX DX

- AX = AH(高8位) + AL(低8位)
  - 高8位与低8位是为了兼容上一代cpu而设置
- BX = BH + BL
- CX = CH + CL
- DX = DH + DL



# 进度

https://www.bilibili.com/video/BV1mt411R7Xv?p=38&spm_id_from=pageDriver









