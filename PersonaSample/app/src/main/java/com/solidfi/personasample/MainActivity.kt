package com.solidfi.personasample

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // pick any number and then use it later to retrieve the response
    private val inputFileRequestCode = 1
    private val cameraPermissionRequestCode = 1111
    private var cameraPermission: PermissionRequest? = null
    private var filePathCallback: ValueCallback<Array<Uri?>?>? = null
    private var cameraPhotoPath: String? = null
    private var redirectUri: String = "https://personacallback"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    /*Function used to initialize the UI for the user input*/
    private fun initUI() {
        verifyButton.setOnClickListener {
            if (valid()) {
                initWebView()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraPermissionRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPermission!!.grant(cameraPermission!!.resources)
            } else {
                cameraPermission!!.deny()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        hideKeyboard(view = verifyButton)
        webView.visibility = View.VISIBLE
        urlEditText.visibility = View.GONE
        verifyButton.visibility = View.GONE
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false

        webView.webViewClient = object : WebViewClient() {
            @SuppressLint("SetTextI18n")
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                super.shouldOverrideUrlLoading(view, request)
                val parsedUri = Uri.parse(request?.url.toString())
                return if (parsedUri.authority == "personacallback") {
                    webView.visibility = View.GONE
                    urlEditText.visibility = View.VISIBLE
                    verifyButton.visibility = View.VISIBLE
                    logsTextView.text = getString(R.string.verification_success)
                    true
                } else {
                    false
                }
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {

            }

            override fun onPermissionRequest(request: PermissionRequest) {
                val permission = Manifest.permission.CAMERA
                val grant = ContextCompat.checkSelfPermission(this@MainActivity, permission)
                cameraPermission = request
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), cameraPermissionRequestCode)
                } else {
                    cameraPermission!!.grant(cameraPermission!!.resources)
                }
            }

            @SuppressLint("QueryPermissionsNeeded")
            override fun onShowFileChooser(
                webView: WebView, newFilePathCallback: ValueCallback<Array<Uri?>?>?,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (filePathCallback != null) {
                    filePathCallback!!.onReceiveValue(null)
                }
                filePathCallback = newFilePathCallback
                var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent!!.resolveActivity(packageManager) != null) {
                    // Create the File where the photo should go
                    var photoFile: File? = null
                    // Create an image file name
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
                    val imageFileName = "JPEG_" + timeStamp + "_"
                    val storageDir: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    try {
                        photoFile = File.createTempFile(imageFileName, ".jpg", storageDir)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraPhotoPath = "file:" + photoFile.absolutePath
                        takePictureIntent.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile)
                        )
                    } else {
                        takePictureIntent = null
                    }
                }
                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "image/*"
                val intentArray: Array<Intent?> = takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                startActivityForResult(chooserIntent, inputFileRequestCode)
                return true
            }
        }
        webView.loadUrl(urlEditText.text.toString() + "&redirect-uri=" + redirectUri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != inputFileRequestCode || filePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        var results: Array<Uri?>? = null

        // Check that the response is a good one
        if (resultCode == RESULT_OK) {
            if (data == null) {
                // If there is not data, then we may have taken a photo
                if (cameraPhotoPath != null) {
                    results = arrayOf(Uri.parse(cameraPhotoPath))
                }
            } else {
                val dataString = data.dataString
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }
        filePathCallback!!.onReceiveValue(results)
        filePathCallback = null
    }

    /*Function used to verify the user input*/
    private fun valid(): Boolean {
        return urlEditText.text?.isNotBlank() == true
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}