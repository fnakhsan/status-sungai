package com.statussungai.android.ui.biotilik

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.statussungai.android.R
import com.statussungai.android.core.utils.Const.EXTRA_POINT_ID
import com.statussungai.android.core.utils.SeasonType.Companion.getSeasonTypeById
import com.statussungai.android.core.utils.TextIsNotBlankUtil.textIsNotBlankListener
import com.statussungai.android.core.utils.UiText
import com.statussungai.android.data.Resource
import com.statussungai.android.data.network.model.BiotilikResult
import com.statussungai.android.databinding.ActivityAddBiotilikBinding
import com.statussungai.android.ui.ActivityHelper.setupActivity
import com.statussungai.android.ui.components.errorToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class AddBiotilikActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBiotilikBinding
    private val addBiotilikViewModel: AddBiotilikViewModel by viewModel<AddBiotilikViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBiotilikBinding.inflate(layoutInflater)
        setupActivity(
            activity = this,
            root = binding.root,
            leftPad = 16,
            topPad = 16,
            rightPad = 16,
            bottomPad = 16
        )

        val pointId = intent.getIntExtra(EXTRA_POINT_ID, 0)
        Timber.d("onCreate: $pointId")

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
                            Timber.d("onCreate: $id")
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
                                Timber.d("onCreate: ${it.error}")
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