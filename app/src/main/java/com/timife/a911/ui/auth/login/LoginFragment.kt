package com.timife.a911.ui.auth.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.timife.a911.AuthNavigationDirections
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentLoginBinding
import com.timife.a911.extensions.disableClick
import com.timife.a911.extensions.enableClick
import com.timife.a911.extensions.navigateSafe
import com.timife.a911.ui.BaseFragment
import com.timife.a911.utils.AuthUtils
import com.timife.a911.utils.Constants
import com.timife.a911.utils.EventObserver
import com.timife.a911.utils.Messages
import com.timife.a911.utils.states.AuthResult
import javax.inject.Inject


class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }
    private  val loginViewModel by viewModels<LoginViewModel>{viewModelFactoryProvider}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.showSnackBar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.loginCoordinator, it, Snackbar.LENGTH_LONG).show()
        })
        binding.createAccount.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToSignUpFragment2()
        }

        binding.signIn.setOnClickListener {
//            uiCommunicator.hideSoftKeyBoard()
            val email = binding.signInEmail.text.toString().trim()
            val password = binding.signInPassword.text.toString().trim()
         validateAndLogin(email, password)
        }


        binding.forgotPassword.setOnClickListener {
            navigateSafe(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }
    }

    private fun validateAndLogin(
        email: String,
        password: String
    ) {
        if (email.isEmpty()) {
            binding.signInEmail.error = Messages.BLANK_FIELDS_IN_FORM
            binding.signInEmail.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.signInEmail.error = "Please provide valid email!"
            binding.signInEmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.signInPassword.error = Messages.BLANK_FIELDS_IN_FORM
            binding.signInPassword.requestFocus()
        } else if (password.length < 6) {
            binding.signInPassword.error = Messages.SHORT_PASSWORD
            binding.signInPassword.requestFocus()
        }else{
            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.loginUser(email, password).observe(viewLifecycleOwner,  { result ->
            when (result) {
                is AuthResult.Loading -> {
                    binding.signIn.disableClick()
                    binding.progressSignIn.visibility = View.VISIBLE
                }

                is AuthResult.Failed -> {
                    loginViewModel.snackBarMessage(result.data)
                    binding.signIn.enableClick()
                    binding.progressSignIn.visibility =View.GONE
                }

                is AuthResult.Success -> {
                    binding.progressSignIn.visibility =View.GONE
                    setIsNotFirstTimeUser()
                    navigateSafe(AuthNavigationDirections.actionGlobalHomeFragment())
                }

                is AuthResult.UnAuthenticated -> {
                    findNavController()
                        .navigate(
                            LoginFragmentDirections
                                .actionLoginFragmentToVerifyPasswordFragment(
                                    email, password
                                )
                        )
                }
            }
        })
    }

    private fun setIsNotFirstTimeUser() {
        if (sharedPreferences.getBoolean(Constants.IS_FIRST_TIME_USER_KEY, true)) {
            sharedPreferencesEditor.putBoolean(Constants.IS_FIRST_TIME_USER_KEY, false)
            sharedPreferencesEditor.apply()
        }
    }
}