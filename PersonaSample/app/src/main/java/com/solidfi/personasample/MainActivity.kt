package com.solidfi.personasample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import com.withpersona.sdk.inquiry.Environment
import com.withpersona.sdk.inquiry.Inquiry
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // pick any number and then use it later to retrieve the response
    private val VERIFY_REQUEST_CODE = 43

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    /*Function used to initialize the UI for the user input*/
    private fun initUI() {
        environmentEditText.setText(getString(R.string.sandbox))
        environmentEditText.setOnClickListener {
            val popupMenu = PopupMenu(this, environmentEditText)
            popupMenu.menu.add(getString(R.string.sandbox))
            popupMenu.menu.add(getString(R.string.production))
            popupMenu.setOnMenuItemClickListener { item ->
                environmentEditText.setText(item.title)
                true
            }
            popupMenu.show()
        }

        verifyButton.setOnClickListener {
            if (valid()) {
                startPersonaFlow()
            }
        }
    }

    /*Function calling the persona SDK for the verification*/
    private fun startPersonaFlow() {
        // Get the template ID from the Dashboard
        // https://withpersona.com/dashboard/development
        val templateId = templateIdEditText.text.toString()

        // Get the person ID from your platform
        // This is the id of the user logged in
        val personId = personIdEditText.text.toString()

        // Choose your environment production or sandbox
        val environment =
            if (environmentEditText.text.toString() == getString(R.string.production)) {
                Environment.PRODUCTION
            } else {
                Environment.SANDBOX
            }

        // Verification starts using Persona UI
        Inquiry.fromTemplate(templateId)
            .referenceId(personId)
            .environment(environment)
            .theme(R.style.blackTheme)
            .build()
            .start(this, VERIFY_REQUEST_CODE)
    }

    // persona result callback
    // overwriting the Activity#onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VERIFY_REQUEST_CODE) {
            when (val result = Inquiry.onActivityResult(data)) {
                is Inquiry.Response.Success -> {
                    // ...
                    finish()
                }
                is Inquiry.Response.Failure -> {
                    // ...
                }
                Inquiry.Response.Cancel -> {
                    // ...
                }
                is Inquiry.Response.Error -> {
                    // ...
                    Toast.makeText(this, result.debugMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /*Function used to verify the user input*/
    private fun valid(): Boolean {
        return personIdEditText.text?.isNotBlank() == true
                && templateIdEditText.text?.isNotBlank() == true
                && environmentEditText.text?.isNotBlank() == true
    }
}