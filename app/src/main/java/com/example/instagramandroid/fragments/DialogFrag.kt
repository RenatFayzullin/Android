package com.example.instagramandroid.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.instagramandroid.R
import java.lang.ClassCastException

class DialogFrag : DialogFragment() {
    interface DialogListener {
        fun onPositiveClick(dialogFragment: DialogFragment)
    }

    lateinit var mListener : DialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as DialogListener
        } catch ( e:ClassCastException){
            throw ClassCastException("$context must implement")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = android.app.AlertDialog.Builder(activity)
        var inflater = activity?.layoutInflater
        var dialogView = inflater?.inflate(R.layout.dialog_fragment,null)

        builder
            .setView(dialogView)
            .setNegativeButton("Отмена"){ _, _ -> this.dialog?.cancel()}
            .setPositiveButton("Добавить"){_, _ -> mListener.onPositiveClick(this)}
        return builder.create()
    }
}
