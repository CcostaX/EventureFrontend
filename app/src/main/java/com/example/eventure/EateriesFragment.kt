package com.example.eventure

import CustomAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EateriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // getting the recyclerview by its id
        val rootView: View = inflater.inflate(R.layout.fragment_eateries, container, false)

        val recyclerview = rootView.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this.context)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        data.add(ItemsViewModel(R.drawable.ic_baseline_restaurant_menu_24, "Café 32" ))
        data.add(ItemsViewModel(R.drawable.ic_baseline_restaurant_menu_24, "Taberna Café Caphe" ))
        data.add(ItemsViewModel(R.drawable.ic_baseline_restaurant_menu_24, "Rei dos frangos" ))

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        return rootView
    }
}