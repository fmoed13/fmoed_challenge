package org.foi.fmoed.managers;

import java.io.File;
import java.util.List;

import org.foi.fmoed.activities.IdeasActivity;
import org.foi.fmoed.activities.MainActivity;
import org.foi.fmoed.models.Group;
import org.foi.fmoed.utilities.CountDown;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.koushikdutta.ion.builder.Builders;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SessionManager {

	public static String SERVER = "http://evodeployment.evolaris.net/brainwriting/";
	public static String START_SESSION = SERVER + "start?group=%s";
	public static String CHECK_SESION = SERVER + "status?group=%s";
	public static String SUBMIT_IDEA = SERVER + "submit?group=%s&user=%s";
	public static String RECEIVE_IDEA = SERVER + "previous?group=%s&user=%s";
	public static String RESULTS = SERVER + "results?group=%s";

	private Context context;
	private DatabaseManager dbManager;

	public SessionManager(Context ctx) {
		context = ctx;
		dbManager = new DatabaseManager(context);
	}

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

	public void startSession(final String groupName, final ProgressDialog progressDialog) {

		Ion.with(this.context, this.formatURL(START_SESSION, groupName))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception ex, JsonObject result) {
						Log.i("sessionStart", result.get("message").toString());
						if (dbManager.checkIfGroupExists(groupName) == 0) {
							dbManager.addRecord(new Group(groupName,
									Group.STATUS_IN_PROGRESS, "1").getValues(),
									DatabaseManager.TABLE_GROUP);
							
						}
						progressDialog.cancel();
						IdeasActivity.groupName = groupName;
						Intent ideasActivity = new Intent(context,
								IdeasActivity.class);
						Activity activity = (Activity) context;
						activity.startActivity(ideasActivity);
					}
				});
	}

	public void checkSession(String groupName) {
		Ion.with(this.context, this.formatURL(CHECK_SESION, groupName))
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

	public void submitIdea(String groupName, String userName,
			List<String> ideasTexts, List<String> imagesList,
			final ProgressDialog progressDialog) {
		Builders.Any.M builder = Ion
				.with(this.context,
						this.formatURL(SUBMIT_IDEA, groupName, userName))
				.uploadProgressDialog(progressDialog)
				.uploadProgress(new ProgressCallback() {
					@Override
					public void onProgress(int uploaded, int total) {
						progressDialog.setProgress(uploaded);
						progressDialog.setMax(total);
					}
				}).setMultipartParameter("text1", ideasTexts.get(0))
				.setMultipartParameter("text2", ideasTexts.get(1))
				.setMultipartParameter("text3", ideasTexts.get(2));

		for (int i = 0; i < imagesList.size(); i++) {
			if (imagesList.get(i) != "") {
				builder.setMultipartFile("image" + (i+1),
						new File(imagesList.get(i)));
			}
		}
		builder.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
			@Override
			public void onCompleted(Exception e, JsonObject result) {
				progressDialog.dismiss();
				Intent mainActivity = new Intent(context, MainActivity.class);
				Activity activity = (Activity) context;
				activity.startActivity(mainActivity);
				Log.i("submitIdea", "completed...");
			}
		});
	}

	public void receiveIdea(String groupName, String userName) {
		Ion.with(this.context,
				this.formatURL(RECEIVE_IDEA, groupName, userName))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {
						Log.i("receiveIdea", "received...");
					}
				});
	}

	public void sessionResults(String groupName) {
		// TODO:
	}

}