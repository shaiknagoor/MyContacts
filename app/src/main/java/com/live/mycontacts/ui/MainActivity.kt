package com.live.mycontacts.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.live.mycontacts.R
import com.live.mycontacts.viewmodel.ContactViewModel
import com.live.mycontacts.viewmodel.NewViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {
    private val TAG="MainActivity"
    lateinit var viewModel: ContactViewModel

    private val RC_CONTACTS_PERM = 126
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val viewModelProviderFactory= NewViewModelProviderFactory()
        viewModel= ViewModelProvider(this, viewModelProviderFactory).get(ContactViewModel::class.java)
        contactTask()
      fab.setOnClickListener {
    navController.navigate(R.id.action_ContactFragment_to_AddContactFragment)
      }
    }




    fun contactTask() {
        if (hasContactPermission())
        {
            Toast.makeText(this, "TODO: Contact things", Toast.LENGTH_LONG).show()
        }
        else
        {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_contact), RC_CONTACTS_PERM,
                Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
            )
        }
    }
    private fun hasContactPermission():Boolean {
        return EasyPermissions.hasPermissions(
            this, Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
        {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
        {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)
            Toast.makeText(
                this, getString(
                    R.string.returned_from_app_settings_to_activity,
                    if (hasContactPermission()) yes else no
                ),
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

}