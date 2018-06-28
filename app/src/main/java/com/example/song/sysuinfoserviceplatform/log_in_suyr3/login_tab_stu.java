package com.example.song.sysuinfoserviceplatform.log_in_suyr3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.song.sysuinfoserviceplatform.R;

import org.greenrobot.eventbus.EventBus;


public class login_tab_stu extends Fragment {
    private LoginActivity_suyr3.UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mStuIDView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_frame_view = inflater.inflate(R.layout.fragment_login_tab_stu, null);

        mStuIDView = root_frame_view.findViewById(R.id.email);
        mPasswordView = root_frame_view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin(textView);
                    return true;
                }
                return false;
            }
        });
        Button mSignInButton = root_frame_view.findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin(view);
                //Intent intent = new Intent("android.intent.action.Student_Page");
                //startActivity(intent);

            }
        });
        return root_frame_view;
    }

    private void attemptLogin(View view) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mStuIDView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mStuIDView.getText().toString();
        String password = mPasswordView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (password.length() < 1) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mStuIDView.setError(getString(R.string.error_field_required));
            focusView = mStuIDView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //TODO
            //mAuthTask = new LoginActivity_suyr3.UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
            EventBus.getDefault().post(new LoginEvent("stu", email, password));

        }
    }


}
