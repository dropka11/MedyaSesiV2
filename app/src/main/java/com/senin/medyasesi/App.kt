package com.senin.medyasesi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.media.AudioManager
import android.service.quicksettings.TileService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "Kurulum tamamlandı!\n\nArtık bildirim panelini aşağı kaydırıp, düzenleme (kalem) ikonuna basarak 'Medya Sesi' butonunu üst menüye ekleyebilirsiniz.\n\nBu ekranı kapatabilirsiniz."
        textView.textSize = 18f
        textView.setPadding(50, 50, 50, 50)
        setContentView(textView)
    }
}

class VolumeTileService : TileService() {
    override fun onClick() {
        super.onClick()
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
    }
}
