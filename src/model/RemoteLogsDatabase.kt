/*
 * Copyright (C) 2018 Gurpreet Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
