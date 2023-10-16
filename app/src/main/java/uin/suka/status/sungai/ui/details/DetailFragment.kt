package uin.suka.status.sungai.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.utils.Const.EXTRA_POINT_ID
import com.example.core.data.Resource
import uin.suka.status.sungai.data.network.model.DataItem
import uin.suka.status.sungai.databinding.FragmentDetailBinding
import uin.suka.status.sungai.ui.components.errorToast


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvBiotilik.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvBiotilik.addItemDecoration(itemDecoration)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val detailsViewModel: DetailsViewModel by viewModels {
            factory
        }
        val argsId = this.arguments?.getInt(EXTRA_POINT_ID)
        Log.d("pointid", "final $argsId")
        detailsViewModel.getStatusByPointId(argsId.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    setListPoints(it.data)
                }

                is Resource.Error -> {
                    showLoading(false)
                    Log.d("point", it.error.toString())
                    requireContext().errorToast(it.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListPoints(data: List<DataItem>) {

        val adapter = DetailAdapter(data)
        binding.rvBiotilik.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(index: Int, pointId: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putInt(EXTRA_POINT_ID, pointId)
                }
            }
    }
}