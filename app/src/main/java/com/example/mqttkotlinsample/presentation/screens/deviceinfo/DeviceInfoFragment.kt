package com.example.mqttkotlinsample.presentation.screens.deviceinfo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.example.mqttkotlinsample.R
import com.example.mqttkotlinsample.databinding.FragmentDeviceInfoBinding
import com.example.mqttkotlinsample.domain.models.DeviceModel
import com.example.mqttkotlinsample.presentation.state.DeviceState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceInfoFragment : Fragment() {

	private lateinit var binding: FragmentDeviceInfoBinding
	private val viewModel: DeviceInfoViewModel by viewModels()

	companion object {

		const val DEVICE_MODEL = "DEVICE_MODEL"
		const val VALUE = ""
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentDeviceInfoBinding.inflate(inflater)
		val model: DeviceModel = arguments?.getSerializable(DEVICE_MODEL) as DeviceModel
		viewModel.initialization(model)
		with(binding) {
			graph.setTitle(model.typeOfValue)
			textTopLimit.text = model.topLimit.toString()
			textBottomLimit.text = model.bottomLimit.toString()
			textDeviceName.text = model.name
			textDeviceTopic.text = model.nameOfTopic
			textQos.text = model.qos.toString()
		}
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setListeners()
		setObserves()
	}

	private fun setListeners() {

	}

	private fun setObserves() {
		viewModel.state.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: DeviceState) {
		when (state) {
			is DeviceState.Loading -> {}

			is DeviceState.Content -> {
				renderContentState(state)
			}

			is DeviceState.Error   -> {}
		}

	}

	private fun renderContentState(state: DeviceState.Content) {
		with(binding) {
			graph.clear()
			val xySeries = SimpleXYSeries(state.list, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, VALUE)
			graph.addSeries(xySeries, LineAndPointFormatter(Color.RED, Color.BLACK, null, null))
			graph.invalidate()
			textValue.text = state.list.last().toString()
			val valueFloat = state.list.last()
			val valueTopLimit = textTopLimit.text.toString().toFloat()
			val valueBottomLimit = textBottomLimit.text.toString().toFloat()
			if (valueFloat in valueBottomLimit..valueTopLimit) {
				textValue.setTextColor(textValue.resources.getColor(R.color.black))
			} else {
				textValue.setTextColor(textValue.resources.getColor(R.color.error_color))
			}
		}
	}
}