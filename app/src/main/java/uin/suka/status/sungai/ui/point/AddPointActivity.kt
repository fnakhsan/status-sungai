package uin.suka.status.sungai.ui.point

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.core.utils.UiText.Companion.asString
import uin.suka.status.sungai.data.Resource
import uin.suka.status.sungai.databinding.ActivityAddPointBinding

class AddPointActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPointBinding
    private lateinit var pointName: String
    private var pointLat: Double? = null
    private var pointLng: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            edtPointName.apply {
                error = if (text?.length == 0) "This field cannot be empty" else null
            }
            edtPointLat.apply {
                error = if (text?.length == 0) "This field cannot be empty" else null
            }
            edtPointLng.apply {
                error = if (text?.length == 0) "This field cannot be empty" else null
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}