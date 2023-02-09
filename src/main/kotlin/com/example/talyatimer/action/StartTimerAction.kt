package com.example.talyatimer.action

import com.example.talyatimer.timerwindow.TimerController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class StartTimerAction(): AnAction() {

    private val timer = TimerController;
    override fun actionPerformed(e: AnActionEvent) {
        timer.startTimer();
    }
}