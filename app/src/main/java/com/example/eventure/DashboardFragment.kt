package com.example.eventure
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class DashboardFragment  : Fragment() , View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val btn: Button = rootView.findViewById<View>(R.id.btnCreate) as Button
        btn.setOnClickListener(this)
        return rootView
    }

    override fun onClick(v: View) {
        when (v.id) {
            //R.id.btnCreate -> Toast.makeText(activity, "Button clicked", Toast.LENGTH_LONG).show()

            R.id.btnCreate -> startActivity(Intent(activity, PlanForToday::class.java))
            //startActivity(Intent(this@MainActivity, MyOtherActivity::class.java));

        }
    }
}
