package com.example.pomodorotimekeeper.resourcelocal

data class TotalTime(
    var pomodoroTime: Int = 30*60,
    var breakTime: Int = 5*60
)
