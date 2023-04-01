package com.example.mqttkotlinsample.presentation.screens.connectfragment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.presentation.state.ConnectState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	private var mqttAndroidClient: MqttAndroidClient
) : ViewModel() {

	private val _state = MutableLiveData<ConnectState>()
	val state: LiveData<ConnectState> = _state

	fun connectToBroker(connectModel: ConnectModel) {
		_state.value = ConnectState.Loading
		viewModelScope.launch {
			mqttAndroidClient = MqttAndroidClient(context, connectModel.serverURL, connectModel.username)
			val options = MqttConnectOptions()
			options.userName = connectModel.username
			options.password = connectModel.password.toCharArray()

			mqttAndroidClient.connect(options, object : IMqttActionListener {
				override fun onSuccess(asyncActionToken: IMqttToken?) {
					_state.value = ConnectState.Connected(
						connectModel.serverURL,
						connectModel.clientID,
						connectModel.username,
						connectModel.password
					)
				}

				override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
					_state.value = ConnectState.Error
				}

			})
		}
	}
}