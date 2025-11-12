
1.  List item one.

    List item one continued with a second paragraph followed by an
    Indented block.

        $ ls *.sh
        $ mv *.sh ~/tmp

    List item continued with a third paragraph.

2.  List item two continued with an open block.

    This paragraph is part of the preceding list item.

    1. This list is nested and does not require explicit item continuation.

       This paragraph is part of the preceding list item.

    2. List item b.

    This paragraph belongs to item two of the outer list.


## 🎯 求职者角度：如何介绍你的MVI设计

### 开场介绍（1-2分钟）

"我在项目中实现了一套基于MVI模式的Android架构，主要解决了传统MVP/MVVM架构中状态管理混乱和数据流向不清晰的问题。我的设计有几个核心特点："

### 核心亮点介绍

#### 1. 三层泛型设计的类型安全

abstract class BaseUIViewModel<S : UIContract.UIState, A : UIContract.UIAction, R : ShareRepository>

表达方式：

> "我通过三个泛型参数实现了编译时类型安全：S代表UI状态，A代表用户行为，R代表数据层。这样确保了每个ViewModel只能处理对应的状态和事件，避免了运行时的类型错误，提高了代码的健壮性。"

#### 2. 统一的状态管理容器

表达方式：

> "我设计了一个BaseUIViewModel作为状态管理容器，所有业务ViewModel都继承它。这个容器负责：

> - StateFlow管理UI状态

> - Channel处理副作用（如Toast、导航）

> - Flow处理用户行为

>

> 这样保证了整个项目的状态管理模式完全一致。"

#### 3. 职责分离的Repository设计

表达方式：

> "我将网络请求封装在独立的Repository中，每个业务模块都有自己的Repository。Repository通过sealed class定义请求事件，既保证了类型安全，又便于统一处理网络状态。"

#### 4. 自动化开发工具

表达方式：

> "为了提高团队开发效率，我开发了Gradle自动化模板工具，一行命令就能生成标准的MVI文件结构：

> ```bash

> ./gradlew createTemplateForMVI -Ptname=Login -Ptpath=1

> ```

> 这大大减少了重复代码编写，保证了架构一致性。"

### 与官方/其他实现的区别

#### 对比Google官方MVI

表达方式：

> "相比Google官方的MVI实现，我的设计有几个改进：

> 1. 更强的类型约束：通过泛型确保编译时安全

> 2. 统一的基类：所有ViewModel都有相同的结构

> 3. 自动化工具：减少样板代码

> 4. 网络层集成：Repository直接集成到MVI流程中"

#### 对比其他开源方案

表达方式：

> "相比MvRx或Orbit等开源方案，我的设计更注重：

> - 业务适配性：针对我们的业务场景定制

> - 学习成本：团队成员更容易上手

> - 调试友好：内置网络调试工具

> - 性能优化：减少不必要的重组"

---

## 🎯 面试官角度：可能的问题和标准答案

### Q1: 为什么选择MVI而不是MVVM？

标准回答：

> "MVVM在复杂业务场景下容易出现状态不一致的问题，比如多个LiveData同时更新时，UI可能处于中间状态。MVI通过单一状态源解决了这个问题：

> - 状态可预测：任何时刻都只有一个明确的状态

> - 调试友好：可以清晰追踪状态变化

> - 测试简单：状态是不可变的，便于单元测试"

### Q2: 你的MVI实现相比官方有什么优势？

标准回答：

> "我的实现主要优势在于：

> 1. 工程化程度更高：提供了完整的基础设施和工具链

> 2. 团队协作友好：统一的代码结构，降低code review成本

> 3. 业务适配性强：针对我们的网络层和业务需求定制

> 4. 渐进式迁移：可以逐步从现有架构迁移过来"

### Q3: 三个泛型参数会不会过于复杂？

标准回答：

> "确实会增加一定复杂度，但带来的收益更大：

> - 编译时安全：避免运行时类型错误

> - IDE支持：更好的代码提示和重构支持

> - 架构一致性：强制团队遵循相同模式

>

> 而且通过模板工具，开发者不需要手写这些泛型代码，实际使用中感知不到复杂度。"

### Q4: 如何保证这套架构的性能？

标准回答：

> "性能优化主要从几个方面：

> 1. StateFlow使用：只在状态真正变化时才通知UI

> 2. Compose优化：合理使用remember和derivedStateOf

> 3. 网络请求优化：Repository层做请求去重和缓存

> 4. 内存管理：及时清理Channel和Flow订阅"

### Q5: 这套架构如何进行单元测试？

标准回答：

class LoginViewModelTest {

    @Test

    fun `login success should update state correctly`() {

        // Given

        val mockRepository = mockk<LoginRepository>()

        val viewModel = LoginViewModel(mockRepository)

        // When

        viewModel.sendUIAction(LoginAction.Login("phone", "password"))

        // Then

        assertEquals(expectedState, viewModel.uiFlow.value)

    }

}

---

## 🚀 你的设计优势总结

### 技术优势

1. ✅ 类型安全: 编译时错误检查

2. ✅ 架构一致性: 统一的代码结构

3. ✅ 开发效率: 自动化工具支持

4. ✅ 可维护性: 清晰的职责分离

5. ✅ 可测试性: 纯函数式状态管理

### 工程优势

1. ✅ 团队协作: 降低沟通成本

2. ✅ 代码质量: 减少bug产生

3. ✅ 学习成本: 相比其他方案更容易掌握

4. ✅ 扩展性: 便于添加新功能

5. ✅ 调试友好: 内置调试工具

## 🎯 面试建议

### 准备demo代码

准备一个简单的登录页面demo，现场展示：

- 状态如何管理

- 事件如何处理

- 网络请求如何集成

- 模板如何生成

### 准备架构图

画一个清晰的架构流程图，展示数据流向：

UI → Action → ViewModel → Repository → Network

                ↓

UI ← State ← ViewModel ← Response ← Network

### 强调实际价值

不要只讲技术细节，要强调解决了什么实际问题：

- 减少了多少bug

- 提高了多少开发效率

- 降低了多少维护成本

记住：面试官更关心你的架构思考能力和解决问题的能力，而不是纯技术实现。重点展示你如何分析问题、设计方案、权衡利弊的过程。




当调用微信支付宝支付时 接受广播 弹出弹窗