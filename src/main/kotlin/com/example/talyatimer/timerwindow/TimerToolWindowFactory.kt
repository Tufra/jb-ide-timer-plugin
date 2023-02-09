package com.example.talyatimer.timerwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.content.Content

class TimerToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        val targetTimerToolWindow = TimerToolWindow(toolWindow);
        val contentFactory = ContentFactory.SERVICE.getInstance();
        var content: Content = contentFactory.createContent(targetTimerToolWindow.getContent(), "", false);
        toolWindow.contentManager.addContent(content);

    }

}