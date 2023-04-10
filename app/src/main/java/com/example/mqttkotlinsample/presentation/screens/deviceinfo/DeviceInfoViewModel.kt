package com.example.mqttkotlinsample.presentation.screens.deviceinfo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mqttkotlinsample.domain.models.DeviceModel
import com.example.mqttkotlinsample.presentation.state.DeviceState
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
class DeviceInfoViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	private var mqttAndroidClient: MqttAndroidClient
) : ViewModel(), IMqttMessageListener {

	private val _state = MutableLiveData<DeviceState>()
	val state: LiveData<DeviceState> = _state

	fun initialization(deviceModel: DeviceModel) {
		mqttAndroidClient = MqttAndroidClient(context, deviceModel.serverURL, deviceModel.clientID)
		mqttAndroidClient.connect(context, object : IMqttActionListener {
			override fun onSuccess(asyncActionToken: IMqttToken?) {
				subscribeToTopic(deviceModel)
			}

			override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
				_state.value = DeviceState.Error
			}
		})
	}

	private fun subscribeToTopic(model: DeviceModel) {
		mqttAndroidClient.subscribe(model.nameOfTopic, model.qos, this)
	}

	override fun messageArrived(topic: String?, message: MqttMessage?) {
		viewModelScope.launch {
			refreshValue(message.toString().toFloatOrNull())
		}
	}

	private fun refreshValue(value: Float?) {
		if (value != null) {
			val content = _state.value as? DeviceState.Content ?: DeviceState.Content(mutableListOf())
			val newList: MutableList<Float> = content.list
			newList.add(value)
			_state.value = DeviceState.Content(newList)
		}
	}
}