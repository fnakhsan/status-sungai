package com.statussungai.android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.statussungai.android.R
import com.statussungai.android.core.utils.UiText.Companion.asString
import com.statussungai.android.core.utils.UserType
import com.statussungai.android.data.Resource
import com.statussungai.android.data.network.model.User
import com.statussungai.android.databinding.FragmentProfileBinding
import com.statussungai.android.ui.components.errorToast
import com.statussungai.android.ui.components.toast
import com.statussungai.android.ui.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        
        profileViewModel.getUserId().observe(viewLifecycleOwner) { id ->
            profileViewModel.getUserById(id.toString()).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        showProfile(it.data.data)
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        if (it.error.asString(requireContext()) == UserType.GUEST.type) {
                            showGuestProfile()
                        } else {
                            requireContext().errorToast(it.error)
                        }
                    }
                }
            }
        }
        binding.fabLogout.setOnClickListener {
            profileViewModel.logoutUser().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        requireContext().toast(it.data.asString(requireContext()))
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        requireContext().errorToast(it.error)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProfile(profile: User) {
        binding.apply {
            edtUsername.setText(profile.username)
            edtName.setText(profile.name)
            edtCommunity.setText(profile.description)
        }
    }

    private fun showGuestProfile() {
        binding.apply {
            edtUsername.setText(getString(R.string.guest))
            edtName.setText(getString(R.string.guest))
            edtCommunity.setText(getString(R.string.guest))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}