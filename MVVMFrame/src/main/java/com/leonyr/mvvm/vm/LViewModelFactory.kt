package com.leonyr.mvvm.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.InvocationTargetException


class LViewModelFactory(private val context: Context?) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (LViewModel::class.java.isAssignableFrom(modelClass)) {

            try {
                return modelClass.getConstructor(Context::class.java).newInstance(context)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }

        }
        return super.create(modelClass)
    }


    companion object {

        private var instance: LViewModelFactory? = null

        /**
         * Retrieve a singleton instance of AndroidViewModelFactory.
         *
         * @param ctx an application to pass in [LViewModel]
         * @return A valid [ViewModelProvider.AndroidViewModelFactory]
         */
        fun getInstance(ctx: Context): LViewModelFactory {
            if (null == instance) {
                instance = LViewModelFactory(ctx)
            }
            return instance!!
        }
    }
}
