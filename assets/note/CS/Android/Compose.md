### 概述

选择compose主要是因为他自动化状态管理以及未来有可能商业化应用的跨端优势。

### 概念

Composable 注解是Compose的核心机制，他的作用是标记一个函数是可组合函数，该函数会被Compose编译器特殊处理，他在运行时会维护一个组件树，包括组件数节点，当前组件位置、状态，这也是composable方法不需要创建类的原因

其次就是重组机制，在composable注解的方法中可以使用remember存储状态，状态变化时触发重组并智能跳过不需要更新的部分。

### 使用技巧

Modifier的方法调用顺序会对结果有影响，调用越靠前，层数更靠外，效果越优先。

### 在组件树中流动的“环境变量”类似LocalContext.current

Compose是一个树结构，有时底层会需要上层的功能，通过上层一层一层提供实例或通过下层使用高阶方法回调明显不是明智之举，这时候就需要像LocalContext.current一样在上层提供一个访问。

我们可以在最上层通过CompositionLocalProvider提供一个访问点。

当需要该访问点时，compose就会从当前节点开始向上遍历组件树，返回找到的第一个值

CompositionLocal 就像一个在组件树中流动的"环境变量"，可以在需要的地方被访问和修改。

```kotlin
object LauncherUIManage {
    //staticCompositionLocalOf 值改变时不触发重组，一般用于不常变化的值，如主题导航。
    //与此相对还有一个compositionLocalOf 值改变时触发使用处重组，适用于经常变化的值，如当前颜色
    private val Local = staticCompositionLocalOf<LauncherUIManageImpl> {
        error("No LauncherUIManage provided")
    }

    //获取提供的值
    val current: LauncherUIManageImpl
        @Composable
        get() = Local.current

    //中缀表达式 类似于 provides LauncherUIManageImpl()   ->    provides(LauncherUIManageImpl())
    infix fun provides(navController: LauncherUIManageImpl) = Local provides navController
}
```
staticCompositionLocalOf：值改变时不触发重组，一般用于不常变化的值，如主题导航。

compositionLocalOf：值改变时触发使用处重组，适用于经常变化的值，如当前颜色

使用：
```kotlin
val navigationManager = remember { LauncherUIManageImpl(navController) }
//提供值
CompositionLocalProvider(
    LauncherUIManage provides navigationManager
) {
    //获取值
    LauncherUIManage.current.navigateBack()
}

```

