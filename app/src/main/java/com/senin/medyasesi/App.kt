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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "Güncelleme tamamlandı!\n\nArtık butona bastığınızda bildirim paneli otomatik kapanacak ve medya çubuğu açılacak."
        textView.textSize = 18f
        textView.setPadding(50, 50, 50, 50)
        setContentView(textView)
    }
}

// YENİ: Paneli kapattıktan sonra sesi çıkaracak olan "Hayalet Ekran"
class TriggerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // Sesi gösterme komutu
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
        // İşini bitirdiği an (saniyenin onda birinde) kendini imha eder
        finish() 
    }
}

class VolumeTileService : TileService() {
    override fun onClick() {
        super.onClick()
        val intent = Intent(this, TriggerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        
        // Paneli kapatıp hayalet ekranı çağırıyoruz
        if (Build.VERSION.SDK_INT >= 34) {
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            startActivityAndCollapse(pendingIntent)
        } else {
            @Suppress("DEPRECATION")
            startActivityAndCollapse(intent)
        }
    }
}
