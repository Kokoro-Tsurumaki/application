package kokoro.mobile.hachimi.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.google.gson.Gson
import timber.log.Timber

class DatabaseRepository(application: Application) : AndroidViewModel(application) {
    val db by lazy{
        Room.databaseBuilder(
            application,
            AppDatabase::class.java, "hachimi"
        ).build()
    }

    fun getCategoriesParentALL():List<CategoriesParent>{
        val categoriesParentDao = db.categoriesParentDao()
        val categories: List<CategoriesParent> = categoriesParentDao.getAll()
        return categories
    }

    fun initializeCategoriesParent(){
        val categoriesParentDao = db.categoriesParentDao()
        if (getCategoriesParentALL().isEmpty()){
           listOf(
                CategoriesParent( content = "常用"),
                CategoriesParent( content = "购物"),
                CategoriesParent( content = "食品"),
                CategoriesParent( content = "起居日常"),
                CategoriesParent( content = "出行"),
                CategoriesParent( content = "娱乐"),
                CategoriesParent( content = "医疗"),
                CategoriesParent( content = "培训"),
                CategoriesParent( content = "交际"),
            ).forEach {
                categoriesParentDao.insertAll(it)
            }
        }
    }

    fun getCategoriesChildALL():List<CategoriesChild>{
        val categoriesChildDao = db.categoriesChildDao()
        val categories: List<CategoriesChild> = categoriesChildDao.getAll()
        return categories
    }

    fun initializeCategoriesChild(){
        val categoriesChildDao = db.categoriesChildDao()
        if (getCategoriesChildALL().isEmpty()){
            listOf(
                CategoriesChild( pid =  1, content = "日常用品"),
                CategoriesChild( pid =  1, content = "衣鞋包"),
                CategoriesChild( pid =  1, content = "化妆饰品"),
                CategoriesChild( pid =  1, content = "数码产品"),
                CategoriesChild( pid =  1, content = "其他购物"),
                CategoriesChild( pid =  2, content = "早午晚餐"),
                CategoriesChild( pid =  2, content = "烟酒茶咖啡"),
                CategoriesChild( pid =  2, content = "水果零食"),
                CategoriesChild( pid =  2, content = "其他食品"),
                CategoriesChild( pid =  3, content = "水电煤气"),
                CategoriesChild( pid =  3, content =  "房租"),
                CategoriesChild( pid =  3, content =  "物业管理"),
                CategoriesChild( pid =  3, content =  "网费"),
                CategoriesChild( pid =  3, content =  "维护保养"),
                CategoriesChild( pid =  4, content =  "公共交通"),
                CategoriesChild( pid =  4, content =  "打车租车"),
                CategoriesChild( pid =  4, content =  "私家车"),
                CategoriesChild( pid =  5, content =  "运动健身"),
                CategoriesChild( pid =  5, content =  "交际聚会"),
                CategoriesChild( pid =  5, content =  "休闲玩乐"),
                CategoriesChild( pid =  5, content =  "旅游度假"),
                CategoriesChild( pid =  6, content =  "药品"),
                CategoriesChild( pid =  6, content =  "保健"),
                CategoriesChild( pid =  6, content =  "美容"),
                CategoriesChild( pid =  6, content =  "治疗"),
                CategoriesChild( pid =  7, content =  "培训"),
                CategoriesChild( pid =  7, content =  "书杂费"),
                CategoriesChild( pid =  8, content =  "送礼请客"),
                CategoriesChild( pid =  8, content =  "孝敬长辈"),
            ).forEach {
                categoriesChildDao.insertAll(it)
            }
        }
    }

    fun insertNote(value:Note,callback:(()-> Unit)){
        val noteDao = db.noteDao()
        Timber.i("Note insert: ${Gson().toJson(value)}")
        noteDao.insertAll(value)
        callback()
    }

    fun getDataByTypeInMonth(year:Int,month:Int):List<Note>{
        val noteDao = db.noteDao()
        val data = noteDao.getDataByTypeInMonth(year,month)
        data.forEach {
            Timber.i("Note insert: ${Gson().toJson(it)}")
        }
        return data
    }

}