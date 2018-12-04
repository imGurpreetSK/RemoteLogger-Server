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
package com.gurpreetsk

import RemoteLogRequest
import com.google.gson.JsonObject
import com.gurpreetsk.model.RemoteLogsDatabase
import com.gurpreetsk.model.RemoteLogsStorage
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
  val db: RemoteLogsStorage = RemoteLogsDatabase()

  install(CallLogging)
  install(ContentNegotiation) {
    gson {
      setPrettyPrinting()
    }
  }

  routing {
    get("/") {
      val list = listOf("Hello", "World")
      call.respond(mapOf("list" to list))
    }

    post("/") {
      val request = call.receive<RemoteLogRequest>()
      log.info("logs: ${request.logs}")
      db.insertLogs(request.logs)
          .also {
            call.respond(HttpStatusCode.OK, JsonObject())
          }
    }
  }
}

