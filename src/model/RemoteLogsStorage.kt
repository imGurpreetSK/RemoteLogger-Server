package com.gurpreetsk.model

interface RemoteLogsStorage {
    fun insertLogs(logs: List<RemoteLog>)
}
