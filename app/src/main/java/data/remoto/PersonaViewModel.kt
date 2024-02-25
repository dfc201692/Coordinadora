package data.remoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.repository.PersonaRepository

class PersonaViewModel(private val repository: PersonaRepository) : ViewModel() {

    // MutableLiveData para el resultado de la validación
    private val _resultadoValidacion = MutableLiveData<Boolean>()
    val resultadoValidacion: LiveData<Boolean>
        get() = _resultadoValidacion

    fun validarUsuarioYPin(usuario: String, pin: String) {
        repository.validarUsuarioYPin(usuario, pin) { resultado ->
            // Actualiza el MutableLiveData con el resultado
            _resultadoValidacion.postValue(resultado)
        }
    }
}
