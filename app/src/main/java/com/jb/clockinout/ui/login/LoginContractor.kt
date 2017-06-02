package com.jb.clockinout.ui.login

import com.google.firebase.auth.FirebaseUser
import com.jb.clockinout.BasePresenter
import com.jb.clockinout.BaseView

/**
 * Created by 81014102 on 24/05/2017.
 */
interface LoginContractor {

    interface View : BaseView<Presenter> {

        fun createUserSuccess()

        fun createUserfailed()

        fun showEmptyField()

        fun loginFailed(username:String, password:String)

        fun noCurrentUser()

        fun loginSuccess(user: FirebaseUser?)

    }

    interface Presenter : BasePresenter {

        fun checkCurrentUser()

        fun validateCredentials(username:String?, password:String?)

        fun createCredentials(username: String?, password: String?)
    }
}