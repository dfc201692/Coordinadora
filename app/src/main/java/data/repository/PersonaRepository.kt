package data.repository

import com.google.firebase.firestore.FirebaseFirestore

class PersonaRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun validarUsuarioYPin(usuario: String, pin: String, callback: (Boolean) -> Unit) {
        firestore.collection("persona")
            .whereEqualTo("usuario", usuario)
            .whereEqualTo("pin", pin)
            .get()
            .addOnSuccessListener { querySnapshot ->
                callback(!querySnapshot.isEmpty)
            }
            .addOnFailureListener { exception ->
                callback(false)
            }
    }
}
