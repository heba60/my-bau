package com.uni.bau.utilities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure

import com.uni.bau.R
import com.uni.bau.models.OnClickSucess

object ProgressUtil {


    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog
    private lateinit var pDialog: ProgressBar

  public  fun showLoading(ctx: Context) {
        dialogBuilder = AlertDialog.Builder(ctx)
        pDialog = ProgressBar(ctx)
        pDialog.getIndeterminateDrawable()
            .setColorFilter(Color.parseColor("#00B555"), android.graphics.PorterDuff.Mode.SRC_IN);
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(pDialog)
        alertDialog = dialogBuilder.create()
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
        alertDialog.show()

    }

     public fun showWarningPopupActionCallBack(
         ctx: Activity, contentMessage: String,
         onClickSucess: OnClickSucess

     ) {
         AwesomeWarningDialog(ctx)
             .setTitle("نجاح")
             .setMessage(contentMessage)
             .setColoredCircle(R.color.dialogSuccessBackgroundColor)
             .setDialogIconAndColor(R.drawable.ic_success, R.color.white)
             .setCancelable(true)
             .setButtonText(ctx.getString(R.string.confirm))
             .setButtonBackgroundColor(R.color.dialogSuccessBackgroundColor)
             .setButtonText(ctx.getString(R.string.confirm))
             .setWarningButtonClick(object : Closure {
                 override fun exec() {
                     onClickSucess.onSccuessClick()
                 }
             })
             .show()
     }

    public fun hideLoading() {
        try {
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            }
            alertDialog.dismiss()

        } catch (e: UninitializedPropertyAccessException) {
        }
    }

    public  fun showWarningPopup(ctx: Context, contentMessage: String) {
        AwesomeWarningDialog(ctx)
            .setTitle("تحذير")
            .setMessage(contentMessage)
            .setColoredCircle(R.color.dialogWarningBackgroundColor)
            .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
            .setCancelable(true)
            .setButtonText(ctx.getString(R.string.confirm))
            .setButtonBackgroundColor(R.color.dialogWarningBackgroundColor)
            .setButtonText(ctx.getString(R.string.confirm))
            .setWarningButtonClick(object : Closure {
                override fun exec() {

                }
            })
            .show()
    }


    public  fun showSuccessPopup(ctx: Context, contentMessage: String) {
        AwesomeWarningDialog(ctx)
            .setTitle("نجاح")
            .setMessage(contentMessage)
            .setColoredCircle(R.color.dialogSuccessBackgroundColor)
            .setDialogIconAndColor(R.drawable.ic_success, R.color.white)
            .setCancelable(true)
            .setButtonText(ctx.getString(R.string.confirm))
            .setButtonBackgroundColor(R.color.dialogSuccessBackgroundColor)
            .setButtonText(ctx.getString(R.string.confirm))
            .setWarningButtonClick(object : Closure {
                override fun exec() {

                }
            })
            .show()
    }


}