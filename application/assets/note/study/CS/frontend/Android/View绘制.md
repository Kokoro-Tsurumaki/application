### Veiw绘制

当Android系统提供给我们的控件不足以完成我们的项目需求时，就需要自定义View来实现我们想要达到的效果。

自定义view的绘制，需要实现三个方法，Measure、Layout、Draw，分别对应测量、布局以及绘制。Measure方法是确定View测量的宽和高，因为有时候我们在xml中需要用到wrap_content，也就是 自适应宽高，而View自定义的处理方式就是直接使用match_content的宽高，所以当View的默认处理不满足我们的需求时，我们就需要自己设置和处理宽高。Draw方法是将View绘制在屏幕上，他的绘制过程有以下几种：绘制背景、绘制自己、绘制子View、绘制装饰，可以绘制各种形状，绘制path，添加各种动画。Layout是用来确定view四个点的位置，一般是在ViewGroup中使用。

绘制流程从Avtiviy的Window对象开始，传递给根视图DecorView，DecorView将测量、布局、绘制的过程逐层向下传递给所有子视图，递归完成整个视图树的绘制。


### 三角函数于View绘制的应用
View绘制中，三角函数可以用于处理圆形/弧形元素定位。其核心公式为
```kotlin
x = centerX + radius * cos(θ)
y = centerY + radius * sin(θ)
```
主要分为三部：

首先将角度转为弧度θ → radians，
然后基于弧度算出相对于android坐标系从圆心指向目标点的单位方向向量，三角函数公式中，cos计算出x，sin计算出y。
然后将单位向量放大到实际圆的尺寸，也就是* 实际圆的半径 x = cos(θ) * radius， y = sin(θ) * radius
最终在+上圆心的坐标，偏移至绘制圆的位置，就得出绘制圆的坐标了。

### 事件分发

触摸屏幕是会产生一个触摸事件，也就是MontionEvent,事件分发就是基于这个事件相对于布局层级的一系列处理。

MontionEvent是一个触摸事件的封装类，他包含了触摸的坐标以及触摸的事件类型，基本事件类型分为按下、抬起、移动、取消。

事件分发主要涉及三个方法，

dispatchTouchEvent：负责事件的分发，事件到达控件时首先会调用该的方法，如果返回true，事件被消费，停止传递，返回false则将时间继续向下传递。

onInterceptTouchEvent：用于拦截事件，只在viewgroup及其子类中可用，返回true表示拦截事件，交由自身的onTouchEvent处理，返回false则继续将事件传递给子视图。

onTouchEvent：用于处理事件，通常子控件最终会在此方法中消费事件。

   关于事件分发的流程，首先会从Activiy接受，Activity将事件传递给DecorView，DecorView传递给子视图，此时会进入dispatchTouchEvent方法，viewgroup可以在onInterceptTouchEvent中决定是否拦截事件，不拦截则传递给子视图，直到最底层的View。
   
最终事件会在一个控件的onTouchEvent中被消费，或沿着事件分发链返回，直到被处理或丢弃。

 常见使用场景一般是在自定义控件中用于控制事件分发顺序，或者是在父视图和子视图都有滑动需求时，可以通过在父视图的onInterceptTouchEvent中判断并动态拦截事件，避免滑动冲突。

### Android中的坐标系

与传统数学中的坐标系不同，Android的x轴是左负右正，y轴是上负下正。Android中的象限顺序是右下，左下，左上，右上，计算角度时，顺时针是指从x轴右半向下开始计算，逆时针相反。

### 绘制

Canvas类和Paint类是绘制主要用到的两个类，Canvas是重写onDraw方法时得到的，而Paint需要我们主动实例化他的对象。

Canvas主要做绘制的工作，一般它绘制方法的参数都是一些独有的绘制条件和Paint实例对象，他的常用方法如下：

1)   drawColor(Color.parseColor("#88880000"))  
绘制底色或蒙版

2)   drawCircle(Float, Float, Float, Paint)  
绘制圆形(圆心x轴位置，圆心y轴位置，半径，Paint())

3)   drawRect(Float, Float, Float, Float, Paint)  
绘制矩形(Left坐标，Top坐标，Right坐标，Bottom坐标，Paint())

4)   drawPoint(Float, Float, Paint)  
绘制点(x轴，y轴，Paint)，点的大小可以通过  strokeWidth = Float来设置；  
点的形状可以通过 strokeCap = Paint.Cap 来设置：ROUND 画出来是圆形的点，SQUARE 或 BUTT 画出来是方形的点。

5)   val points: FloatArray =  
            FloatArrayOf(0F, 0F, 50F, 50F, 50F, 100F, 100F, 50F, 100F, 100F, 150F, 50F, 150F, 100F)  
drawPoints(FloatArray，Int，Int，Paint)  
绘制多个点(浮点数组，跳过的数字，一共绘制的数字，Paint)，两个一组，每两个数就可以确定一个点的坐标

6)   drawOval(Float, Float, Float, Float, Paint )  
绘制椭圆(Left坐标，Top坐标，Right坐标，Bottom坐标，Paint())

7)   drawLine(Float, Float, Float, Float, paint);  
绘制线(起点x轴，起点y轴，终点x轴，终点y轴，Paint())，由于直线不是封闭图形，所以 setStyle(style) 对直线没有影响。

8)   val points: FloatArray =  
            FloatArrayOf(0F, 0F, 50F, 50F, 50F, 100F, 100F, 50F, 100F, 100F, 150F, 50F, 150F, 100F)  
drawLines(FloatArray，Int，Int，Paint)  
绘制多条线(浮点数组，跳过的数字，一共绘制的数字，Paint)，两个一组，每两个数就可以确定一个点的坐标

9)   drawRoundRect(Float, Float, Float, Float, Float, Float, Paint)  
绘制圆角矩形(Left坐标，Top坐标，Right坐标，Bottom坐标，圆角横向半径，圆角纵向半径，Paint())

10) canvas.drawArc(Float, Float, Float, Float, Float, Float, Boolean, paint)  
绘制扇形和弧形(椭圆Left坐标，椭圆Top坐标，椭圆Right坐标，椭圆Bottom坐标，起始角度，划过的角度，是否连接圆心,Paint)  
椭圆的圆心作为坐标轴的原点来计算角度，连接圆心是扇形，不连接是弧形

Paint主要用于设置绘制时的共有属性，一般作为Canvas绘制方法的参数使用，它的常用方法如下：