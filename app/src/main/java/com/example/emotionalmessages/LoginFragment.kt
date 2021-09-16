package com.example.emotionalmessages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser





class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login_button = view.findViewById<Button>(R.id.login_button)
        val email = view.findViewById<EditText>(R.id.user_name)
        val password = view.findViewById<EditText>(R.id.password)
        val registr = view.findViewById<TextView>(R.id.registration)
        registr.setOnClickListener(){

        }
        login_button.setOnClickListener{
            if (email.text.isEmpty() || password.text.isEmpty()){
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener
                    else{

                    }
                }
        }

    }

}