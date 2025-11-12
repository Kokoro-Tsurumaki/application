原生Android APP开发一般使用[[Java]]与[[Kotlin]]，[[Framework]]使用C++与Java
需要了解[[网络]]技术，Android中的网络请求一般使用[[Okhttp]]库
Android使用[[Gradle]]进行项目构建
可以使用[[adb]]进行调试
关于Android进阶一般需要[[View绘制]]和[[三方框架与架构搭建]]
目前Google推崇声明式UI-[[Compose]]
关于平时遇到的问题在[[工作问题记录]]
### 四大组件

四大组件包含Activity，Service，BroadcastReciver，ContentProvider。

Activity：

Activity提供一个供用户操作的可视化页面。
正常情况下他的生命周期包括：
onCreate->onStart->onResume->onPause->onStop->onDestory  onRestart()

当他跳转时他的生命周期：

A(onPause)-跳转->B(onCreate-onStart-onResume)->A(onStop)若B为透明主题则不会onStop();

B（onPause）-返回->A(onRestart-onStart-onResume)->B(onStop-onDestroy)

异常退出时：

在onStop执行之前（一般是onPause和onStop之间），会有onSaveInstanceState方法来保存异常退出的activity的情况，当应用重启时，会在onRestoreInstanceState中的bundle参数来恢复数据。

他的启动模式一般通过在清单文件中添加launchMode实现，这种方式的模式有四种：

1、标准模式（standard）

标准模式也是默认模式，每启动一个activity就会创建一个实例，即使启动的activity已经存在，依然会创建。

2、栈顶复用模式（singleTop）

当启动的activity处于栈顶时，会复用之前的实例，不过不处于栈顶和标准模式相同。

3、单任务栈模式（栈内单例模式）（singleTask）

在栈顶时不创建，回调onNewIntent()，不在栈顶时，那么直接复用之前的activity，并且把activity上面所有的avtivity销毁，使他成为栈顶,没有实例会它需要的栈创建。

4、单实例模式（单例模式）（singleStance）

启动的activity会放在一个单独的任务栈中来管理，之后每次启动都会复用，其实singleTask如果设置不同的taskaffinity的话也会启动一个新的栈，相较于singleTask，它只能在新的栈启动。

设置启动方式的另一种方式是通过在Intent中添加flag实现，它的优先级要高于第一种方式。

关于它的启动分为显式启动和隐式启动，

显示启动需要明确指定启动对象的包名类名，隐式启动需要Intent能够匹配启动目标的intentFilter中所设置的过滤信息，包括action、category、data，未设置除外，不匹配则无法启动。

Activity的视图结构最底层是一个Window，window是一个抽象类，PhoneWindow是window的唯一实现类，所有的视图dialog、toast底层都是window，可以给他设置flag来调整显示特性，比如禁用焦点、控制软键盘默认显示。Window主要负责View的管理，通过windowManager可以添加Window以及对window进行添加删除更新view，在onCreate中的setContentView最终的处理逻辑就是由Window处理的。DecorView是一个FrameLayout，是一个顶层的View，作为变量被Window所持有，内部布局随主题而改变，一般包含一个垂直方向的LinearLayout,这个LinearLayout有两部分，分为titleBar和contentParent，contentParent的布局id是content，通过setContentView设置的xml文件就是作为子元素添加到contentParent中的，在handleResumeActivity方法中，在onResume之后，会调用makeVisible将DecorView添加和显示到Winodw中。

就是 startActivity - Instrumentation Binder 与 AMS 通信，AMS 检验各种信息，如果没问题，ApplicationThread 发送信息给 ActivityThread ，ActivityThread 的Handler 启动 activity。

Service：

Service有两种工作方式，分别是启动状态和绑定状态，通过startService运行为启动状态，Service启动后将和启动它的组件没有任何关系，具有独立的声明周期，启动状态的Service会走onStartCommand生命周期；通过onbindSercie运行的为绑定状态，Sercie启动后可以与其他组件交互，这种Service绑定绑时会走onbind和onunbind生命周期。

