package com.gurpreetsk.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values

class RemoteLogsDatabase(
    val db: DatabaseConnection = H2Connection.createMemoryConnection(catalogue = "DB_CLOSE_DELAY=-1")
) : RemoteLogsStorage {
  init {
    db.transaction {
      databaseSchema().create(RemoteLogsTable)
    }
  }

  override fun insertLogs(logs: List<RemoteLog>) {
    if (logs.isNotEmpty()) {
      GlobalScope.launch {
        db.transaction {
          insertInto(RemoteLogsTable).values {
            logs.forEach { log ->
              it[userUUID] = log.userUUID
              it[timestamp] = log.timestamp
              it[osName] = log.osName
              it[osVersion] = log.osVersion
              it[appVersion] = log.appVersion
              it[logTag] = log.logTag
              it[logLevel] = log.logLevel
              it[message] = log.message
              it[stackTrace] = log.stackTrace
            }
          }.execute()
        }
      }
    }
  }
}
