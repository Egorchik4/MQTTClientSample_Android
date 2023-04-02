package com.example.mqttkotlinsample.presentation.screens.deviceslist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mqttkotlinsample.domain.models.DeviceModel

class DevicesListDiffUtil(
	private val oldList: List<DeviceModel>,
	private val newList: List<DeviceModel>
) : DiffUtil.Callback() {

	override fun getOldListSize() = oldList.size

	override fun getNewListSize() = newList.size

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition].name == newList[newItemPosition].name
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}
}