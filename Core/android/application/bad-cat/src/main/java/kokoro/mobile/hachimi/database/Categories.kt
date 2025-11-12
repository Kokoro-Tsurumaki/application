package kokoro.mobile.hachimi.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class CategoriesParent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid")
    val pid: Int = 0,
    @ColumnInfo(name = "content") val content: String,
)

@Entity
data class CategoriesChild(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cid")
    val cid: Int = 0,
    @ColumnInfo(name = "pid")
    val pid: Int,
    @ColumnInfo(name = "content") val content: String,
)

@Dao
interface CategoriesParentDao {
    @Query("SELECT * FROM CategoriesParent")
    fun getAll(): List<CategoriesParent>

    @Query("SELECT * FROM CategoriesParent WHERE pid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CategoriesParent>

    @Insert
    fun insertAll(vararg categories: CategoriesParent)

    @Delete
    fun delete(Categories: CategoriesParent)
}

@Dao
interface CategoriesChildDao {
    @Query("SELECT * FROM CategoriesChild")
    fun getAll(): List<CategoriesChild>

    @Query("SELECT * FROM CategoriesChild WHERE pid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CategoriesChild>

    @Insert
    fun insertAll(vararg categories: CategoriesChild)

    @Delete
    fun delete(Categories: CategoriesChild)
}