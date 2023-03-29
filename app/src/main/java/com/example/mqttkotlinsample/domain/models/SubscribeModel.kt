package com.example.mqttkotlinsample.domain.models

data class SubscribeModel(
	val topic: String,
	val qos: Int = 1
)
