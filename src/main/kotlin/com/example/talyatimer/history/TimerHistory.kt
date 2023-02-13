package com.example.talyatimer.history

import java.util.*

object TimerHistory {

    var historyList = mutableListOf<HistoryEntry>();

    var f = { }

    fun addEntry(initTime: Date, duration: Long): HistoryEntry {
        val entry = HistoryEntry(initTime, duration)
        historyList.add(entry);

        return entry
    }

    fun removeEntry(entry: HistoryEntry) {
        historyList.remove(entry)
    }

    fun removeEntryById(id: UUID): Boolean {
        return historyList.removeIf {el -> el.id == id}
    }

}

data class HistoryEntry(val initTime: Date, val duration: Long, val id: UUID = UUID.randomUUID());