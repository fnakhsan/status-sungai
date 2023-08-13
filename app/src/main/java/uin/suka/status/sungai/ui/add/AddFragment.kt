package uin.suka.status.sungai.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.core.utils.ThreadUtil.runOnUiThread
import uin.suka.status.sungai.data.Resource
import uin.suka.status.sungai.data.network.model.PointsItem
import uin.suka.status.sungai.databinding.FragmentAddBinding
import uin.suka.status.sungai.ui.details.DetailsActivity

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

        lifecycleScope.launch(Dispatchers.IO) {
            val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
            val addViewModel: AddViewModel by viewModels {
                factory
            }
            addViewModel.apply {
                getToken().collectLatest { token ->
                    if (token.isNullOrBlank()) {
                        showAddContent(false)
                    } else {
                        showAddContent(true)
                        getPoint().collectLatest {
                            when (it) {
                                is Resource.Loading -> {
                                    showLoading(true)
                                }

                                is Resource.Success -> {
                                    showLoading(false)
                                    runOnUiThread {
                                        setListPoints(it.data)
                                    }
                                }

                                is Resource.Error -> {
                                    showLoading(false)
                                    runOnUiThread {
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
            }
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
//                intent.putExtra(Const.EXTRA_TOKEN, token)
                startActivity(intent)
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
//                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
//                toDetailFragment.username = data.login
//                findNavController().navigate(toDetailFragment)
            }
        })
        binding.rvPoint.adapter = adapter
    }

    private fun showAddContent(isAlreadyLogin: Boolean) {
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

    private fun showLoading(isLoading: Boolean) {
        runOnUiThread {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}