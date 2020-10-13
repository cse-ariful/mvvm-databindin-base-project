package com.inverse.core.async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Courotines{
    /**
     * below method is a generic method to execute some work on io thread
     * and after the work finished you need the callback to pass using maing thread
     */
    fun <T:Any> ioThenMain(work: suspend (() -> T?),callback:((T?) -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }
}
