package com.example.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainMenu : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var movies: ArrayList<Movie>
    val database = Firebase.database
    val myRef = database.getReference("movies")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        auth = Firebase.auth
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var listMovies = findViewById<ListView>(R.id.list)
        listMovies.setOnItemClickListener { parent, view, position, id ->
            startActivity(
                Intent(this, Details::class.java).putExtra("id", movies[position].id.toString())
                    .putExtra("name", movies[position].name.toString()).putExtra("genre", movies[position].genre.toString())
                    .putExtra("year", movies[position].year.toString())
            )
        }

        this.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.exit -> {
                        auth.signOut()
                        finish()
                        true
                    }

                    R.id.profile -> {
                        true
                    }

                    else -> false
                }
            }
        })

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                movies = ArrayList<Movie>()
                val value = snapshot.value
                Log.d(TAG, "Value is: " + value)
                snapshot.children.forEach { ch ->
                    var movie: Movie = Movie(
                        ch.child("name").value.toString(),
                        ch.child("genre").value.toString(),
                        ch.child("year").value.toString(),
                        ch.key
                    )
                    movies.add(movie)
                }
                fullList()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        var addButton = findViewById<FloatingActionButton>(R.id.add_button)

        addButton.setOnClickListener{
            startActivity(Intent(this, AddMovie::class.java))
        }
    }

    public fun fullList() {
        val adapter = MovieAdapter(this, movies)
        var list = findViewById<ListView>(R.id.list)
        list.adapter = adapter
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            auth.signOut()
        return super.onKeyDown(keyCode, event)
    }
}