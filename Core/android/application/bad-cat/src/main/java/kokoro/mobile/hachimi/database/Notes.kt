package kokoro.mobile.hachimi.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import java.time.LocalDate

@Entity(
    tableName = "note",
    indices = [
        Index(value = ["year", "month"], unique = false),
        Index(value = ["year"], unique = false),
        Index(value = ["month"], unique = false)
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoriesParent::class,
            parentColumns = ["pid"],
            childColumns = ["pid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoriesChild::class,
            parentColumns = ["cid"],
            childColumns = ["cid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nid")
    val nid: Int,
    @ColumnInfo(name = "pid") val pid: Int,
    @ColumnInfo(name = "cid") val cid: Int,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "month") val month: Int,
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "sum") val sum: Long,
)

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM Note WHERE pid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Note>

    // 查询某年的所有数据
    @Query("SELECT * FROM note WHERE year = :year")
    fun getDataInYear(year: Int): List<Note>

    // 查询某个日期范围的数据（例如从某年某月到某年某月）
    @Query("""
        SELECT * FROM note 
        WHERE (year > :startYear OR (year = :startYear AND month >= :startMonth)) 
        AND (year < :endYear OR (year = :endYear AND month <= :endMonth))
        ORDER BY year, month, day
    """)
    fun getDataBetween(startYear: Int, startMonth: Int, endYear: Int, endMonth: Int): List<Note>


    // 按类型查询某年月的数据
    @Query("SELECT * FROM note WHERE year = :year AND month = :month")
    fun getDataByTypeInMonth(year: Int, month: Int): List<Note>

    // 获取最新数据（按时间降序）
    @Query("SELECT * FROM note ORDER BY year DESC, month DESC, day DESC LIMIT :limit")
    fun getRecentNotes(limit: Int): List<Note>

    // 根据pid查询数据
    @Query("SELECT * FROM note WHERE pid = :pid ORDER BY year DESC, month DESC, day DESC")
    fun getNotesByPid(pid: Int): List<Note>

    // 根据cid查询数据
    @Query("SELECT * FROM note WHERE cid = :cid ORDER BY year DESC, month DESC, day DESC")
    fun getNotesByCid(cid: Int): List<Note>

    @Insert
    fun insertAll(vararg note: Note)

    @Delete
    fun delete(note: Note)
}

/**
* 将 LocalDate 分解为年、月、日三个字段
*/
fun LocalDate.decompose(): Triple<Int, Int, Int> {
    return Triple(year, monthValue, dayOfMonth)
}