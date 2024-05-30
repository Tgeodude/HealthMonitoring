package com.example.healthmonitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    val profile: Flow<Profile> = profileRepository.getProfile()

    fun updateProfile(newProfile: Profile) {
        viewModelScope.launch {
            profileRepository.updateProfile(newProfile)
        }
    }
}