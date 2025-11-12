package kokoro.mobile.hachimi.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoriesParent::class, CategoriesChild::class, Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesParentDao(): CategoriesParentDao
    abstract fun categoriesChildDao(): CategoriesChildDao
    abstract fun noteDao(): NoteDao
}