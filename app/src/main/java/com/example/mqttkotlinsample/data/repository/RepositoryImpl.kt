package com.example.mqttkotlinsample.data.repository

import com.example.mqttkotlinsample.data.datasource.DataSource
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: DataSource): Repository {

	override fun connectToBroker(connectModel: ConnectModel) {
		dataSource.connectToBroker(connectModel)
	}

	override fun brokerIsConnected() =
		dataSource.brokerIsConnected()

	override fun disconnectToBroker(): Boolean {
		TODO("Not yet implemented")
	}

	override fun publishMessageToTopic() {
		TODO("Not yet implemented")
	}

	override fun subscribeToTopic() {
		TODO("Not yet implemented")
	}

	override fun unsubscribeToTopic() {
		TODO("Not yet implemented")
	}
}