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
    object Types : Table() {
        val id = integer("id").autoIncrement()
        val type = varchar("type", length = MAX_VARCHAR_LENGTH).uniqueIndex("type")
        override val primaryKey = PrimaryKey(id, name = "PK_Type_ID")
    }

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Types)
        }
    }



    suspend fun create(name: String): Int = dbQuery {
        Types.insert {
            it[type] = name
        }[Types.id]
    }

    suspend fun read(id: Int): TypeModel? {
        return dbQuery {
            Types.selectAll()
                .where { Types.id eq id }
                .map { TypeModel(it[Types.id], it[Types.type]) }
                .singleOrNull()
        }
    }

    suspend fun readAll(): List<TypeModel> {
        return dbQuery {
            Types.selectAll()
                .orderBy(Types.id to SortOrder.ASC)
                .map { TypeModel(it[Types.id], it[Types.type]) }
        }
    }

    suspend fun update(id: Int, input: TypeModel) {
        dbQuery {
            Types.update({ Types.id eq id }) {
                it[type] = input.type
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Types.deleteWhere { Types.id.eq(id) }
        }
    }
}