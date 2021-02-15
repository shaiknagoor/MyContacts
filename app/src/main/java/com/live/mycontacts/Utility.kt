package com.live.mycontacts

import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import java.util.jar.Manifest

object Utility {
    fun hasPermission(context: Context)={
        if(Build.VERSION.SDK_INT>23){
            EasyPermissions.hasPermissions(context,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS
            )
        }


        }

}