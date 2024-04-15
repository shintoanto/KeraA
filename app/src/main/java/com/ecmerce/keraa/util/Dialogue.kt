package com.ecmerce.keraa.util

import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.ecmerce.keraa.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setUpBottomSheetDialogue(onSendClick: (String) -> Unit) {
    val dialogue = BottomSheetDialog(requireContext())
    val view = layoutInflater.inflate(R.layout.reset_password, null)
    dialogue.setContentView(view)
    dialogue.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialogue.show()

    val edEmail: AppCompatEditText = view.findViewById(R.id.edResetPassword)
    val btnCancel: AppCompatButton = view.findViewById(R.id.btnCancel)
    val btnSend: AppCompatButton = view.findViewById(R.id.btnSend)

    btnSend.setOnClickListener {
        val email = edEmail.text.toString()
        onSendClick(email)
        dialogue.dismiss()
    }

    btnCancel.setOnClickListener {
        dialogue.dismiss()
    }
}