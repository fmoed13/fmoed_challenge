package org.foi.fmoed.managers;

import java.io.File;
import java.util.List;

import org.foi.fmoed.activities.IdeasActivity;
import org.foi.fmoed.activities.MainActivity;
import org.foi.fmoed.adapters.GroupAdapter;
import org.foi.fmoed.fragments.ResultFragment;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

	/**
	 * Method for starting new session
	 * @param groupName Group name
	 * @param progressDialog dialog to cancel when session is started
	 */
	public void startSession(final String groupName,
			final ProgressDialog progressDialog) {

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

	/**
	 * Method for checking session state
	 * @param groupName Name of group
	 * @param txtRound TextView with round number
	 * @param imgView ImageView to enable when it is needed
	 */
	public void checkSession(final String groupName, final TextView txtRound,
			final ImageView imgView) {
		Ion.with(this.context, this.formatURL(CHECK_SESION, groupName))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {

						Log.i("sessionCompleted", "sesion completed!");

						if (result != null) {
							Integer status = Integer.parseInt(result.get(
									"round").toString());

							try {
								if (imgView != null && txtRound != null) {
									String[] parts = txtRound.getText()
											.toString().split(":");
									Integer _status = Integer.parseInt(parts[1]
											.replaceAll("\\s+", ""));
									if (status > _status) {
										imgView.setVisibility(View.VISIBLE);
										txtRound.setText("Round: "
												+ String.valueOf(status));
										GroupAdapter.groupRoundMap.put(groupName, status);
									}
								}
							} catch (Exception ex) {
								// TODO: handle exception
							}

							if (status == 0) {
								Log.i("checkSession",
										"session not started or finished");
							} else {
								Log.i("checkStasion", "session in round "
										+ status.toString());
							}
						}
					}
				});
	}

	public void submitIdea(String groupName, String userName,
			List<String> ideasTexts, List<String> imagesList,
			final ProgressDialog progressDialog) {
		Builders.Any.B builder = Ion
				.with(this.context,
						this.formatURL(SUBMIT_IDEA, groupName, userName))
				.uploadProgressDialog(progressDialog)
				.uploadProgress(new ProgressCallback() {
					@Override
					public void onProgress(int uploaded, int total) {
						progressDialog.setProgress(uploaded);
						progressDialog.setMax(total);
					}
				});

		for (int i = 0; i < ideasTexts.size(); ++i) {
			if (ideasTexts.get(i) != null && !ideasTexts.get(i).isEmpty()) {
				builder.setMultipartParameter("text" + i, ideasTexts.get(i));
			}
		}

		for (int i = 0; i < imagesList.size(); ++i) {
			if (imagesList.get(i) != "" && imagesList.get(i) != null) {
				builder.setMultipartFile("image" + i,
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
						ResultFragment.resultStorage = result;
					}
				});
	}

	public void sessionResults(String groupName) {
		// TODO:
	}

}