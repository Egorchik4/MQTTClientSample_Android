package com.example.mqttkotlinsample.presentation.screens.deviceslist.adapter

import com.example.mqttkotlinsample.domain.models.DeviceModel

interface DevicesListOnClickListener {

	fun onClick(model: DeviceModel)
}