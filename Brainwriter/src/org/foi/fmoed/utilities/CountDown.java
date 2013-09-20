package org.foi.fmoed.utilities;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.IdeasActivity;
import org.foi.fmoed.managers.SessionManager;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

//countdowntimer is an abstract class, so extend it and fill in methods
public class CountDown extends CountDownTimer {
	
	private Context context;
	private TextView boxTime;
	private IdeasActivity ideasActivity;
	private SessionManager sessionManager;
	
	public long currentState;
	public static long tmpCurrentState;
	public static HashMap<String, CountDown> countDownCache = new HashMap<String, CountDown>();
	
	/**
	 * Main constructor of CountDown
	 * @param ctx Context of activity
	 */
	public CountDown(Context ctx) {
		// interval 1s, time 5m
		super(tmpCurrentState, 1000);
		context = ctx;
		ideasActivity = (IdeasActivity) context;
		sessionManager = new SessionManager(context);
		countDownCache.put(IdeasActivity.groupName, this);
	}
	
	/**
	 * Static method for reseting counter of some group
	 * @param groupName Name of group
	 * @param ctx Context of activity
	 */
	public static void resetCountDown(String groupName, Context ctx) {
		CountDown _countDown = CountDown.countDownCache.get(groupName);
		if(_countDown != null) {
			CountDown.tmpCurrentState = _countDown.currentState;
			_countDown.cancel();
			CountDown.countDownCache.remove(groupName);
		}
		CountDown.tmpCurrentState = 330000;
		CountDown countDown = new CountDown(ctx);
		countDown.start();
	}

	/**
	 * Method for getting String with rest minutes and seconds
	 * @return String
	 */
	public String getRestMinutesString() {
		return String.format(Locale.getDefault(), "%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(this.currentState),
			    TimeUnit.MILLISECONDS.toSeconds(this.currentState) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.currentState))
			);
	}
	
	/**
	 * Method is executed when countDown is finished
	 */
	@Override
	public void onFinish() {
		if(!ideasActivity.submitted) {
			if (boxTime == null) {
				boxTime = (TextView) ideasActivity.findViewById(R.id.group_time);
			}
			boxTime.setText(String.valueOf(0));
			sessionManager
					.submitIdea(IdeasActivity.groupName,
							ideasActivity.settingsManager.getUserName(),
							ideasActivity.getIdeasTextsList(),
							ideasActivity.getIdeasPictureList(),
							ideasActivity.progressDialog);
		}
	}

	/**
	 * Method which is called periodically
	 */
	@Override
	public void onTick(long millisUntilFinished) {
		this.currentState = millisUntilFinished;
		if(!ideasActivity.submitted) {
			if (boxTime == null) {
				boxTime = (TextView) ideasActivity.findViewById(R.id.group_time);
			}
			if (boxTime != null) {
				boxTime.setText(getRestMinutesString());
			}

		}
	}
}