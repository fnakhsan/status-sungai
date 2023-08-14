package uin.suka.status.sungai.ui.maps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.core.utils.ThreadUtil.runOnUiThread
import uin.suka.status.sungai.data.Resource
import uin.suka.status.sungai.data.network.model.RiversItem
import uin.suka.status.sungai.data.network.model.ViewPointsItem
import uin.suka.status.sungai.databinding.FragmentMapsBinding
import uin.suka.status.sungai.ui.details.DetailsActivity

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

        googleMap.setOnMarkerClickListener {
            showSnackBar()
            false
        }


//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
            val mapsViewModel: MapsViewModel by viewModels {
                factory
            }
            mapsViewModel.views().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        runOnUiThread {
                            showRiver(googleMap, it.data.data.rivers)
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRiver(googleMap: GoogleMap, listRiver: List<RiversItem>) {
        val allLatLngList = mutableListOf<LatLng>()
        listRiver.forEach { river ->
            val latLngList = mutableListOf<LatLng>()
            river.segments.forEach { segment ->
                segment.details.forEach {
                    latLngList.add(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                    allLatLngList.add(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                }
                addMarkers(googleMap, segment.points)
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
        val bounds: LatLngBounds = boundsBuilder.build()
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
        /*LatLng(-7.7240656222641455, 110.3893486301115),
        LatLng(-7.724786, 110.389623),
        LatLng(-7.72477027191311, 110.38969525698727),
        LatLng(-7.72566605315169, 110.38981780776163)*/
    }

    private fun addMarkers(googleMap: GoogleMap, listPoint: List<ViewPointsItem>) {
        listPoint.forEach { point ->
            point.datas.lastOrNull {
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            point.latitude.toDouble(),
                            point.longitude.toDouble()
                        )
                    ).title(point.name)
                        .snippet(getString(R.string.snippet, it.result.indeksBiotilik))
                )
                true
            }
        }
    }

    private fun showSnackBar() {
//        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            requireView(),
            getString(R.string.snack_bar_message),
            Snackbar.LENGTH_SHORT
        ).setAction(getString(R.string.snack_bar_action)) {
            val intent = Intent(activity, DetailsActivity::class.java)
            startActivity(intent)
        }.show()
    }

    private fun showLoading(isLoading: Boolean) {
        runOnUiThread {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}