package com.lostfoundapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lostfoundapp.presentation.user.LoginActivity
import com.lostfoundapp.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btAccount.setOnClickListener {
            SharedPrefManager.getInstance(applicationContext).clear() //Logout
            onStart()
        }


    }

    override fun onStart() {
        super.onStart()

        if (!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }else{
            var name = SharedPrefManager.getInstance(applicationContext).user.name
            Toast.makeText(applicationContext, name, Toast.LENGTH_LONG).show()
        }
    }
}
