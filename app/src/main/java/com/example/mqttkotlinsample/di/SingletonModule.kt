package com.example.mqttkotlinsample.di

import android.content.Context
import com.example.mqttkotlinsample.data.datasource.DataSource
import com.example.mqttkotlinsample.data.datasource.DataSourceImpl
import com.example.mqttkotlinsample.data.repository.RepositoryImpl
import com.example.mqttkotlinsample.domain.repository.Repository
import com.example.mqttkotlinsample.domain.usecase.ConnectToBrokerUseCase
import com.example.mqttkotlinsample.domain.usecase.DisconnectToBrokerUseCase
import com.example.mqttkotlinsample.domain.usecase.PublishMessageToTopicUseCase
import com.example.mqttkotlinsample.domain.usecase.SubscribeToTopicUseCase
import com.example.mqttkotlinsample.domain.usecase.UnsubscribeToTopicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun provideDataSource(@ApplicationContext context: Context): DataSource {
		return DataSourceImpl(context)
	}

	@Provides
	@Singleton
	fun provideRepository(dataSource: DataSource): Repository {
		return RepositoryImpl(dataSource)
	}

	@Provides
	@Singleton
	fun provideConnectToBrokerUseCase(repository: Repository): ConnectToBrokerUseCase {
		return ConnectToBrokerUseCase(repository)
	}
	@Provides
	@Singleton
	fun provideDisconnectToBrokerUseCase(repository: Repository): DisconnectToBrokerUseCase {
		return DisconnectToBrokerUseCase(repository)
	}
	@Provides
	@Singleton
	fun providePublishMessageToTopicUseCase(repository: Repository): PublishMessageToTopicUseCase {
		return PublishMessageToTopicUseCase(repository)
	}
	@Provides
	@Singleton
	fun provideSubscribeToTopicUseCase(repository: Repository): SubscribeToTopicUseCase {
		return SubscribeToTopicUseCase(repository)
	}
	@Provides
	@Singleton
	fun provideUnsubscribeToTopicUseCase(repository: Repository): UnsubscribeToTopicUseCase {
		return UnsubscribeToTopicUseCase(repository)
	}

}