package com.example.sharity_apk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.databinding.ContactFormBinding
import com.example.sharity_apk.databinding.LoginBinding

class ContactForm : Fragment() {

    private var _binding: ContactFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ContactFormBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//
//
//    lateinit var editTextMail: EditText
//    lateinit var editTextSubject: EditText
//    lateinit var editTextMessage: EditText
//    lateinit var buttonSend: Button
//    lateinit var email: String
//    lateinit var subject: String
//    lateinit var message: String
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        title = "KotlinApp"
//        editTextMail = findViewById(R.id.editTextMail)
//        editTextSubject = findViewById(R.id.editTextSubject)
//        editTextMessage = findViewById(R.id.editTextMessage)
//        buttonSend = findViewById(R.id.buttonSend)
//        buttonSend.setOnClickListener {
//            getData()
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.putExtra(Intent.EXTRA_EMAIL, email)
//            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
//            intent.putExtra(Intent.EXTRA_TEXT, message)
//            intent.type = "message/rfc822"
//            startActivity(Intent.createChooser(intent, "Select email"))
//        }
//    }
//    private fun getData() {
//        email = editTextMail.text.toString()
//        subject = editTextSubject.text.toString()
//        message = editTextMessage.text.toString()
//    }
//}