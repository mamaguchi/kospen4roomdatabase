package com.example.intel.kospenmove02.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.kospenmove02.MainActivity;
import com.example.intel.kospenmove02.R;
import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.db.viewmodel.NewKospenuserFormViewModel;
import com.example.intel.kospenmove02.singleton.ViewPagerSingleton;
import com.example.intel.kospenmove02.validator.ValidationHelper;

import java.util.List;


public class FragmentNewKospenuserForm extends Fragment {

    // MEMBER VARIBALES -> New-Kospenuser-Registration-Form
    //TextInputLayout variables
    private TextInputLayout textInputLayoutIc;
    private TextInputLayout textInputLayoutKospenuserName;
    private TextInputLayout textInputLayoutAddress;

    //EditText variables
    private EditText editTextIc;
    private EditText editTextKospenuserName;
    private EditText editTextAddress;

    //Button
    private Button buttonSignUp;
    private Button buttonShowDb;

    //Input validator
    private ValidationHelper validation;

    //DatePickerDialog
    private EditText editTextBirthday;
    private DatePickerDialog datePickerDialog;

    //TextInputLayout for Menu
    private TextInputLayout text_input_layout_gender;
    private TextInputLayout text_input_layout_state;
    private TextInputLayout text_input_layout_region;
    private TextInputLayout text_input_layout_subregion;
    private TextInputLayout text_input_layout_locality;

    //EditText for Menu
    private EditText editTextGender;
    private EditText editTextState;
    private EditText editTextRegion;
    private EditText editTextSubregion;
    private EditText editTextLocality;

    //ViewModel
    private NewKospenuserFormViewModel mNewKospenuserFormViewModel;

    //TextView
    private TextView textViewDisplayDb;


    public FragmentNewKospenuserForm() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ViewModel
        mNewKospenuserFormViewModel = ViewModelProviders.of(this).get(NewKospenuserFormViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = initViews(inflater, container);
        initListeners();

        return rootView;
    }


    // To initialize views objects -> New-Kospenuser-Registration-Form
    private View initViews(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_kospenuser_form, container, false);

