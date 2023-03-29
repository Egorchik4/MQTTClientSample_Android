package com.example.mqttkotlinsample.domain.models

data class ConnectModel(
	val serverURL: String,
	val username: String = "",
	val password: String = ""
)
