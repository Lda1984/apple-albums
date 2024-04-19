package com.saltpay.music.top

import com.saltpay.music.top.utils.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProviderImpl : DispatcherProvider {
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(testScheduler)

    override val main = testDispatcher
    override val io = testDispatcher
    override val default = testDispatcher

    fun getTestScheduler(): TestCoroutineScheduler = testScheduler
}