package com.example.mqttkotlinsample.presentation.screens.connectfragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.domain.usecase.ConnectToBrokerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
	private val connectToBrokerUseCase: ConnectToBrokerUseCase
) : ViewModel() {

	fun checkConnection(connectModel: ConnectModel) {
		connectToBrokerUseCase.connectToBroker(connectModel)
	}

	fun check() {
		var r = connectToBrokerUseCase.checkConnection()
		Log.e("eee", "GGGG: $r")
	}


}