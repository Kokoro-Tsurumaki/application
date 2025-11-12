 	kotlin改进了[[Java]]许多缺点并提供了更多方便开发的语法糖与API，用于[[Android]] App开发
### Kotlin属性与类

Kotlin的声明与Java不同，Java的声明是类型跟变量名，常量用final修饰，而Kotlin的声明是通变var(常量)，val(常量)，而它的类型在变量名后，如下：val number:int = 200

Kotlin有类型推导的功能：如果某个变量或常量的值可以确定他的类型，那么可以不用定义类型，


   在Java中声明对象需要通过new，如：People people = new People()。而在Kotlin中的对象声明可以直接声明，如：val people = People()。

   在kotlin中的继承与实现都是通过：来实现，类是:T()接口是:T。

关于属性赋值，getter () = 会使每次获取都是新的对象。

### Kotlin的空安全

Kotlin中的每个类型除了基础类型外，还会有一个与基础类型相对的非空类型如下，Int：不可空类型，Int?：可空类型，在给Int类型赋值时，不可以赋值null，或者是有可能为null的类型，如Int?，但是Int?可以赋值Int类型。比如，var a:Int = 10，var b:Int? = 20，b=a是正确写法，而a=b会不通过编译器的检查。

为了防止空指针，在使用T?(可为空类型，下文将用T?代替)类型时将会有一些约束，比如
```kotlin
val textView:TextView = findViewById<TextView>(R.id.textView)

textView.setText("Hello World")
```
这行代码将无法通过编译器检查

需要通过两种调用符修饰，！！强行调用符，？安全调用符，使用!!修饰的话会和java一样会出现空指针异常，使用?的话就是非空执行，如果该对象不为空的话，才会执行后面的操作。

 在Java中，是通过注解来实现一个类型的可空和非空。@Nullable 表示可空类型 @NotNull @NonNul l 表示不可空类型。

### Lateinit关键字

* 只能修饰 var 可读可写变量，

* 关键字声明的变量的类型必须是不可空类型

* 声明的变量不能有初始值

* 声明的变量不能是基本数据类型

* 在构造器中初始化的属性不需要 lateinit 关键字

### 类型的判断与转换

* is 判断属于某类型

* !is 判断不属于某类型

 * as 类型强转，失败时抛出类型强转失败异常

* as? 类型强转，但失败时不会抛出异常而是返回 null

### 构造器

使用constructor关键字声明构造器

主构造：

Class User constructor(name:String){}

次构造：

Class User {

constructor()

}

构造属性 在主构造参数前面加上 var/val 使构造参数同时成为成员变量

Class User constructor(val name:String){}

如果我们在构造器主动调用了了父类构造，那么在继承类的时候就不能在类的后面加上小括号

constructor(context: Context) : this(context, null)

// 主动调用了父类的构造器

constructor(context: Context, attr: AttributeSet?) : super(context, attr)

### init代码块

主构造不能包含任何的代码，初始化代码可以放到 init 代码块中

在初始化的时候，初始化块会按照它们在「文件中出现的顺序」执行。

### 协程

是kotlin提供的轻量级线程机制，用于简化异步和并发任务的处理。可以使异步操作像同步操作一样编写，提高可读性。

关于他和线程的区别，他是在已有线程中进行任务调度，可以在同一个线程中调度成多个携程，不需要为每个任务分配新线程，通过挂起和恢复机制实现异步操作。

协程通过launch和async启动，launch是一个不返回结果的协程，async是一个返回结果的协程，返回一个deferred对象，通过await方法获取协程的返回值

Suspend可以在协程内部暂停

runBlocking会阻塞当前线程 直到协程执行完成，通常是在主线程使用

withContext用于在协程内部切换上下文线程

常用的调度器：

* Dispatchers.main-主线程

* Dispatchers.IO-适用于IO操作

* Dispatchers.Default-适用于计算密集型任务(数据处理、图像处理)，默认线程池调度器

* Dispatchers.Unconfined-没有固定线程的调度器，通常不依赖于具体线程的协程

作用域:
GlobalScope：全局作用域，协程生命周期与整个应用进程绑定。
- 协程不会自动取消，即使调用它的组件（如 Activity）已被销毁。
- 容易导致内存泄漏，**不推荐在 Android 开发中使用**。

CoroutineScope：通过手动创建作用域，控制协程的生命周期。
- 需要手动调用 `cancel()` 来取消协程。
- 可以指定调度器（如 `Dispatchers.IO`、`Dispatchers.Main`）。

viewModelScope（ViewModel 作用域）：与 `ViewModel` 生命周期绑定，当 `ViewModel` 被销毁时自动取消所有协程。
- 默认使用 `Dispatchers.Main`，适合在 `ViewModel` 中执行 UI 相关操作。
- 避免内存泄漏，**推荐在 MVVM 架构中使用**。

lifecycleScope（Activity/Fragment 作用域)
与 `Activity` 或 `Fragment` 的生命周期绑定，当组件销毁时自动取消协程。
- 提供更细粒度的生命周期控制（如 `launchWhenStarted`、`launchWhenResumed`）。
- 默认使用 `Dispatchers.Main`，适合在 UI 组件中使用。

MainScope 一个预定义的主线程作用域，需要手动管理生命周期。
- 适用于非 Android 环境（如纯 Kotlin 项目）或需要手动控制生命周期的场景。
- 在 Android 中不如 `viewModelScope` 或 `lifecycleScope` 方便。


