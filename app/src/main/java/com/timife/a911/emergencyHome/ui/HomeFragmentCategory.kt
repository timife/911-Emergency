package com.timife.a911.emergencyHome.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.databinding.FragmentHomeCategoryBinding
import com.timife.a911.getClickableSpan
import java.util.*
import javax.inject.Inject


const val EMERGENCY_SERVICES: String = "EMERGENCY_SERVICES"
const val NON_EMERGENCY_SERVICES: String = "NON_EMERGENCY_SERVICES"

class HomeFragmentCategory : Fragment() {

    private lateinit var emergencyType: String
    private lateinit var binding: FragmentHomeCategoryBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

//    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

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
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)
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
                val emergencyHelpSpan = SpannableStringBuilder()
                emergencyHelpSpan.append(immediateHelpSpan())
                binding.emergencyTitle.text = emergencyHelpSpan
                binding.emergencyTitle.movementMethod = LinkMovementMethod.getInstance()

                if (country != null) {
                    binding.progressBar.visibility = View.VISIBLE
                    setUpEmergencyNumbers(country)
                    viewModel.navigateToSaveOption.observe(viewLifecycleOwner, {
                        this.findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToCallOptionDialog(it)
                        )
                    })
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
            NON_EMERGENCY_SERVICES -> {
                val nonEmergencyHelpSpan = SpannableStringBuilder()
                nonEmergencyHelpSpan.append(nonImmediateHelpSpan())
                binding.emergencyTitle.text = nonEmergencyHelpSpan
                binding.emergencyTitle.movementMethod = LinkMovementMethod.getInstance()

                if (state != null) {
                    binding.progressBar.visibility = View.VISIBLE
                    setUpNonEmergencyNumbers("Lagos")
                    viewModel.navigateToNonSaveOption.observe(viewLifecycleOwner, {
                        this.findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToCallOptionDialog(it)
                        )
                    })
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun immediateHelpSpan() = getString(
        R.string.non_clickable_emergency,
        getString(R.string.clickable_immediate_help)
    ).getClickableSpan(
        getString(R.string.clickable_immediate_help),
        ContextCompat.getColor(requireContext(), R.color.on_surface),
        isHavingUnderline = true,
        shouldBeBold = true
    ) {
        this.findNavController().navigate(R.id.action_homeFragment_to_immediateDialog)
    }

    private fun nonImmediateHelpSpan() = getString(
        R.string.non_clickable_emergency,
        getString(R.string.clickable_non_immediate)
    ).getClickableSpan(
        getString(R.string.clickable_non_immediate),
        ContextCompat.getColor(requireContext(), R.color.on_surface),
        isHavingUnderline = true,
        shouldBeBold = true
    ) {
        this.findNavController().navigate(R.id.action_homeFragment_to_nonImmediateDialog)

    }


    private fun setUpEmergencyNumbers(country: String?) {
        viewModel.emergency.observe(viewLifecycleOwner, {
            val emergencyNumbersList = it.filter {
                it.country.equals(country, ignoreCase = true)
            }
            if (emergencyNumbersList.isNotEmpty()) {
                val number = emergencyNumbersList[0]
                if (true) {
                    val emergencyNumbersAdapter =
                        ESvRecyclerViewAdapter(
                            requireContext(),
                            listOf(
                                EmergencyInfo(
                                    UUID.randomUUID().toString(),
                                    "Ambulance",
                                    number.ambulance!!,
                                    EMERGENCY_SERVICES,
                                    country!!
                                ),
                                EmergencyInfo(
                                    UUID.randomUUID().toString(),
                                    "Fire",
                                    number.fire!!,
                                    EMERGENCY_SERVICES,
                                    country
                                ),
                                EmergencyInfo(
                                    UUID.randomUUID().toString(),
                                    "Police",
                                    number.police!!,
                                    EMERGENCY_SERVICES,
                                    country
                                )
                            ),
                            ESvRecyclerViewAdapter.OnClickListener {
                                viewModel.passEmergencyDetails(it)
                            }
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
        })
    }

    private fun setUpNonEmergencyNumbers(state: String?) {
        viewModel.nonEmergency.observe(viewLifecycleOwner, {
            val nonEmergencyNumbersList = it.filter {
                it.place.equals(state, ignoreCase = true)
            }
            if (nonEmergencyNumbersList.isNotEmpty()) {
                val nonEmergencyNo = nonEmergencyNumbersList[0]
                val numbersList = arrayListOf<EmergencyInfo>()
                for ((key, value) in nonEmergencyNo.numbers.entries) {
                    numbersList.add(
                        EmergencyInfo(
                            UUID.randomUUID().toString(),
                            key.toString(), value.toString(), NON_EMERGENCY_SERVICES,
                            state!!
                        )
                    )
                }
                val nonEsvAdapter = ESvRecyclerViewAdapter(
                    requireContext(),
                    numbersList,
                    ESvRecyclerViewAdapter.OnClickListener {
                        viewModel.passNonEmergencyDetails(it)
                    })
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
        })
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