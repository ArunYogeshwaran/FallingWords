package com.aruny.fallingwords.view.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Test class for [SingleLiveEvent]
 */
@ExperimentalCoroutinesApi
class SingleLiveEventTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `single live event should update observer only once`() = runTest {
        val liveData = SingleLiveEvent<Int>()
        val observer = MyObserver()
        val lifeCycleOwner = MyLifeCycleOwner()
        liveData.observe(lifeCycleOwner, observer)
        liveData.value = 10
        Assert.assertEquals(10, observer.myValue)
        lifeCycleOwner.destroy()
        liveData.removeObserver(observer)
        lifeCycleOwner.resume()
        liveData.observe(lifeCycleOwner, observer)
        Assert.assertEquals(10, observer.myValue)
    }

    @Test
    fun `single live event should update observer`() {
        val liveData = SingleLiveEvent<Int>()
        val observer = MyObserver()
        val lifeCycleOwner = MyLifeCycleOwner()
        liveData.observe(lifeCycleOwner, observer)
        liveData.value = 10
        Assert.assertEquals(10, observer.myValue)
        liveData.value = 11
        Assert.assertEquals(21, observer.myValue)
    }

    private class MyLifeCycleOwner : LifecycleOwner {
        private val lifecycle = LifecycleRegistry(this)

        init {
            resume()
        }

        fun resume() = lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun destroy() = lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

        override fun getLifecycle() = lifecycle
    }

    private class MyObserver : Observer<Int?> {
        var myValue: Int = 0

        override fun onChanged(t: Int?) {
            if (t != null) {
                myValue += t
            }
        }
    }
}