package com.example.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ChooseUserActivity : AppCompatActivity() {

    var listview: ListView? = null
    var emails: ArrayList<String> = ArrayList()
    var keys: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)


        listview = findViewById(R.id.listview)

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,emails)

        listview?.adapter= adapter



        FirebaseDatabase.getInstance().getReference().child("Users").addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
               
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                val email = snapshot.child("email").value as String
                emails.add(email)
                snapshot.key?.let { keys.add(it) }
                adapter.notifyDataSetChanged()

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })


        listview?.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
        val snapmap: Map<String,String> = mapOf("from" to FirebaseAuth.getInstance().currentUser!!.email!!,"imagename" to intent.getStringExtra("imageName"),"imageURL" to intent.getStringExtra("imageURL"),"message" to intent.getStringExtra("message"))

        FirebaseDatabase.getInstance().getReference().child("Users").child(keys.get(i)).child("snaps").push().setValue(snapmap)

        val intent = Intent(this, SnapsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


        startActivity(intent)
        Toast.makeText(applicationContext,"snap sent",Toast.LENGTH_LONG).show()



    }



    }
}