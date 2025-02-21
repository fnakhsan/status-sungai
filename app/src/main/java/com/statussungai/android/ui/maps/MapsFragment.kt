package com.statussungai.android.ui.maps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.statussungai.android.R
import com.statussungai.android.core.factory.ViewModelFactory
import com.statussungai.android.core.utils.Const.EXTRA_POINT_ID
import com.statussungai.android.core.utils.RiverStatusUtil.Companion.getStatusById
import com.statussungai.android.core.utils.Score
import com.statussungai.android.core.utils.ThreadUtil.runOnUiThread
import com.statussungai.android.core.utils.UserType
import com.statussungai.android.data.Resource
import com.statussungai.android.data.network.model.RiversItem
import com.statussungai.android.data.network.model.ViewPointsItem
import com.statussungai.android.databinding.FragmentMapsBinding
import com.statussungai.android.ui.details.DetailsActivity


class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val mapsViewModel: MapsViewModel by viewModels {
            factory
        }
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mapsViewModel.views().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    runOnUiThread {
                        showRiver(googleMap, it.data.data.rivers, mapsViewModel)
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

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.maps_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                requireParentFragment().findNavController()
                    .navigate(R.id.action_mapsFragment_to_mapsFilterFragment)
                return true
            }
        }, viewLifecycleOwner)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRiver(
        googleMap: GoogleMap,
        listRiver: List<RiversItem>,
        mapsViewModel: MapsViewModel
    ) {
        val allLatLngList = mutableListOf<LatLng>()
        listRiver.forEach { river ->
            val latLngList = mutableListOf<LatLng>()
            river.segments.forEach { segment ->
                segment.details.forEach {
                    latLngList.add(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                    allLatLngList.add(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                }
                addMarkers(googleMap, segment.points, mapsViewModel)
            }
            val polyline1 =
                googleMap.addPolyline(
                    PolylineOptions().clickable(true)
                        .color(ContextCompat.getColor(requireContext(), R.color.seed))
                        .geodesic(true)
                )
            polyline1.points = latLngList
            latLngList.clear()
        }

        val boundsBuilder = LatLngBounds.Builder()
        allLatLngList.forEach {
            boundsBuilder.include(it)
        }
        if (allLatLngList.isNotEmpty()) {
            val bounds: LatLngBounds = boundsBuilder.build()
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    resources.displayMetrics.widthPixels,
                    resources.displayMetrics.heightPixels,
                    300
                )
            )
        }
        /*LatLng(-7.7240656222641455, 110.3893486301115),
        LatLng(-7.724786, 110.389623),
        LatLng(-7.72477027191311, 110.38969525698727),
        LatLng(-7.72566605315169, 110.38981780776163)*/
    }

    private fun addMarkers(
        googleMap: GoogleMap,
        listPoint: List<ViewPointsItem>,
        mapsViewModel: MapsViewModel
    ) {
        listPoint.forEach { point ->
            point.datas.lastOrNull {
//                Log.d("score", "addMarkers: res ${it.result}")
//                val score = (((it.result.jenisEpt.toFloatOrNull() ?: 0.0f) +
//                        (it.result.jenisFamili.toFloatOrNull() ?: 0.0f) +
//                        (it.result.persenEpt.toFloatOrNull() ?: 0.0f) +
//                        (it.result.indeksBiotilik.toFloatOrNull() ?: 0.0f)) / 4)
//                Log.d("score", "addMarkers: score $score")
//                val scoreRounded =
//                    BigDecimal(score.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            point.latitude.toDouble(),
                            point.longitude.toDouble()
                        )
                    ).title(point.name)
                        .snippet(
                            getString(
                                R.string.snippet1,
                                getString(getStatusById(it.status)),
                                Score.sumScore(
                                    familyType = it.result?.jenisFamili?.toIntOrNull() ?: 0,
                                    eptType = it.result?.jenisEpt?.toIntOrNull() ?: 0,
                                    eptPercentage = it.result?.persenEpt?.toFloatOrNull() ?: 0.0f,
                                    biotilikIndex = it.result?.indeksBiotilik?.toFloatOrNull()
                                        ?: 0.0f,
                                )
                            )
                        )
                )?.tag = point.id
                true
            }
        }
        googleMap.setOnMarkerClickListener { marker ->
            showSnackBar(mapsViewModel, if (marker.tag is Int) marker.tag.toString().toInt() else 0)
            false
        }
    }

    private fun showSnackBar(mapsViewModel: MapsViewModel, pointId: Int) {
        mapsViewModel.getRole().observe(viewLifecycleOwner) {
            when (it) {
                UserType.GUEST.type -> {}
                else -> {
                    Snackbar.make(
                        binding.clSnackBar,
                        getString(R.string.snack_bar_message),
                        Snackbar.LENGTH_SHORT
                    ).setAction(getString(R.string.snack_bar_action)) {
                        val intent = Intent(activity, DetailsActivity::class.java)
                        startActivity(intent.putExtra(EXTRA_POINT_ID, pointId))
                    }.show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        runOnUiThread {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}