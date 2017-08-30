package com.mncomunity1.service;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mncomunity1.DailogActivity;
import com.mncomunity1.MainActivity;
import com.mncomunity1.activity.BitItemActivity;
import com.mncomunity1.activity.ListNewsActivity;
import com.mncomunity1.activity.ListQuestionActivity;
import com.mncomunity1.activity.VendorOrderActivity;
import com.mncomunity1.app.Config;
import com.mncomunity1.util.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    String messages;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");
            String userName = payload.getString("team");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            Log.e(TAG, "Username" + userName);
            messages = message;

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                pushNotification.putExtra("check", userName);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                
                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {

                    if (title.equals("New Messag Chat")) {
                        intentChatMsg(getApplicationContext(), message, userName);
                    } else {

                        if (userName.equals("new order")) {

                            Intent resultIntent = new Intent(getApplicationContext(), VendorOrderActivity.class);
                            resultIntent.putExtra("message", message);
                            resultIntent.putExtra("check",userName);

                            showNotificationMessage(getApplicationContext(), "สินค้ามาใหม่", message, timestamp, resultIntent);

                        }
                        if (userName.equals("News")) {

                            Intent  resultIntent = new Intent(getApplicationContext(), ListNewsActivity.class);
                            resultIntent.putExtra("message", message);
                            resultIntent.putExtra("check",userName);
                            showNotificationMessage(getApplicationContext(), "ข่าสารมาใหม่", message, timestamp, resultIntent);

                        }

                        if(userName.equals("vender")){
                            Intent  resultIntent = new Intent(getApplicationContext(), BitItemActivity.class);
                            resultIntent.putExtra("message", message);
                            resultIntent.putExtra("check",userName);
                            showNotificationMessage(getApplicationContext(), "สอบถามสินค้า", message, timestamp, resultIntent);
                        }

                        if(userName.equals("customer")){
                            Intent  resultIntent = new Intent(getApplicationContext(), ListQuestionActivity.class);
                            resultIntent.putExtra("message", message);
                            resultIntent.putExtra("check",userName);
                            showNotificationMessage(getApplicationContext(), "ผู้ขายเสนอราคา", message, timestamp, resultIntent);
                        }

                        else {

                        }

                    }


                } else {
                    // image is present, show notification with image
                    //showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);

    }


    public void intentChatMsg(Context context, String msg, String name) {

        Intent scheduledIntent = new Intent(context, DailogActivity.class);
        scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        scheduledIntent.putExtra("msg", msg);
        context.startActivity(scheduledIntent);
    }


    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}

