package com.isc.petshopapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.isc.petshopapp.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
  private lateinit var auth: FirebaseAuth
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    FirebaseApp.initializeApp(this)
    auth = FirebaseAuth.getInstance()

    binding.loginBtn.setOnClickListener {
      login()
    }

    binding.signUpBtn.setOnClickListener {
      signUp()
    }
  }

  private fun signUp() {
    val email = binding.emailInput.text.toString()
    val pass = binding.passInput.text.toString()

    auth.createUserWithEmailAndPassword(email, pass)
      .addOnCompleteListener(this) { task ->
        if(task.isSuccessful) {
          Toast.makeText(
            baseContext,
            "Registrando correctamtente!",
            Toast.LENGTH_LONG
          )
          val user = auth.currentUser
          updateUI(user)
        } else {
          Toast.makeText(
            baseContext,
            "Fallo creando el usuario. Intente de nuevo.",
            Toast.LENGTH_LONG
          )
          updateUI(null)
        }
      }
  }

  private fun updateUI(user: FirebaseUser?) {
    if (user != null) {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }
  }

  public override fun onStart() {
    super.onStart()
    updateUI(auth.currentUser)
  }

  private fun login() {
    val email = binding.emailInput.text.toString()
    val pass = binding.passInput.text.toString()

    auth.signInWithEmailAndPassword(email, pass)
      .addOnCompleteListener(this) { task ->
        if(task.isSuccessful) {
          Toast.makeText(
            baseContext,
            "Ingresando...",
            Toast.LENGTH_LONG
          )
          val user = auth.currentUser
          updateUI(user)
        } else {
          Toast.makeText(
            baseContext,
            "Fallo ingresando al sistema. Intente de nuevo.",
            Toast.LENGTH_LONG
          )
          updateUI(null)
        }
      }
  }
}