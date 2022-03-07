package com.demo.simplearithmetic

import android.app.Application
import com.demo.simplearithmetic.di.AppComponent

class SimpleArithmeticApp : Application() {

    val appComponent: AppComponent by lazy { AppComponent(this) }
}