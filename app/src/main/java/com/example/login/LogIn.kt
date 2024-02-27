package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.TaskStackBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LogIn : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = Firebase.auth

        var btnLogin = findViewById<Button>(R.id.login)
        var email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        var pwd = findViewById<EditText>(R.id.editTextTextPassword)

        btnLogin.setOnClickListener {
            if (!email.text.toString().isEmpty() && !pwd.text.toString().isEmpty()) {
                auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                    .addOnCompleteListener { task ->

                        if (!task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "ERROR " + task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(this, "Ya estás autenticado", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainMenu::class.java))
                        }
                    }
            }
            else
                Toast.makeText(this, "Escriba sus credenciales", Toast.LENGTH_LONG).show()

        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        checkUser(currentUser)
    }

    private fun checkUser(user: FirebaseUser?) {
        if (user != null) {
            return Toast.makeText(this, "Favor de autenticarse", Toast.LENGTH_SHORT).show()
        }
        return Toast.makeText(this, "No hay usuarios autenticados", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.signOut()
    }
}