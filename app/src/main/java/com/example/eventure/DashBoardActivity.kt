package com.example.eventure
import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DashBoardActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        val btn: Button = findViewById<View>(R.id.btnCreate) as Button
        btn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnCreate) {
            startActivity(Intent(this, placeToStay::class.java))
        }
    }
}