package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.util.isNotEmpty
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentBarcodeScannerBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentCustomPopupBinding
import com.development.mydaggerhiltmvvm.util.MyConstant
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import java.lang.Exception

class BarcodeScannerFragment : BaseFragment() {

    private lateinit var binding: FragmentBarcodeScannerBinding
    private lateinit var codeScanner: CodeScanner

    //Todo kotlin permission
    private var listPermission = java.util.ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPermission.addAll(MyConstant.APP_PERMISSION.CAMERA_PERMISSION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_barcode_scanner, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissionForScan()
    }

    private fun checkPermissionForScan() {
        if (baseActivity.checkPermission(listPermission))
            startScanning()
        else {
            baseActivity.askPermission(listPermission) {
                if (it)
                    startScanning()
            }
        }
    }

    private fun startScanning() {
        codeScanner = CodeScanner(requireActivity(), binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Scan result: ${it.text}", Toast.LENGTH_LONG)
                    .show()
                goToNextFragment(
                    R.id.action_barcodeScannerFragment_to_dashboardFragment,
                    null
                )
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            requireActivity().runOnUiThread {
                Toast.makeText(
                    requireActivity(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized) {
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if(::codeScanner.isInitialized) {
            codeScanner?.releaseResources()
        }
        super.onPause()
    }
}