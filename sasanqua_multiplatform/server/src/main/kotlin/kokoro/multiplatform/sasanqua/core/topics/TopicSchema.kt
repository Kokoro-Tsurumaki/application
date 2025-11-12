package kokoro.multiplatform.sasanqua.core.topics

import kokoro.multiplatform.sasanqua.*
import kokoro.multiplatform.sasanqua.core.record.service.RecordService.*
import kokoro.multiplatform.sasanqua.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

class TopicService(private val database: Database) {
    object Topic : Table() {
        override val tableName: String
            get() = "topic"
        val id = integer("id").autoIncrement()
        val title = varchar("title", length = MAX_VARCHAR_LENGTH)
        val answer = varchar("answer", length = MAX_VARCHAR_LENGTH)
        val type = varchar("type", length = MAX_VARCHAR_LENGTH)

        override val primaryKey = PrimaryKey(id, name = "PK_Topic_ID") // name is optional here
    }

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Topic)
        }
    }

    suspend fun create(input: TopicModel): Int = dbQuery {
        Topic.insert {
            it[title] = input.title
            it[type] = input.type
            it[answer] = input.answer
        }[Topic.id]
    }

    suspend fun readAll(): List<TopicModel> {
        return dbQuery {
            Topic.selectAll()
                .orderBy(Topic.id to SortOrder.ASC)
                .map {
                    TopicModel(
                        it[Record.id],
                        it[Topic.type],
                        it[Topic.title],
                        it[Topic.answer]
                    )
                }
        }
    }

    suspend fun read(type: String): List<TopicModel> {
        return dbQuery {
            Topic.selectAll()
                .where { Topic.type eq type }
                .map { TopicModel(it[Topic.id], it[Topic.type],it[Topic.title],it[Topic.answer]) }
        }
    }
}