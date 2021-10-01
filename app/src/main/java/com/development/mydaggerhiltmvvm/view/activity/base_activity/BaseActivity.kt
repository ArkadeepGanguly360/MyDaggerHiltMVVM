package com.development.mydaggerhiltmvvm.view.activity.base_activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ActivityBaseBinding
import com.development.mydaggerhiltmvvm.service.LocationUpdateService
import com.development.mydaggerhiltmvvm.util.MyConstant
import com.development.mydaggerhiltmvvm.util.kotlin_permission.KotlinPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.datepicker.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.DataFormatException

open class BaseActivity : BaseActivityAbstract() {
    private lateinit var progressDialog: Dialog
    private lateinit var alertDialog: AlertDialog
    private lateinit var baseActivityBinding: ActivityBaseBinding

    // kotlin permission
    private var listPermission = ArrayList<String>()

    private val REQUEST_CHECK_GPS_SETTINGS = 101
    private var mLocationIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivityBinding = setContentView(this, R.layout.activity_base)
        baseActivityBinding.baseActivity = this
        createProgressDialog()
    }

    override fun createProgressDialog() {
        progressDialog = Dialog(this)
        val inflatedView = LayoutInflater.from(this).inflate(R.layout.progressbar_layout, null)
        progressDialog.setContentView(inflatedView)
        progressDialog.setCancelable(false)

        val window = progressDialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#10000000")))
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        val wlp = window.attributes

        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
    }

    override fun showProgressDialog() {
        createProgressDialog()
        progressDialog.show()
    }

    override fun showProgressDialog(title: String) {
        /* val tv = progressDialog.progress_title
      tv.text = title*/
        progressDialog.show()
    }

    override fun cancelProgressDialog() {
        /* val tv = progressDialog.progress_title
           tv.text = "Loading"*/
        createProgressDialog()
        progressDialog.cancel()
    }

    override fun createAlertDialog(
        title: String,
        msg: String,
        positiveButtonText: String,
        negativeButtonText: String,
        listener: (Boolean) -> Unit
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(
            positiveButtonText
        ) { dialog, which ->
            dialog.cancel()
            listener(true)
        }
        builder.setNegativeButton(
            negativeButtonText
        ) { dialog, which ->
            dialog.cancel()
            listener(false)
        }

        alertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

        val buttonbackground: Button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        buttonbackground.setTextColor(Color.BLACK)

        val buttonbackground1: Button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonbackground1.setTextColor(Color.BLACK)
    }

    override fun showAlertDialog() {
        alertDialog.show()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun showSnackBar(v: View, msg: String) {
        val snackBar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG)
        val snackView = snackBar.view
        snackView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        val textView = snackView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()
    }

    override fun nextActivity(t: Class<*>, bundle: Bundle?, finishActivity: Boolean) {
        val intent = Intent(this@BaseActivity, t)
        if (bundle != null)
            intent.putExtra("bundle", bundle)

        startActivity(intent)
        if (finishActivity)
            finish()
    }

    override fun hideKeyboard(activity: Activity?) {
        var view = activity?.findViewById<View>(android.R.id.content);
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view = currentFocus
        if (view == null) {
            if (inputMethodManager.isAcceptingText) {
                try {
                    inputMethodManager.hideSoftInputFromWindow(
                        this.findViewById<View>(android.R.id.content).windowToken, 0
                    )
                } catch (e: Exception) {
                    //  MyLog.printStackTrace(e);
                }
                try {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                } catch (e: Exception) {
                    //  MyLog.printStackTrace(e);
                }
                // inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        } else {
            if (view is EditText) {
                (view as EditText).setText((view as EditText).text.toString()) // reset edit text bug on some keyboards bug
                try {
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                } catch (e: Exception) {
                    // MyLog.printStackTrace(e);
                }
                try {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                } catch (e: Exception) {
                    //MyLog.printStackTrace(e);
                }
            }
        }
    }

    override fun hideKeyBoard(et: EditText?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et?.windowToken, 0)
    }

    fun checkForLocationPermission() {

        listPermission.addAll(MyConstant.APP_PERMISSION.ACCESS_FINE_LOCATION)

        if (checkPermission(listPermission)) {
            checkIfGpsIsOn()
        } else {
            askPermission(listPermission) {
                if (it) checkIfGpsIsOn() else {
                }
            }
        }
    }

    fun checkPermission(list: List<String?>): Boolean {
        var valid = true
        for (item in list) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    item!!
                ) != PackageManager.PERMISSION_GRANTED
            ) valid = false
        }
        return valid
    }

    fun checkIfGpsIsOn() {
        val enabled = isGpsOn()
        if (!enabled) {
            askToEnableGps()
        } else {
            onGpsEnabled()
        }
    }

    fun isGpsOn(): Boolean {
        val service = getSystemService(LOCATION_SERVICE) as LocationManager
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun askToEnableGps() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val result = LocationServices.getSettingsClient(this)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener { task: Task<LocationSettingsResponse> ->
            try {
                val response =
                    task.getResult(ApiException::class.java)
                if (response.locationSettingsStates.isLocationPresent) onGpsEnabled()
            } catch (e: ApiException) {
                if (e.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    val resolvable = e as ResolvableApiException
                    try {
                        startIntentSenderForResult(
                            resolvable.resolution.intentSender,
                            REQUEST_CHECK_GPS_SETTINGS,
                            null,
                            0,
                            0,
                            0,
                            null
                        )
                    } catch (ex: IntentSender.SendIntentException) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

    fun onGpsEnabled() {
        val locationUpdateService = LocationUpdateService()
        if (mLocationIntent == null) {
            mLocationIntent = Intent(this, locationUpdateService.javaClass)
            startService(mLocationIntent)
        }
    }

    fun askPermission(list: List<String>, listener: (Boolean) -> Unit) {
        KotlinPermission.with(this)
            .permissions(list)
            .onAccepted {
                if (list.size == it.size)
                    listener(true)
                /* else
                     listener(false)*/
            }
            .onDenied {
                listener(false)
                showToast("You have to grant permissions!")
            }
            .onForeverDenied {
                listener(false)
                showToast("You have to grant permissions! Grant them from app settings please.")
                createAlertDialog(
                    "App Settings",
                    "Click Yes to open settings",
                    "YES",
                    "NO"
                ) {
                    if (it) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", this.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } else
                        showToast("You have to grant permissions!")
                }
            }
            .ask()
    }

    fun openDateCalendar(listener: (String) -> Unit) {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setEnd(today)
        val builder = MaterialDatePicker.Builder.datePicker().setCalendarConstraints(/*constraintsBuilder*/
            limitRange().build()
        )
        builder.setTheme(R.style.MaterialCalendarTheme);
        builder.setTitleText("Select Date")
        val picker = builder.build()


        picker.show(supportFragmentManager, picker.toString())

        picker.addOnPositiveButtonClickListener {
            val format =
                SimpleDateFormat(MyConstant.DATE_FORMAT.GENERAL_FORMAT, Locale.getDefault())
            try {
                val final = format.format(it)
                listener(final)
            } catch (e: DataFormatException) {
                listener("")
            }
        }
        picker.addOnCancelListener {
            listener("")
        }

    }

    private fun limitRange(): CalendarConstraints.Builder {
        val calendarStart: Calendar = Calendar.getInstance()
        val calendarEnd: Calendar = Calendar.getInstance()
        val year = 1900
        val startMonth = 1
        val startDate = 1
        calendarStart.set(year, startMonth, startDate)
        val cal = Calendar.getInstance()
        calendarEnd.set(cal[Calendar.YEAR],cal[Calendar.MONTH],cal[Calendar.DAY_OF_MONTH])
        val minDate: Long = calendarStart.timeInMillis
        val maxDate: Long = calendarEnd.timeInMillis

        val constraintsBuilder = CalendarConstraints.Builder()
        val validators: ArrayList<CalendarConstraints.DateValidator> = ArrayList()
        validators.add(DateValidatorPointForward.from(minDate))
        validators.add(DateValidatorPointBackward.before(maxDate))
        constraintsBuilder.setValidator(CompositeDateValidator.allOf(validators))

        return constraintsBuilder
    }
}