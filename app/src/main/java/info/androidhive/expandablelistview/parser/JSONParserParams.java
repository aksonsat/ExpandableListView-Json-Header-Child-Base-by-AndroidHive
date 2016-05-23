package info.androidhive.expandablelistview.parser;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JSONParserParams {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	/**
	 * TAGs Defined Here...
	 */
	public static final String TAG = "TAG";

	/**
	 * Response
	 */
	private static Response response;

	// constructor
	public JSONParserParams() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	public JSONObject makeHttpRequest(String url, String method,
									  RequestBody params) {
		Log.i("Check MakeHttp","URL="+url+" "+"Method="+method+" Params="+params);
		// Making HTTP request
		try {
			// check for request method
			if(method == "POST"){
				// request method is POST
				Log.i("Check Method","In method POST");
				// defaultHttpClient
				OkHttpClient client = new OkHttpClient();

				Request request = new Request.Builder()
						.url(url)
						.method("POST",params)
						.build();

				response = client.newCall(request).execute();
				return new JSONObject(response.body().string());

			}else if(method == "GET"){
				// request method is GET
				Log.i("Check Method","In method GET");
                // url += "?" + "id=1";
				// defaultHttpClient
				OkHttpClient client = new OkHttpClient();
				//url += "?"+params;

				Log.i("Check Url Get","url = " + url);

				Request request = new Request.Builder()
						.url(url)
						.build();

				Log.i("Check request","request = " + request);
				response = client.newCall(request).execute();
				Log.i("Check response","response = " + response);
				// return new JSONObject(response.body().string());
				/**
                OkHttpClient client = new OkHttpClient();
				Request request = new Request.Builder()
						.url(url)
						.build();
				Log.i("Check Url Get","reauest = " + request);
				response = client.newCall(request).execute();
				return new JSONObject(response.body().string());
				 **/
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			Log.e(TAG, "" + e.getLocalizedMessage());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
			is.close();

			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
}
