package uin.suka.status.sungai.ui.biotilik

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.utils.Const.EXTRA_POINT_ID
import uin.suka.status.sungai.utils.SeasonType.Companion.getSeasonTypeById
import uin.suka.status.sungai.utils.TextIsNotBlankUtil.textIsNotBlankListener
import com.example.core.utils.UiText
import com.example.core.data.Resource
import uin.suka.status.sungai.data.network.model.BiotilikResult
import uin.suka.status.sungai.databinding.ActivityAddBiotilikBinding
import uin.suka.status.sungai.ui.components.errorToast


class AddBiotilikActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBiotilikBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBiotilikBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val addBiotilikViewModel: AddBiotilikViewModel by viewModels {
            factory
        }
        val pointId = intent.getIntExtra(EXTRA_POINT_ID, 0)
        Log.d("biotilik", pointId.toString())
        val season = resources.getStringArray(R.array.season_array)
        val year = resources.getStringArray(R.array.year_array)
        val seasonAdapter = ArrayAdapter(this, R.layout.dropdown_item, season)
        val yearAdapter = ArrayAdapter(this, R.layout.dropdown_item, year)
        binding.apply {
            edtSeason.setAdapter(seasonAdapter)
            edtYear.setAdapter(yearAdapter)
            textIsNotBlankListener(edtEpt)
            textIsNotBlankListener(edtPercent)
            textIsNotBlankListener(edtFamili)
            textIsNotBlankListener(edtBiotilik)
            btnAddBiotilik.setOnClickListener {
                if (edtEpt.error.isNullOrBlank() && edtPercent.error.isNullOrBlank() && edtFamili.error.isNullOrBlank() && edtBiotilik.error.isNullOrBlank()) {
                    val biotilik = BiotilikResult(
                        edtPercent.text.toString().toFloat(),
                        edtBiotilik.text.toString().toFloat(),
                        edtFamili.text.toString().toInt(),
                        edtEpt.text.toString().toInt()
                    )
                    var seasonId = 1
                    edtSeason.onItemClickListener =
                        OnItemClickListener { _, _, _, id -> //// id contains item if from database
                            seasonId = id.toInt()
                            Log.d("biotilik", id.toString())
                        }
                    addBiotilikViewModel.addBiotilik(
                        pointId.toString(),
                        biotilik,
                        getSeasonTypeById(seasonId),
                        edtYear.text.toString().toInt()
                    ).observe(this@AddBiotilikActivity) {
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
                                Log.d("biotilik", it.error.toString())
                                this@AddBiotilikActivity.errorToast(it.error)
                            }
                        }
                    }
                } else {
                    this@AddBiotilikActivity.errorToast(UiText.DynamicString("Please fill the required field"))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}