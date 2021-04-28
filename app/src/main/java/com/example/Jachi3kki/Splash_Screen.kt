package com.example.Jachi3kki

import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.refrigerator.*
import kotlinx.android.synthetic.main.refrigerator.cart_image
import kotlinx.android.synthetic.main.splash_view.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view);

        Glide.with(this).load(R.raw.splash_final).into(splash_view)

        startLoading()
    }

    fun startLoading() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3400)
    }
}