package com.example.talyatimer.timerwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class TimerToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        val targetTimerToolWindow = TimerToolWindow(toolWindow);
        val contentFactory = ContentFactory.SERVICE.getInstance();

        var timerContent = contentFactory.createContent(targetTimerToolWindow.getTimerContent(), "Timer", false);
        var historyContent = contentFactory.createContent(targetTimerToolWindow.getHistoryContent(), "History", false);

        toolWindow.contentManager.addContent(timerContent);
        toolWindow.contentManager.addContent(historyContent);

    }

}