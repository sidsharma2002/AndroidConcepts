package com.example.androidconcepts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidconcepts.fragments.problems.problem1.FragProbOneFragment

class MainActivity : AppCompatActivity() {

    enum class CURRENT_BUILD {
        FRAG_PROBLEM_1
    }

    // change this to modify app's state.
    private val currentBuild = CURRENT_BUILD.FRAG_PROBLEM_1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AndroidThreadSpammer().execute()

        if (currentBuild == CURRENT_BUILD.FRAG_PROBLEM_1) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root_fragment_container, FragProbOneFragment())
                .commit()
        }
    }

    override fun onBackPressed() {

//  SOLUTION : manually remove the unintended fragments attached.

//        if (currentBuild == CURRENT_BUILD.FRAG_PROBLEM_1) {
//            val currentFragment = supportFragmentManager.findFragmentById(R.id.root_fragment_container)
//                ?: return

//            Log.d("frag problem", "current frag : " + (currentFragment as FragProbOneFragment).number)

//            this does the main fix, i.e remove currently unintended shown fragment from the fm.

//            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
//        }

        super.onBackPressed() // internally does popBackStackImmediate or finish activity.
    }
}