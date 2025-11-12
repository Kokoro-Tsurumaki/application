### Gradle是什么

Gradle是一个构建工具，一般用于[[Android]] APP与[[Java]]的编译。

### Gradle配置文件拆解

Gradle配置本质上就是方法调用，只不过它的语法可以去掉()，所以不容易看出来是方法。

### Compile, Implementation 和 Api的区别

implementation：不会传递依赖，即A依赖B，B依赖C，若B依赖C使用implementation则A访问不了C的方法。

compile/api：会传递依赖；api是compile的替代品，效果完全等同 ，会传递依赖。

当依赖被传递时，C的改动会导致A重新编译；当依赖不传递时,C的改动不会导致A重新编译

### Gradle执行的生命周期

Gradle的执行有三个阶段：

第一阶段是初始化阶段，执行setting.gradle.kts文件确定主项目与子项目以及项目名字。

第二阶段是定义阶段，执行每个project的build.gradle.kts，确认所有task所组成的有向无环图。

第三阶段就是执行阶段，按照上一阶段确定的有向无环图来执行指定的task。

### Gradle中Task的格式

一个task分为三个部分，一个是task函数，task的doFirst函数，task的doLast函数。

普通代码段：在task创建过程中就会被执行，发生在定义阶段

doFirst()和doLast()：在 task 执行过程中被执行，如果用户没有直接或间接执行 task，那么它的doLast()doFirst()代码 不会被执行doFirst()和doLast()都是task代码，其中 doFirst() 是往队列的前面插入代码，doLast() 是往队列的后面插入代码。

### Gradle中语法

  
