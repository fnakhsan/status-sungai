package com.statussungai.android.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.statussungai.android.R
import com.statussungai.android.core.utils.DateTimeConverter.convertDateTime
import com.statussungai.android.core.utils.RiverStatusUtil.Companion.getStatusById
import com.statussungai.android.core.utils.Score
import com.statussungai.android.core.utils.SeasonType.Companion.getSeasonValueById
import com.statussungai.android.data.network.model.DataItem
import com.statussungai.android.databinding.ItemRowBiotilikBinding


class DetailAdapter(private val listData: List<DataItem>) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRowBiotilikBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        val seasonId = data.seasonId ?: 0
        holder.apply {
            itemView.context.apply {
                tvDate.text = getString(
                    R.string.item_date_value,
                    convertDateTime(data.updatedAt ?: ""),
                    getSeasonValueById(seasonId),
                    data.year.toString()
                )
                data.result?.apply {
                    tvStatus.text = getString(
                        R.string.item_status_value,
                        getString(getStatusById(data.status)),
                        Score.sumScore(
                            familyType = this.jenisFamili ?: 0,
                            eptType = this.jenisEpt ?: 0,
                            eptPercentage = this.persenEpt ?: 0.0f,
                            biotilikIndex = this.indeksBiotilik ?: 0.0f
                        )
                    )
                    tvEpt.text = getString(R.string.item_ept_value, jenisEpt.toString())
                    tvPercent.text =
                        getString(R.string.item_ept_percent_value, persenEpt.toString())
                    tvFamily.text =
                        getString(R.string.item_famili_value, jenisFamili.toString())
                    tvIndex.text =
                        getString(R.string.item_biotilik_value, indeksBiotilik.toString())
                }
            }
        }
    }

    inner class ViewHolder(binding: ItemRowBiotilikBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvStatus = binding.tvItemStatus
        val tvDate = binding.tvItemDate
        val tvEpt = binding.tvEpt
        val tvPercent = binding.tvPercent
        val tvFamily = binding.tvFamily
        val tvIndex = binding.tvIndex
    }
}