package com.example.talyatimer.timerwindow

import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class TimerToolWindow(toolWindow: ToolWindow) {

    private val timeLabel: JLabel = JLabel();

    private val resetTimerButton = JButton("reset");
    private val startTimerButton = JButton("start");
    private val pauseTimerButton = JButton("pause");

    private val timerController = TimerController(timeLabel);
    private val targetWindow: ToolWindow;

    init {
        addListeners();
        targetWindow = toolWindow;
    }

    private fun addListeners() {
        this.resetTimerButton.addActionListener { _ -> timerController.resetTimer() }
        this.startTimerButton.addActionListener { _ -> timerController.startTimer() }
        this.pauseTimerButton.addActionListener { _ -> timerController.pauseTimer() }
    }

    private fun getPanel(): JPanel {

        return panel {
            row {
                cell(timeLabel)
                    .horizontalAlign(HorizontalAlign.CENTER)
                    .verticalAlign(VerticalAlign.FILL)
                    .bold()
            }.resizableRow()

            group {
                row {
                    cell(startTimerButton)
                    cell(pauseTimerButton)
                    cell(resetTimerButton)
                }
            }
        }
    }

    fun getContent(): JPanel {
        return getPanel();
    }
}