package org.foi.fmoed.activities;

import org.foi.fmoed.R;
import org.foi.fmoed.fragments.ModalIdeaTextFragment;
import org.foi.fmoed.fragments.ModalIdeaTextFragment.EditNameDialogListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

public class FragmentDialogActivity extends FragmentActivity implements EditNameDialogListener {

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
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }
}