package com.timife.a911.emergencyHome.ui

import android.content.Context
import android.content.SharedPreferences
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
import com.timife.a911.data.model.jsonmodel.NonEmergency
import com.timife.a911.databinding.FragmentHomeCategoryBinding
import java.util.*
import javax.inject.Inject


const val EMERGENCY_SERVICES: String = "EMERGENCY_SERVICES"
const val NON_EMERGENCY_SERVICES: String = "NON_EMERGENCY_SERVICES"

class HomeFragmentCategory : Fragment() {

    private lateinit var emergencyType: String
    private lateinit var binding: FragmentHomeCategoryBinding
    private lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emergencyType = it.getString("FRAGMENT")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeCategoryBinding.inflate(inflater)
//        viewModel =
//            ViewModelProvider(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)

        sharedPreferences =
            requireActivity().getSharedPreferences("countryPref", Context.MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategoryData(emergencyType)
    }


    private fun getCategoryData(fragment: String) {
        val country = sharedPreferences.getString("country", "Nigeria")
        val state = sharedPreferences.getString("state", "Lagos")

        when (fragment) {
            EMERGENCY_SERVICES -> {
                setUpEmergencyNumbers(country)
            }
            NON_EMERGENCY_SERVICES -> {
                setUpNonEmergencyNumbers("Lagos")
            }

        }
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
                val nonEsvAdapter = ESvRecyclerViewAdapter(numbersList)
                binding.esvRecycler.setHasFixedSize(false)
                binding.esvRecycler.addItemDecoration(
                    GridItemDecoration(
                        resources.getDimensionPixelOffset(R.dimen.h1),
                        2,
                        true
                    )
                )
                binding.esvRecycler.adapter = nonEsvAdapter
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(fragment: String) =
            HomeFragmentCategory().apply {
                arguments = Bundle().apply {
                    putString("FRAGMENT", fragment)
                }
            }
    }
}