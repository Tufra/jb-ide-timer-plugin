package com.example.talyatimer.timerwindow

import java.util.*
import javax.swing.JLabel
import kotlin.time.ExperimentalTime
import kotlin.time.TestTimeSource

@OptIn(ExperimentalTime::class)
class TimerController(timeLabel: JLabel) {

    private var model = TimerModel();
    private val timeSource = TestTimeSource();
    private var beginTime = timeSource.markNow();
    private var timer: Timer = Timer();
    private val targetLabel: JLabel;
    private var isCancelled: Boolean = true;

    init {
        targetLabel = timeLabel;
        model.seconds = 0;

        updateLabel()
    }

    private fun updateTime() {
        model.seconds++;

        updateLabel()
    }
    private fun updateLabel() {
        val time: Array<Long> = getTime();

        targetLabel.text = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
    }

    fun startTimer() {
        beginTime = timeSource.markNow();

        if (isCancelled) {
            timer = Timer();

            val task = object: TimerTask() {
                override fun run() {
                    updateTime()
                }
            }

            timer.schedule(task, Date(), 1000);
            isCancelled = false;
        }
    }

    fun pauseTimer() {

        if (!isCancelled) {
            timer.cancel();
            isCancelled = true;
        }
    }

    fun resetTimer() {

        if (!isCancelled) {
            timer.cancel();
            isCancelled = true;
        }

        model.seconds = 0;
        updateLabel();
    }

    fun getSeconds(): Long {
        return model.seconds;
    }

    fun getTime(): Array<Long> {
        val hours = model.seconds / 3600;
        val minutes = (model.seconds % 3600) / 60;
        val seconds = model.seconds % 60;

        return arrayOf(hours, minutes, seconds);
    }
}

data class TimerModel (
    var seconds: Long = 0
)