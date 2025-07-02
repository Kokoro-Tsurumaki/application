原生Android APP开发一般使用[[CS/语言/Java]]与[[Kotlin]]，[[framework]]使用C++与Java
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

UI优化：避免过度布局和深层嵌套，必要的时候使用ViewStub进行视图懒加载

内存优化：减少内存泄漏，在不造成观看的情况下压缩图片质量。

电量优化：减少不必要的网络请求和GPS定位，虽然okhttp本身支持缓存，但是要注意服务端是否指定响应报文的header让okhttp进行缓存

启动优化：一些非关键模块可以延迟加载

Apk大小优化：某些图片可以转换为webp，节省空间。使用混淆时同时使用压缩进行代码缩小、优化和合并，对于某些三方SDK，在必要的情况下进行裁剪，使用buildTypes分离不同架构的打包。

### Handler机制

Handler是子线程与主线程之间通信的一种机制，主要包含四个类：handler、looper、messagequeue、message。
这种设计模式在GUI开发中广泛使用，本质是生产者-消费者模型的变体。

handler是用来发送和处理消息的，Looper是通过loop方法进入死循环来轮询 MessageQueue判定是否有需要发送的消息，MessageQueue是基于时间排序的链表，用于存储message，meaage是存储消息内容，包含 `what`（消息标识）、`obj`（数据）、`target`（目标 Handler）、`when`（执行时间戳）。

调用sendMessage时将当前的message传入到handler中，通过handler中的messagequeue对象的引用，把message放入到MessageQueue中，`Message.target` 自动绑定当前 Handler，用以防止消息错乱；放入时按照message的when时间排序，

Looper的loop方法将会轮询通过时间戳判断是否有需要执行的message，当消息的执行时间小于或等于系统时间戳时，将消息从链表中删除并返回给loop方法中。在loop方法中获取到message后判断target对应的handler是否存在，存在就调用target的dispatchMessage方法进行消息分发， 若 `Message.callback` 存在（如 `post(Runnable)`），优先执行 Runnable
 否则调用 `handleMessage()` 处理消息

关于Handler发送异步消息，在Activity组件中提供了一个叫runOnUiThread的方法，该方法可以直接切至主线程，其内部就是通过handler实现的，具体就是首先他会通过currentThread方法来判断当前线程是否为主线程，不是主线程的话就会调用Activity中handler的post方法，并传入一个runnable接口参数，在post方法中通过sendMessage发送一个消息，并通过getPostMessage方法将runnable接口作为message对象的callback参数，这样消息处理时Activiy传过来的Runnable接口就会回调，从而实现一个runOnUiThread方法就可以直接切主线程的操作。

Handler 持有 Activity 的强引用，MessageQueue 未处理完导致 Activity 无法回收
### 依赖注入 javax.Inject

Inject是一种获取对象的方法，用于代替构造器、工厂模式等初始化，具有更好的复用性和维护性

注解于字段不能是fineal，注解于方法不能是抽象，不能声明自身参数类型

### Flow

Flow是协程中对数据流处理的API，以协程为基础构建，可以按顺序发出多个值。流程包含使用方、流程方，也可以修改发送到数据流的值或者修正数据流本身。

官方的定位Flow结合协程是用于代替Rxjava。

冷流，flow默认创建的是冷流，即数据被订阅或被消费时，发布者才开始执行发射数据流的代码，若有多个订阅者，每个订阅这和发布者都是一对一的关系，相当于每个订阅者都会收到发布者的完整数据

热流，SharedFolw、StateFlow：不管是否被订阅或消费，都会执行发射数据流的操作，并且发布者和订阅这是一对多的关系。它的使用场景类似于liveData

flowOn用于将Flow中的代码块进行线程切换

filter用于对结果添加限制

filterNot，与filter相反，筛选不符合条件的值，返回false继续往下执行

filterNotNull筛选不为空的值

asFlow将其他数据转成Flow，一般是集合

flowof构造一组数据的flow进行发送

map对上游发送的数据进行变换，collect最后接受的是变换后的值

mapNotNull，仅发送不为空


