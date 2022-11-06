package com.example.eventure

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.eventure.databinding.ActivityRouteForYouBinding

class routeForYou : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_route_for_you)

        val button = findViewById<View>(R.id.buttonRoute1) as Button
        button.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        val button1 = findViewById<View>(R.id.buttonRoute2) as Button
        button1.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        val button2 = findViewById<View>(R.id.buttonRoute3) as Button
        button2.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}