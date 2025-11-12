package kokoro.multiplatform.sasanqua.ui.features.main

import kokoro.multiplatform.sasanqua.model.RecordModel
import kokoro.multiplatform.sasanqua.model.TypeModel
import kokoro.multiplatform.sasanqua.ui.common.base.UIContract

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Oct 29 11:41:13 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Main -Ptpackage=kokoro.multiplatform.sasanqua -Ptpath=composeApp/src/commonMain/kotlin/kokoro/multiplatform/sasanqua/ui/features`
 * Module: Main
 * Package: kokoro.multiplatform.sasanqua
 * =================================================================================================
 */

class MainContract:UIContract {
    data class MainState(
        var createTime:String = "Wed Oct 29 11:41:13 CST 2025",
        //md内容
        var markdownContent:String = "",
        //选中的类型
        var selectedType:String = "",
        //所有类型
        var types:List<TypeModel> = mutableListOf(),
        //所有文章
        var records:List<RecordModel> = mutableListOf()

    ): UIContract.UIState

    sealed class MainAction: UIContract.UIAction {

    }
}