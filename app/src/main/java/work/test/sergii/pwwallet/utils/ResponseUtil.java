package work.test.sergii.pwwallet.utils;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import work.test.sergii.pwwallet.R;

/**
 * Created by sergii on 09.04.18.
 */

public class ResponseUtil {

    public static String responceToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static void showErrorMessage(final String message, final Activity activity){
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(
                        activity,
                        activity.getString(
                                R.string.error,
                                message),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
