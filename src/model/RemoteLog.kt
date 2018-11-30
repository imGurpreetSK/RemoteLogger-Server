package com.gurpreetsk.model

data class RemoteLog(
    val userUUID: String,
    val timestamp: Long,
    val osName: String,
    val osVersion: String,
    val appVersion: String,
    val logTag: String,
    val logLevel: String,
    val message: String,
    val stackTrace: String
)
