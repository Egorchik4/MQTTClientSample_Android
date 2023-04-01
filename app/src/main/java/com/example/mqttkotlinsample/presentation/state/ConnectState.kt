package com.example.mqttkotlinsample.presentation.state

sealed class ConnectState{

	object Loading: ConnectState()

	data class Connected(
		val serverURL: String,
		val clientID: String = "",
		val username: String = "",
		val password: String = ""): ConnectState()

	object Error: ConnectState()
}