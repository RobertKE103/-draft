package com.example.networkinteractions.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import javax.inject.Singleton


class AirplaneModeChangeReceiver: BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        val isAirplaneModeEnabled = p1?.getBooleanExtra(
            STATE_AIRPLANE_MODE_ENABLED,
            false
        ) ?: return

        if (isAirplaneModeEnabled){
            Toast.makeText(p0, "Airplane Mode Enabled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(p0, "Airplane Mode Disabled", Toast.LENGTH_LONG).show()
        }

    }


    companion object {

        const val STATE_AIRPLANE_MODE_ENABLED = "state"


    }


}