package com.pwneill.fingergame

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var thousandTextView: TextView
    private lateinit var tapButton: Button
    private lateinit var countdownTimer: CountDownTimer
    private val timeIncrement: Long = 1000
    private var timeMm: Long = 30000
    private var clicks: Long = 300

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("savedInstance", "narroooooooo")
        super.onSaveInstanceState(outState)
        outState.putLong("timeRemaining", timeMm)
        outState.putLong("clicks", clicks)
        countdownTimer.cancel();
        val truthy = countdownTimer == null
        Log.i("timer", truthy.toString())
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("timer", "On Create Methods is Called");
        Log.i("timer", "onCreateYonder")
        timerTextView = findViewById(R.id.textView)
        thousandTextView = findViewById(R.id.textView2)
        tapButton = findViewById(R.id.button)

        if (savedInstanceState != null) {
            timeMm =  savedInstanceState.getLong("timeRemaining")
            clicks = savedInstanceState.getLong("clicks")

            Log.i("timer", "yes!")

            countdownTimer.start()

        } else {
            thousandTextView.text = clicks.toString()
            timer(timeMm, timeIncrement)
        }

            tapButton.setOnClickListener { //Your code here

                clicks -= 1
                thousandTextView.text = clicks.toString()

                if (timeMm > 0 && clicks <= 0) {

                    Toast.makeText(applicationContext, "Game Over!", Toast.LENGTH_SHORT).show()

                    gameReset(true)
                }
            }

        }

    private fun timer(time: Long, increment: Long) {

        Log.i("timer", "yonder")

        countdownTimer = object : CountDownTimer(time, increment) {
            override fun onTick(millisUntilFinished: Long) {
                val timerText: String = "seconds remaining: " + millisUntilFinished / 1000
                timerTextView.text = timerText
            }

            override fun onFinish() {
                timerTextView.text = "done!"
                gameReset(false)
            }
        }
        countdownTimer.start()

    }

    private fun gameReset(winner: Boolean) {

        val builder = AlertDialog.Builder(this@MainActivity)

        if (winner) {
            builder.setTitle(R.string.alertTitleVictory)
        } else {
            builder.setTitle((R.string.alertTitleLoss))
        }

        builder.setMessage("Play Again?")

        builder.setPositiveButton("Let's go") { _, _ ->
            val clicks: Long = 300
            thousandTextView.text = clicks.toString()

            val time: Long = 30000
            timerTextView.text = time.toString()

            timer(time, timeIncrement)
        }
        builder.setCancelable(false)
        builder.show()
    }

    override fun onResume() {
        super.onResume()

        val newTimer = timerTextView.text.split(": ")
        if (newTimer.size >= 2) {
            timeMm = newTimer[1].toLong() * 1000
            timer (timeMm, timeIncrement)
        }
    }


}
