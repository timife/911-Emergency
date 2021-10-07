package com.timife.a911.emergencyHome.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.model.jsonmodel.Emergency
import com.timife.a911.databinding.FragmentEsvListBinding
import java.util.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class ESvFragment : Fragment() {

    companion object {
        const val EMERGENCY_SERVICES: String = "EMERGENCY_SERVICES"
    }

    private lateinit var binding: FragmentEsvListBinding

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
        binding = FragmentEsvListBinding.inflate(inflater)
        val sharedPreferences =
            requireActivity().getSharedPreferences("countryPref", Context.MODE_PRIVATE)

        val country = sharedPreferences.getString("country", "Nigeria")
        setUpEmergencyNumbers(country)

        return binding.root

    }

    private fun setUpEmergencyNumbers(country: String?) {

        viewModel.searchEmergencyNumbers(country!!).observe(viewLifecycleOwner) {
            // safe check in case of null result from search ***note its not a useless check***

            if (it is Emergency) {
                val emergencyNumbersAdapter =
                    ESvRecyclerViewAdapter(
                        listOf(
                            EmergencyInfo(
                                UUID.randomUUID().toString(),
                                "Ambulance",
                                it.ambulance!!,
                                EMERGENCY_SERVICES,
                                country
                            ),
                            EmergencyInfo(
                                UUID.randomUUID().toString(),
                                "Fire",
                                it.fire!!,
                                EMERGENCY_SERVICES,
                                country
                            ),
                            EmergencyInfo(
                                UUID.randomUUID().toString(),
                                "Police",
                                it.police!!,
                                EMERGENCY_SERVICES,
                                country
                            )
                        ),
                        //                        saveEmergencyListenerListener
                    )

                binding.esvRecycler.setHasFixedSize(false)
                binding.esvRecycler.addItemDecoration(
                    GridItemDecoration(
                        resources.getDimensionPixelOffset(R.dimen.h1),
                        2,
                        true
                    )
                )
                binding.esvRecycler.adapter = emergencyNumbersAdapter
            }
        }
    }
}