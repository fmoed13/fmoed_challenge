package org.foi.managers;

import java.io.File;
import java.util.List;

import org.foi.fmoed.MainActivity;
import org.foi.fmoed.R;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.util.Log;
import retrofit.Server;

public class SessionManager extends Activity {

	public static String SERVER = "http://evodeployment.evolaris.net/brainwriting/";
	public static String START_SESSION = SERVER + "start?group=%s";
	public static String CHECK_SESION = SERVER + "status?group=%s";
	public static String SUBMIT_IDEA = SERVER + "submit?group=%s&user=%s";
	public static String RECEIVE_IDEA = SERVER + "previous?group=%s&user=%s";
	public static String RESULTS = SERVER + "results?group=%s";

	/**
	 * Method for formating URLs
	 * 
	 * @param url
	 *            URL to format
	 * @param strings
	 *            List of argument to put into URL
	 * @return String URL
	 */
	public String formatURL(String url, String... strings) {
		return String.format(url, (Object[]) strings);
	}

	public void startSession(String groupName, Context context) {

		Ion.with(context, this.formatURL(START_SESSION, groupName))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {
						String res = result.get("message").toString();
						Log.i("sessinnStart", "started");
						// TODO: check if session is started without errors...
					}
				});
	}

	public void checkSession(String groupName, Context context) {
		Ion.with(context, this.formatURL(CHECK_SESION, groupName))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {

						Integer status = Integer.parseInt(result.get("round")
								.toString());

						if (status == 0) {
							Log.i("checkSession",
									"session not started or finished");
						} else {
							Log.i("checkStasion",
									"session in round " + status.toString());
						}
					}
				});
	}

	public void submitIdea(String groupName, String userName, Context context) {
		Ion.with(context, this.formatURL(SUBMIT_IDEA, groupName, userName))
				.setMultipartParameter("text1", "first text").asJsonObject()
				.setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {
						Log.i("submitIdea", "completed...");
					}
				});
	}

	public void receiveIdea(String groupName, String userName, Context context) {
		Ion.with(context, this.formatURL(RECEIVE_IDEA, groupName, userName))
		.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
			@Override
			public void onCompleted(Exception e, JsonObject result) {
				Log.i("receiveIdea", "received...");
			}
		});
	}

	public void sessionResults(String groupName, Context context) {
		// TODO:
	}

}
