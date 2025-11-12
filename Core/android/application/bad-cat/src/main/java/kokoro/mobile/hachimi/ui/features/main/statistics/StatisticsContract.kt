package kokoro.mobile.hachimi.ui.features.main.statistics

import androidx.compose.ui.graphics.Color
import kokoro.mobile.hachimi.database.Note
import kokoro.mobile.hachimi.ui.common.base.UIContract
import java.time.LocalDate

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Tue Sep 23 16:55:16 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Statistics -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Statistics
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

class StatisticsContract:UIContract {
    data class StatisticsState(
        var createTime:String = "Tue Sep 23 16:55:16 CST 2025",
        val isLoading: Boolean = false,
        val error: String? = null,
        val selectedTimeRange: String = "本月",
        //总支出
        val totalOut: String = "0",
        //总收入
        val totalIn: String = "0",
        //结余
        val surplus: String = "0",
        //今日时间
        var currentDate: LocalDate = LocalDate.now(),
        //月度日期
        var currentDayLengthOfMonth:List<Int> = emptyList(),
        //月度日期 xx.xx格式
        var currentDayLengthOfMonthFormat:List<String> = emptyList(),
        //月度每日支出
        val dayOfMonthOut:List<Double> = emptyList(),
        //月度每日收入
        val dayOfMonthIn:List<Double> = emptyList(),

        //月度 总数据
        val monthDataList: List<Note> = emptyList(),
        val visitData: List<VisitData> = listOf(
            VisitData("label1",90),
            VisitData("label2",20),
            VisitData("label3",35),
            VisitData("label4",74)
        ),
        val trafficSourceData: List<TrafficSource> = listOf(
            TrafficSource("测试",20, Color.Gray)
        ),
        val detailedData: List<DetailedData> = listOf(
            DetailedData("page1",20,12.00,15.00)
        )
    ): UIContract.UIState

    sealed class StatisticsAction: UIContract.UIAction {
        data class SelectTimeRange(val value: String) : StatisticsAction()
        object Reload : StatisticsAction()
    }
    data class VisitData(
        val label: String,
        val count: Int
    )

    data class TrafficSource(
        val name: String,
        val percentage: Int,
        val color: Color
    )

    data class DetailedData(
        val page: String,
        val visits: Int,
        val bounceRate: Double,
        val conversionRate: Double
    )
}