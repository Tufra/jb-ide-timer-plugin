package com.example.talyatimer.timerwindow

import com.example.talyatimer.history.HistoryEntry
import com.example.talyatimer.history.TimerHistory
import com.example.talyatimer.notification.StartTimerNotifier
import groovy.lang.Tuple2
import java.util.*
import javax.swing.JLabel

object TimerController {

    private var model = object {
        var seconds: Long = 0
    };

    private var beginTime = Date();
    private var timer: Timer = Timer();
    private var targetLabel: JLabel = JLabel();
    private var isCancelled: Boolean = true;

    private val notifier = StartTimerNotifier;

    val history = TimerHistory;

    init {
        model.seconds = 0;
    }

    private fun updateTime() {
        model.seconds++;

        updateLabel()
    }

    private fun updateLabel() {
        val time: Array<Long> = getTime(model.seconds);

        targetLabel.text = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
    }

    fun setTargetLabel(targetLabel: JLabel) {
        this.targetLabel = targetLabel;

        updateLabel();
    }

    fun startTimer() {
        beginTime = Date();

        if (isCancelled) {
            timer = Timer();

            val task = object : TimerTask() {
                override fun run() {
                    updateTime()
                }
            }

            timer.schedule(task, Date(), 1000);
            isCancelled = false;

            notifier.notifyStart();
        }
    }

    fun pauseTimer() {

        if (!isCancelled) {
            timer.cancel();
            isCancelled = true;

            notifier.notifyPause();
        }
    }

    fun resetTimer(): HistoryEntry? {

        var entry: HistoryEntry? = null

        if (!isCancelled) {
            timer.cancel();
            isCancelled = true;

            val snapshot: Tuple2<Date, Long> = getTimerSnapshot()
            entry = history.addEntry(snapshot.v1, snapshot.v2);
        }

        model.seconds = 0;
        updateLabel();

        return entry
    }

    fun getTimerSnapshot(): Tuple2<Date, Long> {
        return Tuple2(beginTime, model.seconds);
    }

    fun getTime(seconds: Long): Array<Long> {
        val hours = seconds / 3600;
        val minutes = (seconds % 3600) / 60;
        val seconds = seconds % 60;

        return arrayOf(hours, minutes, seconds);
    }
}