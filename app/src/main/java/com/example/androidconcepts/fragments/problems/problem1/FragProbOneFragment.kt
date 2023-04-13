package com.example.androidconcepts.fragments.problems.problem1

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidconcepts.R
import com.google.android.material.button.MaterialButton

class FragProbOneFragment : Fragment() {

    private lateinit var button: MaterialButton

    var number = 0

    fun getInstance(number: Int): FragProbOneFragment {
        return FragProbOneFragment().apply {
            val bundle = Bundle()
            bundle.putInt("number", number)
            this.arguments = bundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        number = arguments?.getInt("number", 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = FrameLayout(requireContext())

        rootView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        rootView.setBackgroundColor(Color.WHITE) // hides the other fragments, due to this I never found this issue.
        rootView.isClickable = true // prevents clicking through the current fragment.

        button = MaterialButton(requireContext())
        button.text = "Fragment Problem : $number"
        val lp = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        lp.gravity = Gravity.TOP
        lp.topMargin = number * 50
        button.layoutParams = lp

        rootView.addView(button)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("frag problem", "frags size : " + requireActivity().supportFragmentManager.fragments.size
                + " backstack count : " + requireActivity().supportFragmentManager.backStackEntryCount)

        button.setOnClickListener {
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.root_fragment_container, getInstance(number + 1))

            if (number % 2 == 1) // only add if current fragment number is odd. eg: frag 3 to 4 is added but 4 to 5 isn't.
                ft.addToBackStack(null)

            ft.commit()
        }
    }

}