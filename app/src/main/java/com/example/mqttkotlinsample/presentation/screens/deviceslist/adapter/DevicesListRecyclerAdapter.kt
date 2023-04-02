package com.example.mqttkotlinsample.presentation.screens.deviceslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mqttkotlinsample.R
import com.example.mqttkotlinsample.databinding.DevicesItemBinding
import com.example.mqttkotlinsample.domain.models.DeviceModel

class DevicesListRecyclerAdapter(private val onClickListener: DevicesListOnClickListener) :
	RecyclerView.Adapter<DevicesListRecyclerAdapter.DevicesListViewHolder>() {

	var devices: List<DeviceModel> = emptyList()
		set(newValue) {
			val diffUtil = DevicesListDiffUtil(field, newValue)
			val diffResult = DiffUtil.calculateDiff(diffUtil)
			field = newValue
			diffResult.dispatchUpdatesTo(this)
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesListViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = DevicesItemBinding.inflate(inflater, parent, false)
		return DevicesListViewHolder(binding)
	}

	override fun onBindViewHolder(holder: DevicesListViewHolder, position: Int) {
		holder.bind(devices[position])
		holder.itemView.setOnClickListener {
			onClickListener.onClick(devices[position])
		}
	}

	override fun getItemCount() = devices.size

	class DevicesListViewHolder(private val binding: DevicesItemBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(model: DeviceModel) {
			with(binding) {
				textViewTopicName.text = model.name
				textViewTopic.text = model.nameOfTopic
				textValue.text = model.value.toString()
				val valueFloat = model.value
				if (valueFloat != null) {
					if (valueFloat <= model.topLimit && valueFloat >= model.bottomLimit) {
						textValue.setTextColor(textValue.resources.getColor(R.color.black))
					} else {
						textValue.setTextColor(textValue.resources.getColor(R.color.error_color))
					}
				}
			}
		}
	}
}