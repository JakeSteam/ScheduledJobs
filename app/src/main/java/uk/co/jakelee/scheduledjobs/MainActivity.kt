package uk.co.jakelee.scheduledjobs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scheduleTask.setOnClickListener { JobScheduler.scheduleJob(this) }
        cancelTask.setOnClickListener { JobScheduler.cancelJobs(this) }
    }
}
