Java
[[Java#Java三大特征]]
[[Java#抽象类与接口]]
[[Java#Final、Static、Synchronize]]
[[Java#Java成员变量，局部变量和静态变量的创建和回收时机]]
[[Java#强引用，弱引用，软引用，虚引用]]
[[Java#String、StringBuffer、StringBuilder]]
[[Java#List,Set,Map的区别]]
[[Java#注解]]
[[Java#反射]]
[[Java#泛型]]
[[Java#协变与逆变]]
[[Java#GC垃圾回收]]
[[Java#类加载机制]]
[[Java#协变与逆变]]
[[Java#Jni]]

android
[[Android#四大组件]]
[[Android#版本适配]]
[[Android#屏幕适配]]
[[Android#文件存储]]
[[Android#优化]]
[[Android#Handler机制]]
[[Android#Veiw绘制]]
[[Android#事件分发]]

net
[[网络#什么是TCP/IP四层模型？]]
[[网络#长连接]]
[[TCP]]
[[UDP]]
[[Http#HTTP是什么?]]
[[Http#什么是Header，常用的几种Header]]
[[Http#Http和Https的区别]]
[[Http#Http1.1和Http2.0的区别]]
[[Http#Https的加密过程]]

杂项
[[Kotlin#协程]]
[[Java#什么叫精度损失？]]
[[Android#关于登录模块登录页的实现思路]]
[[三方框架与架构搭建#MVC、MVP、MVVM的区别]]
[[三方框架与架构搭建#Glide原理]]
[[Okhttp#Okhttp及网络架构搭建]]

小知识
单与号双与号区别。
equal和 == 比较对象时的区别。
equals为true时hashcode会有相等的吗?

## equals为true时hashcode会有相等的吗
Java规范要求

**equals与hashCode的强制约定**（摘自Object类文档）：

如果两个对象通过equals方法比较相等，那么它们的hashCode值必须相同。
反之不成立：hashCode相同，equals不一定为true（哈希冲突时）。

在Java中，若两个对象的equals方法返回true，则它们的hashCode必须相同。这是Java对象相等性契约的强制要求，主要为了确保哈希表（如HashMap、HashSet）的正确性。若违反此规则，会导致对象在哈希表中无法被正确检索，即使equals认为它们相等。设计类时，应始终同时重写equals和hashCode，并保证逻辑一致。


char类型可不可以存汉字。
Java基础类型及所占字节数。
转类型时字节溢出怎么处理?
抽象方法可以静态吗? 
Java中有几种异常，ArrayList内部如何实现的。
Java泛型有什么了解?
泛型擦除有没有了解。
对多态的理解。什么是面向对象。什么是重载，什么是重写，子类中有一个父类中同方法名的方法但参数不同他是重载还是重写?
返回值不同是重载吗?抽象类与接口区别。说说Java的反射。
动态代理和静态代理有用过吗?
普通代码块静态代码块构造代码块。
Java引用类型有哪几种，区别?
Java有哪几种流多线程有用过吗?
创建线程的几种方式，wait和sleep区别。
说说你知道的设计模式。
说说Java的垃圾回收机制，知道最新的回收算法吗?
说说类加载机制。
activity启动模式的区别。两个service的启动方式。说说广播。广播是怎么发送和接受的，比如你发了个广播，为什么另一个APP可以接收到。
用过provider吗?他是怎么实现的。传递数据有限制吗，比如startactivity。Android中有哪几种类型的进程。
事件分发，怎么避免滑动冲突。
anr遇到过吗。
handler怎么实现的，handler做定时器的缺点是什么?
handler中如果有两个massage执行实现一样，他们的顺序是什么?
binder通信。
http和https，
tcp三次握手四次挥手。
okhttp和retrofit源码。
新特性。图片压缩原理，三级缓存，lrucache算法具体的实现?他是用了什么策略?
sp的原理，他可以在ui线程调用吗?
用过c语言吗?MVC与MVP。APP启动流程。
RecyclerView的复用机制，他是复用了整个布局还是根View？为什么要复用？ ​节省的不是内存，而是对象创建成本