package com.example.emotionalmessages

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class RegistrationFragment: Fragment() {

    var selectedPhotoUri: Uri? = null
    lateinit var image: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {

        return return inflater.inflate(R.layout.fragment_registration, container, false)
    }


    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registration_button = view.findViewById<Button>(R.id.registration_button)
        val email = view.findViewById<EditText>(R.id.email)
        val password = view.findViewById<EditText>(R.id.password_rg)
        val userName = view.findViewById<EditText>(R.id.user_name_rg)
        image = view.findViewById<CircleImageView>(R.id.image)
        image.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent,0)
        }
        registration_button.setOnClickListener{
            if (email.text.isEmpty() || password.text.isEmpty() || userName.text.isEmpty()){
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    uploadImage(userName.text.toString())
                }
                .addOnFailureListener{
                }
        }
    }


    private fun uploadImage(user:String){
        if (selectedPhotoUri == null ) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegistrationFragmet", "Photo was uploaded!")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegistrationFragmet", "File location: $it !")
                    saveUserToFirebaseDatabase(user,it.toString())
                }
            }
    }

    private fun saveUserToFirebaseDatabase(user: String,profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref =
        FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User (uid, user,profileImageUrl)
        ref.setValue(user)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && requestCode == Activity.RESULT_OK && data != null ){
            Log.d("RegistrationFragmet", "Photo was selected!")
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,selectedPhotoUri)
            image.setImageBitmap(bitmap)
        }
    }


    class User(val uid: String ,val username: String, val profileImageUri: String)

}