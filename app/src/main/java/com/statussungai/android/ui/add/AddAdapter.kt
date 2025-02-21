package com.statussungai.android.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.statussungai.android.R
import com.statussungai.android.data.network.model.PointsItem
import com.statussungai.android.databinding.ItemRowPointBinding

class AddAdapter(private val listPoint: List<PointsItem>) :
    RecyclerView.Adapter<AddAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: PointsItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRowPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = listPoint.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val point = listPoint[position]
        val url = "https://demofree.sirv.com/nope-not-here.jpg?w=100"
        holder.apply {
            itemView.apply {
                Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(ivPoint)
                tvPointName.text = point.name
                tvPointUser.text = point.userName
                tvPointStatus.text = point.active
                tvPointLat.text =  context.getString(R.string.lat_value, point.latitude)
                tvPointLng.text = context.getString(R.string.lon_value, point.longitude)

                setOnClickListener {
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        itemView.context as Activity,
//                        androidx.core.util.Pair(ivStory, "image"),
//                        androidx.core.util.Pair(tvName, "name"),
//                        androidx.core.util.Pair(tvDesc, "description"),
//                        androidx.core.util.Pair(tvDate, "date")
//                    )
                    onItemClickCallback.onItemClicked(point)
                }
            }
        }
    }

    inner class ViewHolder(binding: ItemRowPointBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivPoint = binding.ivPoint
        val tvPointName = binding.tvPointName
        val tvPointUser = binding.tvPointUser
        val tvPointStatus = binding.tvPointStatus
        val tvPointLat = binding.tvPointLat
        val tvPointLng = binding.tvPointLng
    }
}