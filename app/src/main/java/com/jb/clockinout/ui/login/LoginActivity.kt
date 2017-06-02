package com.jb.clockinout.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jb.clockinout.BaseActivity
import com.jb.clockinout.R
import com.jb.clockinout.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_toolbar_title.*


class LoginActivity : BaseActivity(), LoginContractor.View {

    private var mPresenter:LoginPresenter? = null

    override fun onCreate(state : Bundle?) {
        super.onCreate(state)

        activity = this

        mPresenter = LoginPresenter(applicationContext, this)

        btnLogin.setOnClickListener {
            var username:String? = editUsername.text.toString()
            var password:String? = editPassword.text.toString()

            showProgressDialog(this.getString(R.string.text_dialog_sign_in))
            mPresenter?.validateCredentials(username, password)
        }

        btnCreateAccount.setOnClickListener {
            var username:String? = editUsername.text.toString()
            var password:String? = editPassword.text.toString()

            showProgressDialog(this.getString(R.string.button_create_account))
            mPresenter?.createCredentials(username, password)
        }

        textTitleOnly.text = activity!!.getString(R.string.button_login)
    }

    override fun setResourceLayout(): Int {
        return R.layout.activity_login
    }

    override fun onStart() {
        super.onStart()

        // Check if current user is already signed-in
        mPresenter!!.checkCurrentUser()
    }


    override fun setPresenter(presenter: LoginContractor.Presenter) {
        //TODO Nothing todo
    }

    override fun createUserSuccess() {
        hideProgressDialog()
        Toast.makeText(activity, "Creating account success! Kindly Log in again.", Toast.LENGTH_SHORT).show()
    }

    override fun createUserfailed() {
        hideProgressDialog()
        Toast.makeText(activity, "Creating account Failed! Please try again later.", Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyField() {
        hideProgressDialog()
        Toast.makeText(activity, "Please enter username & password", Toast.LENGTH_SHORT).show()
    }

    override fun loginFailed(username: String, password: String) {
        hideProgressDialog()
        Toast.makeText(activity, "Invalid email and password", Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccess(user: FirebaseUser?) {
        hideProgressDialog()
        Toast.makeText(activity, "Welcome " + user?.email + "!", Toast.LENGTH_SHORT).show()
        startActivity(MainActivity.getInstance(activity!!.applicationContext))
    }

    override fun noCurrentUser() {

    }

}