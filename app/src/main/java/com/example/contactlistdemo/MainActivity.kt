package com.example.contactlistdemo

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.*
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        //private const val CAMERA_PERMISSION_CODE = 100
        private const val READ_CONTACTS_CODE = 101
        private const val PERMISSION_STRING = Manifest.permission.READ_CONTACTS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGetPermission = findViewById<Button>(R.id.btnGetPermission)
        btnGetPermission.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnGetPermission -> {
                if (checkWriteExternalPermission(PERMISSION_STRING)) {
                    permissionIsGranted()
                } else {
                    checkPermission(
                        PERMISSION_STRING,
                        READ_CONTACTS_CODE
                    )
                }
            }

        }
    }

    private fun checkWriteExternalPermission(permissionString: String): Boolean {
        val res: Int = checkCallingOrSelfPermission(permissionString)
        return res == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            openSettingActivity()
        }
    }

    private fun permissionIsGranted() {
        //Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
       /* val btn = findViewById<Button>(R.id.btnGetPermission)
        btn.setOnClickListener {*/
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        //}
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_CONTACTS_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionIsGranted()
                } else {
                    openDialogForAskPermissionConfirmation()
                }
                return
            }
        }
    }

    private fun openDialogForAskPermissionConfirmation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(PERMISSION_STRING)) {
                shwMessageOkCancel("You need to allow give allow to access the storage") { _, _ ->
                    checkPermission(
                        PERMISSION_STRING,
                        READ_CONTACTS_CODE
                    )
                }
            }
        }
    }

    private fun openSettingActivity() {
        val intent = Intent(
            ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityForResult(intent, READ_CONTACTS_CODE)
    }

    private fun shwMessageOkCancel(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            READ_CONTACTS_CODE -> {
            }
        }
    }
}





/*  val btn = findViewById<Button>(R.id.btnGetPermission)
  btn.setOnClickListener {
      val intent = Intent(this, SecondActivity::class.java)
      startActivity(intent)
  }*/
