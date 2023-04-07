package com.example.sleeeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeeptracker.database.SleepNight
import com.example.sleeeptracker.databinding.SleepListItemBinding

class SleepNightAdapter(val clickListener : SleepNightListener) : ListAdapter<SleepNight,SleepNightAdapter.MyViewHolder>(SleepNightDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener,item)
    }

    class MyViewHolder private constructor(private val binding: SleepListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: SleepNightListener,
            item: SleepNight
        ) {
            binding.clickListener = clickListener
            binding.sleep = item
            binding.executePendingBindings()
//            val res = itemView.context.resources
//            binding.sleepLength.text =
//                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
//
//            binding.qualityImage.setImageResource(
//                when (item.sleepQuality) {
//                    0 -> R.drawable.ic_sleep_0
//                    1 -> R.drawable.ic_sleep_1
//                    2 -> R.drawable.ic_sleep_2
//                    3 -> R.drawable.ic_sleep_3
//                    4 -> R.drawable.ic_sleep_4
//                    5 -> R.drawable.ic_sleep_5
//                    else -> R.drawable.ic_sleep_active
//                }
//            )
        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SleepListItemBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    class SleepNightDiffCallBack : DiffUtil.ItemCallback<SleepNight>(){
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }
    }

    class SleepNightListener(val clickListener : (sleepId : Long) -> Unit){
        fun onClick(night : SleepNight) = clickListener(night.nightId)
    }

}


