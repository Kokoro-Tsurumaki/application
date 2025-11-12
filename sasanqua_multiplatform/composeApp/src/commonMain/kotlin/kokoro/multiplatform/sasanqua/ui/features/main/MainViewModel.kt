package kokoro.multiplatform.sasanqua.ui.features.main

import androidx.lifecycle.viewModelScope
import kokoro.multiplatform.sasanqua.model.RecordModel
import kokoro.multiplatform.sasanqua.model.TypeModel
import kokoro.multiplatform.sasanqua.ui.common.base.BaseUIViewModel
import kokoro.multiplatform.sasanqua.network.client.ApplicationApi
import kokoro.multiplatform.sasanqua.network.wrapper.ResultWrapper

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Oct 29 11:41:13 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Main -Ptpackage=kokoro.multiplatform.sasanqua -Ptpath=composeApp/src/commonMain/kotlin/kokoro/multiplatform/sasanqua/ui/features`
 * Module: Main
 * Package: kokoro.multiplatform.sasanqua
 * =================================================================================================
 */

class MainViewModel constructor(private val currentRepository: ApplicationApi) :
    BaseUIViewModel<MainContract.MainState, MainContract.MainAction>(MainContract.MainState()) {


    override fun initializeData() {
        currentRepository.getTypes(viewModelScope, object : ResultWrapper.JsonWrapperResultCallback<List<TypeModel>> {
            override fun onLoading() {

            }

            override fun onSuccess(code: Int, msg: String, data: List<TypeModel>?) {
                data?.let {
                    updateUIState { copy(types = it) }
                }
            }

            override fun onFailure(t: Throwable) {
                println(t)
            }
        })
        currentRepository.readMd(viewModelScope, object : ResultWrapper.StringResultCallback {
            override fun onLoading() {

            }

            override fun onSuccess(data: String) {
                updateUIState { copy(markdownContent = data) }
            }

            override fun onFailure(t: Throwable) {

            }
        })

        currentRepository.getRecord(viewModelScope,object:ResultWrapper.JsonWrapperResultCallback<List<RecordModel>>{
            override fun onLoading() {

            }

            override fun onSuccess(code: Int, msg: String, data: List<RecordModel>?) {
                data?.let {
                    updateUIState { copy(records = data) }
                }
            }

            override fun onFailure(t: Throwable) {

            }
        })

    }

    override fun onUIAction(event: MainContract.MainAction) {
        when (event) {
            else -> {}
        }
    }
}