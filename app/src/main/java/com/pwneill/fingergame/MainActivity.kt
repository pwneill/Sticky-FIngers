package com.pwneill.fingergame

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var thousandTextView: TextView
    private lateinit var tapButton: Button

    private val timeMm: Long = 30000
    private val timeIncrement: Long = 1000
    private var thousand: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.textView)
        thousandTextView = findViewById(R.id.textView2)
        tapButton = findViewById(R.id.button)

        thousandTextView.text = thousand.toString()

        timer()

        tapButton.setOnClickListener { //Your code here

            thousand -= 1
            thousandTextView.text = thousand.toString()

            if (timeMm > 0 && thousand <= 0) {

                Toast.makeText(applicationContext, "Game Over Man!", Toast.LENGTH_SHORT).show()\\

                gameReset()

            }

        }

    }

    private fun timer () {
    object : CountDownTimer(timeMm, timeIncrement) {
        override fun onTick(millisUntilFinished: Long) {
            timerTextView.text = "seconds remaining: " + millisUntilFinished / 1000
        }

        override fun onFinish() {
            timerTextView.text = "done!"
            Toast.makeText(applicationContext, "That's Time!", Toast.LENGTH_LONG).show()
        }
    }.start()

}
    private fun gameReset() {

        val builder = AlertDialog.Builder(applicationContext)
        builder.setTitle(R.string.alertTitle)
        builder.setMessage("Play Again?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Let's go") { dialog, which ->
            gameReset()
        }
        builder.show()

        var thousand: Long = 1000
        thousandTextView.text = thousand.toString()

        var time = 30000
        timerTextView.text = time.toString()

        timer()
    }
}