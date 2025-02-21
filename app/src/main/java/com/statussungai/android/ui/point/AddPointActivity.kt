package com.statussungai.android.ui.point

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import com.statussungai.android.R
import com.statussungai.android.core.factory.ViewModelFactory
import com.statussungai.android.core.utils.TextIsNotBlankUtil.textIsNotBlankListener
import com.statussungai.android.core.utils.UiText.Companion.asString
import com.statussungai.android.data.Resource
import com.statussungai.android.databinding.ActivityAddPointBinding
import com.statussungai.android.ui.ActivityHelper.setupActivity

class AddPointActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPointBinding
    private lateinit var pointName: String
    private var pointLat: Double? = null
    private var pointLng: Double? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }

                else -> {
                    binding.switchLocation.isChecked = false
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPointBinding.inflate(layoutInflater)
        setupActivity(
            activity = this,
            root = binding.root,
            leftPad = 16,
            topPad = 16,
            rightPad = 16,
            bottomPad = 16
        )

        binding.apply {
            textIsNotBlankListener(edtPointName)
            textIsNotBlankListener(edtPointLat)
            textIsNotBlankListener(edtPointLng)
            switchLocation.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    lifecycleScope.launch {
                        getMyLastLocation()
                    }
                } else {
                    location = null
                }
            }
            btnAddPoint.setOnClickListener {
                if (edtPointName.error.isNullOrBlank() && edtPointLat.error.isNullOrBlank() && edtPointLng.error.isNullOrBlank()) {
                    val factory: ViewModelFactory =
                        ViewModelFactory.getInstance(this@AddPointActivity)
                    val addPointViewModel: AddPointViewModel by viewModels {
                        factory
                    }
                    pointName = edtPointName.text.toString()
                    pointLat = edtPointLat.text.toString().toDouble()
                    pointLng = edtPointLng.text.toString().toDouble()
                    submit(addPointViewModel, pointName, pointLat!!, pointLng!!)
                } else {
                    Toast.makeText(
                        this@AddPointActivity,
                        "Please fill the required field",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@AddPointActivity)
    }

    private fun submit(
        addPointViewModel: AddPointViewModel,
        pointName: String,
        pointLat: Double,
        pointLng: Double
    ) {
        addPointViewModel.addPoint(
            pointName, pointLat, pointLng
        ).observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    finish()
                }

                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        this@AddPointActivity,
                        it.error.asString(this),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    this.location = location
                    runOnUiThread {
                        binding.edtPointLat.setText(location.latitude.toString())
                        binding.edtPointLng.setText(location.longitude.toString())
                    }
                } else {
                    Toast.makeText(
                        this@AddPointActivity,
                        R.string.error_no_location,
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.switchLocation.isChecked = false
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}