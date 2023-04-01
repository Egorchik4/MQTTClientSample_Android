package com.example.mqttkotlinsample.presentation.screens.connectfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mqttkotlinsample.R
import com.example.mqttkotlinsample.databinding.FragmentConnectBinding
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.presentation.state.ConnectState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectFragment : Fragment() {

	private lateinit var binding: FragmentConnectBinding
	private val viewModel: ConnectViewModel by viewModels()

	companion object {

		const val CONNECT_MODEL = "CONNECT_MODEL"

		const val MQTT_SERVER_URL = "tcp://192.168.1.2:1883"
		const val MQTT_CLIENT_ID = ""
		const val MQTT_USERNAME = ""
		const val MQTT_PWD = ""
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentConnectBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setListeners()
		setObserves()
	}

	private fun setListeners() {
		with(binding) {
			buttonPrefill.setOnClickListener {
				edittextServerUrl.setText(MQTT_SERVER_URL)
				edittextClientId.setText(MQTT_CLIENT_ID)
				edittextUsername.setText(MQTT_USERNAME)
				edittextPassword.setText(MQTT_PWD)
			}
			buttonClean.setOnClickListener {
				edittextServerUrl.setText("")
				edittextClientId.setText("")
				edittextUsername.setText("")
				edittextPassword.setText("")
			}

			buttonConnect.setOnClickListener {
				val serverURLFromEditText = edittextServerUrl.text.toString()
				val clientIDFromEditText = edittextClientId.text.toString()
				val usernameFromEditText = edittextUsername.text.toString()
				val pwdFromEditText = edittextPassword.text.toString()

				viewModel.connectToBroker(
					ConnectModel(
						serverURL = serverURLFromEditText,
						clientID = clientIDFromEditText,
						username = usernameFromEditText,
						password = pwdFromEditText
					)
				)
			}
		}
	}

	private fun setObserves() {
		viewModel.state.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: ConnectState) {
		when (state) {
			is ConnectState.Loading   -> {
				renderLoading()
			}

			is ConnectState.Connected -> {
				renderConnected(state)
			}

			is ConnectState.Error     -> {
				renderError()
			}
		}
	}

	private fun renderLoading() {
		with(binding) {
			progressBar.visibility = View.VISIBLE
			buttonConnect.visibility = View.INVISIBLE
		}
	}

	private fun renderConnected(state: ConnectState.Connected) {
		with(binding) {
			progressBar.visibility = View.INVISIBLE
			buttonConnect.visibility = View.VISIBLE
			buttonConnect.setBackgroundColor(resources.getColor(R.color.purple_700))
		}
		val bundle = bundleOf(
			CONNECT_MODEL to ConnectModel(
				serverURL = state.serverURL,
				clientID = state.clientID,
				username = state.username,
				password = state.password
			)
		)
		findNavController().navigate(R.id.action_ConnectFragment_to_devicesListFragment, bundle)
	}

	private fun renderError() {
		with(binding) {
			progressBar.visibility = View.INVISIBLE
			buttonConnect.visibility = View.VISIBLE
			buttonConnect.setBackgroundColor(resources.getColor(R.color.error_color))
		}
	}
}