package com.statussungai.android.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.statussungai.android.R
import com.statussungai.android.core.utils.Const.EXTRA_POINT
import com.statussungai.android.core.utils.Const.EXTRA_POINT_ID
import com.statussungai.android.core.utils.DateTimeConverter.convertDateTime
import com.statussungai.android.core.utils.parcelable
import com.statussungai.android.data.Resource
import com.statussungai.android.data.network.model.PointsItem
import com.statussungai.android.databinding.ActivityDetailsBinding
import com.statussungai.android.ui.ActivityHelper.setupActivity
import com.statussungai.android.ui.biotilik.AddBiotilikActivity
import com.statussungai.android.ui.components.errorToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModel<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setupActivity(activity = this, root = binding.root)

        val point = intent.parcelable<PointsItem>(EXTRA_POINT)
        if (point != null) {
            setViewPager(point.id)
            binding.apply {
                tvPointName.text = point.name
                tvPointUser.text = this@DetailsActivity.getString(R.string.point_user, point.userName)
                tvPointDate.text = this@DetailsActivity.getString(R.string.point_date, convertDateTime(point.createdAt))
                tvPointStatus.text = this@DetailsActivity.getString(R.string.point_status, point.active)
                tvPointRiver.text = this@DetailsActivity.getString(R.string.point_river, resources.getStringArray(R.array.river_array)[point.riverId-1])
                tvPointLat.text =
                    this@DetailsActivity.getString(R.string.point_lat, point.latitude)
                tvPointLng.text =
                    this@DetailsActivity.getString(R.string.point_lng, point.longitude)
                fabAdd.setOnClickListener {
                    val intent = Intent(this@DetailsActivity, AddBiotilikActivity::class.java)
                    intent.putExtra(EXTRA_POINT_ID, point.id)
                    startActivity(intent)
                }
            }
        } else {
            val pointId = intent.getIntExtra(EXTRA_POINT_ID, 0)
            Timber.d("onCreate: $pointId")
            setViewPager(pointId)

            detailsViewModel.getPointById(pointId.toString()).observe(this) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        binding.apply {
                            tvPointName.text = it.data.name
                            tvPointUser.text = this@DetailsActivity.getString(R.string.point_user, it.data.userName)
                            tvPointDate.text = this@DetailsActivity.getString(R.string.point_date, convertDateTime(it.data.createdAt))
                            tvPointStatus.text = this@DetailsActivity.getString(R.string.point_status, it.data.active)
                            tvPointRiver.text = this@DetailsActivity.getString(R.string.point_river, resources.getStringArray(R.array.river_array)[it.data.riverId-1])
                            tvPointLat.text =
                                this@DetailsActivity.getString(R.string.point_lat, it.data.latitude)
                            tvPointLng.text =
                                this@DetailsActivity.getString(R.string.point_lng, it.data.longitude)
                            fabAdd.setOnClickListener { _ ->
                                val intent =
                                    Intent(this@DetailsActivity, AddBiotilikActivity::class.java)
                                intent.putExtra(EXTRA_POINT_ID, it.data.id)
                                startActivity(intent)
                            }
                        }
                        showLoading(false)
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        this.errorToast(it.error)
                    }
                }
            }
        }
    }

    private fun setViewPager(pointId: Int) {
        Timber.d("setViewPager: $pointId")

        val sectionsPagerAdapter = SectionsPagerAdapter(this, pointId)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.biotilik
        )
    }
}