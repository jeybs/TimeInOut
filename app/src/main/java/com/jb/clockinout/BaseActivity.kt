package com.jb.clockinout

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by 81014102 on 25/05/2017.
 */

abstract class BaseActivity : AppCompatActivity() {

    var activity: Activity? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(state:Bundle?) {
        super.onCreate(state)
        setContentView(setResourceLayout())
    }

    abstract fun setResourceLayout(): Int

    fun showProgressDialog(message: String): Unit {
        if(progressDialog == null) progressDialog = ProgressDialog(activity)

        progressDialog!!.setMessage(message)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    fun hideProgressDialog(): Unit {
        if(progressDialog == null) return

        progressDialog!!.dismiss()
    }
}