package hansung.ac.ex2

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
import android.provider.Settings.EXTRA_APP_PACKAGE
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private var notify1Count = 0
    private val channelID1 = "channel1"
    private val channelID2 = "channel2"
    private val myNotificationID1 = 1
    private val myNotificationID2 = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)

        createNotificationChannel(channelID1, "Channel 1", "Channel 1 Description")
        createNotificationChannel(channelID2, "Channel 2", "Channel 2 Description")

        val button1 = findViewById<Button>(R.id.notify1)
        button1.setOnClickListener {
            notify1Count++
            showNotification(channelID1, "따라하기 실습", "버튼1 클릭 횟수: $notify1Count")
        }

        val button2 = findViewById<Button>(R.id.notify2)
        val editText = findViewById<EditText>(R.id.editTextNotification)
        button2.setOnClickListener {
            val text = editText.text.toString()
            showNotification(channelID2, "따라하기 실습", text)
        }

        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener {
            val intent = Intent(ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(EXTRA_APP_PACKAGE, packageName)
            startActivity(intent)
        }
    }

    private fun createNotificationChannel(channelId: String, channelName: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = description
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(channelId: String, title: String, content: String) {
        val builder = NotificationCompat.Builder(this, channelId)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = builder.build()

        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notificationId = if (channelId == channelID1) myNotificationID1 else myNotificationID2
            NotificationManagerCompat.from(this).notify(notificationId, notification)
        }
    }

    private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return
        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage("Permission denied: $permission")
                }.show()
            }
        }
        if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage("Permission is required to show notifications.")
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            requestPermLauncher.launch(permission)
        }
    }
}
