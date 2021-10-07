package com.timife.a911.emergencyHome.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.model.jsonmodel.NonEmergency
import com.timife.a911.databinding.FragmentNonEsvListBinding
import java.util.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class NonESvFragment : Fragment() {

    companion object {
        const val NON_EMERGENCY_SERVICES: String = "NON_EMERGENCY_SERVICES"
    }

    private lateinit var binding: FragmentNonEsvListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNonEsvListBinding.inflate(inflater)
        val sharedPreferences =
            requireActivity().getSharedPreferences("countryPref", Context.MODE_PRIVATE)

        val state = sharedPreferences.getString("state", "Lagos")
        Toast.makeText(requireContext(), "$state", Toast.LENGTH_SHORT).show()
        setUpNonEmergencyNumbers("Rome")

        return binding.root
    }

    private fun setUpNonEmergencyNumbers(state: String?) {
        viewModel.searchNonEmergencyNumbers(state!!).observe(viewLifecycleOwner) {
            // safe check in case of null result from search ***note its not a useless check***
            if (it is NonEmergency) {
                val numbersList = arrayListOf<EmergencyInfo>()
                for ((key, value) in it.numbers.entries) {
                    numbersList.add(
                        EmergencyInfo(
                            UUID.randomUUID().toString(),
                            key.toString(), value.toString(), NON_EMERGENCY_SERVICES,
                            state
                        )
                    )
                }
                val nonEsvAdapter = NonESvAdapter(numbersList)
                binding.nonEsvRecycler.setHasFixedSize(false)
                binding.nonEsvRecycler.addItemDecoration(
                    GridItemDecoration(
                        resources.getDimensionPixelOffset(R.dimen.h1),
                        2,
                        true
                    )
                )
                binding.nonEsvRecycler.adapter = nonEsvAdapter
            }
        }
    }
}