package com.statussungai.android.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.statussungai.android.R
import com.statussungai.android.databinding.FragmentMapsFilterBinding
import com.statussungai.android.ui.maps.MapsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MapsFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMapsFilterBinding? = null
    private val binding get() = _binding!!

    private val mapsViewModel: MapsViewModel by viewModel<MapsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMapsFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val river = resources.getStringArray(R.array.river_array)
        val season = resources.getStringArray(R.array.season_array)
        val year = resources.getStringArray(R.array.year_array)
        val riverAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, river)
        val seasonAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, season)
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, year)
        binding.apply {
            edtRiver.setAdapter(riverAdapter)
            edtSeason.setAdapter(seasonAdapter)
            edtYear.setAdapter(yearAdapter)
            mapsViewModel.getRiverId().observe(viewLifecycleOwner) {
                it?.let {
                    edtRiver.setText(riverAdapter.getItem(it - 1), false)
                }
            }
            mapsViewModel.getSeasonId().observe(viewLifecycleOwner) {
                it?.let {
                    edtSeason.setText(seasonAdapter.getItem(it - 1), false)
                }
            }
            mapsViewModel.getYear().observe(viewLifecycleOwner) {
                it?.let {
                    edtYear.setText(
                        yearAdapter.getItem(yearAdapter.getPosition(it.toString())),
                        false
                    )
                }
            }
            edtRiver.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ -> //// id contains item if from database
                    Timber.d("onViewCreated: ${position + 1}")
                    mapsViewModel.setRiverId(position + 1)
                }
            edtSeason.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ -> //// id contains item if from database
                    Timber.d("onViewCreated: ${position + 1}")
                    mapsViewModel.setSeasonId(position + 1)
                }
            edtYear.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Timber.d("onViewCreated: ${yearAdapter.getItem(position)}")
                    mapsViewModel.setYear(yearAdapter.getItem(position).toString().toInt())
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}