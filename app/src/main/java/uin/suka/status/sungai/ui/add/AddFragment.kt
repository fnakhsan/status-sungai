package uin.suka.status.sungai.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.core.utils.Const.EXTRA_POINT
import uin.suka.status.sungai.core.utils.ThreadUtil.runOnUiThread
import uin.suka.status.sungai.data.Resource
import uin.suka.status.sungai.data.network.model.PointsItem
import uin.suka.status.sungai.databinding.FragmentAddBinding
import uin.suka.status.sungai.ui.details.DetailsActivity
import uin.suka.status.sungai.ui.point.AddPointActivity

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPoint.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvPoint.addItemDecoration(itemDecoration)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val addViewModel: AddViewModel by viewModels {
            factory
        }
        addViewModel.apply {
            getToken().observe(viewLifecycleOwner) { token ->
                if (token.isNullOrBlank()) {
                    Log.d("Add", "token null")
                    showAddContent(false)
                } else {
                    Log.d("Add", "token not null")
                    showAddContent(true)
                    Log.d("Add", "check")
                    getAllPoints().observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> {
                                Log.d("Add", "loading")
                                showLoading(true)
                            }

                            is Resource.Success -> {
                                showLoading(false)
                                Log.d("Add", "sukses")
                                setListPoints(it.data)
                            }

                            is Resource.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    requireContext(),
                                    it.error.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddPointActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setListPoints(point: List<PointsItem>) {
//        if (search?.size == 0) {
//            binding.layoutNotFound.apply {
//                tvNotFound.text = getString(R.string.search_not_found)
//                setLayoutNotFoundVisibility(true)
//            }
//        }
        val adapter = AddAdapter(point)

        adapter.setOnItemClickCallback(object : AddAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PointsItem) {
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                Log.d("point", "kirim $data")
                startActivity(intent.putExtra(EXTRA_POINT, data))
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
//                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
//                toDetailFragment.username = data.login
//                findNavController().navigate(toDetailFragment)
            }
        })
        binding.rvPoint.adapter = adapter
    }

    private fun showAddContent(isAlreadyLogin: Boolean) {
        runOnUiThread {
            binding.apply {
                if (isAlreadyLogin) {
                    layoutNotLogin.root.visibility = View.INVISIBLE
                    fabAdd.visibility = View.VISIBLE
                    rvPoint.visibility = View.VISIBLE
                } else {
                    layoutNotLogin.root.visibility = View.VISIBLE
                    fabAdd.visibility = View.INVISIBLE
                    rvPoint.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}