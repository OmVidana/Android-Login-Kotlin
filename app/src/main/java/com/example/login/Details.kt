package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        when (parameters?.getCharSequence("name").toString()) {
            "Cars" -> image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cars1))
            "Cars 2" -> image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cars2))
            "Cars 3" -> image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cars3))
            else -> {
                image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.login))
            }
        }

        editBtn.setOnClickListener {
            var movie = MovieParameters(name.text.toString(), genre.text.toString(), year.text.toString())
            myRef.child(parameters?.getCharSequence("id").toString()).setValue(movie).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Movie Edited Successfully.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                    Toast.makeText(this, "Error: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
            }
        }

        deleteBtn.setOnClickListener{
            val builder : AlertDialog.Builder = MaterialAlertDialogBuilder(this)
            builder.setMessage("¿Deseas Borrar esta película?").setPositiveButton("Aceptar") {
                dialog, id ->
                myRef.child(parameters?.getCharSequence("id").toString()).removeValue()
                    .addOnCompleteListener{
                        task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Movie Deleted Successfully.", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else
                            Toast.makeText(this, "Error: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
            }.setNegativeButton("Cancelar") {
                dialog, id ->

            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
}