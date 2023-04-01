package com.example.mqttkotlinsample.presentation.state

sealed class ConnectState{
	object Loading: ConnectState()
	object Connected: ConnectState()
}