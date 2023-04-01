package com.example.mqttkotlinsample.presentation.screens.connectfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.domain.usecase.ConnectToBrokerUseCase
import com.example.mqttkotlinsample.presentation.state.ConnectState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
	private val connectToBrokerUseCase: ConnectToBrokerUseCase
) : ViewModel() {

	private val _state = MutableLiveData<ConnectState>()
	val state: LiveData<ConnectState> = _state

	fun checkConnection(connectModel: ConnectModel) {
		connectToBrokerUseCase.connectToBroker(connectModel)
	}

	fun check() {
//		var r = connectToBrokerUseCase.checkConnection()
//		Log.e("eee", "GGGG: $r")
	}




}