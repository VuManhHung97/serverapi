package com.vmh.kubetool.screen.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProviders
import com.vmh.kubetool.R
import com.vmh.kubetool.utils.BaseActivity
import com.vmh.kubetool.viewmodel.ViewModelProviderFactory
import dmax.dialog.SpotsDialog
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URI
import javax.inject.Inject


class MainActivity : BaseActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var dialog: AlertDialog
    private var player: MediaPlayer? = null

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val listenerNotification = Emitter.Listener {
        runOnUiThread {
            textViewNotification.text = it[0].toString()
        }
    }

    private val listenerNumberTable = Emitter.Listener {
        runOnUiThread {
            textViewNumberTable.text = it[0].toString()
        }
    }

    private val listenerCommand = Emitter.Listener {
        runOnUiThread {
            val data = (it[0]) as JSONObject
            textViewCommand.textSize = 15f
            textViewCommand.text = data.getString("title")
            updateCasinoChip(data.getString("command"))
        }
    }

    private val listenerOffTool = Emitter.Listener {
        runOnUiThread {
            if (it[0].toString() != "On") {
                offTool()
                stopPlayer()
            }else {
                onTool()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initSocket()
        initData()
        registerLiveData()
    }

    override fun onBackPressed() {
        if (webView.canGoBack() ) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        dialog = SpotsDialog.Builder().setContext(this).build()
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = WebViewClient()
    }

    private fun play(music: Int, xeng: Boolean) {
        stopPlayer()
        if (player == null) {
            player =  MediaPlayer.create(this, music)
            player!!.setOnCompletionListener {
                if (!xeng) {
                    player!!.start()
                }
            }
        }
        player!!.start()
    }

    private fun stop() {
        stopPlayer()
    }

    private fun stopPlayer() {
        if (player != null) {
            player!!.release()
            player = null
        }
    }

    private fun initSocket() {
        val uri = URI.create("http://103.82.20.229:5656/")
        val socket = IO.socket(uri)
        socket.connect()
        socket.on("send-notification-app", listenerNotification)
        socket.on("send-number-table-app", listenerNumberTable)
        socket.on("send-command-app", listenerCommand)
        socket.on("send-status-tool-app", listenerOffTool)
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel::class.java)
        viewModel.getStatusTool()
        viewModel.getLink("Game")
        dialog.show()
    }

    private fun registerLiveData() {
        viewModel.errorLiveData.observe(this, {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        })

        viewModel.statusToolLiveData.observe(this, {
            if (it != null && it.status == "On") {
                onTool()
            }
        })

        viewModel.linkLiveData.observe(this, {
            if (it != null && it.message == "Successfuly") {
                webView.loadUrl(it.linkName)
                dialog.dismiss()
            }
        })
    }

    private fun offTool() {
        textViewNotification.text = ""
        textViewNumberTable.text = ""
        textViewCommand.text = ""
        imageViewChip.visibility = View.GONE
        textViewCommand.visibility = View.GONE
    }

    private fun onTool() {
        textViewCommand.textSize = 15f
        textViewCommand.setTextColor(Color.YELLOW)
        textViewCommand.text = "Bạn đã sẵn sàng chưa?"
        imageViewChip.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.circle, theme))
        imageViewChip.visibility = View.VISIBLE
        textViewCommand.visibility = View.VISIBLE
    }

    private fun updateCasinoChip(command: String) {
        when (command) {
            "Chẵn", "Con" -> {
                play(R.raw.xeng, true)
                textViewCommand.setTextColor(Color.RED)
                textViewCommand.textSize = 22f
                imageViewChip.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.casino_chip_orange, theme))
            }

            "Tài", "Rồng" -> {
                play(R.raw.xeng, true)
                textViewCommand.setTextColor(Color.RED)
                textViewCommand.textSize = 22f
                imageViewChip.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.casino_chip_red, theme))
            }

            "Lẻ", "Xỉu" -> {
                play(R.raw.xeng, true)
                textViewCommand.textSize = 24f
                textViewCommand.setTextColor(Color.RED)
                imageViewChip.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.casino_chip_blue, theme))
            }

            "Cái", "Hổ" -> {
                play(R.raw.xeng, true)
                textViewCommand.textSize = 24f
                textViewCommand.setTextColor(Color.RED)
                imageViewChip.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.casino_chip_blue_primary, theme))
            }

            "Phân Tích" -> {
                play(R.raw.phantich, false)
                textViewCommand.textSize = 15f
                textViewCommand.setTextColor(Color.RED)
                imageViewChip.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.casino_chip_green, theme))
            }
        }
    }

}
