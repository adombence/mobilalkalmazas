package hu.adombence.bongeszo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        web_browser.loadUrl("https://www.google.com")
        web_browser.settings.javaScriptEnabled = true
        web_browser.canGoBack()
        web_browser.webViewClient = WebClient(this)

        search_button.setOnClickListener {
            val URL = url_txt.text.toString()
            web_browser.loadUrl(URL)
        }

        back.setOnClickListener {
            web_browser.goBack()
        }

    }

    class WebClient internal constructor(private val activity: Activity) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
    }
}