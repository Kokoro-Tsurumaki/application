# android_

#### 介绍
Compose重构版，基于[MVI架构](#软件架构)

##### 模版代码生成
```sh
# flag 文件名 path 0 在根目录template创建 type 项目目录创建
./gradlew allDeps | grep -i "kotlinx-metadata"
./gradlew compose:dependencies --configuration kapt
./gradlew createTemplateForMVI -Ptflag=Mine -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features
```
##### 构建

##### 需要了解：
1. 基于Hilt的依赖注入， 
2. 基于Flow的状态管理及响应式编程， 
3. 基于Retrofit与Okhttp的网络请求


#### 软件架构
##### 软件架构说明
M-Model(状态)  V-View(UI展示) V-ViewModel(处理逻辑) I-Intent(用户意图/事件)
实际应用中
M-Model -> UIContract.State
V-View  -> Screen
V-ViewModel -> BaseUIViewModel
I-Intent -> UIContract.Action UIContract.UIEffect
总体
UI → Action → ViewModel → State/Effect → UI

##### 使用时尽量遵循：
1. 单向数据流：事件从 UI 流向 ViewModel，状态从 ViewModel 流向 UI 、
2. 状态不可变：使用 StateFlow 管理状态 
3. 事件驱动：通过密封类定义所有可能的事件 
4. UI 是状态的函数：UI 根据状态渲染，不直接修改状态

##### 项目目录说明
```tree
├── ui（实际业务代码）
│   ├── features(子模块)
│   │   ├── screen
│   │   ├── state
│   │   └── viewmodel
│   ├── Launcher(程序入口，主Activity)
│   └── common(程序内通用的View)
├── core
│   ├── network
│   └── database
└── model(通用的数据Model)
├── theme(主题和颜色)
├── util(封装)
└── common(通用的)
```

#### 注意事项

1.  在Compose中，Modifier的调用顺序很重要，调用越早，嵌套层级越靠外，一般情况顺序应该是这样：
```kotlin
Box(
    modifier = Modifier
        // 1. 尺寸相关
        .fillMaxWidth()    // 或 width(), size(), fillMaxSize() 等
        .height(56.dp)
        
        // 2. 背景和形状
        .background(
            color = Color.White,
            shape = RoundedCornerShape(8.dp)
        )
        .clip(RoundedCornerShape(8.dp))
        
        // 3. 边距
        .padding(horizontal = 16.dp)
        
        // 4. 交互相关
        .clickable { }
        
        // 5. 其他布局约束
        .wrapContentHeight()
)
```
2. collect类似于while，在它之后的代码永远不会执行。



