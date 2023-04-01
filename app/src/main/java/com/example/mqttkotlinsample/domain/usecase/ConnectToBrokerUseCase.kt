package com.example.mqttkotlinsample.domain.usecase

import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.domain.repository.Repository
import javax.inject.Inject

class ConnectToBrokerUseCase @Inject constructor(private val repository: Repository) {

	fun connectToBroker(connectModel: ConnectModel) {
		repository.connectToBroker(connectModel)
	}
}