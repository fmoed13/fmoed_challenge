package org.foi.fmoed.fragments;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.IdeasActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ModalIdeaTextFragment extends DialogFragment implements OnEditorActionListener {

	    public interface EditNameDialogListener {
	        void onFinishEditDialog(String inputText);
	    }

	    private EditText mEditText;
	    private Button btnSaveDesc;

	    public ModalIdeaTextFragment() {
	        // Empty constructor required for DialogFragment
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.modal_idea_text, container);
	        mEditText = (EditText) view.findViewById(R.id.txt_idea_desc);
	        btnSaveDesc = (Button) view.findViewById(R.id.btn_save_txt);
	        btnSaveDesc.setOnClickListener(saveTextBtnListener);
			
	        getDialog().setTitle("Description");

	        // Show soft keyboard automatically
	        mEditText.requestFocus();
	        getDialog().getWindow().setSoftInputMode(
	                LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	        mEditText.setOnEditorActionListener(this);

	        return view;
	    }
	    
		private OnClickListener saveTextBtnListener = new OnClickListener() {

		    @Override
		    public void onClick(final View v) {
		    }
		};


		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	        if (EditorInfo.IME_ACTION_DONE == actionId) {
	            // Return input text to activity
	            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
	            activity.onFinishEditDialog(mEditText.getText().toString());
	            this.dismiss();
	            return true;
	        }
	        return false;
	}
}