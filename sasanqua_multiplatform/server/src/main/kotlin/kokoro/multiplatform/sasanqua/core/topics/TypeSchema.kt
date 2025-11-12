package kokoro.multiplatform.sasanqua.core.topics

import kokoro.multiplatform.sasanqua.MAX_VARCHAR_LENGTH
import kokoro.multiplatform.sasanqua.dbQuery
import kokoro.multiplatform.sasanqua.model.TypeModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TopicTypeService(private val database: Database) {
    object TopicType : Table() {
        override val tableName: String
            get() = "topic_type"
        val id = integer("id").autoIncrement()
        val type = varchar("type", length = MAX_VARCHAR_LENGTH).uniqueIndex("type")
        override val primaryKey = PrimaryKey(id, name = "PK_topic_type_ID")
    }
    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(TopicType)
        }
    }

    suspend fun create(name: String): Int = dbQuery {
        TopicType.insert {
            it[type] = name
        }[TopicType.id]
    }


    suspend fun readAll(): List<TypeModel> {
        return dbQuery {
            TopicType.selectAll()
                .orderBy(TopicType.id to SortOrder.ASC)
                .map { TypeModel(it[TopicType.id], it[TopicType.type]) }
        }
    }

    suspend fun update(id: Int, input: TypeModel) {
        dbQuery {
            TopicType.update({ TopicType.id eq id }) {
                it[type] = input.type
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            TopicType.deleteWhere { TopicType.id.eq(id) }
        }
    }
}