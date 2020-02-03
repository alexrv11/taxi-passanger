package com.taxi.friend.taxifriendclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView


class WaitOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wait_order_activity)
        val actionBar = supportActionBar
        actionBar!!.title = "Solicitando..."
        val textView: TextView = findViewById(R.id.textinputCounter)
        counter(textView)
        val cancelButton = findViewById<Button>(R.id.btnCancel)
        cancelButton.setOnClickListener{
            this.finish()
        }
    }

    private fun counter(counterView: TextView) {
        object : CountDownTimer(20000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val counterValue: String = "" + millisUntilFinished / 1000
                counterView.setText(counterValue)
            }

            override fun onFinish() {
                counterView.setText("done!")
            }
        }.start()
    }
}
