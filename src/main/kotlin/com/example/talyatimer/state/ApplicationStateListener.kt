package com.example.talyatimer.state

import com.example.talyatimer.history.HistoryEntry
import com.example.talyatimer.history.TimerHistory
import com.intellij.ide.AppLifecycleListener
import java.util.*

object ApplicationStateListener: AppLifecycleListener {

    override fun appClosing() {
        super.appClosing()

        HistoryStateService.loadState(TimerHistory.historyList)
    }


    override fun appStarted() {
        super.appStarted()

        TimerHistory.historyList = HistoryStateService.state ?: mutableListOf(HistoryEntry(Date(), 0));

        print(HistoryStateService.state)
        TimerHistory.f()
    }
}