BroadcastReceiver：

 BroadCastReceiver是应用间、应用与系统间、应用内部进行通信的一种方式，利用Binder机制实现，支持动态与静态两种注册方式，但是8.0之后大部分广播只能动态注册了。除此之外还有一个LocalBroadcastReceiver可以进行应用内通信，数据更加安全且效率更高，它是通过Handler实现的，只支持通过LocalBroadcastManager动态注册，不过localBroadcastReceive在android10的API及之后都被标记为弃用了。

ContentProvider:

ContentProvider用于对外提供数据，或者通过内置API访问私有目录。ContentResolver来访问ContentProvider提供的数据，ContentObserver，通过registerContentProvider来监听数据的改变状态。

 

### 关于登录模块登录页的实现思路

首先要考虑到登录页的出现时机，他只会在首次启动APP、退出登录或是APP的Token过期时通过调用使用到Toekn的接口触发419来跳转，登录完成后跳转首页，即跳转时清除之前栈内所有实例。

第一种情况不需要考虑，因为首次启动APP的登录页栈内一定没有其他实例，退出登录就需要跳转登录页并清除栈内实例了,419跟退出登录相似，不同处在于419触发的跳转不持有某个Activity的Context。

实现思路就是通过在启动页与登录页主页之间增加一个透明的Activity，登录页主页监听返回键调用moveTaskToBack()将应用移至后台，避免返回至透明Activity，这时候就可以通过FLAG_ACTIVITY_CLEAR_TOP来进行清栈了。419是通过在透明Activity中使用广播来通知它重新登录，因为广播会只有动态注册它的Activity的实例。

### 应用Application的生命周期监听

通过LifecycleObserver来实现，首先创建一个生命周期观察者ApplicationObserver继承自LifecycleObserver内部通过注解@onLifecycleEvent实现各个生命周期的方法，然后再初始化Application时添加这个观察者，ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationObserver)，该功能一般是用于应用处于前后台的判断，防止应用劫持。

使用中有几点需要注意：

ProcessLifecycleOwner是针对整个应用程序的监听，与Activity数量无关，你有一个Activity或多个Activity，对ProcessLifecycleOwner来说是没有区别的。

Lifecycle.Event.ON_CREATE只会被调用一次，而Lifecycle.Event.ON_DESTROY永远不会被调用。

当应用程序从后台回到前台，或者应用程序被首次打开时，会依次调用Lifecycle.Event.ON_START和Lifecycle.Event.ON_RESUME。

当应用程序从前台退到后台（用户按下Home键或任务菜单键），会依次调用Lifecycle.Event.ON_PAUSE和Lifecycle.Event.ON_STOP。需要注意的是，这两个方法的调用会有一定的延后。这是因为系统需要为“屏幕旋转，由于配置发生变化而导致Activity重新创建”的情况预留一些时间。也就是说，系统需要保证当设备出现这种情况时，这两个事件不会被调用。因为当旋转屏幕时，你的应用程序并没有退到后台，它只是进入了横/竖屏模式而已。

### 版本适配

