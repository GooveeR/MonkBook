package com.gover.monkbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.gover.monkbook.R
import com.gover.monkbook.adapter.WorkOutRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class UserActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //basic splash screen
        Handler().postDelayed({
            setContentView(R.layout.activity_main)
        },3000)
        setContentView(R.layout.activity_splash_avtivity)
        supportActionBar?.hide()


        auth = FirebaseAuth.getInstance()

        val guncelKullanici = auth.currentUser
        if (guncelKullanici != null){
            val intent = Intent(this, WorkOutActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun girisYap(view: View){
        auth.signInWithEmailAndPassword(emailText.text.toString(),passwordText.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful){
                val guncelKullanici = auth.currentUser?.email.toString()
                Toast.makeText(applicationContext,"Hoş Geldiniz ${guncelKullanici}",Toast.LENGTH_LONG).show()
                val intent = Intent(this, WorkOutRecyclerAdapter::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
    fun kayitOl(view: View){
        val email = emailText.text.toString()
        val sifre = passwordText.text.toString()

        auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                val intent = Intent(this, WorkOutActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}