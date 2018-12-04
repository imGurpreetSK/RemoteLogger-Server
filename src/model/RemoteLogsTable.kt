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