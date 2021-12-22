package com.timife.a911.ui.emergencySave

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.data.UserPreferences
import com.timife.a911.databinding.FragmentSaveBinding

class SaveFragment : Fragment() {
    private lateinit var binding: FragmentSaveBinding
    private lateinit var navController: NavController
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveBinding.inflate(inflater)
        userPreferences = UserPreferences(requireContext())
        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner){
            if (it == null){
                binding.profileFalseLinear.visibility = View.VISIBLE
                binding.profileTrueLinear.visibility = View.GONE
            }else{
                binding.profileFalseLinear.visibility = View.GONE
                binding.profileTrueLinear.visibility = View.VISIBLE
            }
            }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu,menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_saveFragment_to_createProfileFragment)

        }
    }

}