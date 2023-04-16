package com.example.androidconcepts.fragments.problems.problem2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.androidconcepts.R
import com.google.android.material.button.MaterialButton

class FragProbTwoFragment : Fragment() {

    private lateinit var button: MaterialButton

    var number = 0

    private fun getInstance(number: Int): FragProbTwoFragment {
        return FragProbTwoFragment().apply {
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
        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        lp.gravity = Gravity.TOP
        lp.topMargin = number * 50
        button.layoutParams = lp

        rootView.addView(button)

        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("frag problem", "$number onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        // state is set to false here. eg : minimize app, state = true, reopen, state = false.
        Log.d("frag problem", "$number onStart, isStateSaved : $isStateSaved")
        super.onStart()
    }

    override fun onResume() {
        Log.d("frag problem", "$number onResume, isStateSaved : $isStateSaved")
        super.onResume()
    }

    override fun onPause() {

        // as far as experimented, isStateSaved is false in onPause.
        Log.d("frag problem", "$number onPause, isStateSaved : $isStateSaved")

        super.onPause()

        // very common case in which we do a fragment transaction on a network call result.
        // user initiates the network call and minimises the app and before opening again the result is fetched
        // and hence a frag transaction is committed.
        // then perhaps, the app would crash throwing "java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState".

        button.postDelayed({
            val fm = requireActivity().supportFragmentManager
            fm.beginTransaction()
                .replace(R.id.root_fragment_container, this.getInstance(number + 1))
                .addToBackStack(null)
                .commit()
        }, 1500L)
    }

    override fun onStop() {
        // weirdly, isStateSaved is true here even when onSaveInstanceState is called yet.
        // also if I commit frag transactions in onStop then isStateSaved is false.
        Log.d("frag problem", "$number onStop, isStateSaved : $isStateSaved")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("frag problem", "$number onDestroyView, isStateSaved : $isStateSaved")
        super.onDestroyView()
    }

}