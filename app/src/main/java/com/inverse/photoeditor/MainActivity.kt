package com.inverse.photoeditor

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity() {
    companion object{
        const val RC_CAMERA_AND_LOCATION = 111
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private fun methodRequiresTwoPermission() {
        val perms =
            arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            EasyPermissions.requestPermissions(  this@MainActivity, getString(R.string.camera_and_location_rationale), RC_CAMERA_AND_LOCATION, *perms)
        }
    }

    override fun onStart() {
        super.onStart()
        methodRequiresTwoPermission()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

     fun onPermissionsDenied(requestCode: Int, perms: List<String?>) {

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                this,
                "Permission changed",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}