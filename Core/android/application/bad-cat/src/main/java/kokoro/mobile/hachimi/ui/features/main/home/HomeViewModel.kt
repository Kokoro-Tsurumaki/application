package kokoro.mobile.hachimi.ui.features.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import kokoro.mobile.hachimi.ui.common.base.BaseUIViewModel
import kokoro.mobile.hachimi.core.network.NetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.hachimi.database.DatabaseRepository
import kokoro.mobile.hachimi.database.Note
import kokoro.mobile.hachimi.database.decompose
import kokoro.mobile.hachimi.util.MoneyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

/**
 * Created by  on 2025年4月11日14:01:34
 *
 * Description:
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val currentRepository: NetRepository,private val databaseRepository: DatabaseRepository) :
    BaseUIViewModel<HomeContract.HomeState, HomeContract.HomeAction>( HomeContract.HomeState()) {


    override fun initializeData() {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.initializeCategoriesParent()
            databaseRepository.initializeCategoriesChild()
            updateUIState { copy(parentCategories = databaseRepository.getCategoriesParentALL()) }
            updateUIState { copy(childCategories = databaseRepository.getCategoriesChildALL()) }
        }

    }


    override fun onUIAction(event: HomeContract.HomeAction) {
        when(event){
            is HomeContract.HomeAction.UpdateSelectedCategory -> {
                updateUIState { copy(selectedCategory = event.value) }
            }

            is HomeContract.HomeAction.ShowDatePicker -> {
                updateUIState { copy(showDatePicker = event.value) }
            }
            is HomeContract.HomeAction.UpdateInputType -> {
                updateUIState { copy(currentInputType = event.value) }
            }
            is HomeContract.HomeAction.UpdateSelectedDate -> {
                updateUIState { copy(selectedDate = event.value) }
            }

            is HomeContract.HomeAction.NumberKeyboardDelete -> {
                if (uiFlow.value.inputValue.length > 1) {
                    updateUIState { copy(inputValue = uiFlow.value.inputValue.dropLast(1) ) }
                } else {
                    updateUIState { copy(inputValue = "0" ) }
                }
            }

            is HomeContract.HomeAction.NumberKeyboardDecimal -> {
                if (!uiFlow.value.inputValue.contains(".")) {
                    updateUIState { copy(inputValue = uiFlow.value.inputValue+"." ) }
                }
            }
            is HomeContract.HomeAction.NumberKeyboardFinish -> {
                val currentCategory = uiFlow.value.childCategories.find { it.content == uiFlow.value.selectedCategory }
                if (currentCategory == null){
                    showToast("请选择分类")
                    return
                }
                if (uiFlow.value.inputValue == "0"){
                    showToast("请输入金额")
                    return
                }
                val (year, month, day) = uiFlow.value.selectedDate.decompose()
                viewModelScope.launch(Dispatchers.IO) {
                    databaseRepository.insertNote(Note(0,currentCategory.pid,currentCategory.cid,year,month,day,
                        if (uiFlow.value.currentInputType == "支出") 1 else 2,
                        MoneyUtils.stringToCents(uiFlow.value.inputValue))){
                        updateUIState { copy(inputValue = "0" ) }
                    }
                }
            }
            is HomeContract.HomeAction.NumberKeyboardNumber -> {
                if (uiFlow.value.inputValue == "0") {
                    updateUIState { copy(inputValue = event.value ) }
                } else {
                    if (uiFlow.value.inputValue.contains(".")){
                        if (uiFlow.value.inputValue.split(".")[1].length >= 2){
                            return
                        }
                        if (uiFlow.value.inputValue.length >= 11){
                            return
                        }
                    }else{
                        if (uiFlow.value.inputValue.length >= 8){
                            return
                        }
                    }
                    updateUIState { copy(inputValue = uiFlow.value.inputValue + event.value ) }
                }
            }
        }

    }

}