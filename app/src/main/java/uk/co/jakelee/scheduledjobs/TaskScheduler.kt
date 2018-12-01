package uk.co.jakelee.scheduledjobs

import android.content.Context
import com.firebase.jobdispatcher.*
import java.util.concurrent.TimeUnit

class TaskScheduler : JobService() {

    override fun onStartJob(job: JobParameters): Boolean {
        when (job.tag) {
            TASK_A_TAG -> simpleTask(job)
            TASK_B_TAG -> customTask(job)
            else -> return false
        }
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return true
    }


    private fun simpleTask(job: JobParameters) {
        // do a thing
    }

    private fun customTask(job: JobParameters) {
        // do a thing
    }

    companion object {
        private const val TASK_A_TAG = "uk.co.jakelee.scheduledjobs.task_a"
        private const val TASK_B_TAG = "uk.co.jakelee.scheduledjobs.task_b"
        private const val MIN_HOURS = 22L
        private const val MAX_HOURS = 26L

        fun scheduleAll(context: Context) {
            val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
            val jobA = dispatcher.newJobBuilder()
                .setService(TaskScheduler::class.java)
                .setTag(TASK_A_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setTrigger(
                    Trigger.executionWindow(
                        TimeUnit.HOURS.toSeconds(MIN_HOURS).toInt(),
                        TimeUnit.HOURS.toSeconds(MAX_HOURS).toInt()
                    )
                )
            dispatcher.mustSchedule(jobA.build())
            val jobB = dispatcher.newJobBuilder()
                .setService(TaskScheduler::class.java)
                .setTag(TASK_B_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setTrigger(
                    Trigger.executionWindow(
                        TimeUnit.HOURS.toSeconds(MIN_HOURS * 2).toInt(),
                        TimeUnit.HOURS.toSeconds(MAX_HOURS * 2).toInt()
                    )
                )
            dispatcher.mustSchedule(jobB.build())
        }

        fun cancelAll(context: Context) {
            FirebaseJobDispatcher(GooglePlayDriver(context)).cancelAll()
        }
    }

}