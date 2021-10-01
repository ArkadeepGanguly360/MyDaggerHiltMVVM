package com.development.mydaggerhiltmvvm.view.fragment.base_fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.development.mydaggerhiltmvvm.util.common_utils.UtilFile.prepareFilePart
import com.development.mydaggerhiltmvvm.util.image_utils.ImageUtilsCameraGallery
import com.development.mydaggerhiltmvvm.util.kotlin_permission.KotlinPermission
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.util.*

open class BaseFragment : BaseFragmentAbstract(), ImageUtilsCameraGallery.ImageAttachmentListener {

    protected lateinit var baseActivity: BaseActivity

    private var imageutils: ImageUtilsCameraGallery? = null

    private var listener = { from: Int,
                             filename: String?,
                             file: Bitmap?,
                             uri: Uri?,
                             count: Int
        ->
        Unit
    }

    override fun showToast(msg: String) {
        baseActivity.showToast(msg)
    }

    override fun showSnackBar(v: View, msg: String) {
        baseActivity.showSnackBar(v, msg)
    }

    override fun goToNextFragment(destinationId: Int, bundle: Bundle?) {
        findNavController().navigate(destinationId, bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = requireActivity() as BaseActivity
    }

    override fun <T : ViewDataBinding?> putContentView(
        @LayoutRes resId: Int,
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T {
        val bind: ViewDataBinding =
            DataBindingUtil.inflate(inflater, resId, container, false)
        return bind as T
    }

    protected fun getImageFromUserSelected(
        listPermission: ArrayList<String>,
        listener: (
            from: Int,
            filename: String?,
            file: Bitmap?,
            uri: Uri?,
            count: Int
        ) ->
        Unit
    ) {
        this.listener = listener
        //baseActivity.checkPermission(listPermission) {
            if (baseActivity.checkPermission(listPermission))
                openImageIntent()
            else {
                baseActivity.askPermission(listPermission) {
                    if (it)
                        openImageIntent()
                }
            }
        }
   // }

    fun getVideoFromUserSelected(
        listPermission: ArrayList<String>,
        toChooseMultipleImage: Boolean,
        listener: (
            from: Int,
            filename: String?,
            file: Bitmap?,
            uri: Uri?,
            count: Int
        ) ->
        Unit
    ) {
        this.listener = listener
        checkPermission(listPermission) {
            if (it)
                openVideoIntent(toChooseMultipleImage)
            else {
                askPermission(listPermission) {
                    if (it)
                        openVideoIntent(toChooseMultipleImage)
                }
            }
        }
    }

    protected fun getImageFromUserSelectedOnlyGallery(
        listPermission: ArrayList<String>,
        listener: (
            from: Int,
            filename: String?,
            file: Bitmap?,
            uri: Uri?,
            count: Int
        ) ->
        Unit
    ) {
        this.listener = listener
        //baseActivity.checkPermission(listPermission) {
        if (baseActivity.checkPermission(listPermission))
            openImageIntentForGallery()
        else {
            baseActivity.askPermission(listPermission) {
                if (it)
                    openImageIntentForGallery()
            }
        }
    }

    private fun openVideoIntent(toChooseMultipleImage: Boolean) {
        imageutils =
            ImageUtilsCameraGallery(
                baseActivity,
                false,
                this, toChooseMultipleImage
            )
        imageutils?.openVideoPicker(0)
    }

    private fun openImageIntent() {
        imageutils =
            ImageUtilsCameraGallery(
                baseActivity,
                false,
                this, false
            )
        imageutils?.imagePicker(0)
    }

    private fun openImageIntentForGallery() {
        imageutils =
            ImageUtilsCameraGallery(
                baseActivity,
                false,
                this, true
            )
        imageutils?.galley_call()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 || requestCode == 1)
            imageutils!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun image_attachment(
        from: Int,
        filename: String?,
        file: Bitmap?,
        uri: Uri?,
        count: Int
    ) {
        listener(from, filename, file, uri, count)
    }

    protected fun createMultipartImageFile(
        filename: String?,
        file: Bitmap?,
        partName: String,
        listener: (MultipartBody.Part) -> Unit
    ) {


        val f = File(baseActivity.cacheDir, filename!!)
        try {
            f.createNewFile()
            //Convert bitmap to byte array
            val bitmap = file!!
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
            val bitmapdata = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()

            val imageBody = prepareFilePart(
                partName,
                Uri.fromFile(f)
            )

            listener(imageBody!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @NonNull
    protected fun createPartFromString(descriptionString: String): RequestBody {
        return descriptionString.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    /////// This are responsible for asking permission at run time using lambda///////////
    protected fun checkPermission(list: List<String>, listener: (Boolean) -> Unit) {
        var valid = true
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        for (item in list) {
            if (ContextCompat.checkSelfPermission(
                    baseActivity,
                    item
                ) != PackageManager.PERMISSION_GRANTED
            )
                valid = false
        }
//        }
        listener(valid)
    }

    fun askPermission(list: List<String>, listener: (Boolean) -> Unit) {
        KotlinPermission.with(baseActivity)
            .permissions(list)
            .onAccepted {
                if (list.size == it.size)
                    listener(true)
                else
                    listener(false)
            }
            .onDenied {
                listener(false)
                showToast("You have to grant permissions!")
            }
            .onForeverDenied {
                listener(false)
                showToast("You have to grant permissions! Grant them from app settings please.")
                baseActivity.createAlertDialog(
                    "App Settings",
                    "Click Yes to open settings",
                    "YES",
                    "NO"
                ) {
                    if (it) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", baseActivity.packageName, null)
                        intent.data = uri
                        baseActivity.startActivity(intent)
                    } else
                        showToast("You have to grant permissions!")
                }
            }
            .ask()
    }
    /////// This are responsible for asking permission at run time using lambda///////////
}