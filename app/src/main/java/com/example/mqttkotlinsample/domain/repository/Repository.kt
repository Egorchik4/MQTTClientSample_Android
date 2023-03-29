package com.example.mqttkotlinsample.domain.repository

import com.example.mqttkotlinsample.domain.models.ConnectModel

interface Repository {

	fun connectToBroker(connectModel: ConnectModel)

	fun brokerIsConnected(): Boolean

	fun disconnectToBroker(): Boolean

	fun publishMessageToTopic()

	fun subscribeToTopic()

	fun unsubscribeToTopic()
}