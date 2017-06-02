package com.jb.clockinout.ui.main

import android.content.Context
import android.text.TextUtils
import android.text.format.Time
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.jb.clockinout.CommonConstants
import com.jb.clockinout.data.models.TimeSheet
import com.jb.clockinout.data.models.User
import com.jb.clockinout.utils.DateTimeUtils

class MainPresenter(context: Context, view: MainContractor.View) : MainContractor.Presenter {

    val TAG: String = javaClass.simpleName

    var mContext: Context? = null
    var mView: MainContractor.View? = null
    var mAuth: FirebaseAuth? = null
    var mDatabase: DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null

    init {
        mContext = context
        mView = view

        mAuth = FirebaseAuth.getInstance()
        mCurrentUser = mAuth?.currentUser
        mDatabase = FirebaseDatabase.getInstance().getReference(CommonConstants.FIREBASE_REF_TIMESHEET)

        mView?.setPresenter(this)

    }

    override fun start() {
        //TODO Initialize things
    }

    override fun clockIn(clockInData: String) {
        if(TextUtils.isEmpty(clockInData)) {
            mView?.clockInFailed()
            return
        }

        val dateToday: String = DateTimeUtils.getDateToday()
        val timeSheet: TimeSheet = TimeSheet(
                DateTimeUtils.convertDateToUnix(dateToday),
                clockInData,
                null)
        val timeSheeList: MutableCollection<TimeSheet> = ArrayList()
        timeSheeList.add(timeSheet)

        mDatabase!!.runTransaction(object: Transaction.Handler {

            override fun doTransaction(mutable: MutableData?): Transaction.Result {
                mutable?.child(CommonConstants.CHILD_USER)
                        ?.child(CommonConstants.FIREBASE_REF_TIMESHEET)
                        ?.child(CommonConstants.CHILD_TIME_RECORD + "_" + DateTimeUtils.convertDateToUnix(dateToday))
                        ?. value = timeSheet
                return Transaction.success(mutable)
            }

            override fun onComplete(databaseError: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {
                Log.d(TAG, databaseError.toString())
            }
        })
    }

    override fun clockOut(clockOutData: String) {
        if(TextUtils.isEmpty(clockOutData)) {
            mView?.clockOutFailed()
            return
        }
    }

    override fun logout() {

    }
}