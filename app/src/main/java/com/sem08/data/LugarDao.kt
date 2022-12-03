package com.sem08.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

import com.sem08.model.Lugar

class LugarDao {

    private var codigoUsuario: String
    private var firestore: FirebaseFirestore

    init{
        codigoUsuario = Firebase.auth.currentUser?.email.toString()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun getLugares(): MutableLiveData<List<Lugar>> {
        val listaLugares = MutableLiveData<List<Lugar>>()
        firestore
            .collection("lugares")
            .document(codigoUsuario)
            .collection("misLugares")
            .addSnapshotListener { value, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                if(value != null){
                    val lista = ArrayList<Lugar>()
                    val lugares = value.documents
                    lugares.forEach{
                        val lugar = it.toObject(Lugar::class.java)
                        if(lugar != null){
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }
            }
        return listaLugares
    }

    fun guardarLugar(lugar: Lugar){
        val document: DocumentReference
        if(lugar.id.isEmpty()){
            //Agregar
            document = firestore
                .collection("lugares")
                .document(codigoUsuario)
                .collection("misLugares")
                .document()
            lugar.id = document.id
        }
        else{
            //Modificar
            document = firestore
                .collection("lugares")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
        }
        document.set(lugar)
        .addOnCompleteListener{
            Log.d("guardarLugar", "Guardado con exito")
        }
        .addOnCompleteListener{
            Log.e("guardarLugar", "Error al guardar un lugar")
        }
    }

    fun eliminarLugar(id: String){
        if(id.isNotEmpty()){
            firestore
                .collection("lugares")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(id)
                .delete()
                .addOnCompleteListener{
                    Log.d("eliminarLugar", "Eliminado con exito")
                }
                .addOnCompleteListener{
                    Log.e("guardarLugar", "Error al eliminar un lugar")
                }
        }
    }
}