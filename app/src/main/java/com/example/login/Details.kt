package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class Details : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("movies")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var name = findViewById<EditText>(R.id.name_text)
        var genre = findViewById<EditText>(R.id.genre_text)
        var year = findViewById<EditText>(R.id.year_text)
        var image = findViewById<ImageView>(R.id.image)
        var editBtn = findViewById<Button>(R.id.movie_edit)
        var deleteBtn = findViewById<Button>(R.id.movie_delete)

        val parameters = intent.extras

        name.setText(parameters?.getCharSequence("name").toString())
        genre.setText(parameters?.getCharSequence("genre").toString())
        year.setText(parameters?.getCharSequence("year").toString())

        when (parameters?.getCharSequence("id").toString().toInt()) {
            1 -> image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cars1))
            2 -> image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cars2))
            3 -> image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cars3))
            else -> {

            }
        }

        editBtn.setOnClickListener {
            var movie = MovieParameters(name.text.toString(), genre.text.toString(), year.text.toString())
            myRef.child(parameters?.getCharSequence("id").toString()).setValue(movie).addOnCompleteListener {
                task ->
                if (task.isSuccessful)
                    Toast.makeText(this, "Movie Edited Successfully.", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "Error: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}