package com.example.mqttkotlinsample.domain.models

import java.io.Serializable

data class ConnectModel(
	val serverURL: String,
	val clientID: String = "",
	val username: String = "",
	val password: String = ""
) : Serializable
