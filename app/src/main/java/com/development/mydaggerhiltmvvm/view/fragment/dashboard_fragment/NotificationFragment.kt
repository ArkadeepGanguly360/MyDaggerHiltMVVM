package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentNotificationBinding
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardActivity
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import java.util.*


class NotificationFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationBinding
    var notificationText = ObservableField("")

    private var numMessages = 0
    private val notificationChannelId = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_notification, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNotification.onClick()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btNotification.id -> {
                    //addNotification()
                    inboxStyle()
                }
            }
        }
    }

    private fun addNotification() {

        val bundle = Bundle()
        val intent = Intent(requireActivity(), DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtras(bundle)
        }

        val randId = gen()

        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(requireActivity()).run {
            addNextIntentWithParentStack(intent)
            addNextIntent(intent)
            getPendingIntent(randId, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder =
            NotificationCompat.Builder(requireActivity(), notificationChannelId).apply {
                setContentTitle("Example")
                setContentText(notificationText.get())
                setAutoCancel(true)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                setContentIntent(resultPendingIntent)
                setContentInfo("Hello")

                setSmallIcon(R.drawable.pic);
                setSound(soundUri)

                /* TODO For show downloading progress*/
                /* setContentText("Download progressbar")
                 setContentTitle("Progress")
                 setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.pic))*/


                /* setProgress(100, 0, true)*/
                /* setStyle(
                     NotificationCompat.BigPictureStyle()
                         .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.pic))
                         .bigLargeIcon(null)
                 )
                 setStyle(
                     NotificationCompat.BigTextStyle()
                         .bigText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                 )*/
                color = ContextCompat.getColor(requireActivity(), R.color.red)
                setLights(Color.RED, 1000, 300)
                setDefaults(Notification.DEFAULT_VIBRATE)
                setNumber(++numMessages)
                setSmallIcon(R.drawable.who)
                setWhen(System.currentTimeMillis())
            }

        val notificationManager = NotificationManagerCompat.from(requireActivity())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESC
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(channel)
        }
        assert(notificationManager != null)
        notificationManager.notify(randId, notificationBuilder.build())
    }

    private fun gen(): Int {
        val r = Random(System.currentTimeMillis())
        return (1 + r.nextInt(2)) * 10000 + r.nextInt(10000)
    }

    companion object {
        private const val CHANNEL_NAME = "Default"
        private const val CHANNEL_DESC = "Default Messaging"
    }

    private fun inboxStyle() {
        val randId = gen()
        val notificationBuilder =
            NotificationCompat.Builder(requireActivity(), notificationChannelId).apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress")
            setSmallIcon(R.drawable.who)
                priority = NotificationCompat.PRIORITY_LOW
        }

        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 0
        NotificationManagerCompat.from(requireActivity()).apply {
            // Issue the initial notification with zero progress
            notificationBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notify(randId, notificationBuilder.build())


            // Do the job here that tracks the progress.
            // Usually, this should be in a
            // worker thread
            // To show progress, update PROGRESS_CURRENT and update the notification with:
            // builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
            // notificationManager.notify(notificationId, builder.build());

            // When done, update the notification one more time to remove the progress bar
            notificationBuilder.setContentText("Download complete")
                .setProgress(0, 0, false)
            notify(randId, notificationBuilder.build())
        }
    }
}
