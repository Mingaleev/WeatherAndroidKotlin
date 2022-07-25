package ru.mingaleev.weatherandroidkotlin.view.contentprovaider

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentContentProviderBinding
import ru.mingaleev.weatherandroidkotlin.utils.REQUEST_CODE_CALL_PHONE
import ru.mingaleev.weatherandroidkotlin.utils.REQUEST_CODE_READ_CONTACTS

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding: FragmentContentProviderBinding
        get() {
            return _binding!!
        }
    private var telBuffer = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
    }

    private fun checkPermissionCall(): Boolean {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        return if (permResult == PackageManager.PERMISSION_GRANTED) {
            true
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Вызов контакта")
                .setMessage("Для вызова контакта необходимо Разрешение")
                .setPositiveButton("Разрешить вызовы") { _, _ ->
                    permissionCallRequest(Manifest.permission.CALL_PHONE)
                }.setNegativeButton("Отказаться") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
            false
        } else {
            permissionCallRequest(Manifest.permission.CALL_PHONE)
            false
        }
    }

    private fun checkPermission() {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getContacts()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к кнотактам")
                .setMessage("Для отображения списка контактов необходимо Разрешение")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionReadRequest(Manifest.permission.READ_CONTACTS)
                }.setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionReadRequest(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun permissionReadRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_READ_CONTACTS)
    }

    private fun permissionCallRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_CALL_PHONE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.READ_CONTACTS
                    && grantResults[i] == PackageManager.PERMISSION_GRANTED
                ) { getContacts() }
            }
        }
        if (requestCode == REQUEST_CODE_CALL_PHONE) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.CALL_PHONE
                    && grantResults[i] == PackageManager.PERMISSION_GRANTED
                ) { callPhone() }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getContacts() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val cursorWithContacts: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursorWithContacts?.let {
            for (i in 0 until it.count) {
                it.moveToPosition(i)
                val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val tel = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                binding.containerForContacts.addView(
                    TextView(requireContext()).apply {
                    text = name
                    textSize = 30f
                })
                binding.containerForContacts.addView(TextView(requireContext()).apply {
                    text = tel
                    textSize = 15f
                    setOnClickListener(){
                        telBuffer = tel
                        if (checkPermissionCall()){
                            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${tel}")))
                        }
                    }
                })
            }
        }
        cursorWithContacts?.close()
    }

    private fun callPhone () {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${telBuffer}")))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}