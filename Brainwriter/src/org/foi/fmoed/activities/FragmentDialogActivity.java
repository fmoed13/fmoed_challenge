package org.foi.fmoed.activities;

import org.foi.fmoed.fragments.ModalIdeaTextFragment;
import org.foi.fmoed.fragments.ModalIdeaTextFragment.EditNameDialogListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class FragmentDialogActivity extends FragmentActivity implements EditNameDialogListener {

	public int idea_id;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ModalIdeaTextFragment editNameDialog = new ModalIdeaTextFragment();
        editNameDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
    	switch (this.idea_id) {
		case 1:
			IdeasActivity.idea1Text = inputText;
			break;
		case 2:
			IdeasActivity.idea2Text = inputText;
			break;
		case 3:
			IdeasActivity.idea3Text = inputText;
		default:
			break;
		}
    }
    
    public void passImage(String imagePath) {
    	switch (this.idea_id) {
		case 1:
			IdeasActivity.idea1Image = imagePath;
			break;
		case 2:
			IdeasActivity.idea2Image = imagePath;
			break;
		case 3:
			IdeasActivity.idea3Image = imagePath;
		default:
			break;
		}
    }
}