### 常用关键字

Infix：中缀表达式

infix fun Int.add(other: Int) = this + other

使用：
``` kotlin
val result1 = 1 add 2  // 等同于 1.add(2)

operator：运算符重载

operator fun Int.plus(other: String) = "$this$other"

// 使用：

val result2 = 1 + "2"  // 输出："12"

// 3. inline：内联函数
inline fun doSomething(action: () -> Unit) {

    action()

}

// 编译时会将函数体直接插入到调用处
```



### 常用集合关键字

```kotlin
fun collectionOperatorsExample() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val words = listOf("apple", "banana", "cherry")
    
    // 1. map: 转换每个元素
    val doubled = numbers.map { it * 2 }
    println("map: $doubled")  // [2, 4, 6, 8, 10]
    
    // 2. flatMap: 转换并展平结果
    val letters = words.flatMap { it.toList() }
    println("flatMap: $letters")  // [a, p, p, l, e, b, a, n, a, n, a, c, h, e, r, r, y]
    
    // 3. filter: 过滤元素
    val evenNumbers = numbers.filter { it % 2 == 0 }
    println("filter: $evenNumbers")  // [2, 4]
    
    // 4. forEach: 遍历每个元素
    numbers.forEach { println("forEach: $it") }
    
    // 5. find/firstOrNull: 查找第一个匹配的元素
    val firstEven = numbers.find { it % 2 == 0 }
    println("find: $firstEven")  // 2
    
    // 6. any: 是否存在匹配的元素
    val hasEven = numbers.any { it % 2 == 0 }
    println("any: $hasEven")  // true
    
    // 7. all: 是否所有元素都匹配
    val allPositive = numbers.all { it > 0 }
    println("all: $allPositive")  // true
    
    // 8. count: 计数
    val evenCount = numbers.count { it % 2 == 0 }
    println("count: $evenCount")  // 2
    
    // 9. groupBy: 分组
    val grouped = numbers.groupBy { it % 2 == 0 }
    println("groupBy: $grouped")  // {false=[1, 3, 5], true=[2, 4]}
    
    // 10. associate: 转换为Map
    val numberMap = numbers.associate { it to it.toString() }
    println("associate: $numberMap")  // {1="1", 2="2", 3="3", 4="4", 5="5"}
    
    // 11. zip: 合并两个列表
    val zipped = numbers.zip(words)
    println("zip: $zipped")  // [(1, apple), (2, banana), (3, cherry)]
    
    // 12. reduce: 累积操作
    val sum = numbers.reduce { acc, num -> acc + num }
    println("reduce: $sum")  // 15
    
    // 13. fold: 带初始值的累积操作
    val sumPlus10 = numbers.fold(10) { acc, num -> acc + num }
    println("fold: $sumPlus10")  // 25
    
    // 14. distinct: 去重
    val duplicates = listOf(1, 1, 2, 2, 3)
    val unique = duplicates.distinct()
    println("distinct: $unique")  // [1, 2, 3]
    
    // 15. chunked: 分块
    val chunks = numbers.chunked(2)
    println("chunked: $chunks")  // [[1, 2], [3, 4], [5]]
    
    // 16. windowed: 滑动窗口
    val windows = numbers.windowed(2, 1)
    println("windowed: $windows")  // [[1, 2], [2, 3], [3, 4], [4, 5]]
    
    // 17. take/drop: 取前几个/丢弃前几个
    println("take(3): ${numbers.take(3)}")  // [1, 2, 3]
    println("drop(3): ${numbers.drop(3)}")  // [4, 5]

    val originalList = arrayListOf(1, 2, 3, 4, 5)
     // 使用构造函数拷贝一个新的 
    ArrayList val newList = ArrayList(originalList) 
    // 或者使用 toArrayList 扩展函数（也是 Kotlin 标准库的一部分） 
    val anotherList = originalList.toArrayList() println(newList) 
    // 输出: [1, 2, 3, 4, 5] println(anotherList) 
    // 输出: [1, 2, 3, 4, 5]
}

// 实际应用示例
data class User(val name: String, val age: Int)

fun practicalExample() {
    val users = listOf(
        User("Alice", 20),
        User("Bob", 25),
        User("Charlie", 30)
    )
    
    // 组合使用多个操作符
    val result = users
        .filter { it.age > 20 }  // 过滤年龄大于20的
        .map { it.name }         // 只取名字
        .sorted()                // 排序
        .joinToString(", ")      // 转为字符串
    
    println("结果: $result")  // Bob, Charlie
    
    // 分组并转换
    val ageGroups = users
        .groupBy { it.age / 10 * 10 }  // 按年龄段分组
        .mapValues { entry ->           // 转换值
            entry.value.map { it.name }
        }
    
    println("年龄分组: $ageGroups")  // {20=[Alice, Bob], 30=[Charlie]}
}

```


### Kotlin扩展函数

apply作用域中的this是对象本身，返回时对象本身，方便在对象的上下文执行操作，用于初始化对象和配置。

let作用域it是对象本身，返回最后一个表达式的结果，通常用于链式操作，或是null检查

run作用域this是对象，返回最后一个表达式的结果，通常用于逻辑封装，执行操作并返回结果

with作用相当于run，但他不是扩展函数，需要传入一个对象

also作用域it是对象本身，返回也是本身，类似于apply，不过对对象本身操作没有apply方便，一般用于执行额外操作，比如日志