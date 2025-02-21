package com.statussungai.android.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.statussungai.android.R
import com.statussungai.android.core.factory.ViewModelFactory
import com.statussungai.android.databinding.FragmentListRiverFilterBinding
import com.statussungai.android.ui.add.AddViewModel

class ListRiverFilterFragment : Fragment() {
    private var _binding: FragmentListRiverFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListRiverFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        val addViewModel: AddViewModel by viewModels {
            factory
        }

        val river = resources.getStringArray(R.array.river_array)
        val riverAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, river)
        binding.apply {
            edtRiver.setAdapter(riverAdapter)
            addViewModel.getRiverId().observe(viewLifecycleOwner) {
                it?.let {
                    edtRiver.setText(riverAdapter.getItem(it - 1), false)
                }
            }
            edtRiver.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ -> //// id contains item if from database
                    Log.d("filter", "onViewCreated: 1 ${position + 1}")
                    addViewModel.setRiverId(position + 1)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}