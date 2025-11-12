## Android常用的四个分区

*  System分区
	   系统分区，存储Android操作系统的核心文件，如framework.jar、系统应用SystemUI.apk等。包含Android框架层（framework）及系统应用（如设置、拨号、短信）。
	   默认只读，用户和大多数应用无法修改。需要root权限才能修改。
	   在OTA更新时，该分区可能会被替换或修改。

*  Vendor分区
	   供应商分区，存储硬件厂商(ORM)提供的驱动程序和HAL(Hardware Abstraction Layer)库，例如：Wi-Fi、蓝牙、相机、传感器等硬件驱动。vendor/lib/目录下的专用库。让Android系统能够适配不同厂商的硬件。该分区通常是SoC厂商(如高通、联发科)预装的内容。
	   默认只读，只能在root权限下修改。

*  Odm分区
	   存储特定设备的HAL(硬件抽象层)实现，是/vandor的扩展。
	   主要用于ODM(Original Design Manufacturer，原始设计制造商)定制的设备驱动，例如：ODM设备专属的传感器驱动、以硬件适配库等。
	   这个分区主要是手机品牌商(如小米、三星)针对不同型号的设备进行定制的部分。
	   只读，一般用户无法直接修改。

*  Product分区
	   存储扩展系统应用和AOSP之外的系统功能，例如：
	   品牌定制应用，设备特有的UI组、主题等。
	   这个分区可以让设备厂商在不影响/system的情况下，增加更多功能。
	   默认只读，root设备可以修改。


* System与Vandor的关系：
	8.0之前驱动与系统文件混合在/system分区，8.0之后，驱动被移到/vendor，实现系统(AOSP)与硬件驱动的解耦，方便系统升级。

* Odm与Vandor的关系：
	/vendor由芯片厂商提供，而/odm由手机品牌商负责适配。

* Product与System的关系：
	/system主要存放AOSP组件，而/product允许厂商添加额外的应用和UI，保持与AOSP分离，便于Google发布更新。

* 分区的原因：
	主要用于简化系统更新，增强模块化和定制化。

* 程序如何制定分区：
	- System 分区
	    - Android.mk 默认就是输出到 system 分区，不用指定
	    - Android.bp 默认就是输出到 system 分区，不用指定
	- Vendor
	    - Android.mk LOCAL_VENDOR_MODULE := true
	    - Android.bp vendor: true
	- Odm 分区
	    - Android.mk LOCAL_ODM_MODULE := true
	    - Android.bp device_specific: true
	- product 分区
	    - Android.mk LOCAL_PRODUCT_MODULE := true
	    - Android.bp product_specific: true

### Android.bp格式
```json
cc_binary {              //模块类型为可执行文件
    name: "hello",       //模块名hello
    srcs: ["helloworld.cpp"], //源文件列表
    product_specific: true,        //编译出来放在/product目录下(默认是放在/system目录下)
    cflags: ["-Werror"], //添加编译选项
}
```

### Android.mk格式
```
//添加到 System 的模块
PRODUCT_ARTIFACT_PATH_REQUIREMENT_WHITELIST += \
    system/bin/hello \
```