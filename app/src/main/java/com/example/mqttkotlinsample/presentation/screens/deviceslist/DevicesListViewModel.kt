package com.example.mqttkotlinsample.presentation.screens.deviceslist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.domain.models.DeviceModel
import com.example.mqttkotlinsample.presentation.state.DeviceListState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

@HiltViewModel
class DevicesListViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	private var mqttAndroidClient: MqttAndroidClient
) : ViewModel(), IMqttMessageListener {

	private val _state = MutableLiveData<DeviceListState>()
	val state: LiveData<DeviceListState> = _state

	private var devicesList: MutableList<DeviceModel> = mutableListOf()
	private var connectModel: ConnectModel? = null

	fun initialization(model: ConnectModel) {
		connectModel = model
		mqttAndroidClient = MqttAndroidClient(context, model.serverURL, model.username)
		mqttAndroidClient.connect(context, object : IMqttActionListener {
			override fun onSuccess(asyncActionToken: IMqttToken?) {}
			override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
				_state.value = DeviceListState.Error
			}

		})
	}

	fun subscribeToTopic(model: DeviceModel) {
		val newDeviceModel: DeviceModel = model.copy(serverURL = connectModel?.serverURL ?: "")
		devicesList.add(newDeviceModel)
		val arrayTopics: Array<String> = getStringTopics(devicesList)
		val topicQOS: IntArray = getIntQos(devicesList)
		val listeners: Array<IMqttMessageListener> = getListeners(devicesList)
		mqttAndroidClient.subscribe(arrayTopics, topicQOS, listeners)

	}

	private fun getStringTopics(devicesList: MutableList<DeviceModel>): Array<String> {
		val topics: MutableList<String> = mutableListOf()
		devicesList.forEach {
			topics.add(it.nameOfTopic)
		}
		return topics.toTypedArray()
	}

	private fun getIntQos(devicesList: MutableList<DeviceModel>): IntArray {
		val qos: MutableList<Int> = mutableListOf()
		devicesList.forEach {
			qos.add(it.qos)
		}
		return qos.toIntArray()
	}

	private fun getListeners(devicesList: MutableList<DeviceModel>): Array<IMqttMessageListener> {
		val listeners: MutableList<IMqttMessageListener> = mutableListOf()
		devicesList.forEach { _ ->
			listeners.add(this)
		}
		return listeners.toTypedArray()
	}

	override fun messageArrived(topic: String?, message: MqttMessage?) {
		viewModelScope.launch {
			refreshList(topic.toString(), message.toString())
		}
	}

	private fun refreshList(topic: String, message: String) {
		if (topic.isNotEmpty()) {
			_state.value = DeviceListState.Loading
			val index = devicesList.indexOfFirst { it.nameOfTopic == topic }
			val deviceModel = devicesList[index].copy(value = message.toFloatOrNull())
			devicesList = ArrayList(devicesList)
			devicesList[index] = deviceModel
			_state.value = DeviceListState.Content(devicesList)
		} else {
			_state.value = DeviceListState.Error
		}
	}
}