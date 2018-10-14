package in.org.celesta2k17.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.annotation.TargetApi;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindViews;
import butterknife.ButterKnife;
import in.org.celesta2k17.R;

public class LogInFragment extends AuthFragment{

    @BindViews(value = {R.id.email_input_edit,R.id.password_input_edit})
    protected List<TextInputEditText> views;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    String mUrl;
    SharedPreferences.Editor sharedPreferences;
    String mEmail;
    String mPassword;
    TextInputLayout emailIDWrapper;
    TextInputLayout passwordWrapper;
    TextView forgotPassword;
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    RequestQueue mQueue;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        caption.setText(getString(R.string.log_in_label));
        mUrl = getString(R.string.url_login);
        emailIDWrapper = view.findViewById(R.id.email_input);
        passwordWrapper = view.findViewById(R.id.password_input);
        view.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_log_in));
        forgotPassword = (TextView)view.findViewById(R.id.forgot_password);
        for (TextInputEditText editText : views) {
            if (editText.getId() == R.id.password_input_edit) {
                final TextInputLayout inputLayout = ButterKnife.findById(view, R.id.password_input);
                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                inputLayout.setTypeface(boldTypeface);
                editText.addTextChangedListener(new TextWatcherAdapter() {
                    @Override
                    public void afterTextChanged(Editable editable) {
                        inputLayout.setPasswordVisibilityToggleEnabled(editable.length() > 0);
                    }
                });
            }
            editText.setOnFocusChangeListener((temp, hasFocus) -> {
                if (!hasFocus) {
                    boolean isEnabled = editText.getText().length() > 0;
                    editText.setSelected(isEnabled);
                }
            });
        }
        mQueue = Volley.newRequestQueue(getContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

        forgotPassword.setOnClickListener(v -> {
            Uri webpage = Uri.parse(getString(R.string.url_forgot_password));
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(intent);
        });

        caption.setOnClickListener(v -> {
            clearErrors();
            boolean b = validateInputs();
            if (b) {
                //Code for sending the details
                Toast.makeText(getContext(), "Logging in..", Toast.LENGTH_SHORT).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, mUrl,
                        response -> {
                            Log.v("Response:", response);
                            Log.v("email:", mPassword);
                            Log.v("pass:", mEmail);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = Integer.parseInt(jsonObject.getString(getString(R.string.JSON_status)));

                                switch (status) {
                                    case 200:
                                        Toast.makeText(getContext(), "Log In Successful", Toast.LENGTH_LONG).show();
                                        int userID = Integer.parseInt(jsonObject.getString("userID"));
                                        String name = jsonObject.getString("name");
                                        String college = jsonObject.getString("college");
//                                        String events = jsonObject.getString("events");
                                        sharedPreferences.putBoolean(getString(R.string.login_status), true);
                                        sharedPreferences.putString(getString(R.string.full_name), name);
                                        sharedPreferences.putString(getString(R.string.id), userID + "");
                                        sharedPreferences.putString(getString(R.string.college_name), college);
//                                                sharedPreferences.putString(getString(R.string.event_participated) , events);
                                        sharedPreferences.apply();
                                        getActivity().finish();
                                        break;
                                    case 400:
                                        Toast.makeText(getContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 409:
                                        Toast.makeText(getContext(), R.string.message_registration_duplicate, Toast.LENGTH_LONG).show();
                                        getActivity().finish();
                                        break;
                                    case 403:
                                        Toast.makeText(getContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                                        getActivity().finish();
                                        break;
                                    default:
                                        Toast.makeText(getContext(), "Error logging in. Please try again later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            Log.v("Error : ", error.toString());
                            error.printStackTrace();
                            Toast.makeText(getContext(), "Error logging in. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put(getString(R.string.register_param_emailid), mEmail);
                        params.put(getString(R.string.register_param_password), mPassword);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                };
                mQueue.add(stringRequest);
            }
        });

    }

    @Override
    public int authLayout() {
        return R.layout.login_fragment;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void fold() {
        lock=false;
        Rotate transition = new Rotate();
        transition.setEndAngle(-90f);
        transition.addTarget(caption);
        TransitionSet set=new TransitionSet();
        set.setDuration(getActivity().getResources().getInteger(R.integer.duration));
        ChangeBounds changeBounds=new ChangeBounds();
        set.addTransition(changeBounds);
        set.addTransition(transition);
        TextSizeTransition sizeTransition=new TextSizeTransition();
        sizeTransition.addTarget(caption);
        set.addTransition(sizeTransition);
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        final float padding=getActivity().getResources().getDimension(R.dimen.folded_label_padding)/2;
        set.addListener(new Transition.TransitionListenerAdapter(){
            @Override
            public void onTransitionEnd(Transition transition) {
                super.onTransitionEnd(transition);
                caption.setTranslationX(-padding);
                caption.setRotation(0);
                caption.setVerticalText(true);
                caption.requestLayout();

            }
        });
        TransitionManager.beginDelayedTransition(parent,set);
        caption.setTextSize(TypedValue.COMPLEX_UNIT_PX,caption.getTextSize()/2);
        caption.setTextColor(Color.WHITE);
        ConstraintLayout.LayoutParams params=getParams();
        params.leftToLeft= ConstraintLayout.LayoutParams.UNSET;
        params.verticalBias=0.5f;
        caption.setLayoutParams(params);
        caption.setTranslationX(caption.getWidth()/8-padding);
    }

    @Override
    public void clearFocus() {
        for(View view:views) view.clearFocus();
    }
    private void clearErrors() {
        emailIDWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
    }

    private boolean validateInputs() {
        if (isAnyFieldEmpty())
            return false;

        mEmail = Objects.requireNonNull(emailIDWrapper.getEditText()).getText().toString();
        mPassword = Objects.requireNonNull(passwordWrapper.getEditText()).getText().toString();

        return true;
    }

    private boolean isAnyFieldEmpty() {
        boolean flag = false;
        if(TextUtils.isEmpty(Objects.requireNonNull(emailIDWrapper.getEditText()).getText().toString()) && TextUtils.isEmpty(Objects.requireNonNull(passwordWrapper.getEditText()).getText().toString()))
        {
            flag = true;
            Toast.makeText(getContext(), "Required fields", Toast.LENGTH_SHORT).show();

        }
        else {
            if (TextUtils.isEmpty(Objects.requireNonNull(emailIDWrapper.getEditText()).getText().toString())) {
                flag = true;
                Toast.makeText(getContext(), "Required field Email", Toast.LENGTH_SHORT).show();
//            emailIDWrapper.setError(getString(R.string.error_empty_field));
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(passwordWrapper.getEditText()).getText().toString())) {
                flag = true;
                Toast.makeText(getContext(), "Required field Password", Toast.LENGTH_SHORT).show();

//            passwordWrapper.setError(getString(R.string.error_empty_field));
            }
        }
        return flag;
    }

    private void setHints() {
        emailIDWrapper.setHint(getString(R.string.email_id_hint));
        passwordWrapper.setHint(getString(R.string.password_hint));
    }
}
