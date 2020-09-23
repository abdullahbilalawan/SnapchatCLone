package com.example.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class SnapsActivity : AppCompatActivity() {


    val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.snap_menu,menu)



        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item?.itemId ==R.id.screatesnap){


            val csintent = Intent(this,CreateSnapActivity::class.java)
            startActivity(csintent)

        }
        else if(item?.itemId == R.id.signout){
            firebaseAuth.signOut()

            finish()


        }



        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        firebaseAuth.signOut()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)
    }
}