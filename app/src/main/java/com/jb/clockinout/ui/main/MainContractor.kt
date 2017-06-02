package com.jb.clockinout.ui.main

import com.jb.clockinout.BasePresenter
import com.jb.clockinout.BaseView

interface MainContractor {

    interface View : BaseView<Presenter> {

        fun clockInSuccess()

        fun clockInFailed()

        fun clockOutSuccess()

        fun clockOutFailed()

    }


    interface Presenter : BasePresenter {

        fun clockIn(clockInData: String)

        fun clockOut(clockOutData: String)

        fun logout()
    }
}