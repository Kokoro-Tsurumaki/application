package kokoro.mobile.hachimi.ui.features.main.statistics

import androidx.lifecycle.viewModelScope
import kokoro.mobile.hachimi.ui.common.base.BaseUIViewModel
import kokoro.mobile.hachimi.core.network.NetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.hachimi.database.DatabaseRepository
import kokoro.mobile.hachimi.database.decompose
import kokoro.mobile.hachimi.util.MoneyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Tue Sep 23 16:55:16 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Statistics -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Statistics
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

 @HiltViewModel
 class StatisticsViewModel @Inject constructor(private val currentRepository: NetRepository,private val databaseRepository: DatabaseRepository) :
     BaseUIViewModel<StatisticsContract.StatisticsState, StatisticsContract.StatisticsAction>(StatisticsContract.StatisticsState()) {


     override fun initializeData() {
         viewModelScope.launch(Dispatchers.IO) {
             //今日
             val (year, month, day) = uiFlow.value.currentDate.decompose()
             //查询今日所在月数据
             val data = databaseRepository.getDataByTypeInMonth(year, month)
             //所有数据
             updateUIState { copy(monthDataList = data) }
             //总支出
             updateUIState { copy(totalOut = MoneyUtils.centsToYuanString(MoneyUtils.sumCents(data.filter { it.type == 1 }.map { it.sum }))) }
             //总收入
             updateUIState { copy(totalIn = MoneyUtils.centsToYuanString(MoneyUtils.sumCents(data.filter { it.type == 2 }.map { it.sum }))) }
             //趋势
             val daysInMonth = uiFlow.value.currentDate.lengthOfMonth()
             //当月所有日
             updateUIState { copy(currentDayLengthOfMonth = (1..daysInMonth).toList() ) }
             //xx.xx格式
             updateUIState { copy(currentDayLengthOfMonthFormat =  uiFlow.value.currentDayLengthOfMonth.map { "${month}.${it}" } ) }
             val dayOfMonthOut = ArrayList<Double>()
             uiFlow.value.currentDayLengthOfMonth.forEach { day ->
                 val sumLong = MoneyUtils.sumCents(uiFlow.value.monthDataList.filter { it.type == 1 && it.day == day }.map { it.sum })
                 val double = MoneyUtils.centsToYuan(sumLong)
                 dayOfMonthOut.add(double)
             }
             updateUIState { copy(dayOfMonthOut =  dayOfMonthOut  ) }
             val dayOfMonthIn = ArrayList<Double>()
             uiFlow.value.currentDayLengthOfMonth.forEach { day ->
                 val sumLong = MoneyUtils.sumCents(uiFlow.value.monthDataList.filter { it.type == 2 && it.day == day }.map { it.sum })
                 val double = MoneyUtils.centsToYuan(sumLong)
                 dayOfMonthIn.add(double)
             }
             updateUIState { copy(dayOfMonthIn =  dayOfMonthIn  ) }
             Timber.i(uiFlow.value.dayOfMonthOut.toString())
             Timber.i(uiFlow.value.dayOfMonthIn.toString())
         }
     }

     override fun onUIAction(event: StatisticsContract.StatisticsAction) {
        when(event){
            StatisticsContract.StatisticsAction.Reload -> {}
            is StatisticsContract.StatisticsAction.SelectTimeRange -> updateUIState { copy(selectedTimeRange = event.value) }

        }
     }
 }