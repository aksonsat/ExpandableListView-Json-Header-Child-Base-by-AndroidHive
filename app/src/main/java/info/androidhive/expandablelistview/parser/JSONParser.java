package info.androidhive.expandablelistview.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * @author Pratik Butani
 */
public class JSONParser {

    /**
     * TAGs Defined Here...
     */
    public static final String TAG = "TAG";
    /**
     * Response
     */
    private static Response response;

    /**
     * Get Table Booking Charge
     *
     * @return JSON Object
     */
    public static JSONObject makeHttp(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}
