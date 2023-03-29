package com.example.mqttkotlinsample.domain.usecase

import com.example.mqttkotlinsample.domain.repository.Repository
import javax.inject.Inject

class UnsubscribeToTopicUseCase @Inject constructor(private val repository: Repository) {

	fun unsubscribeToTopic(){
		repository.unsubscribeToTopic()
	}
}