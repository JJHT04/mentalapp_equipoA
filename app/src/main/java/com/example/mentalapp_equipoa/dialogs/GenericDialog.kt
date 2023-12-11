package com.example.mentalapp_equipoa.dialogs

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GenericDialog(private var title: CharSequence, private var body: CharSequence, private var icon: Drawable? = null) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            MaterialAlertDialogBuilder(it).apply {
                setTitle(title)
                setMessage(body)
                if (icon != null) {
                    setIcon(icon)
                }

                setPositiveButton("OK") {
                    _,_ -> //No action needed
                }
            }.create()
        }?: throw IllegalStateException("Activity can't be null")
    }

    companion object {
        fun showGenericDialog(supportFragmentManager: FragmentManager,title: CharSequence, body: CharSequence, icon: Drawable?) {
            GenericDialog(title, body, icon).show(supportFragmentManager, "Generic Dialog")
        }

    }

}