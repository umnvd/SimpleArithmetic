package com.demo.simplearithmetic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.demo.simplearithmetic.presentation.HomeFragment
import com.demo.simplearithmetic.presentation.Navigator

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(MAIN_CONTAINER_ID, HomeFragment())
                .commit()
        }
    }

    override fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment is HomeFragment) {
            for (i in 0..supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
        } else {
            transaction.addToBackStack(fragment::class.java.name)
        }
        transaction
            .replace(MAIN_CONTAINER_ID, fragment)
            .commit()
    }

    companion object {
        private const val MAIN_CONTAINER_ID = R.id.main_container
    }
}