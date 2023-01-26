package com.example.networkinteractions.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.networkinteractions.broadcastReceiver.AirplaneModeChangeReceiver
import com.example.networkinteractions.databinding.ActivityMainBinding
import com.example.networkinteractions.worker.MyFirstWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SwitchMenu {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val receiver by lazy { AirplaneModeChangeReceiver() }
    private val connectivityObserver by lazy { NetworkConnectivityObserver(this) }
    private val newsViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registeringReceiver()

        // At the top level of your kotlin file:


        connectivityObserver.observe().onEach {
            Log.d("statusNetworkAAA", it.toString())
        }.launchIn(lifecycleScope)

    }

    private fun registeringReceiver() {
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupWorkManager() {
        val constrains = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val myFirstWorker = PeriodicWorkRequestBuilder<MyFirstWorker>(
            15,
            TimeUnit.MINUTES,
            17,
            TimeUnit.MINUTES
        )
            .setConstraints(constrains)
            .build()


        WorkManager.getInstance(this)
            .enqueue(myFirstWorker)

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(myFirstWorker.id)
            .observe(this) {
                Log.d("SERVICE_TAG", it.state.toString())
            }
    }

    override fun visibleMenu(flag: Boolean) {
        binding.bottomNavigationView.visibility = if (flag) View.VISIBLE else View.GONE
    }


}