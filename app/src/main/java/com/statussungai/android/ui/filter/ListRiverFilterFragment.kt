package com.statussungai.android.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.statussungai.android.R
import com.statussungai.android.databinding.FragmentListRiverFilterBinding
import com.statussungai.android.ui.add.AddViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ListRiverFilterFragment : Fragment() {
    private var _binding: FragmentListRiverFilterBinding? = null
    private val binding get() = _binding!!

    private val addViewModel: AddViewModel by viewModel<AddViewModel>()

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
                    Timber.d("onViewCreated: ${position + 1}")
                    addViewModel.setRiverId(position + 1)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}