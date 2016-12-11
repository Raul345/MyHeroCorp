package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.AlertDialogFragment;
import com.herocorp.ui.utility.CustomViewParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class EditPersonalInfoFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private View rootView;
    private CustomViewParams customViewParams;
    private int mPage;

    EditText firstname_et, lastname_et, mobile_et, email_et, age_et;
    Spinner gender_spinner;

    String arr_gender[] = {"M", "F"};
    String firstname, lastname, mobile, age, email, gender;

    SharedPreferences mypref;
    SharedPreferences.Editor edit;


    public static EditPersonalInfoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);

        EditPersonalInfoFragment fragment = new EditPersonalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_editpersonalinfo_fragment, container, false);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        mypref = getActivity().getSharedPreferences("herocorp", 0);
        edit = mypref.edit();

        TextView firstname_tv = (TextView) rootView.findViewById(R.id.firstname_textview);

        firstname_tv.setText(colortext("First Name", "*"));

        TextView lastname_tv = (TextView) rootView.findViewById(R.id.lastname_textview);

        lastname_tv.setText(colortext("Last Name", "*"));

        TextView mobile_tv = (TextView) rootView.findViewById(R.id.mobile_textview);

        mobile_tv.setText(colortext("Mobile", "*"));

        TextView age_tv = (TextView) rootView.findViewById(R.id.age_textview);

        age_tv.setText(colortext("Age", "*"));

        TextView gender_tv = (TextView) rootView.findViewById(R.id.gender_textview);

        gender_tv.setText(colortext("Gender", "*"));

        firstname_et = (EditText) rootView.findViewById(R.id.firstname_edittext);
        lastname_et = (EditText) rootView.findViewById(R.id.lastname_edittext);
        mobile_et = (EditText) rootView.findViewById(R.id.mobile_edittext);
        age_et = (EditText) rootView.findViewById(R.id.age_edittext);
        email_et = (EditText) rootView.findViewById(R.id.email_edittext);
        gender_spinner = (Spinner) rootView.findViewById(R.id.gender_spinner);
        fetch_data();

        ArrayAdapter<String> at = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, arr_gender);
        gender_spinner.setAdapter(at);

        firstname_et.setText(firstname);
        lastname_et.setText(lastname);
        mobile_et.setText(mobile);
        age_et.setText(age);
        email_et.setText(email);
        gender_spinner.setSelection(((ArrayAdapter<String>) gender_spinner.getAdapter()).getPosition(gender));

        firstname_et.setEnabled(false);
       /* firstname_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(firstname_et.getText().toString())) {
                    } else {
                        edit.putString("firstname", firstname_et.getText().toString());
                        edit.commit();
                    }
                }
            }
        })
        ;*/

        lastname_et.setEnabled(false);
        /*lastname_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                        edit.putString("lastname", lastname_et.getText().toString());
                        edit.commit();
                    }


        });
*/
       mobile_et.setEnabled(false);
     /*   mobile_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    edit.putString("mobile", mobile_et.getText().toString());
                edit.commit();
            }
        });
*/
        age_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    edit.putString("age", age_et.getText().toString());
                edit.commit();
            }
        });

        email_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    edit.putString("email", email_et.getText().toString());
                edit.commit();
            }
        });

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                edit.putString("gender", gender);
                edit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = "--select--";
            }
        });


    }

    public SpannableStringBuilder colortext(String simple, String colored) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        }
    }

    public void fetch_data() {
      /*  final Bundle bundle = getArguments();
        firstname = bundle.getString("fname");
        lastname = bundle.getString("lname");
        mobile = bundle.getString("mobile");
        age = bundle.getString("age");
        gender = bundle.getString("sex");
        email = bundle.getString("email");
*/
       /* edit.putString("firstname", firstname);
        edit.putString("lastname", lastname);
        edit.putString("mobile", mobile);
        edit.putString("age", age);
        edit.putString("email", gender);
        edit.putString("gender", email);
        edit.commit();

*/
        if (mypref.contains("firstname")) {
            firstname = mypref.getString("firstname", "");
        }
        if (mypref.contains("lastname")) {
            lastname = mypref.getString("lastname", "");
        }
        if (mypref.contains("mobile")) {
            mobile = mypref.getString("mobile", "");
        }
        if (mypref.contains("age")) {
            age = mypref.getString("age", "");
        }
        if (mypref.contains("email")) {
            email = mypref.getString("email", "");
        }
        if (mypref.contains("gender")) {
            gender = mypref.getString("gender", "");
        }
        if (email.equalsIgnoreCase("null"))
            email = "";
        if (age.equalsIgnoreCase("null"))
            age = "";
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}