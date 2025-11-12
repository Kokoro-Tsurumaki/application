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
    object TopicTypes : Table() {
        val id = integer("id").autoIncrement()
        val type = varchar("type", length = MAX_VARCHAR_LENGTH).uniqueIndex("type")
        override val primaryKey = PrimaryKey(id, name = "PK_Topic_Type_ID")
    }
    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(TopicTypes)
        }
    }

    suspend fun create(name: String): Int = dbQuery {
        TopicTypes.insert {
            it[type] = name
        }[TopicTypes.id]
    }


    suspend fun readAll(): List<TypeModel> {
        return dbQuery {
            TopicTypes.selectAll()
                .orderBy(TopicTypes.id to SortOrder.ASC)
                .map { TypeModel(it[TopicTypes.id], it[TopicTypes.type]) }
        }
    }

    suspend fun update(id: Int, input: TypeModel) {
        dbQuery {
            TopicTypes.update({ TopicTypes.id eq id }) {
                it[type] = input.type
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            TopicTypes.deleteWhere { TopicTypes.id.eq(id) }
        }
    }
}