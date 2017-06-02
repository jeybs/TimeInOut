package com.jb.clockinout.ui.login

import android.content.Context
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.jb.clockinout.CommonConstants
import com.jb.clockinout.data.models.TimeSheet
import com.jb.clockinout.data.models.User

/**
 * Created by 81014102 on 24/05/2017.
 */
class LoginPresenter(context:Context, view: LoginContractor.View) : LoginContractor.Presenter {

    var mContext:Context? = null
    var mView:LoginContractor.View? = null
    var mAuth: FirebaseAuth
    var mDatabase: DatabaseReference? = null

    init {
        this.mContext = context
        this.mView = view

        this.mView!!.setPresenter(this)
        this.mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference(CommonConstants.FIREBASE_REF_TIMESHEET)
    }

    override fun start() {
        //TODO initialize things
    }

    override fun checkCurrentUser() {
        var currentUser: FirebaseUser? = mAuth.currentUser
        if(currentUser != null) {
            mView!!.loginSuccess(currentUser)
        } else {
            mView!!.noCurrentUser()
        }
    }

    override fun createCredentials(username: String?, password: String?) {
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            mAuth.createUserWithEmailAndPassword(username!!, password!!)
                    .addOnCompleteListener { task ->
                        run {
                            if(task.isSuccessful) mView!!.createUserSuccess()
                            else mView!!.createUserfailed()
                        }
                    }


        } else mView!!.showEmptyField()
    }

    override fun validateCredentials(username: String?, password: String?) {
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(username!!, password!!)
                    .addOnCompleteListener { task ->
                        run {
                            if(task.isSuccessful) {
                                mDatabase!!.runTransaction(object: Transaction.Handler {
                                    override fun doTransaction(mutable: MutableData?): Transaction.Result {
                                        val mUser: User = User(mAuth.currentUser!!.uid, mAuth.currentUser?.email!!, ArrayList())
                                        mutable?.child(CommonConstants.CHILD_USER)!!.value = mUser

                                        return Transaction.success(mutable)
                                    }

                                    override fun onComplete(dbError: DatabaseError?, success: Boolean, snapshot: DataSnapshot?) {

                                    }

                                })
                                mView?.loginSuccess(mAuth.currentUser)
                            }
                            else mView?.loginFailed(username, password)
                        }
                    }


        } else mView!!.showEmptyField()
    }

}