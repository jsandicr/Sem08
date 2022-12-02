package com.sem08

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sem08.databinding.ActivityMainBinding
import java.security.Principal

class MainActivity : AppCompatActivity() {

    //Objeto Firebase
    private lateinit var auth: FirebaseAuth

    //Pantalla XML
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar Auth
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btRegistro.setOnClickListener{ registrar() }
        binding.btInicio.setOnClickListener{ login() }
    }

    private fun registrar(){
        val email = binding.etCorreo.text.toString()
        val contraseña = binding.etClave.text.toString()

        auth.createUserWithEmailAndPassword(email, contraseña)
            .addOnCompleteListener(this){
                task -> if(task.isSuccessful){
                    val user = auth.currentUser
                    cargarPantalla(user)
                }else{
                    Toast.makeText(baseContext, "Falta ingresar datos", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun cargarPantalla(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val email = binding.etCorreo.text.toString()
        val contraseña = binding.etClave.text.toString()
        print("Credenciales"+": "+email+" "+contraseña)

        auth.signInWithEmailAndPassword(email, contraseña)
            .addOnCompleteListener{ result ->
                Log.d("TAG", result.toString())
                if(result.isSuccessful){
                    val user = auth.currentUser
                    cargarPantalla(user)
                }else{
                    Toast.makeText(baseContext, R.string.no_login, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        cargarPantalla(user)
    }
}