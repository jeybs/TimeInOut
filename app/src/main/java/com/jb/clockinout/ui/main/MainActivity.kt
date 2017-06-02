package com.jb.clockinout.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jb.clockinout.BaseActivity
import com.jb.clockinout.R
import com.jb.clockinout.utils.DateTimeUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_base_toolbar.*

class MainActivity : BaseActivity(), MainContractor.View {

    var mPresenter: MainPresenter? = null

    companion object {

        /**
         * Provide intent to start this activity
         */
        fun getInstance(context: Context): Intent =
                Intent(context, MainActivity::class.java).apply {
        }

    }

    fun clockInClick(view: View): Unit {
        val clockInDateTime: String = DateTimeUtils.getCurrentDatetime(activity!!.applicationContext)
        textClockIn.text = clockInDateTime
        //view.visibility = View.GONE
        //btnClockOut.visibility = View.VISIBLE

        //showProgressDialog(this.getString(R.string.text_dialog_save_timeheet))
        mPresenter?.clockIn(clockInDateTime)
    }

    fun clockOutClick(view: View): Unit {
        val clockOutDatetime: String = DateTimeUtils.getCurrentDatetime(activity!!.applicationContext)
        textClockOut.text = clockOutDatetime

        view.visibility = View.GONE
        btnClockIn.visibility = View.VISIBLE

        showProgressDialog(this.getString(R.string.text_dialog_save_timeheet))
        mPresenter?.clockOut(clockOutDatetime)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this

        mPresenter = MainPresenter(activity!!.applicationContext, this)

        textTitle.text = activity!!.getString(R.string.text_title_main)
    }

    override fun setResourceLayout(): Int {
        return R.layout.activity_main
    }

    override fun setPresenter(presenter: MainContractor.Presenter) {
        //Nothing todo
    }

    override fun clockInSuccess() {
        hideProgressDialog()
        Toast.makeText(activity, this.getString(R.string.text_dialog_save_timesheet_success), Toast.LENGTH_SHORT).show()
    }

    override fun clockInFailed() {
        hideProgressDialog()
        Toast.makeText(activity, this.getString(R.string.text_dialog_save_timesheet_failed), Toast.LENGTH_SHORT).show()
    }

    override fun clockOutSuccess() {
        hideProgressDialog()
        Toast.makeText(activity, this.getString(R.string.text_dialog_save_timesheet_success), Toast.LENGTH_SHORT).show()
    }

    override fun clockOutFailed() {
        hideProgressDialog()
        Toast.makeText(activity, this.getString(R.string.text_dialog_save_timesheet_failed), Toast.LENGTH_SHORT).show()
    }
}
