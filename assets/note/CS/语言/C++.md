### 概述
C++是以[[C]]语言为基础发展而来，与[[CS/语言/Java]]一样是面向对象的高级程序设计语言。一般用于底层开发/系统开发：比如Android [[Framework]]、JVM、C#的CLR、Python编译器；游戏开发：比如UE5；网络开发；服务端开发；嵌入式开发；
C++几乎完全兼容C语言，即保持的C的优点，又对C的类型进行了改革和扩充，他们的关系类似[子集](语言#子集)(C语言)和[超集](语言#超集)(C++)的概念。
优点：
* C++的编译系统能检查出更多的类型错误。
* [面向对象](语言#面向对象)
* 凭借对C的兼容可以嵌入汇编语言，调用底层

### 关键字
```
//宏定义
/宏名全部用字符串代替，宏名一般用大写字母，类似JAVA的静态常量
# define PI 3.14159

inline 内联函数
类似kotlin 但在C++中是内存换时间
一般，执行内容少但是频繁地可以把他设为内联函数
本质是把执行的函数复制一份副本置于当前函数，避免了跳转和回调函数地址的操作。

开发者请求将函数作为内联函数时,编译系统并不一定会响应需求。如果函数过大或者函数用于递归,编译系统将不会把该函数作为内联函数,甚至部分编译系统不支持内联函数这一特性。

内联函数是C++新增的特性。C语言可以使用预处理命令#define来提供宏,宏也可看作内联代码的原始实现。

宏没有进行参数传递，而是进行的文本替换，比如->
b=SQUARE(6.5+7.5);//等同于b=6.5+7.5×6.5+7.5

//文件包含
# include<文件名>
# include"[路径]文件名"
第一种直接到C++系统目录查找被包含文件
第二种首先回去指定路径查找，其次再去系统目录

//全局变量
有同名局部变量的情况下，可以通过::bianliang访问全局变量


```

### 数据类型

____
<table>
	<tr>
		<td>类别</td>
		<td>数据类型</td>
		<td>字节数</td>
		<td>数据表达范围</td>
	</tr>
	<tr>
	    <td>bool布尔型</td>
	    <td>bool</td>
	    <td>1</td>
	    <td>false,true(0,1)</td>
	</tr>
  <tr>
    <td rowspan="9"; style="text-align: center; vertical-align: middle;">int整型</td>
    <td>基本型(int)</td>
    <td>4</td>
    <td>-2147483648~2147483647</td>
  </tr>
  <tr>
    <td>短整型(short)</td>
    <td>2</td>
    <td>-32768~32767</td>
  </tr>
  <tr>
    <td>长整型(long)</td>
    <td>4</td>
    <td>-2147483648~2147483647</td>
  </tr>
  <tr>
    <td>无符号整型(unsigned [int])</td>
    <td>4</td>
    <td>0~4294967295</td>
  </tr>
  <tr>
    <td>无符号短整型(unsigned short [int])</td>
    <td>2</td>
    <td>0~65535</td>
  </tr>
  <tr>
    <td>无符号长整型(unsigned long [int])</td>
    <td>4</td>
    <td>0~4294967295</td>
  </tr>
  <tr>
    <td>有符号整形(signed [int])</td>
    <td>4</td>
    <td>-2147483648~2147483647</td>
  </tr>
  <tr>
    <td>有符号短整型([signed] short [int])</td>
    <td>2</td>
    <td>-32768~32767</td>
  </tr>
  <tr>
    <td>有符号长整型([signed] long [int])</td>
    <td>4</td>
    <td>-2147483648~2147483647</td>
  </tr>
  <tr>
	<td rowspan="3"; style="text-align: center; vertical-align: middle;">char 字符</td>
    <td>无符号 unsigned char</td>
    <td>1</td>
    <td>0~255</td>
  </tr>
  <tr>
	<td>有符号 [signed] char</td>
    <td>1</td>
    <td>-128~127</td>
  </tr>
   <tr>
	<td>wchar_t 宽字符</td>
    <td>2</td>
    <td>0~65 535</td>
  </tr>
   <tr>
    <td rowspan="3"; style="text-align: center; vertical-align: middle;">float 实型</td>
    <td>单精度浮点型(float)</td>
    <td>4</td>
    <td>-3.4x1038~3.4x1038</td>
  </tr>
  <tr>
	<td>双精度浮点型(double)</td>
    <td>8</td>
    <td>-1.7x10308~1.7x10308</td>
  </tr>
   <tr>
	<td>长双精度浮点型(long double)</td>
    <td>10 or 8</td>
    <td>-1.7x10308~1.7x10308</td>
  </tr>
</table>
表中的[]表示默认，代表括号中的内容可选，如为空，默认就是括号内数据。

常量通常是指在程序运行过程中不能被改变的量。常量可以是任何的基本数据类型,可以为整型数字、浮点数字、字符、字符串和布尔值。常量包括字面常量、字符常量、符号常量、枚举常量等多种类型。

Java中的常量在C++中对应常变量，类似于const int a = 1