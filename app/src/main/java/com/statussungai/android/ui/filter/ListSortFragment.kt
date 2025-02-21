package com.statussungai.android.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.statussungai.android.R
import com.statussungai.android.databinding.FragmentListSortBinding

class ListSortFragment : Fragment() {
    private var _binding: FragmentListSortBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val factory = ViewModelFactory.getInstance(requireContext())
//        val addViewModel: AddViewModel by viewModels {
//            factory
//        }
//        addViewModel.apply {
//            binding.apply {
//                getSortAlphabetically().observe(viewLifecycleOwner) {isAscending ->
//                    if (isAscending != null) {
//                        showSortAlphabetically(isAscending)
//                        sortAlphabetically.setOnClickListener {
//                            if (isAscending) saveSortAlphabetically(false) else saveSortAlphabetically(true)
//                        }
//                    } else {
//                        ivAlphabetically.visibility = View.INVISIBLE
//                        sortAlphabetically.setOnClickListener {
//                            clearSortAlphabetically()
//                        }
//                    }
//                }
//                getSortDate().observe(viewLifecycleOwner) { isAscending ->
//                    if (isAscending != null) {
//                        showSortDate(isAscending)
//                        sortDate.setOnClickListener {
//                            if (isAscending) saveSortDate(false) else saveSortDate(true)
//                        }
//                    } else {
//                        ivDate.visibility = View.INVISIBLE
//                        sortDate.setOnClickListener {
//                            clearSortDate()
//                        }
//                    }
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSortAlphabetically(isAscending: Boolean) {
        binding.apply {
            if (isAscending) ivAlphabetically.setImageResource(R.drawable.ic_arrow_upward_24) else ivAlphabetically.setImageResource(
                R.drawable.ic_arrow_downward_24
            )
            ivAlphabetically.visibility = View.VISIBLE
        }
    }

    private fun showSortDate(isAscending: Boolean) {
        binding.apply {
            if (isAscending) ivDate.setImageResource(R.drawable.ic_arrow_upward_24) else ivDate.setImageResource(
                R.drawable.ic_arrow_downward_24
            )
            ivDate.visibility = View.VISIBLE
        }
    }
}