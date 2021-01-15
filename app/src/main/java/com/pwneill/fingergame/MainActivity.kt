package com.pwneill.fingergame

import android.os.Bundle
import android.os.CountDownTimer
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
    private lateinit var timer: CountDownTimer
    private val timeIncrement: Long = 1000
    private var timeMm: Long = 30000
    private var clicks: Long = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState != null) {
            timeMm = savedInstanceState.getLong("timeRemaining")
            clicks = savedInstanceState.getLong("clicks")
        } else {
            timeMm = 30000
            clicks = 300
        }

        timerTextView = findViewById(R.id.textView)
        thousandTextView = findViewById(R.id.textView2)
        tapButton = findViewById(R.id.button)

        thousandTextView.text = clicks.toString()

        timer()

        tapButton.setOnClickListener { //Your code here

            clicks -= 1
            thousandTextView.text = clicks.toString()

            if (timeMm > 0 && clicks <= 0) {

                Toast.makeText(applicationContext, "Game Over!", Toast.LENGTH_SHORT).show()

                gameReset(true)
            }
        }
    }

    private fun timer () {
    timer = object: CountDownTimer(timeMm, timeIncrement) {
            override fun onTick(millisUntilFinished: Long) {
                val timerText: String = "seconds remaining: " + millisUntilFinished / 1000
                timerTextView.text = timerText
            }

            override fun onFinish() {
                timerTextView.text = "done!"
                gameReset(false)
            }
        }.start()

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

            val time = 30000
            timerTextView.text = time.toString()

            timer()
        }
        builder.setCancelable(false)
        builder.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("timeRemaining", timeMm)
        outState.putLong("clicks", clicks)
        timer.cancel()
    }
}