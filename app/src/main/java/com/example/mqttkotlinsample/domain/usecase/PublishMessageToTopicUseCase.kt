package com.example.mqttkotlinsample.domain.usecase

import com.example.mqttkotlinsample.domain.repository.Repository
import javax.inject.Inject

class PublishMessageToTopicUseCase @Inject constructor(private val repository: Repository) {

	fun publishMessageToTopic(){
		repository.publishMessageToTopic()
	}
}