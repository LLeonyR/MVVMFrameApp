package com.leonyr.mvvm.frag

import android.content.Intent
import android.support.v4.app.Fragment
import com.leonyr.lib.utils.LogUtil
import com.leonyr.mvvm.R
//import com.leonyr.mvvm.act.Common

open class IFragment : Fragment() {

    /*fun openFragment(type: Common.Type) {
        openFragment(type.newFragment(), type.tag)
    }*/

    fun openFragment(fragment: Fragment, tag: String) {

        var targetParent: Fragment = this
        while (targetParent.id != R.id.fragment_container) {
            targetParent = targetParent.parentFragment!!
            LogUtil.e(javaClass.simpleName, "id: " + targetParent.id)
        }

        val fm = targetParent.fragmentManager
        fm!!.beginTransaction()
            .hide(targetParent)
            .add(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    protected fun setResult(result_OK: Int, intent: Intent) {
        val targetFragment = targetFragment ?: return
        targetFragment.onActivityResult(targetRequestCode, result_OK, intent)
        fragmentManager!!.popBackStack()
    }

}
