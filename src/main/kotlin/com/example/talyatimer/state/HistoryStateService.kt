package com.example.talyatimer.state

import com.example.talyatimer.history.HistoryEntry
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import java.util.*

@Service
@State(name = "TalyaTimerHistory", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)], reloadable = false)
object HistoryStateService: PersistentStateComponent<MutableList<HistoryEntry>> {

    private var stateList: MutableList<HistoryEntry> = mutableListOf(HistoryEntry(Date(), 228));

    override fun getState(): MutableList<HistoryEntry>? {
        return stateList
    }

    override fun loadState(state: MutableList<HistoryEntry>) {
        stateList = state
    }
}