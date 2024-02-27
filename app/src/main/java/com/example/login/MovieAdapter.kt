package com.example.login

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class MovieAdapter(private val context: Activity, private val movieArL: ArrayList<Movie>) :
    ArrayAdapter<Movie>(context, R.layout.item, movieArL) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        return super.getView(position, convertView, parent)

        var inflater: LayoutInflater = LayoutInflater.from(context)
        var view : View = inflater.inflate(R.layout.item, null)

        view.findViewById<TextView>(R.id.name).text = movieArL[position].name
        view.findViewById<TextView>(R.id.genre).text = movieArL[position].genre
        view.findViewById<TextView>(R.id.year).text = movieArL[position].year

        when (movieArL[position].id?.toInt()) {
            1 -> view.findViewById<ImageView>(R.id.image).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cars1))
            2 -> view.findViewById<ImageView>(R.id.image).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cars2))
            3 -> view.findViewById<ImageView>(R.id.image).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cars3))
            else -> {

            }
        }

        return view
    }

}