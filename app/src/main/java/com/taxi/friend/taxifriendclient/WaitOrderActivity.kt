package com.taxi.friend.taxifriendclient

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.taxi.friend.taxifriendclient.models.Location
import com.taxi.friend.taxifriendclient.models.OrderStatus
import com.taxi.friend.taxifriendclient.services.OrderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.Callable


class WaitOrderActivity : AppCompatActivity() {
    var orderStatus: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wait_order_activity)
        val actionBar = supportActionBar
        actionBar!!.title = "Solicitando..."
        val textView: TextView = findViewById(R.id.textinputCounter)
        createOrder(PassangerInfo.driverId, PassangerInfo.location)
        val cancelButton = findViewById<Button>(R.id.btnCancel)
        cancelButton.setOnClickListener{
            this.finish()
        }

        counter(textView)


    }


    private fun createOrder(driverId: String, location: Location?) {
        try {
            val service = OrderService()
            val callCreateOrder = service.createOrder(driverId, location!!)
            callCreateOrder.enqueue(object : Callback<OrderStatus>{
                override fun onResponse(call: Call<OrderStatus>, response: Response<OrderStatus>) {
                    if (response.code() != 200) {
                        Log.i("ErrorCreatingOrder ", "status: " + response.code())
                        return
                    }

                    val order = response.body()!!
                    PassangerInfo.orderId = order.id
                }

                override fun onFailure(call: Call<OrderStatus>, t: Throwable) {
                    Log.e("ErrorCreatingOrder", t.message)
                }
            })

        }catch (e: Exception) {
            Log.e("ErrorCreatingOrder", e.message)
        }
    }

    private fun getOrderStatus(orderId: String) {
        try {
            val service = OrderService()
            val callGetOrder = service.getOrderStatus(orderId)
            callGetOrder.enqueue(object : Callback<OrderStatus>{
                override fun onResponse(call: Call<OrderStatus>, response: Response<OrderStatus>) {
                    if (response.code() != 200) {
                        Log.i("ErrorCreatingOrder ", "status: " + response.code())
                        return
                    }

                    val order = response.body()!!
                    orderStatus = order.status
                }

                override fun onFailure(call: Call<OrderStatus>, t: Throwable) {
                    Log.e("ErrorCreatingOrder", t.message)
                }
            })

        }catch (e: Exception) {
            Log.e("ErrorCreatingOrder", e.message)
        }
    }

    private fun counter(counterView: TextView) {
        object : CountDownTimer(20000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if (orderStatus == "Accepted") {
                    counterView.text = "Solicitud Aceptada"
                    closeActivity(RideActivity::class.java)

                }

                getOrderStatus(PassangerInfo.orderId)
                val counterValue: String = "" + millisUntilFinished / 1000
                counterView.text = counterValue
            }

            override fun onFinish() {
                PassangerInfo.orderId = ""
                counterView.text = "No aceptaron tu solucitud, intenta con otro conductor"

                closeActivity(MainActivity::class.java)
            }
        }.start()
    }

    private fun <T> closeActivity(clazz: Class<T>) {
        val context = this
        object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val intent = Intent(context, clazz)
                startActivity(intent)
                context.finish()
            }
        }.start()
    }
}
