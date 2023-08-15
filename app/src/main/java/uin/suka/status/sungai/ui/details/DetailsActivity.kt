package uin.suka.status.sungai.ui.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.utils.Const.EXTRA_POINT
import uin.suka.status.sungai.core.utils.Const.EXTRA_POINT_ID
import uin.suka.status.sungai.core.utils.parcelable
import uin.suka.status.sungai.data.network.model.PointsItem
import uin.suka.status.sungai.databinding.ActivityDetailsBinding
import uin.suka.status.sungai.ui.biotilik.AddBiotilikActivity

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val point = intent.parcelable<PointsItem>(EXTRA_POINT)
//        val point = intent.getParcelableExtra<PointsItem>(EXTRA_POINT_ID)
//        val point = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableExtra(EXTRA_POINT, PointsItem::class.java)
//        } else {
//             @Suppress("DEPRECATION")
//            intent.getParcelableExtra(EXTRA_POINT)
//        }
        Log.d("point", point.toString())
        if (point != null) {
            setViewPager(point.id)
            binding.apply {
                tvPointName.text = point.name
                tvPointUser.text = point.userName
                tvPointStatus.text = point.active
                tvPointLat.text =  this@DetailsActivity.getString(R.string.lat_value, point.latitude)
                tvPointLng.text = this@DetailsActivity.getString(R.string.lon_value, point.longitude)
                fabAdd.setOnClickListener {
                    val intent = Intent(this@DetailsActivity, AddBiotilikActivity::class.java)
                    intent.putExtra(EXTRA_POINT_ID, point)
                    startActivity(intent)
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

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.biotilik
        )
    }
}