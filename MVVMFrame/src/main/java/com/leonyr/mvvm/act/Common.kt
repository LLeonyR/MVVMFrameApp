package com.leonyr.mvvm.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.leonyr.lib.utils.LogUtil
import com.leonyr.lib.utils.StatusBarUtil
import com.leonyr.mvvm.R
import com.leonyr.mvvm.databinding.CommonBinding
import com.leonyr.mvvm.frag.AbBindFragment
import com.leonyr.mvvm.vm.LViewModel
import java.lang.ref.WeakReference


class Common : AbBindActivity<LViewModel, CommonBinding>() {

    override val layoutResId: Int
        get() = R.layout.common

    protected val fragList: MutableList<WeakReference<Fragment>> = arrayListOf()

    val activeFragments: List<Fragment>
        get() {
            val ret = arrayListOf<Fragment>()
            fragList.forEach { ref ->
                val f = ref.get()
                if (null != f && f.isVisible) {
                    ret.add(f)
                }
            }
            return ret
        }

    override fun initView(savedInstanceState: Bundle?) {
        initData(savedInstanceState)
        getIntentData(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        getIntentData(intent, true)
    }

    @JvmOverloads
    protected fun getIntentData(intent: Intent, canBack: Boolean = false) {
        val type = intent.getParcelableExtra<Type>(KEY_TYPE)
        if (type == null) {
            LogUtil.e("Common", "fragment type is null.")
            return
        }

        if (fragList.isEmpty()) {
            replaceFragment(type, canBack)
        } else {
            val frag = fragList[fragList.size - 1].get()
            if (frag is DialogFragment) {
                (frag).dismiss()
            }
            replaceFragment(type, canBack)
        }
    }

    protected fun initData(savedInstanceState: Bundle?) {
        StatusBarUtil.setTransparentForWindow(this)
    }

    interface Type : Parcelable {

        @get:NonNull
        val tag: String

        @NonNull
        fun newFragment(): AbBindFragment<LViewModel, ViewDataBinding>
    }

    protected fun replaceFragment(type: Type, canBack: Boolean) {

        val transaction = supportFragmentManager.beginTransaction()

        val fragment = supportFragmentManager.findFragmentByTag(type.tag)
        if (null == fragment) {
            transaction.replace(R.id.fragment_container, type.newFragment(), type.tag)
            if (canBack) {
                transaction.addToBackStack(type.tag)
            }
            transaction.commit()
        } else {
            supportFragmentManager.popBackStack(fragment.tag, 0)
        }

    }


    /**
     * 用户按返回键监听器
     */

    interface OnBackPressedListener {
        fun onBackPressed(): Boolean
    }

    override fun onAttachFragment(fragment: Fragment) {
        fragList.add(WeakReference(fragment))
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager

        //  如果返回栈的数量大于0，那么存在除 ViewPager 中创建 Fragment

        val fragments = activeFragments
        val fragment = fragments[fragments.size - 1]
        if (fragment is OnBackPressedListener) {
            val b = (fragment as OnBackPressedListener).onBackPressed()
            if (b) {
                return
            }else{
                super.onBackPressed()
            }
        }else{
            super.onBackPressed()
        }

    }

    companion object {

        val KEY_TYPE = "TYPE"

        fun start(context: Context, type: Type) {
            val intent = Intent(context, Common::class.java)
            intent.putExtra(KEY_TYPE, type)
            context.startActivity(intent)
        }


        /**
         * activity 跳转
         * @param c 当前上下文
         * @param type 目的
         */

        fun startClearTop(c: Context, type: Type) {
            val intent = Intent(c, Common::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(KEY_TYPE, type)
            c.startActivity(intent)
        }

        fun startForResult(fragment: Fragment, type: Type, requestCode: Int) {
            val intent = Intent(fragment.getContext(), Common::class.java)
            intent.putExtra(KEY_TYPE, type)
            fragment.startActivityForResult(intent, requestCode)
        }

        fun startForResult(c: FragmentActivity, type: Type, requestCode: Int) {
            val intent = Intent(c, Common::class.java)
            intent.putExtra(KEY_TYPE, type)
            c.startActivityForResult(intent, requestCode)
        }
    }
}
