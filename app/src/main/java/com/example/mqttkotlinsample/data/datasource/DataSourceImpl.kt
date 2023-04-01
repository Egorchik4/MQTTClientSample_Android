package com.example.mqttkotlinsample.data.datasource

import android.content.Context
import android.util.Log
import com.example.mqttkotlinsample.MQTT_SERVER_URI
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.domain.repository.Repository
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
	private val context: Context,
	private var mqttAndroidClient: MqttAndroidClient): DataSource {

//	private lateinit var mqttClient: MqttAndroidClient

	private val defaultCbConnect = object : IMqttActionListener {
		override fun onSuccess(asyncActionToken: IMqttToken?) {
			Log.d(this.javaClass.name, "(Default) Connection success")
			//repository.sendMessage("HELLO")
		}

		override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
			Log.d(this.javaClass.name, "Connection failure: ${exception.toString()}")
		}
	}
//	private val defaultCbClient = object : MqttCallback {
//		override fun messageArrived(topic: String?, message: MqttMessage?) {
//			Log.d(this.javaClass.name, "Receive message: ${message.toString()} from topic: $topic")
//		}
//
//		override fun connectionLost(cause: Throwable?) {
//			Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
//		}
//
//		override fun deliveryComplete(token: IMqttDeliveryToken?) {
//			Log.d(this.javaClass.name, "Delivery completed")
//		}
//	}
//	private val defaultCbSubscribe = object : IMqttActionListener {
//		override fun onSuccess(asyncActionToken: IMqttToken?) {
//			Log.d(this.javaClass.name, "Subscribed to topic")
//		}
//
//		override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
//			Log.d(this.javaClass.name, "Failed to subscribe topic")
//		}
//	}
//	private val defaultCbUnsubscribe = object : IMqttActionListener {
//		override fun onSuccess(asyncActionToken: IMqttToken?) {
//			Log.d(this.javaClass.name, "Unsubscribed to topic")
//		}
//
//		override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
//			Log.d(this.javaClass.name, "Failed to unsubscribe topic")
//		}
//	}
//	private val defaultCbPublish = object : IMqttActionListener {
//		override fun onSuccess(asyncActionToken: IMqttToken?) {
//			Log.d(this.javaClass.name, "Message published to topic")
//		}
//
//		override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
//			Log.d(this.javaClass.name, "Failed to publish message to topic")
//		}
//	}
//	private val defaultCbDisconnect = object : IMqttActionListener {
//		override fun onSuccess(asyncActionToken: IMqttToken?) {
//			Log.d(this.javaClass.name, "Disconnected")
//		}
//
//		override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
//			Log.d(this.javaClass.name, "Failed to disconnect")
//		}
//	}

	override fun connectToBroker(connectModel: ConnectModel) {
		mqttAndroidClient = MqttAndroidClient(context,connectModel.serverURL,connectModel.username)
		val options = MqttConnectOptions()
		options.userName = connectModel.username
		options.password = connectModel.password.toCharArray()

		mqttAndroidClient.connect(options)
	}

	override fun brokerIsConnected() =
		mqttAndroidClient.isConnected

	override fun disconnectToBroker(): Boolean {
		TODO("Not yet implemented")
	}

	override fun publishMessageToTopic() {
//		mqttAndroidClient.subscribe("topic", 1 ){ topic, message ->
//			// входящие сообщения
//		}
	}

	override fun subscribeToTopic() {
		TODO("Not yet implemented")
	}

	override fun unsubscribeToTopic() {
		TODO("Not yet implemented")
	}
}