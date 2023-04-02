package com.example.mqttkotlinsample.presentation.screens.deviceslist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.mqttkotlinsample.R
import com.example.mqttkotlinsample.databinding.DevicesListCustomDialogBinding
import com.example.mqttkotlinsample.databinding.FragmentDevicesListBinding
import com.example.mqttkotlinsample.domain.models.ConnectModel
import com.example.mqttkotlinsample.domain.models.DeviceModel
import com.example.mqttkotlinsample.presentation.screens.deviceslist.adapter.DevicesListOnClickListener
import com.example.mqttkotlinsample.presentation.screens.deviceslist.adapter.DevicesListRecyclerAdapter
import com.example.mqttkotlinsample.presentation.state.DeviceListState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DevicesListFragment : Fragment(), DevicesListOnClickListener {

	private lateinit var binding: FragmentDevicesListBinding
	private val viewModel: DevicesListViewModel by viewModels()
	private lateinit var adapter: DevicesListRecyclerAdapter

	companion object {

		const val CONNECT_MODEL = "CONNECT_MODEL"
		const val DEVICE_MODEL = "DEVICE_MODEL"

		const val SETUP_DEVICE = "Setup Device"
		const val ADD = "Add"
		const val VALUE_IS_EMPTY = "Value is empty"
		const val VALUE_IS_INVALID = "Value is invalid"

	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentDevicesListBinding.inflate(inflater)
		val model: ConnectModel = arguments?.getSerializable(CONNECT_MODEL) as ConnectModel
		viewModel.initialization(model)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setAdapters()
		setListeners()
		setObserves()
	}

	private fun setAdapters() {
		adapter = DevicesListRecyclerAdapter(this)
		binding.recyclerAdapter.adapter = adapter
		val itemAnimator = binding.recyclerAdapter.itemAnimator
		if (itemAnimator is DefaultItemAnimator) {
			itemAnimator.supportsChangeAnimations = false
		}
	}

	private fun setListeners() {
		with(binding) {
			buttonAddDevice.setOnClickListener {
				customAlertDialog {
					viewModel.subscribeToTopic(it)
				}
			}

		}
	}

	private fun setObserves() {
		viewModel.state.observe(viewLifecycleOwner, ::handleState)
	}

	private fun customAlertDialog(onClick: (DeviceModel) -> Unit) {
		val dialogBinding = DevicesListCustomDialogBinding.inflate(layoutInflater)
		val dialog = AlertDialog.Builder(requireContext())
			.setTitle(SETUP_DEVICE)
			.setView(dialogBinding.root)
			.setPositiveButton(ADD, null)
			.create()
		dialog.setOnShowListener {
			dialogBinding.editTextDeviceName.requestFocus()

			dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
				val deviceName: String = dialogBinding.editTextDeviceName.text.toString()
				val deviceTopic: String = dialogBinding.editTextTopic.text.toString()
				val deviceQos: Int? = dialogBinding.editTextQos.text.toString().toIntOrNull()
				val deviceTypeOfValue: String = dialogBinding.editTextTypeOfValue.text.toString()
				val topLimit: Int? = dialogBinding.editTopLimit.text.toString().toIntOrNull()
				val bottomLimit: Int? = dialogBinding.editBottomLimit.text.toString().toIntOrNull()
				if (deviceName.isBlank()) {
					dialogBinding.editTextDeviceName.error = VALUE_IS_EMPTY
					return@setOnClickListener
				} else if (deviceTopic.isBlank()) {
					dialogBinding.editTextTopic.error = VALUE_IS_EMPTY
					return@setOnClickListener
				} else if (deviceQos == null || deviceQos >= 2) {
					dialogBinding.editTextQos.error = VALUE_IS_INVALID
					return@setOnClickListener
				} else if (deviceTypeOfValue.isBlank()) {
					dialogBinding.editTopLimit.error = VALUE_IS_EMPTY
					return@setOnClickListener
				} else if (topLimit == null) {
					dialogBinding.editTopLimit.error = VALUE_IS_INVALID
					return@setOnClickListener
				} else if (bottomLimit == null) {
					dialogBinding.editBottomLimit.error = VALUE_IS_INVALID
					return@setOnClickListener
				}
				onClick.invoke(
					DeviceModel(
						name = deviceName,
						nameOfTopic = deviceTopic,
						qos = deviceQos,
						topLimit = topLimit,
						bottomLimit = bottomLimit,
						typeOfValue = deviceTypeOfValue
					)
				)
				dialog.dismiss()
			}
		}
		dialog.show()
	}

	private fun handleState(state: DeviceListState) {
		when (state) {
			is DeviceListState.Loading -> {}

			is DeviceListState.Content -> {
				renderContentState(state)
			}

			is DeviceListState.Error   -> {
				renderErrorState()
			}
		}

	}

	private fun renderContentState(state: DeviceListState.Content) {
		adapter.devices = state.listOfDevice
	}

	private fun renderErrorState() {
		findNavController().popBackStack()
	}

	override fun onClick(model: DeviceModel) {
		val bundle = bundleOf(
			DEVICE_MODEL to model
		)
		findNavController().navigate(R.id.action_devicesListFragment_to_deviceInfoFragment, bundle)
	}
}