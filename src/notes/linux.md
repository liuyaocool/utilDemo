# 公钥私钥免密登录

- 1 客户机执行命令 ssh-keygen -t rsa -P "",  一路回车
- 2 客户机执行命令ssh-copy-id 用户名@ip, 输入密码
- 3 ssh免密登录即可

# 内核-接口关系

( 程序或服务... 	(系统调用接口...	(内核...	(硬件...))))

# shell

可直接调用内核

## 查看支持shell

- cat /etc/shells  --解析器 解析哪些shell
- echo $SHELL  --查看当前正使用

## 与终端的区别

终端输入 → shell → 调用内核

