package com.timife.a911.ui.splash

import androidx.lifecycle.ViewModel
import com.timife.a911.createProfile.ui.AuthRepository
import com.timife.a911.data.repository.UserRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
userRepository:UserRepository
): ViewModel() {
    val currentUserStatus = userRepository.checkLoginStatus()
}