        textInputLayoutIc = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_ic);
        textInputLayoutKospenuserName = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_kospenuser_name);
        textInputLayoutAddress = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_address);

        editTextIc = (EditText) rootView.findViewById(R.id.edit_text_ic);
        editTextKospenuserName = (EditText) rootView.findViewById(R.id.edit_text_kospenuser_name);
        editTextAddress = (EditText) rootView.findViewById(R.id.edit_text_address);

        buttonSignUp = (Button) rootView.findViewById(R.id.button_sign_up);
        buttonShowDb = (Button) rootView.findViewById(R.id.button_show_db);

        textViewDisplayDb = (TextView) rootView.findViewById(R.id.text_view_display_db);

        validation = new ValidationHelper(getContext());

        editTextBirthday = (EditText) rootView.findViewById(R.id.edit_text_birthday);// TO-DO : to be removed later.

        text_input_layout_gender = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_gender);
        editTextGender = (EditText) rootView.findViewById(R.id.edit_text_gender);

        text_input_layout_state = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_state);
        text_input_layout_region = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_region);
        text_input_layout_subregion = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_subregion);
        text_input_layout_locality = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_locality);

        editTextState = (EditText) rootView.findViewById(R.id.edit_text_state);
        editTextRegion = (EditText) rootView.findViewById(R.id.edit_text_region);
        editTextSubregion = (EditText) rootView.findViewById(R.id.edit_text_subregion);
        editTextLocality = (EditText) rootView.findViewById(R.id.edit_text_locality);

        return rootView;
    }


    // To initialize listeners -> New-Kospenuser-Registration-Form
    private void initListeners() {
        // SignUp Button-Listener
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation() == 0) {
                    insertNewKospenuser();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("currentItem", 1);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), getString(R.string.fail_form_input_invalid), Toast.LENGTH_LONG).show();
                }

            }
        });

        // SignUp Button-Listener
        buttonShowDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewKospenuserFormViewModel.getLiveDataKospenusers().observe(getActivity(),
                        new Observer<List<Kospenuser>>() {
                            @Override
                            public void onChanged(@Nullable List<Kospenuser> kospenusers) {

                                StringBuilder sb = new StringBuilder();

                                for (Kospenuser kospenuser : kospenusers) {
                                    sb.append(kospenuser.getName());
                                    sb.append("_Addr:");
                                    sb.append(kospenuser.getAddress());
                                    sb.append("_Date:");
                                    sb.append(kospenuser.getTimestamp());
                                    sb.append("_Version:");
                                    sb.append(kospenuser.getVersion());
                                    sb.append("_Dirty:");
                                    sb.append(kospenuser.isDirty());
                                    sb.append("_SoftDel:");
                                    sb.append(kospenuser.isSoftDel());
                                    sb.append("\n");
                                }
                                textViewDisplayDb.setText(sb.toString());
                            }
                        });
            }
        });

        // editTextBirthday EditText-Listener
        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editTextBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                };
                datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        // editTextGender EditText-Listener
        editTextGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);

                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.gender_male:
                                editTextGender.setText(R.string.menu_male);
                                return true;
                            case R.id.gender_female:
                                editTextGender.setText(R.string.menu_female);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                popup.setOnMenuItemClickListener(onMenuItemClickListener);
                popup.inflate(R.menu.menu_gender);
                popup.show();
            }
        });

        // editTextState EditText-Listener
        editTextState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);

                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.state_pahang:
                                editTextState.setText(R.string.menu_state_pahang);
                                return true;
                            case R.id.state_nonpahang:
                                editTextState.setText(R.string.menu_state_nonpahang);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                popup.setOnMenuItemClickListener(onMenuItemClickListener);
                popup.inflate(R.menu.menu_state);
                popup.show();
            }
        });

        // editTextRegion EditText-Listener
        editTextRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);

                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.region_maran:
                                editTextRegion.setText(R.string.menu_region_maran);
                                return true;
                            case R.id.region_jerantut:
                                editTextRegion.setText(R.string.menu_region_jerantut);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                popup.setOnMenuItemClickListener(onMenuItemClickListener);
                popup.inflate(R.menu.menu_region);
                popup.show();
            }
        });

        // editTextSubregion EditText-Listener
        editTextSubregion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);

                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.subregion_jengka2:
                                editTextSubregion.setText(R.string.menu_subregion_jengka2);
                                return true;
                            case R.id.subregion_maran:
                                editTextSubregion.setText(R.string.menu_subregion_maran);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                popup.setOnMenuItemClickListener(onMenuItemClickListener);
                popup.inflate(R.menu.menu_subregion);
                popup.show();
            }
        });

        // editTextLocality EditText-Listener
        editTextLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);

                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.locality_ulujempol:
                                editTextLocality.setText(R.string.menu_locality_ulujempol);
                                return true;
                            case R.id.locality_jengka6:
                                editTextLocality.setText(R.string.menu_locality_jengka6);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                popup.setOnMenuItemClickListener(onMenuItemClickListener);
                popup.inflate(R.menu.menu_locality);
                popup.show();
            }
        });

    }


    // method for validation of form on sign up button click -> New-Kospenuser-Registration-Form
    private int checkValidation() {
        if (!validation.isEditTextFilled(editTextIc, textInputLayoutIc, getString(R.string.error_message_ic))) {
            return 1;
        }

        if (!validation.isEditTextFilled(editTextKospenuserName, textInputLayoutKospenuserName, getString(R.string.error_message_name))) {
            return 1;
        }

        if (!validation.isEditTextFilled(editTextAddress, textInputLayoutAddress, getString(R.string.error_message_address))) {
            return 1;
        }

        if (!validation.isEditTextFilled(editTextGender, text_input_layout_gender, getString(R.string.error_message_gender))) {
            return 1;
        }

        if (!validation.isEditTextFilled(editTextState, text_input_layout_state, getString(R.string.error_message_state))) {
            return 1;
        }

        if (!validation.isEditTextFilled(editTextRegion, text_input_layout_region, getString(R.string.error_message_region))) {
            return 1;
        }

        if (!validation.isEditTextFilled(editTextSubregion, text_input_layout_subregion, getString(R.string.error_message_subregion))) {
            return 1;
        }

        if (!validation.isEditTextFilled(editTextLocality, text_input_layout_locality, getString(R.string.error_message_locality))) {
            return 1;
        }

        Toast.makeText(getContext(), getString(R.string.success_message), Toast.LENGTH_LONG).show();
        return 0;
    }


    // Insert new kospenuser data into Android-DB
    private void insertNewKospenuser() {

        mNewKospenuserFormViewModel.insertNewKospenuser(editTextIc, editTextKospenuserName, editTextAddress,
                editTextGender, editTextState, editTextRegion, editTextSubregion, editTextLocality);
    }



}