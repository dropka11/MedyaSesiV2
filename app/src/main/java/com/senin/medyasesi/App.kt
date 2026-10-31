package com.senin.medyasesi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.media.AudioManager
import android.service.quicksettings.TileService
import android.content.Intent
import android.app.Activity
import android.app.PendingIntent
import android.os.Build
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "Zamanlama güncellendi!"
        textView.textSize = 18f
        textView.setPadding(50, 50, 50, 50)
        setContentView(textView)
    }
}

class TriggerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0) 
        finish() 
    }
}

class VolumeTileService : TileService() {
    override fun onClick() {
        super.onClick()
        val intent = Intent(this, TriggerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        
        if (Build.VERSION.SDK_INT >= 34) {
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            startActivityAndCollapse(pendingIntent)
        } else {
            @Suppress("DEPRECATION")
            startActivityAndCollapse(intent)
        }

        // SÜRE UZATILDI: Panel kapandıktan sonra 600 milisaniye (0.6 saniye) bekleyip barı açar.
        Handler(Looper.getMainLooper()).postDelayed({
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
        }, 600)
    }
}
