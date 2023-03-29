package com.example.mqttkotlinsample.data.datasource

import com.example.mqttkotlinsample.domain.models.ConnectModel

interface DataSource {

	fun connectToBroker(connectModel: ConnectModel)

	fun brokerIsConnected(): Boolean

	fun disconnectToBroker(): Boolean

	fun publishMessageToTopic()

	fun subscribeToTopic()

	fun unsubscribeToTopic()
}