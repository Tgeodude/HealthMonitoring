package com.example.healthmonitoring

import kotlinx.coroutines.flow.Flow

/**
 * @author Щеглов Виктор on 28.05.2024
 */
data class  Profile(
    val gender: String,
    val age: Int,
    val height: Int,
    val weight: Int,
)

interface ProfileRepository {
    fun getProfile(): Flow<Profile>
    suspend fun updateProfile(profile: Profile)
}

class ProfileRepositoryImpl(private val database: ProfileDatabase) : ProfileRepository {
    override fun getProfile(): Flow<Profile> {
        // Реализация получения данных из базы данных
    }
    override suspend fun updateProfile(profile: Profile) {
        // Реализация обновления данных в базе данных
    }
}