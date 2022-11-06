package com.example.eventure

import GoogleMapDTO
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.tiagoaguiar.tutorial.gmaps.BitmapHelper
import co.tiagoaguiar.tutorial.gmaps.MarkerInfoAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class MapsActivity : AppCompatActivity() {

    private lateinit var googleMap: GoogleMap

    private val places = arrayListOf(
        Place("Castelo de Leiria", LatLng(39.74732947310308, -8.809364574261117), "Largo de São Pedro, 2400-235 Leiria", 4.4f),
        Place("Feira Medieval Leiria", LatLng(39.74471994001709, -8.80716552991252), "Largo 5 de Outubro, 2400-137 Leiria", 4.0f),
        Place("Museu de Leiria", LatLng(39.74171374340606, -8.802812350703187), "R. Ten. Valadim, 2400-126 Leiria", 4.5f),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(this))
            addMarkers(googleMap)
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()

                places.forEach {
                    bounds.include(it.latlong)
                }


                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
                val location1 = places[0].latlong;
                val location2 = places[1].latlong;
                val location3 = places[2].latlong;

                Log.d("GoogleMap", "before URL")
                var URL = getDirectionURL(location1,location2)
                Log.d("GoogleMap", "URL : $URL")
                GetDirection(URL, "green").execute()

                URL = getDirectionURL(location2,location3)
                GetDirection(URL, "red").execute()
            }
        })
    }

    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .snippet(place.address)
                    .position(place.latlong)
                    .icon(
                        BitmapHelper.vectorToBitmap(this, R.drawable.ic_location, ContextCompat.getColor(this, R.color.teal_700))
                    )
            )

            if (marker != null) {
                marker.tag = place
            }
        }
    }

    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving&key=AIzaSyBx8njEoNsN8vJ6d_s2Ab8cHoEKHM7p0Q0"
    }

    private inner class GetDirection(val url : String, val color: String) : AsyncTask<Void,Void,List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)
                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                if (color == "blue") {
                    lineoption.color(Color.BLUE)
                }
                else if (color == "red") {
                    lineoption.color(Color.RED)
                }
                else if (color == "green") {
                    lineoption.color(Color.GREEN)
                }
                else if (color == "yellow") {
                    lineoption.color(Color.YELLOW)
                }
                lineoption.geodesic(true)
            }
            googleMap.addPolyline(lineoption)
        }
    }

    public fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

    data class Place(
        val name: String,
        val latlong: LatLng,
        val address: String,
        val rating: Float
    )
}