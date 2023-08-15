package uin.suka.status.sungai.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.utils.SeasonType.Companion.getSeasonById
import uin.suka.status.sungai.data.network.model.DataItem
import uin.suka.status.sungai.databinding.ItemRowBiotilikBinding

class DetailAdapter(private val listData: List<DataItem>) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ViewHolder =
        ViewHolder(
            ItemRowBiotilikBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: DetailAdapter.ViewHolder, position: Int) {
        val data = listData[position]
        val seasonId = data.seasonId ?: 0
        holder.apply {
            tvStatus.text = data.status.toString()
            itemView.context.apply {
                tvSeasonYear.text = getString(R.string.season_year, getSeasonById(seasonId), data.year.toString())
                tvEpt.text = getString(R.string.point_ept_value, data.result?.jenisEpt)
                tvPercent.text = getString(R.string.point_ept_percent_value, data.result?.persenEpt)
                tvFamily.text = getString(R.string.point_famili_value, data.result?.jenisFamili)
                tvIndex.text = getString(R.string.point_biotilik_value, data.result?.indeksBiotilik)
            }
        }
    }

    inner class ViewHolder(binding: ItemRowBiotilikBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvStatus = binding.tvStatus
        val tvSeasonYear = binding.tvSeasonYear
        val tvEpt = binding.tvEpt
        val tvPercent = binding.tvPercent
        val tvFamily = binding.tvFamily
        val tvIndex = binding.tvIndex
    }
}