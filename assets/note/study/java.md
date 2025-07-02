
### 1. `javac` 和 `java` 命令​
- `javac` 编译 `.java` 文件生成 `.class` 字节码文件
- `java` 命令用于运行编译后的类文件
    
        ```bash
        javac Main.java    # 编译
        java Main         # 运行
        ```
-  java语言的解释器是java命令，即JVM启动器，它是JVM的一部分，负责解释执行.class字节码文件

---
### 2. Java基础
1. ​**`i++` 和 `++i` 的区别**​
    
    - `i++`（后置递增）：返回 ​**自增前的值**，然后变量 `i` 加 1
    - `++i`（前置递增）：先对 `i` 加 1，然后返回 ​**自增后的值**​
        ```java
        int i = 5;
        int a = i++; // a = 5, i = 6
        int b = ++i; // b = 7, i = 7
		```
		
2. ​**Java 继承链中的类型转换**​
    
    - ​**父子类可以转换**​（`ClassCastException`）：
        
        ```java
        Parent p = new Child();  // 向上转型（安全）
        Child c = (Child)p;      // 向下转型（需确保 p 实际指向 Child）
        ```
        
    - ​**兄弟类不能直接强转**​：
        
        ```java
        Child1 c1 = new Child1();
        Child2 c2 = (Child2)c1; // 抛出 ClassCastException
        ```
3. 构造方法
	- **作用**​：在创建对象时初始化成员变量
	- ​**特点**​：
	    - 必须与类同名
	    - 无返回值类型（连 `void` 都不写）
	    - 默认提供无参构造（若未显式定义）
	- ​**重载规则**​：
	    - 方法名相同
	    - 参数列表不同（类型/数量/顺序）
	    - 常用于提供多种对象初始化方式
	    
4. String vs StringBuffer
| 特性         | String                | StringBuffer          |
| ---------- | --------------------- | --------------------- |
| ​**可变性**​  | 不可变（final char[]）     | 可变（动态扩容char[]）        |
| ​**线程安全**​ | 天然线程安全                | 线程安全（方法加synchronized） |
| ​**性能**​   | 高频修改会产生大量对象           | 适合频繁修改                |
| ​**内存占用**​ | 可能产生未回收的字符串常量         | 动态调整缓冲区大小             |
| ​**典型用法**​ | `String s = "hello";` | `sb.append("world");` |
​**使用场景**​：
- ​**String**​：字符串常量、配置信息等不需要修改的场景
- ​**StringBuffer**​：多线程环境下的字符串拼接（Java 5+推荐用 `StringBuilder` 替代单线程场景）

5. java的方法参数传递是值传递机制，基本类型传递值的副本，引用类型传递引用的副本，即修改时基本对象不会影响原始值，引用类型会影响原始对象，即使是通过new String("");创建的也属于基本类型
6. 接口中的所有变量默认为public static final，即常量
7. 用于读取字节流的抽象类是InputStream

---
### 2. Java GUI
1. ​**Java GUI 的顶层容器**​
    
    - `JFrame` 是带标题栏、边框和控制按钮的独立窗口，​**最常用于主窗口**​
        
        ```java
        JFrame frame = new JFrame("标题");
        frame.setSize(400, 300);
        frame.setVisible(true);
        ```
    -  为JButton注册事件的方法是addActionListener()
	-  绘制直线需要使用paintComponent()方法


---


### 3. Java线程

线程除了继承thread类，还可以实现Runnable接口创建
#### 线程状态
Java 线程生命周期通过 `Thread.State` 枚举定义：

图片代码

​**状态详解**​：

1. ​**NEW**​：刚创建未启动
    
    ```java
    Thread t = new Thread(); // 此时状态为NEW
    ```
    
2. ​**RUNNABLE**​：可运行（包括就绪和运行中）
3. ​**BLOCKED**​：等待监视器锁（同步代码块）
4. ​**WAITING**​：无限期等待（需其他线程显式唤醒）
5. ​**TIMED_WAITING**​：限期等待（带超时参数的方法）
6. ​**TERMINATED**​：线程执行完毕

​**状态检查方法**​：

```java
Thread thread = new Thread(/*...*/);
Thread.State state = thread.getState(); // 获取当前状态
```

---

