package com.appsamurai.storyly

import RedBottomSheet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class DemoFragment : Fragment() {

    var onCloseClick: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = RedBottomSheet()
        bottomSheet.onCloseClick = onCloseClick
        bottomSheet.onOpenBottomSheet = {
            openBottomSheet(it)
        }
        openBottomSheet(bottomSheet)
    }

    fun openBottomSheet(dialog: DialogFragment) {
        dialog.show(
            parentFragmentManager,
            "TAG"
        )
    }
}