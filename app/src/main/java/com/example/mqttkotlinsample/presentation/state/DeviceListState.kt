package com.example.mqttkotlinsample.presentation.state

import com.example.mqttkotlinsample.domain.models.DeviceModel

sealed class DeviceListState {
	object Loading : DeviceListState()

	data class Content(
		val listOfDevice: List<DeviceModel>
		) : DeviceListState()

	object Error : DeviceListState()
}