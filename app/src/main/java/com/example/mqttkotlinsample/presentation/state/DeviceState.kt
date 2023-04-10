package com.example.mqttkotlinsample.presentation.state

sealed class DeviceState {

	object Loading : DeviceState()

	data class Content(
		val list: MutableList<Float>
	) : DeviceState()

	object Error : DeviceState()
}
