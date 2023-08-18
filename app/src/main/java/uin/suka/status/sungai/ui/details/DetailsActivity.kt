package uin.suka.status.sungai.ui.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.core.utils.Const.EXTRA_POINT
import uin.suka.status.sungai.core.utils.Const.EXTRA_POINT_ID
import uin.suka.status.sungai.core.utils.parcelable
import uin.suka.status.sungai.data.Resource
import uin.suka.status.sungai.data.network.model.PointsItem
import uin.suka.status.sungai.databinding.ActivityDetailsBinding
import uin.suka.status.sungai.ui.biotilik.AddBiotilikActivity
import uin.suka.status.sungai.ui.components.errorToast

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val point = intent.parcelable<PointsItem>(EXTRA_POINT)
        if (point != null) {
            setViewPager(point.id)
            binding.apply {
                tvPointName.text = point.name
                tvPointUser.text = point.userName
                tvPointStatus.text = point.active
                tvPointLat.text =
                    this@DetailsActivity.getString(R.string.latitude_value, point.latitude)
                tvPointLng.text =
                    this@DetailsActivity.getString(R.string.longitude_value, point.longitude)
                fabAdd.setOnClickListener {
                    val intent = Intent(this@DetailsActivity, AddBiotilikActivity::class.java)
                    intent.putExtra(EXTRA_POINT_ID, point.id)
                    startActivity(intent)
                }
            }
        } else {
            val pointId = intent.getIntExtra(EXTRA_POINT_ID, 0)
            Log.d("point", pointId.toString())
            setViewPager(pointId)
            val detailsViewModel: DetailsViewModel by viewModels {
                ViewModelFactory.getInstance(this)
            }
            detailsViewModel.getPointById(pointId.toString()).observe(this) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        binding.apply {
                            tvPointName.text = it.data.name
                            tvPointUser.text = it.data.userName
                            tvPointStatus.text = it.data.active
                            tvPointLat.text =
                                this@DetailsActivity.getString(R.string.latitude_value, it.data.latitude)
                            tvPointLng.text =
                                this@DetailsActivity.getString(R.string.longitude_value, it.data.longitude)
                            fabAdd.setOnClickListener { _ ->
                                val intent = Intent(this@DetailsActivity, AddBiotilikActivity::class.java)
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
        Log.d("pointid", "masuk $pointId")
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