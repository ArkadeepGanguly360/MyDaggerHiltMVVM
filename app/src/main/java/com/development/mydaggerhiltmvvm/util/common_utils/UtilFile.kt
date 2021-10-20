package com.development.mydaggerhiltmvvm.util.common_utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.development.mydaggerhiltmvvm.MyApplication
import com.development.mydaggerhiltmvvm.util.MyConstant.COMMON_CONST.EMAIL_PATTERN
import com.development.mydaggerhiltmvvm.util.image_utils.FileUtils
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object UtilFile {

    private fun isInputCorrect(
        s: Editable,
        totalSymbols: Int,
        dividerModulo: Int,
        divider: Char
    ): Boolean {
        var isCorrect = s.length <= totalSymbols // check size of entered string
        for (i in s.indices) { // check that every element is right
            isCorrect = if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect and (divider == s[i])
            } else {
                isCorrect and Character.isDigit(s[i])
            }
        }
        return isCorrect
    }

    private fun buildCorrectString(
        digits: CharArray,
        dividerPosition: Int,
        divider: Char
    ): String? {
        val formatted = StringBuilder()
        for (i in digits.indices) {
            if (digits[i].toInt() != 0) {
                formatted.append(digits[i])
                if (i > 0 && i < digits.size - 1 && (i + 1) % dividerPosition == 0) {
                    formatted.append(divider)
                }
            }
        }
        return formatted.toString()
    }

    private fun getDigitArray(s: Editable, size: Int): CharArray? {
        val digits = CharArray(size)
        var index = 0
        var i = 0
        while (i < s.length && index < size) {
            val current = s[i]
            if (Character.isDigit(current)) {
                digits[index] = current
                index++
            }
            i++
        }
        return digits
    }


    private var isSlash = false

    @SuppressLint("ClickableViewAccessibility")
    fun AppCompatAutoCompleteTextView.onTouchView(listener: (String) -> Unit) {
        this.setOnTouchListener { v, event ->
            run {
                listener(this.text.toString())
            }
            return@setOnTouchListener false
        }
    }

    fun AppCompatAutoCompleteTextView.onClickAutoCompleteItem(listener: (Any?) -> Unit) {
        this.setOnItemClickListener { parent: AdapterView<*>?,
                                      view: View?,
                                      position: Int,
                                      id: Long ->

            val item = parent?.getItemAtPosition(position)

            when (item) {


            }
            listener(item)
            this.setSelection(this.text.length)


        }
    }

    fun getParsedDate(date: String?, inputDateFormat: String, outputDateFormat: String): String? {
        if(date==null)
            return ""
        val input = SimpleDateFormat(inputDateFormat, Locale.getDefault())
        val output = SimpleDateFormat(outputDateFormat, Locale.getDefault())

        var d: Date? = null
        try {
            d = input.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
        return output.format(d!!)
    }
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now: Long = Calendar.getInstance().timeInMillis
        if (time > now || time <= 0) {
            return null
        }

        // TODO: localize
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
              "${diff / MINUTE_MILLIS} minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
             "${ diff / HOUR_MILLIS } hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else {
              "${ diff / DAY_MILLIS } days ago"
        }
    }

   /* fun calculateDaysBetweenDate(date: String, inputDateFormat: String): Long {
        val dateFormat = SimpleDateFormat(inputDateFormat, Locale.getDefault())

        var toDate: Date? = null
        val currentDate = dateFormat.format(Date())
        var dayDiff = 0L

        try {
            toDate = dateFormat.parse(date)
            val current = dateFormat.parse(currentDate)
            val diff = toDate.time - current.time
            dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        } catch (e: ParseException) {
            e.printStackTrace()
        } finally {
            return dayDiff
        }

    }*/

  /*  fun getParsedDate(date: String, inputDateFormat: String, outputDateFormat: String): String {
        val input = SimpleDateFormat(inputDateFormat, Locale.getDefault())
        val output = SimpleDateFormat(outputDateFormat, Locale.getDefault())

        var d: Date? = null
        try {
            d = input.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
        return output.format(d!!)
    }*/

   /*  fun getScreenHeight(context: AppCompatActivity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

        return height
    }*/

    val emailValid =
        fun(mail: String): Boolean { return Pattern.compile(EMAIL_PATTERN).matcher(mail).matches() }

    fun prepareFilePart(
        partName: String?,
        fileUri: Uri?
    ): MultipartBody.Part? {
        val file =
            FileUtils.getFile(MyApplication.applicationContext(), fileUri)
        val type: MediaType = getMimeType(fileUri!!)?.let { it.toMediaTypeOrNull() }!!
        val requestFile: RequestBody = RequestBody.create(type, file)
        return createFormData(partName!!, file.name, requestFile)
    }

    private fun getMimeType(uri: Uri): String? {
        var mimeType: String? = null
        mimeType = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val cr: ContentResolver = MyApplication.applicationContext().contentResolver
            cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                uri
                    .toString()
            )
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.toLowerCase()
            )
        }
        return mimeType
    }

   /* @SuppressLint("RestrictedApi")
    fun RecyclerView.setUpCenterZoomView(
        prefixText: String,
        height: Int,
        padding: Int,
        listener: (Int) -> Unit
    ) {
        val padding: Int = ViewUtils.dpToPx(
            context,
            height
        ).toInt() / 2 - ViewUtils.dpToPx(
            context,
            padding
        ).toInt()

        val layoutManager =
            CentralZoomedLinearLayoutManager(
                context,
                this,
                prefixText,
                object :
                    ScrolledCentralZoomedRecyclerViewItemOnClickListener {
                    override fun onItemSelected(position: Int) {
                        listener(position)
//                    showToast(endList[position] + " " + list[position])
                    }
                })
        this.layoutManager = layoutManager
        this.setPadding(0, padding, 0, padding)
        LinearSnapHelper().attachToRecyclerView(this)

    }*/

    fun TextInputEditText.convertPhToRequiredFormat() {

        this.addTextChangedListener(object : TextWatcher {
            private var backspacingFlag = false

            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private var editedFlag = false

            //we need to mark the cursor position and restore it after the edition
            private var cursorComplement = 0

            override fun afterTextChanged(s: Editable?) {
                val string = s.toString()
                //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                val phone = string.replace("[^\\d]".toRegex(), "")
                //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                if (!editedFlag) {

                    //we start verifying the worst case, many characters mask need to be added
                    //example: 999999999 <- 6+ digits already typed
                    // masked: (999) 999-999
                    if (phone.length == 10 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true
                        //here is the core. we substring the raw digits and add the mask as convenient
                        val ans = phone.substring(0, 2) +
                                " " + phone.substring(2, 6) + " " + phone.substring(6, 10)
                        setText(ans)
                        //we deliver the cursor to its original position relative to the end of the string
                        setSelection(length() - cursorComplement)

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length == 6 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true
                        //here is the core. we substring the raw digits and add the mask as convenient
                        val ans = phone.substring(0, 2) +
                                " " + phone.substring(2, 6) + " "
                        setText(ans)
                        //we deliver the cursor to its original position relative to the end of the string
                        setSelection(length() - cursorComplement)

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length == 2 && !backspacingFlag) {
                        editedFlag = true
                        val ans = phone.substring(0, 2) +
                                " "
                        setText(ans)
                        setSelection(length() - cursorComplement)
                    } else if (phone.length == 1 && backspacingFlag) {
                        editedFlag = true
                        setText("")
                        setSelection(length() - cursorComplement)
                    }
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                cursorComplement = s?.length!! - selectionStart
                //we check if the user ir inputing or erasing a character
                backspacingFlag = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}