package kokoro.mobile.hachimi.ui.features.main.home

import kokoro.mobile.hachimi.database.CategoriesChild
import kokoro.mobile.hachimi.database.CategoriesParent
import kokoro.mobile.hachimi.ui.common.base.UIContract
import java.time.LocalDate

/**
 * Description:
 */
class HomeContract : UIContract {
    data class HomeState(
        // 分类选项
        val parentCategories: List<CategoriesParent> = listOf(),
        val childCategories: List<CategoriesChild> = listOf(),
        var selectedCategory: String = "日常支出",
        var selectedDate: LocalDate = LocalDate.now(),
        var inputValue:String = "0",
        var currentInputType:String = "支出",
        var showDatePicker: Boolean = false,
    ) : UIContract.UIState

    sealed class HomeAction() : UIContract.UIAction {
        //更新选中分类
        class UpdateSelectedCategory(val value:String): HomeAction()
        //更新选中时间
        class UpdateSelectedDate(val value:LocalDate): HomeAction()
        //更新时间弹窗状态
        class ShowDatePicker(val value:Boolean): HomeAction()
        class UpdateInputType(val value:String): HomeAction()
        //
        class NumberKeyboardDelete(): HomeAction()
        class NumberKeyboardFinish(): HomeAction()
        class NumberKeyboardDecimal(): HomeAction()
        class NumberKeyboardNumber(val value: String): HomeAction()
    }

}