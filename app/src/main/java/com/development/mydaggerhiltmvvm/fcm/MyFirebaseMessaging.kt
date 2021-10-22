package com.development.mydaggerhiltmvvm.fcm

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.util.MyConstant
import com.development.mydaggerhiltmvvm.util.UserSharedPrefrence
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.*

class MyFirebaseMessaging : FirebaseMessagingService() {
    private var numMessages = 0
    private val notificationChannelId = "100"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        sendNotification(notification, data)
    }

    private fun sendNotification(
            notification: RemoteMessage.Notification?,
            data: Map<String, String>
    ) {
        val bundle = Bundle()
        bundle.putString(MyConstant.NOTIFICATION_TYPE.NOTIFY_TYPE, data["notification_type"])
        when (data["notification_type"].toString().toUpperCase()) {
            MyConstant.NOTIFICATION_TYPE.TAG -> {
                val jsonObject = JSONObject(data["extra"])
                bundle.putString("event_date", jsonObject.optString("event_date"))
                bundle.putString("event_time", jsonObject.optString("event_time"))
            }
            MyConstant.NOTIFICATION_TYPE.PROMOTION -> {
                val jsonObject = JSONObject(data["extra"])
                bundle.putString("promotionId", jsonObject.optString("promotion_id"))
            }
            MyConstant.NOTIFICATION_TYPE.LIVE_EVENT -> {
                val jsonObject = JSONObject(data["extra"])
                bundle.putString("liveEventId", jsonObject.optString("live_event_id"))
            }
            MyConstant.NOTIFICATION_TYPE.GENERAL -> {
                val jsonObject = JSONObject(data["extra"])
                bundle.putString("notificationId", jsonObject.optString("notification_id"))
            }
            MyConstant.NOTIFICATION_TYPE.PUSH -> {
                val jsonObject = JSONObject(data)
                bundle.putString("RecieverId", jsonObject.optString("RecieverId"))
                bundle.putString("RecieverName", jsonObject.optString("RecieverName"))
                bundle.putString("RecieverPic", jsonObject.optString("RecieverPic"))
            }
        }

        val intent = Intent(this, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtras(bundle)
        }
        val randId = gen()

        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            addNextIntent(intent)
            getPendingIntent(randId, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationBuilder =
                NotificationCompat.Builder(this, notificationChannelId).apply {
                    setContentTitle(data["title"])
                    setContentText(data["body"])
                    setAutoCancel(true)
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    setContentIntent(resultPendingIntent)
                    setContentInfo("Hello")
                    setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_profile))
                    color = ContextCompat.getColor(applicationContext, R.color.blue)
                    setLights(Color.RED, 1000, 300)
                    setDefaults(Notification.DEFAULT_VIBRATE)
                    setNumber(++numMessages)
                    setSmallIcon(R.drawable.ic_profile)
                    setWhen(System.currentTimeMillis())
                }

        val notificationManager = NotificationManagerCompat.from(this)

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
        const val FCM_PARAM = "picture"
        private const val CHANNEL_NAME = "FCM"
        private const val CHANNEL_DESC = "Firebase Cloud Messaging"
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
      // UserSharedPrefrence(applicationContext).userFCMToken = p0
    }
}