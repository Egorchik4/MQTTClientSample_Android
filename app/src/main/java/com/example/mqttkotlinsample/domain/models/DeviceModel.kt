package com.example.mqttkotlinsample.domain.models

import java.io.Serializable

data class DeviceModel(
	val name: String,
	val nameOfTopic: String,
	val qos: Int,
	val topLimit: Int,
	val bottomLimit: Int,
	val value: Float? = null,
	val typeOfValue: String = "",
	val serverURL: String = "",
	val clientID: String = ""
) : Serializable
