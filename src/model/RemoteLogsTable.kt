package com.gurpreetsk.model

import org.jetbrains.squash.definition.*

object RemoteLogsTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val userUUID = text("userUUID")
    val timestamp = long("timestamp")
    val osName = text("osName")
    val osVersion = text("osVersion")
    val appVersion = text("appVersion")
    val logTag = text("logTag")
    val logLevel = text("logLevel")
    val message = text("message")
    val stackTrace = text("stackTrace")
}