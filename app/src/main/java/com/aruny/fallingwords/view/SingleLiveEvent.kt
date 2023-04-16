package com.aruny.fallingwords.view

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(
            owner
        ) {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        }
    }

    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}
