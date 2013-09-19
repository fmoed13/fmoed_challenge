package org.foi.fmoed.utilities;

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

	public CountDown(Context ctx) {
		// interval 1s, time 5m
		super(300000, 1000);
		context = ctx;
		ideasActivity = (IdeasActivity) context;
		sessionManager = new SessionManager(context);
	}

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
							ideasActivity.progressDialog);
		}
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if(!ideasActivity.submitted) {
			if (boxTime == null) {
				boxTime = (TextView) ideasActivity.findViewById(R.id.group_time);
			}
			String timeAvailable = String.format(Locale.getDefault(), "Time: %d min, %d sec", 
				    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
				    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
				);
			boxTime.setText(timeAvailable);
		} else {
			this.cancel();
		}
	}
}