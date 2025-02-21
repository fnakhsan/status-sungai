package com.statussungai.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import com.statussungai.android.R
import com.statussungai.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cardMaps.setOnClickListener {
                requireParentFragment().findNavController()
                    .navigate(R.id.action_homeFragment_to_mapsFragment)
//                parentFragmentManager.beginTransaction().apply {
//                    replace(R.id.nav_host, MapsFragment())
//                    addToBackStack(null)
//                    commit()
//                }
            }
            cardInfo.setOnClickListener {
//                parentFragmentManager.beginTransaction().apply {
//                    replace(R.id.nav_host, InfoFragment())
//                    addToBackStack(null)
//                    commit()
//                }
                startActivity(
                    PdfViewerActivity.launchPdfFromPath(
                        context = requireContext(),
                        path = "panduan_biotilik.pdf",
                        pdfTitle = "Materi",
                        saveTo = saveTo.ASK_EVERYTIME,
                        fromAssets = true
                    )
                )
            }
            cardVolunteer.setOnClickListener {
                requireParentFragment().findNavController()
                    .navigate(R.id.action_homeFragment_to_addFragment)
//                parentFragmentManager.beginTransaction().apply {
//                    replace(R.id.nav_host, AddFragment())
//                    addToBackStack(null)
//                    commit()
//                }
            }
            cardProfile.setOnClickListener {
                requireParentFragment().findNavController()
                    .navigate(R.id.action_homeFragment_to_profileFragment)
//                parentFragmentManager.beginTransaction().apply {
//                    replace(R.id.nav_host, ProfileFragment())
//                    addToBackStack(null)
//                    commit()
//                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}