package kokoro.mobile.hachimi.model

/**
 * Created by xianjie on 2024年12月30日15:02:51
 *
 * Description: 标准数据类 与业务无关的数据都会使用这个（...大概）
 */
data class CommonModel(
    //一般作为唯一标识符
    val id:Int = -1,
    //内容 标题
    val content:String = "",
    //具体的 描述 详情
    val detail:String = "",
    //可能会用到的东西
    val extend:Any? = null,
    //目前的状态
    val state:Boolean = false)