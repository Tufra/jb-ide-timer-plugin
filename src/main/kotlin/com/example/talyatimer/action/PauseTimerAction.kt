package com.example.talyatimer.action

import com.example.talyatimer.timerwindow.TimerController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class PauseTimerAction: AnAction() {

    private val timer = TimerController;

    override fun actionPerformed(e: AnActionEvent) {
        timer.pauseTimer();
    }
}