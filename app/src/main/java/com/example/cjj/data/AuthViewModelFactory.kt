package com.example.emptyactivity.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cjj.CJJApp

/**
 * The VideModelProvider Factory for creating AuthViewModels.
 */
class AuthViewModelFactory : ViewModelProvider.Factory {
    /**
     * Initializes a new AuthViewModel instance.
     * @param modelClass The class that extends ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(CJJApp.appModule.authRepository) as T
    }
}