package com.example.mqttkotlinsample.domain.models

data class UnsubscribeModel(
	val topic: String,
	val qos: Int = 1
)