### adb是什么？

adb就是用于调试Android设备的一组指令集。

### Adb常用命令

```adb
adb connect 127.0.0.1:5555

adb shell

启动某个应用：

monkey -p < packagename > -c android.intent.category.LAUNCHER 1

杀死某个应用：

am force-stop <packagename>

模拟按键(Home-3，返回-4，电源-26，亮屏-224、熄屏-223，切换应用-187，小键盘删除-67):

input keyevent 3

在焦点处于某文本框时，使用input命令输入文本 :

input text Hello

滑动，从起始坐标点滑动到结束坐标点:

input swipe 300 1000 300 500

点击坐标点:

input tap 500 500

截图

screencap -p /sdcard/sc.png

退出

exit

导出到电脑

adb pull /sdcard/sc.png

查看分辨率

adb shell wm size
```