与权限相关：在Android6.0增加了运行时权限，对于某些敏感权限需要在使用时手动申请。
registerForActivityResult(ActivityResultConstracts.requestMultiplePermissions()){
}.launch(arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

与文件存储相关：Android7.0时限制了私有目录的访问，在7.0及以上通过File.toUri()获取的Uri访问时会提示FileUriExposedException，需要使用FileProvider.getUriForFile()的方式获取Uri。Android10.0引入了分区存储，实际上就是限制对根目录的访问，不能再在根目录读取与创建文件。解決方案是在清单文件中添加requestLegacyExternalStorage为true。但是不建议使用，因为在Android11.0时强制分区存储，这个配置会无效化，文件存储可以使用MediaStore和Storage Access来实现。

与通知相关：Android8.0增加了通知渠道，8.0及以上必须添加通知渠道才能正常显示通知。

与服务相关：Android9.0及以上使用前台服务需要在AndroidManifest添加权限，否则会提示SecurityException。Android14 时需要注明该服务的用途，可以通过清单文件中注册时的foregroundServiceType属性或者在代码中调用setForeground方法设置。

此外Android10.0对后台启动Activity进行了限制

与网络请求相关：Android9.0就是不支持明文传输的问题，也就是不再支持http协议的接口了，可以在AndoridManifest中配置userClearTextTraffic为true来允许它明文传输。

与Toast相关：Android11.0中从后台发送自定义View的Toast会被屏蔽掉，后台使用只允许默认的Toast和Snackbar，在后台时应该使用通知来代替Toast，通知更持久且可交互。

与信息获取相关：Android12.0由于隐私政策的加强，不能通过bluetooth_name获取设备名称，而且会报错，应该使用对应的API，比如BlutoothAdapter来获取设备信息。

### 屏幕适配

官方的dp单位适配的是屏幕密度，而最小宽度限定符是适配的不同尺寸和长宽比。

屏幕适配就是因为手机的屏幕大小、像素密度不同，我们相同大小的控件在不同手机上显示可能存在偏差。而我们的app为了适应各种手机，就需要进行屏幕适配。

分辨率限定符、最小宽度限定符，两个限定符的原理类似，系统根据限定符去寻找对应的dimens文件，区别在于屏幕分辨率限定符适配是拿px值等比例缩放，而最小宽度限定符是拿dp值等比例缩放，最小宽度限定符是不区分宽高的，无论是宽度还是高度，哪一边小就认为哪一边为最小宽度，最小宽度想限定符需要的dimens文件更少，因为无论手机屏幕像素多少，密度多少，很多手机的最小宽度都是一致的。

### 文件存储
![[Pasted image 20250122150030.png]]![[Pasted image 20250122150034.png]]
### RecyclerView使用技巧

当RecyclerView高度固定时，可以使用setHasFixedSize和局部刷新来使RecyclerView避免重复计算大小。

使用局部刷新时会有动画效果，一般在执行动画的时候我们要避免对布局的操作，以防止

### 优化

##### UI优化：
避免过度布局和深层嵌套，必要的时候使用ViewStub进行视图懒加载
过度绘制检测、布局层级优化、ViewStub/Merge/Include使用场景

##### 内存优化：
减少内存泄漏，在不造成观看的情况下压缩图片质量。
内存泄漏检测（LeakCanary原理）、内存抖动分析、大图加载优化（Bitmap）

##### 能耗优化：
减少不必要的网络请求和GPS定位，虽然okhttp本身支持缓存，但是要注意服务端是否指定响应报文的header让okhttp进行缓存
WakeLock使用规范、后台任务限制（WorkManager）、AlarmManager最佳实践

##### 启动优化：
一些非关键模块可以延迟加载
冷启动/热启动流程、启动任务调度（AppStartUp）、类加载优化

##### Apk大小优化：
某些图片可以转换为webp，节省空间。使用混淆时同时使用压缩进行代码缩小、优化和合并，对于某些三方SDK，在必要的情况下进行裁剪，使用buildTypes分离不同架构的打包。

##### 网络优化：
请求合并、数据压缩、HTTP/2优势、连接复用机制

### Handler机制

Handler是子线程与主线程之间通信的一种机制，主要涉及四个类：handler、looper、messagequeue、message。
这种设计模式在GUI开发中广泛使用，本质是生产者-消费者模型的变体。

handler是用来发送和处理消息的，当创建Handler时会通过Looper.myLooper持有当前线程的Looper，并通过looper对mQueue的持有来持有MessageQueue。
Looper的loop方法通过死循环来轮询 MessageQueue判定是否有需要发送的消息。
MessageQueue是基于时间排序的链表，用于存储message。
meaage是存储消息内容，包含 `what`（消息标识）、`obj`（数据）、`target`（目标 Handler）、`when`（执行时间戳）。

Handler创建时可以传入callback、Looper，如果没有Looper会通过Looper.myLooper()方法通过ThreadLocal中的ThreadLocalMap获取当前线程的Loper，如果是子线程，需要调用Looper.prepare()来绑定Looper，否则会返回null，并且需要显示调用Looper.loop开启循环。当调用sendMessage时将当前的message传入到handler中，通过handler中的messagequeue对象的引用，把message放入到MessageQueue中，在enqueueMessage之前会把当前handler赋值给Message的target，后续直接通过Message.taget直接分发消息，用于防止消息错乱；在enqueueMessage会把SystemClock.uptimeMillis+delayMillis作为消息执行时间赋值给message的when，放入时按照when升序排序，
()。
`SystemClock.uptimeMillis()` 返回自系统启动（boot）以来经过的时间，以毫秒为单位。它不包括设备进入深度睡眠（CPU关闭，屏幕变暗，设备等待外部输入）的时间。也就是说，它计算的是系统处于“唤醒”状态的总时间。
​它具有单调递增特性，即这个时间会一直增加，不会因为系统时间的改变（例如用户调整了时间，或者自动时区调整）而回退或跳跃。它保证是单调递增的。

Looper的loop方法将会轮询调用MessageQueue的next()判断是否有需要执行的message，next内部通过当前的系统执行时间与message.when对比来判断，当when小于now时，将消息从链表中删除并返回给loop方法中。在loopOnce方法中判断message的target对应的handler是否存在，存在就调用target的dispatchMessage方法进行消息分发， 若 `Message.callback` 存在（如 `post(Runnable)`），优先执行 Runnable，否则调用 `handleMessage()` 处理消息

关于Handler发送异步消息，在Activity组件中提供了一个叫runOnUiThread的方法，该方法可以直接切至主线程，其内部就是通过handler实现的，具体就是首先他会通过currentThread方法来判断当前线程是否为主线程，不是主线程的话就会调用Activity中handler的post方法，并传入一个runnable接口参数，在post方法中通过sendMessage发送一个消息，并通过getPostMessage方法将runnable接口作为message对象的callback参数，这样消息处理时Activiy传过来的Runnable接口就会回调，从而实现一个runOnUiThread方法就可以直接切主线程的操作。

关于同步屏障，同步屏障是一种特殊消息，用于阻塞普通同步消息，优先处理异步消息：
主要用于确保绘制优先，避免卡顿，防止业务逻辑阻塞UI更新

Handler 持有 Activity 的强引用，MessageQueue 未处理完导致 Activity 无法回收

### Android 线程同步机制

在 Android 开发中，线程同步是保证多线程程序正确性的关键。您已经接触到了 Mutex 和 CountDownLatch，下面我将全面解析这些同步机制，并介绍更多重要的同步类。

#### 一、Mutex（互斥锁）

##### 1. 核心概念

Mutex 是 Mutual Exclusion（互斥）的缩写，用于确保同一时间只有一个线程可以访问共享资源。

##### 2. Android 实现

在 Android 中，Mutex 通过 `ReentrantLock` 类实现：

java

复制

```java
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private final ReentrantLock lock = new ReentrantLock();
    private int counter = 0;
    
    public void increment() {
        lock.lock(); // 获取锁
        try {
            counter++;
        } finally {
            lock.unlock(); // 释放锁
        }
    }
}
```

##### 3. 关键特性

- ​**可重入性**​：同一个线程可以多次获取同一个锁
- ​**公平性**​：可配置公平锁（按请求顺序获取）或非公平锁
- ​**条件变量**​：支持 Condition 对象实现更复杂的同步

##### 4. 使用场景

- 保护共享数据结构
- 实现临界区
- 构建更高级的同步工具

#### 二、CountDownLatch（倒计时门闩）

##### 1. 核心概念

允许一个或多个线程等待其他线程完成操作。

##### 2. 基本用法

java

复制

```java
import java.util.concurrent.CountDownLatch;

// 主线程等待3个工作线程完成
CountDownLatch latch = new CountDownLatch(3);

// 工作线程
new Thread(() -> {
    // 执行任务
    latch.countDown(); // 计数器减1
}).start();

// 主线程等待
latch.await(); // 阻塞直到计数器为0
```

##### 3. 关键特性

- ​**一次性**​：计数器无法重置
- ​**不可逆**​：计数器只能减少不能增加
- ​**阻塞等待**​：await() 会阻塞直到计数器归零

##### 4. 使用场景

- 主线程等待多个子线程初始化完成
- 并行任务完成后汇总结果
- 服务启动前的依赖检查

#### 三、更多重要同步机制

##### 1. Semaphore（信号量）

控制同时访问特定资源的线程数量。

java

复制

```java
import java.util.concurrent.Semaphore;

// 限制最多5个线程同时访问
Semaphore semaphore = new Semaphore(5);

void accessResource() {
    semaphore.acquire(); // 获取许可
    try {
        // 访问共享资源
    } finally {
        semaphore.release(); // 释放许可
    }
}
```

​**使用场景**​：

- 连接池管理
- 限流控制
- 资源池实现

##### 2. CyclicBarrier（循环屏障）

让一组线程到达一个屏障时被阻塞，直到最后一个线程到达。

java

复制

```java
import java.util.concurrent.CyclicBarrier;

// 4个线程到达屏障后执行回调
CyclicBarrier barrier = new CyclicBarrier(4, () -> 
    System.out.println("所有线程到达屏障"));

// 工作线程
new Thread(() -> {
    // 执行第一阶段任务
    barrier.await(); // 等待其他线程
    // 执行第二阶段任务
}).start();
```

​**特点**​：

- 可重用（与 CountDownLatch 的主要区别）
- 支持屏障操作（所有线程到达后执行）
- 支持超时机制

​**使用场景**​：

- 多阶段并行计算
- 迭代算法中的同步点
- 分布式系统协调

##### 3. Phaser（阶段器）

更灵活的多阶段同步屏障，支持动态调整参与线程数。

java

复制

```java
import java.util.concurrent.Phaser;

Phaser phaser = new Phaser(3); // 初始参与线程数

// 工作线程
new Thread(() -> {
    phaser.arriveAndAwaitAdvance(); // 阶段1完成
    // 阶段2任务
    phaser.arriveAndDeregister(); // 完成并退出
}).start();
```

​**特点**​：

- 动态注册/注销参与者
- 支持分层结构
- 可中断等待

​**使用场景**​：

- 复杂多阶段任务协调
- 动态线程池任务同步
- 遗传算法等科学计算

##### 4. ReadWriteLock（读写锁）

允许多个读操作同时进行，但写操作独占。

java

复制

```java
import java.util.concurrent.locks.ReentrantReadWriteLock;

ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

// 读操作
void readData() {
    rwLock.readLock().lock();
    try {
        // 读取共享数据
    } finally {
        rwLock.readLock().unlock();
    }
}

// 写操作
void writeData() {
    rwLock.writeLock().lock();
    try {
        // 修改共享数据
    } finally {
        rwLock.writeLock().unlock();
    }
}
```

​**使用场景**​：

- 读多写少的共享数据结构
- 缓存系统
- 配置信息管理

##### 5. Exchanger（交换器）

两个线程在同步点交换数据。

java

复制

```java
import java.util.concurrent.Exchanger;

Exchanger<String> exchanger = new Exchanger<>();

// 线程A
new Thread(() -> {
    String dataA = "Data from A";
    String dataB = exchanger.exchange(dataA); // 交换数据
}).start();

// 线程B
new Thread(() -> {
    String dataB = "Data from B";
    String dataA = exchanger.exchange(dataB); // 交换数据
}).start();
```

​**使用场景**​：

- 管道式数据处理
- 遗传算法中的染色体交换
- 双向数据流处理

##### 6. Condition（条件变量）

与 Lock 配合使用，实现更灵活的线程等待/通知机制。

java

复制

```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

ReentrantLock lock = new ReentrantLock();
Condition condition = lock.newCondition();
Queue<Data> queue = new ArrayDeque<>();

// 生产者
void produce(Data data) {
    lock.lock();
    try {
        queue.add(data);
        condition.signal(); // 通知消费者
    } finally {
        lock.unlock();
    }
}

// 消费者
Data consume() throws InterruptedException {
    lock.lock();
    try {
        while (queue.isEmpty()) {
            condition.await(); // 等待数据
        }
        return queue.remove();
    } finally {
        lock.unlock();
    }
}
```

​**使用场景**​：

- 实现阻塞队列
- 生产者-消费者模式
- 状态依赖的操作

#### 四、Android 特有同步机制

##### 1. HandlerThread

内置 Looper 的工作线程，简化 Handler 使用。

java

复制

```java
HandlerThread handlerThread = new HandlerThread("Worker");
handlerThread.start();

Handler handler = new Handler(handlerThread.getLooper());
handler.post(() -> {
    // 在工作线程执行
});
```

##### 2. IntentService（已弃用，替代方案 WorkManager）

自动管理后台任务的 Service。

##### 3. SyncAdapter

用于账户同步的标准框架。

##### 4. JobScheduler/WorkManager

后台任务调度框架，提供高级同步机制。

#### 五、同步机制选择指南

|场景|推荐机制|
|---|---|
|简单互斥访问|synchronized 或 ReentrantLock|
|等待多个任务完成|CountDownLatch|
|多阶段任务协调|CyclicBarrier 或 Phaser|
|资源池管理|Semaphore|
|读多写少场景|ReadWriteLock|
|线程间数据交换|Exchanger|
|条件等待|Condition + Lock|
|Android 后台任务|HandlerThread 或 WorkManager|

#### 六、最佳实践与注意事项

1. ​**避免锁嵌套**​：
    
    java
    
    复制
    
    ```java
    // 错误示例：可能导致死锁
    synchronized(lockA) {
        synchronized(lockB) {
            // ...
        }
    }
    ```
    
2. ​**使用 tryLock 避免死锁**​：
    
    java
    
    复制
    
    ```java
    if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
        try {
            // ...
        } finally {
            lock.unlock();
        }
    }
    ```
    
3. ​**优先使用并发集合**​：
    
    java
    
    复制
    
    ```java
    // 替代手动同步
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    ```
    
4. ​**注意锁粒度**​：
    
    - 粗粒度锁：简单但性能差
    - 细粒度锁：复杂但性能好
5. ​**避免锁竞争**​：
    
    - 使用 ThreadLocal 减少共享
    - 采用无锁数据结构（如 AtomicInteger）
6. ​**监控死锁**​：
    
    bash
    
    复制
    
    ```bash
    # 检测Java死锁
    jstack <pid>
    ```
    
7. ​**Android 主线程注意事项**​：
    
    - 避免在主线程使用同步阻塞操作
    - 使用 AsyncTask 或协程处理耗时操作

#### 七、高级同步模式

##### 1. 双重检查锁定（Double-Checked Locking）

java

复制

```java
public class Singleton {
    private volatile static Singleton instance;
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

##### 2. 生产者-消费者模式

java

复制

```java
BlockingQueue<Item> queue = new LinkedBlockingQueue<>(10);

// 生产者
new Thread(() -> {
    while (true) {
        Item item = produceItem();
        queue.put(item); // 阻塞如果队列满
    }
}).start();

// 消费者
new Thread(() -> {
    while (true) {
        Item item = queue.take(); // 阻塞如果队列空
        consumeItem(item);
    }
}).start();
```

##### 3. 读写锁升级

java

复制

```java
ReadWriteLock lock = new ReentrantReadWriteLock();

void upgrade() {
    lock.readLock().lock();
    try {
        if (needWrite) {
            lock.readLock().unlock();
            lock.writeLock().lock(); // 升级为写锁
            try {
                // 写操作
            } finally {
                lock.readLock().lock(); // 降级为读锁
                lock.writeLock().unlock();
            }
        }
    } finally {
        lock.readLock().unlock();
    }
}
```

#### 总结

Android 提供了丰富的线程同步机制，从基础的 Mutex 到高级的 Phaser，每种机制都有其适用场景：

1. ​**Mutex**​：基础互斥，保护共享资源
2. ​**CountDownLatch**​：一次性等待多个任务完成
3. ​**Semaphore**​：控制资源访问并发数
4. ​**CyclicBarrier**​：可重用的多线程集合点
5. ​**Phaser**​：灵活的多阶段同步
6. ​**ReadWriteLock**​：优化读多写少场景
7. ​**Exchanger**​：线程间数据交换
8. ​**Condition**​：复杂的条件等待

在实际开发中，应根据具体需求选择合适的同步机制：

- 简单场景优先使用 synchronized
- 复杂控制使用 Lock/Condition
- 任务协调使用 CountDownLatch/CyclicBarrier
- 资源管理使用 Semaphore
- Android 特有场景使用 HandlerThread/WorkManager

掌握这些同步机制并遵循最佳实践，可以构建出高效、稳定的多线程 Android 应用。

### 依赖注入 javax.Inject

Inject是一种获取对象的方法，用于代替构造器、工厂模式等初始化，具有更好的复用性和维护性

注解于字段不能是fineal，注解于方法不能是抽象，不能声明自身参数类型

### Flow

Flow是协程中对数据流处理的API，以协程为基础构建，可以按顺序发出多个值。流程包含使用方、流程方，也可以修改发送到数据流的值或者修正数据流本身。

官方的定位Flow结合协程是用于代替Rxjava。

冷流，flow默认创建的是冷流，即数据被订阅或被消费时(collect)，发布者才开始执行发射数据流的代码，若有多个订阅者，每个订阅这和发布者都是一对一的关系，相当于每个订阅者都会收到发布者的完整数据

热流，SharedFolw、StateFlow：不管是否被订阅或消费，都会执行发射数据流的操作，并且发布者和订阅这是一对多的关系。它的使用场景类似于liveData



flowOn用于将Flow中的代码块进行线程切换

filter用于对结果添加限制

filterNot，与filter相反，筛选不符合条件的值，返回false继续往下执行

filterNotNull筛选不为空的值

asFlow将其他数据转成Flow，一般是集合

flowof构造一组数据的flow进行发送

map对上游发送的数据进行变换，collect最后接受的是变换后的值

mapNotNull，仅发送不为空


|类型|特点|适用场景|
|---|---|---|
|Flow|冷流，每次收集启动新的执行，支持背压|数据库查询、网络请求等异步数据流|
|StateFlow|热流，有当前值，只保留最新值，收集者立即获得当前值|状态管理（如UI状态）|
|MutableStateFlow|可变的StateFlow，可以更新值|在ViewModel中管理状态，对外暴露只读StateFlow|
|Channel|用于协程间通信，一次性的消费|一次性的事件（如导航事件、显示Snackbar事件）|
|SharedFlow|热流，可配置重放和缓冲区，可广播给多个收集者|事件通知（如广播事件）|
|MutableSharedFlow|可变的SharedFlow，可以发射值|在ViewModel中定义事件，由UI层收集处理|

