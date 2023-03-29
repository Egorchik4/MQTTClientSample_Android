package com.example.mqttkotlinsample.domain.usecase

import com.example.mqttkotlinsample.domain.repository.Repository
import javax.inject.Inject

class DisconnectToBrokerUseCase @Inject constructor(private val repository: Repository) {

	fun disconnectToBroker(){
		repository.disconnectToBroker()
	}
}