package data.remoto
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.repository.PersonaRepository

class PersonaViewModelFactory(private val repository: PersonaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonaViewModel::class.java)) {
            return PersonaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
