package com.example.mqttkotlinsample.domain.models

data class PublishModel(
	val topic: String,
	val message: String,
	val qos: Int = 1
)
