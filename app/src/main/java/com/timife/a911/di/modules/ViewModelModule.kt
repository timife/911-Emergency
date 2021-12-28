package com.timife.a911.di.modules

import androidx.lifecycle.ViewModel
import com.timife.a911.createProfile.ui.CreateProfileViewModel
import com.timife.a911.ui.auth.forgotpassword.ResetPasswordViewModel
import com.timife.a911.ui.auth.login.LoginViewModel
import com.timife.a911.ui.auth.signup.SignupViewModel
import com.timife.a911.ui.auth.verifydetails.VerifyDetailsViewModel
import com.timife.a911.ui.emergencyHome.ui.dialogs.CallOptionViewModel
import com.timife.a911.ui.emergencyHome.HomeViewModel
import com.timife.a911.ui.emergencyPref.PrefViewModel
import com.timife.a911.ui.emergencySave.SaveViewModel
import com.timife.a911.ui.emergencySearch.SearchViewModel
import com.timife.a911.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel:SplashViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewmodel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrefViewModel::class)
    abstract fun bindPrefViewModel(viewmodel: PrefViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SaveViewModel::class)
    abstract fun bindSaveViewModel(viewmodel: SaveViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewmodel: SearchViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateProfileViewModel::class)
    abstract fun bindProfileViewModel(viewmodel: CreateProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CallOptionViewModel::class)
    abstract fun bindOptionViewModel(viewModel: CallOptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResetPasswordViewModel::class)
    abstract fun bindResetViewModel(viewModel: ResetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VerifyDetailsViewModel::class)
    abstract fun bindVerifyViewModel(viewModel: VerifyDetailsViewModel): ViewModel





}