package kokoro.multiplatform.sasanqua.core.record.service

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


class TypeService(private val database: Database) {
    object RecordType : Table() {
        override val tableName: String
            get() = "record_type"
        val id = integer("id").autoIncrement()
        val type = varchar("type", length = MAX_VARCHAR_LENGTH).uniqueIndex("type")
        override val primaryKey = PrimaryKey(id, name = "PK_record_type_ID")
    }

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(RecordType)
        }
    }



    suspend fun create(name: String): Int = dbQuery {
        RecordType.insert {
            it[type] = name
        }[RecordType.id]
    }

    suspend fun read(id: Int): TypeModel? {
        return dbQuery {
            RecordType.selectAll()
                .where { RecordType.id eq id }
                .map { TypeModel(it[RecordType.id], it[RecordType.type]) }
                .singleOrNull()
        }
    }

    suspend fun readAll(): List<TypeModel> {
        return dbQuery {
            RecordType.selectAll()
                .orderBy(RecordType.id to SortOrder.ASC)
                .map { TypeModel(it[RecordType.id], it[RecordType.type]) }
        }
    }

    suspend fun update(id: Int, input: TypeModel) {
        dbQuery {
            RecordType.update({ RecordType.id eq id }) {
                it[type] = input.type
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            RecordType.deleteWhere { RecordType.id.eq(id) }
        }
    }
}