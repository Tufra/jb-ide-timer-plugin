package com.example.talyatimer.timerwindow

import com.example.talyatimer.history.HistoryEntry
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBList
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import java.awt.BorderLayout
import java.awt.Dimension
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import kotlin.time.ExperimentalTime

class TimerToolWindow(toolWindow: ToolWindow) {

    private val timeLabel = JLabel();
    private var historyPanel = JPanel()

    private val resetTimerButton = JButton("Reset");
    private val startTimerButton = JButton("Start");
    private val pauseTimerButton = JButton("Pause");

    private val timerController = TimerController;
    private val windowSettings = WindowSettings;

    private val targetWindow: ToolWindow;

    init {
        addListeners();
        createHistoryContent()

        targetWindow = toolWindow;
        timerController.setTargetLabel(timeLabel);

        startTimerButton.icon = windowSettings.startIcon
        pauseTimerButton.icon = windowSettings.pauseIcon

        updateTimerFontSize(windowSettings.timerFontSize);

        timerController.history.f = { createHistoryContent() };
    }

    private fun updateTimerFontSize(newSize: Float) {
        timeLabel.font = timeLabel.font.deriveFont(newSize);
    }

    private fun addListeners() {
        this.resetTimerButton.addActionListener { _ ->
            val entry: HistoryEntry? = timerController.resetTimer()

            if (entry != null) {
                addHistoryPanelEntry(entry)
            }
        }
        this.startTimerButton.addActionListener { _ -> timerController.startTimer() }
        this.pauseTimerButton.addActionListener { _ -> timerController.pauseTimer() }
    }

    fun getTimerContent(): JPanel {
        lateinit var panel: DialogPanel;

        panel = panel {
            row {
                cell(timeLabel)
                    .horizontalAlign(HorizontalAlign.CENTER)
                    .bold()
            }

            resetTimerButton.addActionListener { _ -> panel.apply() }
            group {
                row {
                    cell(startTimerButton)
                    cell(pauseTimerButton)
                    cell(resetTimerButton)
                }
            }

            collapsibleGroup("Settings") {
                row ("Font Size") {
                    intTextField(1..100).bindIntText(
                            { -> windowSettings.timerFontSize.toInt()},
                            {newSize: Int ->
                                windowSettings.timerFontSize = newSize.toFloat();
                                updateTimerFontSize(windowSettings.timerFontSize)

                    })
                }
                row {
                    checkBox("Send notifications").bindSelected(
                            { -> windowSettings.enableNotifications},
                            {enable: Boolean -> windowSettings.enableNotifications = enable; })
                }

                row {
                    button("Apply") {
                        panel.apply();
                    }
                }
            }
        }

        return panel;
    }

    private fun addHistoryPanelEntry(entry: HistoryEntry) {

        val sdf = SimpleDateFormat("dd/MM/yyyy");

        val row = JPanel()
        row.layout = BoxLayout(row, BoxLayout.X_AXIS)

        val transformedTime = timerController.getTime(entry.duration);

        val glue = Box.Filler(Dimension(0, 0), Dimension(50, 0), Dimension(100, 0))
        val glue2 = Box.Filler(Dimension(0, 0), Dimension(50, 0), Dimension(100, 0))

        val initDateLabel = JLabel(sdf.format(entry.initTime))
        initDateLabel.font = initDateLabel.font.deriveFont(windowSettings.historyFontSize)

        val durationLabel = JLabel(String.format("%02d:%02d:%02d", transformedTime[0], transformedTime[1], transformedTime[2]))
        durationLabel.font = durationLabel.font.deriveFont(windowSettings.historyFontSize)

        val deleteButton = JButton("Delete")
        deleteButton.addActionListener { _ ->
            timerController.history.removeEntry(entry)
            historyPanel.remove(row)
        }

        row.add(initDateLabel)
        row.add(glue)
        row.add(durationLabel)
        row.add(glue2)
        row.add(deleteButton)

        historyPanel.add(row, 0)
    }

    private fun createHistoryContent() {

        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        for (entry in timerController.history.historyList) {
            addHistoryPanelEntry(entry)
        }

        historyPanel = panel;
    }

    fun getHistoryContent(): JPanel {
        return historyPanel
    }
}