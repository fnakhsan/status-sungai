package com.statussungai.android.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.statussungai.android.R
import com.statussungai.android.core.factory.ViewModelFactory
import com.statussungai.android.databinding.FragmentMapsFilterBinding
import com.statussungai.android.ui.maps.MapsViewModel

class MapsFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMapsFilterBinding? = null
    private val binding get() = _binding!!

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
        Log.d("menu", "onViewCreated: masuk1")
        val factory = ViewModelFactory.getInstance(requireContext())
        val mapsViewModel: MapsViewModel by viewModels {
            factory
        }
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
                    Log.d("filter", "onViewCreated: 1 ${position + 1}")
                    mapsViewModel.setRiverId(position + 1)
                }
            edtSeason.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ -> //// id contains item if from database
                    Log.d("filter", "onViewCreated: 2 ${position + 1}")
                    mapsViewModel.setSeasonId(position + 1)
                }
            edtYear.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d("filter", "onViewCreated: 3 ${yearAdapter.getItem(position)}")
                    mapsViewModel.setYear(yearAdapter.getItem(position).toString().toInt())
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}