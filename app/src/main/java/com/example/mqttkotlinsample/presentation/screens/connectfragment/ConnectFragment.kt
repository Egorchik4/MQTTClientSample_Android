package com.example.mqttkotlinsample.presentation.screens.connectfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.mqttkotlinsample.MQTT_CLIENT_ID
import com.example.mqttkotlinsample.MQTT_CLIENT_ID_KEY
import com.example.mqttkotlinsample.MQTT_PWD
import com.example.mqttkotlinsample.MQTT_PWD_KEY
import com.example.mqttkotlinsample.MQTT_SERVER_URI
import com.example.mqttkotlinsample.MQTT_SERVER_URI_KEY
import com.example.mqttkotlinsample.MQTT_USERNAME
import com.example.mqttkotlinsample.MQTT_USERNAME_KEY
import com.example.mqttkotlinsample.R
import com.example.mqttkotlinsample.databinding.FragmentConnectBinding

class ConnectFragment : Fragment() {
    lateinit var binding: FragmentConnectBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentConnectBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPrefill.setOnClickListener {
            binding.edittextServerUri.setText(MQTT_SERVER_URI)
            binding.edittextClientId.setText(MQTT_CLIENT_ID)
            binding.edittextUsername.setText(MQTT_USERNAME)
            binding.edittextPassword.setText(MQTT_PWD)
        }

        binding.buttonClean.setOnClickListener {
            binding.edittextServerUri.setText("")
            binding.edittextClientId.setText("")
            binding.edittextUsername.setText("")
            binding.edittextPassword.setText("")
        }

        binding.buttonConnect.setOnClickListener {
            val serverURIFromEditText   = binding.edittextServerUri.text.toString()
            val clientIDFromEditText    = binding.edittextClientId.text.toString()
            val usernameFromEditText    = binding.edittextUsername.text.toString()
            val pwdFromEditText         = binding.edittextPassword.text.toString()

            val mqttCredentialsBundle = bundleOf(
				MQTT_SERVER_URI_KEY    to serverURIFromEditText,
				MQTT_CLIENT_ID_KEY     to clientIDFromEditText,
				MQTT_USERNAME_KEY      to usernameFromEditText,
				MQTT_PWD_KEY           to pwdFromEditText)
            navigateToClientFragment(mqttCredentialsBundle)
        }
    }

    private fun navigateToClientFragment(bundle: Bundle){
        findNavController().navigate(R.id.action_ConnectFragment_to_ClientFragment, bundle)
    }
}