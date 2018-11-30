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

