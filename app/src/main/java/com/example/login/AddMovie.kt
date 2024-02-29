package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database

class AddMovie : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("movies")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        var name = findViewById<EditText>(R.id.movie_name)
        var genre = findViewById<EditText>(R.id.movie_genre)
        var year = findViewById<EditText>(R.id.movie_year)
        var addMovie = findViewById<Button>(R.id.movie_add)

        addMovie.setOnClickListener{
            var movie = MovieParameters(name.text.toString(), genre.text.toString(), year.text.toString())
            myRef.push().setValue(movie).addOnCompleteListener{
                task ->

                if (task.isSuccessful) {
                    Toast.makeText(this, "Movie Added Successfully.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                    Toast.makeText(this, "Error: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}