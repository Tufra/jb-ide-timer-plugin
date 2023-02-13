package com.example.talyatimer.notification

import com.example.talyatimer.timerwindow.WindowSettings
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType

object StartTimerNotifier {

    private const val groupId = "TalyaTimer Notifications";
    private const val startMessage = "Timer started";
    private const val pauseMessage = "Timer paused";

    private val settings = WindowSettings

    private val group = NotificationGroupManager.getInstance()
            .getNotificationGroup(groupId)

    fun notifyStart() {
        if (settings.enableNotifications) {
            group.createNotification(startMessage, NotificationType.INFORMATION).notify(null);
        }

    }

    fun notifyPause() {
        if (settings.enableNotifications) {
            group.createNotification(pauseMessage, NotificationType.INFORMATION).notify(null);
        }
    }
}