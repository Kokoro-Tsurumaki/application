package kokoro.multiplatform.sasanqua.core.record.service

import kokoro.multiplatform.sasanqua.*
import kokoro.multiplatform.sasanqua.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

class RecordService(private val database: Database) {
    object Records : Table() {
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
            SchemaUtils.create(Records)
        }
    }

    suspend fun create(input: RecordModel): Int = dbQuery {
        Records.insert {
            it[title] = input.title
            it[types] = input.types
            it[hint] = input.hint
            it[fileUrl] = input.fileUrl
            it[createTime] = input.createTime
            it[updateTime] = input.updateTime
        }[Records.id]
    }

    suspend fun readAll(): List<RecordModel> {
        return dbQuery {
            Records.selectAll()
                .orderBy(Records.id to SortOrder.ASC)
                .map {
                    RecordModel(
                        it[Records.id],
                        it[Records.types],
                        it[Records.title],
                        it[Records.hint],
                        it[Records.fileUrl],
                        it[Records.createTime],
                        it[Records.updateTime],
                    )
                }
        }
    }

}
