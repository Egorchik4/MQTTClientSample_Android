package com.example.mqttkotlinsample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.eclipse.paho.android.service.MqttAndroidClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun provideMqttAndroidClient(@ApplicationContext context: Context): MqttAndroidClient {
		return MqttAndroidClient(context, null, null)
	}
}