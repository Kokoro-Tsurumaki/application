package kokoro.multiplatform.sasanqua.core.record.service

import kokoro.multiplatform.sasanqua.*
import kokoro.multiplatform.sasanqua.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

class RecordService(private val database: Database) {
    object Record : Table() {
        override val tableName: String
            get() = "record"
        val id = integer("id").autoIncrement()
        val title = varchar("title", length = MAX_VARCHAR_LENGTH)
        val types = varchar("types", length = MAX_VARCHAR_LENGTH)
        val hint = varchar("hint", length = MAX_VARCHAR_LENGTH)
        val fileUrl = varchar("fileUrl", length = MAX_VARCHAR_LENGTH)
        val createTime = varchar("createTime", length = MAX_VARCHAR_LENGTH)
        val updateTime = varchar("updateTime", length = MAX_VARCHAR_LENGTH).nullable()

        override val primaryKey = PrimaryKey(id, name = "PK_Record_ID") // name is optional here
    }

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Record)
        }
    }

    suspend fun create(input: RecordModel): Int = dbQuery {
        Record.insert {
            it[title] = input.title
            it[types] = input.types
            it[hint] = input.hint
            it[fileUrl] = input.fileUrl
            it[createTime] = input.createTime
            it[updateTime] = input.updateTime
        }[Record.id]
    }

    suspend fun readAll(): List<RecordModel> {
        return dbQuery {
            Record.selectAll()
                .orderBy(Record.id to SortOrder.ASC)
                .map {
                    RecordModel(
                        it[Record.id],
                        it[Record.types],
                        it[Record.title],
                        it[Record.hint],
                        it[Record.fileUrl],
                        it[Record.createTime],
                        it[Record.updateTime],
                    )
                }
        }
    }

}
