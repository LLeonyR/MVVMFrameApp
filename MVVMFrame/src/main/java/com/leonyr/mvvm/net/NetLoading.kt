package com.leonyr.mvvm.net

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.Nullable
import com.leonyr.mvvm.R

class NetLoading {

    lateinit var mDialog: Dialog
    protected lateinit var mCtx: Context
    val animation: Animation
    lateinit var icon: ImageView
    var cancel: Boolean = true

    constructor(ctx: Context, cancel: Boolean = false) {
        val view = getDefaultView(ctx)
        mCtx = ctx
        this.cancel = cancel
        mDialog = createDialog(ctx, view)
        animation = AnimationUtils.loadAnimation(ctx, R.anim.net_dialog_loading)
    }

    /**
     * 子类重写该方法，即可创建样式相同的对话框。
     *
     * @param context
     * @return
     */
    protected fun getDefaultView(context: Context): View {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.net_dialog, null)

        icon = v.findViewById(R.id.icon_loading)
        return v
    }

    private fun createDialog(context: Context?, v: View): Dialog {
        val dialog = Dialog(context, R.style.net_dialog_default)
        dialog.setCancelable(cancel)
        dialog.setContentView(v)
        val dialogWindow = dialog.window
        val lp = dialogWindow.attributes
        //        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        if (setWidth() > 0) {
            lp.width = setWidth()
            dialogWindow.attributes = lp
        }
        return dialog
    }


    fun setCanceledOnTouchOutside(cancel: Boolean) {
        mDialog.setCanceledOnTouchOutside(cancel)
    }

    fun setOnCancelListener(@Nullable listener: DialogInterface.OnCancelListener?) {
        mDialog.setOnCancelListener(listener)
    }

    fun setCancelable(cancelable: Boolean) {
        mDialog.setCancelable(cancelable)
    }

    fun isShowing(): Boolean {
        return mDialog.isShowing
    }

    protected fun setWidth(): Int {
        return 0
    }


    fun show() {
        mDialog.show()
        icon.startAnimation(animation)
    }

    fun dismiss() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

}