package com.statussungai.android.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.statussungai.android.R
import com.statussungai.android.core.factory.ViewModelFactory
import com.statussungai.android.core.utils.Const.EXTRA_POINT
import com.statussungai.android.core.utils.ThreadUtil.runOnUiThread
import com.statussungai.android.core.utils.UiText.Companion.asString
import com.statussungai.android.data.Resource
import com.statussungai.android.data.network.model.PointsItem
import com.statussungai.android.databinding.FragmentAddBinding
import com.statussungai.android.ui.details.DetailsActivity
import com.statussungai.android.ui.login.LoginActivity
import com.statussungai.android.ui.point.AddPointActivity
import com.statussungai.android.ui.register.RegisterActivity

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

        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                parentFragmentManager.beginTransaction().show(ListFilterFragment()).commit()
//                requireActivity().findNavController(R.id.nav_host).navigate(R.id.action_addFragment_to_listFilterFragment)
                requireParentFragment().findNavController()
                    .navigate(R.id.action_addFragment_to_listFilterFragment)
                return true
            }
        }, viewLifecycleOwner)

        binding.apply {
            fabAdd.setOnClickListener {
                startActivity(Intent(requireContext(), AddPointActivity::class.java))
            }
            layoutNotLogin.tvRegister.setOnClickListener {
                startActivity(Intent(requireContext(), RegisterActivity::class.java))
            }
            layoutNotLogin.tvLogin.setOnClickListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

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
                                    it.error.asString(requireContext()),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
                    setMenuVisibility(true)
                } else {
                    layoutNotLogin.root.visibility = View.VISIBLE
                    fabAdd.visibility = View.INVISIBLE
                    rvPoint.visibility = View.INVISIBLE
                    setMenuVisibility(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}