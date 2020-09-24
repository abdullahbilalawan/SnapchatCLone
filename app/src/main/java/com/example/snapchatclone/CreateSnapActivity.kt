package com.example.snapchatclone

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.util.*


class CreateSnapActivity : AppCompatActivity() {

    var createsnapImageView: ImageView? = null
    var message: EditText? = null
    val image_name = UUID.randomUUID().toString() +".jpg"






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        createsnapImageView = findViewById(R.id.snap)
    }

    fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    fun chooseImageClicked(view: View){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createsnapImageView?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }


    fun nextClicked(view: View){

        // Get the data from an ImageView as bytes

        // Get the data from an ImageView as bytes
        createsnapImageView?.setDrawingCacheEnabled(true)
        createsnapImageView?.buildDrawingCache()
        val bitmap = (  createsnapImageView?.getDrawable() as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()





        val uploadTask: UploadTask =  FirebaseStorage.getInstance().getReference().child("images").child(image_name).putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads

            Toast.makeText(applicationContext,"upload failed",Toast.LENGTH_LONG).show()


        }.addOnSuccessListener {OnSuccessListener<UploadTask.TaskSnapshot>{taskSnapshot ->

            val download = taskSnapshot.getMetadata()?.getReference()?.getDownloadUrl().toString()

            Log.i("Download url ", download )


        }
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...





        }


    }













}