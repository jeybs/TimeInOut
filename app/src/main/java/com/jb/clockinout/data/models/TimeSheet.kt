package com.jb.clockinout.data.models

data class User (
        val uuid: String,
        val email: String,
        val timeSheetList: MutableCollection<TimeSheet>
)

data class TimeSheet (
        val timesheetId: Long,
        val clockInDate: String,
        val clockOutDate: String?
)