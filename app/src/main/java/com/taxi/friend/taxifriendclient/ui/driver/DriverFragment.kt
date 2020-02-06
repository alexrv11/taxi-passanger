package com.taxi.friend.taxifriendclient.ui.driver

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.taxi.friend.taxifriendclient.DriverActivity
import com.taxi.friend.taxifriendclient.PassangerInfo
import com.taxi.friend.taxifriendclient.R
import com.taxi.friend.taxifriendclient.WaitOrderActivity
import com.taxi.friend.taxifriendclient.models.DriverData
import kotlinx.android.synthetic.main.driver_fragment.view.*
import java.lang.Exception

class DriverFragment : Fragment() {
    val driverId: String = "070f886c-ae24-4a2d-a252-9f9c3df7c399"
    private var mViewModel: DriverViewModel = DriverViewModel(driverId)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.driver_fragment, container, false)
        mViewModel.loadDriver(driverId)

        view.driverRecycler.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

        //val model = ViewModelProviders.of(this)[MyViewModel::class.java]
        mViewModel.getDriver().observe(this, Observer<DriverData>{ driverModel ->
            view.driverRecycler.adapter = RecyclerDriverAdapter(driverModel.pictures)
            view.name.text = driverModel.name
            view.identityCar.text = driverModel.identityCar
        })



        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.driverRecycler)

        val orderButton = view.findViewById<Button>(R.id.btnRegister)

        orderButton.setOnClickListener {
            Log.i("TaxiOrder", "ordering taxi")
            PassangerInfo.driverId = driverId
            val intent = Intent(view.context, WaitOrderActivity::class.java)
            startActivity(intent)
            activity!!.finish()

        }

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {

        fun newInstance(): DriverFragment {
            return DriverFragment()
        }
    }
}
