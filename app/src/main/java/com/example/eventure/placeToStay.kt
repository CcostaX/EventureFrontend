package com.example.eventure
import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class   placeToStay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_place_to_stay)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclervieww)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        data.add(ItemsViewModel(R.drawable.ic_house, "ibis Leiria Fatima" ))
        data.add(ItemsViewModel(R.drawable.ic_house, "TRYP Leiria Hotel" ))
        data.add(ItemsViewModel(R.drawable.ic_house, "Hotel São Luis" ))

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        val button = findViewById<View>(R.id.buttonGo) as Button
        button.setOnClickListener {
            startActivity(Intent(this, routeForYou::class.java))
        }
    }
}