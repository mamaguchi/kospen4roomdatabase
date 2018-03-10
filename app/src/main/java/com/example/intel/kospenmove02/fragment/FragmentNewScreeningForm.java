package com.example.intel.kospenmove02.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.kospenmove02.MainActivity;
import com.example.intel.kospenmove02.R;
import com.example.intel.kospenmove02.db.entity.Screening;
import com.example.intel.kospenmove02.db.viewmodel.NewScreeningFormViewModel;
import com.example.intel.kospenmove02.validator.ValidationHelper;

import java.util.List;


public class FragmentNewScreeningForm extends Fragment {

    // MEMBER VARIBALES -> New-Screening-Form
    //TextInputLayout variables
    private TextInputLayout text_input_layout_ic;
    private TextInputLayout text_input_layout_weight;
    private TextInputLayout text_input_layout_height;
    private TextInputLayout text_input_layout_systolic;
    private TextInputLayout text_input_layout_diastolic;
    private TextInputLayout text_input_layout_dxt;
    private TextInputLayout text_input_layout_smoker;

    //EditText variables
    private EditText edit_text_ic;
    private EditText edit_text_weight;
    private EditText edit_text_height;
    private EditText edit_text_systolic;
    private EditText edit_text_diastolic;
    private EditText edit_text_dxt;
    private EditText edit_text_smoker;

    //Button
    private Button button_save;
    private Button button_show_db_screening;

    //Input validator
    private ValidationHelper validation;


    //ViewModel
    private NewScreeningFormViewModel mNewScreeningFormViewModel;

    //TextView
    private TextView text_view_display_db_screening;


    public FragmentNewScreeningForm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ViewModel
        mNewScreeningFormViewModel = ViewModelProviders.of(this).get(NewScreeningFormViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = initViews(inflater, container);
        initListeners();

        return rootView;
    }


    // To initialize views objects -> New-Screening-Form
    private View initViews(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_screening_form, container, false);

        text_input_layout_ic = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_screening_ic);
        text_input_layout_weight = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_screening_weight);
        text_input_layout_height = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_screening_height);
        text_input_layout_systolic = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_screening_systolic);
        text_input_layout_diastolic = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_screening_diastolic);
        text_input_layout_dxt = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_screening_dxt);
        text_input_layout_smoker = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_screening_smoker);

        edit_text_ic = (EditText) rootView.findViewById(R.id.edit_text_screening_ic);
        edit_text_weight = (EditText) rootView.findViewById(R.id.edit_text_screening_weight);
        edit_text_height = (EditText) rootView.findViewById(R.id.edit_text_screening_height);
        edit_text_systolic = (EditText) rootView.findViewById(R.id.edit_text_screening_systolic);
        edit_text_diastolic = (EditText) rootView.findViewById(R.id.edit_text_screening_diastolic);
        edit_text_dxt = (EditText) rootView.findViewById(R.id.edit_text_screening_dxt);
        edit_text_smoker = (EditText) rootView.findViewById(R.id.edit_text_screening_smoker);

        button_save = (Button) rootView.findViewById(R.id.button_screening_save);
        button_show_db_screening = (Button) rootView.findViewById(R.id.button_show_db_screening);

        text_view_display_db_screening = (TextView) rootView.findViewById(R.id.text_view_display_db_screening);

        validation = new ValidationHelper(getContext());

        return rootView;
    }


    // To initialize listeners -> New-Kospenuser-Registration-Form
    private void initListeners() {
        // SignUp Button-Listener
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation() == 0) {
                    insertNewScreening();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("currentItem", 1);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), getString(R.string.fail_form_input_invalid), Toast.LENGTH_LONG).show();
                }

            }
        });

        // SignUp Button-Listener
        button_show_db_screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewScreeningFormViewModel.getLiveDataScreenings().observe(getActivity(),
                        new Observer<List<Screening>>() {
                            @Override
                            public void onChanged(@Nullable List<Screening> screenings) {

                                StringBuilder sb = new StringBuilder();

                                for (Screening screening : screenings) {
                                    sb.append("_Id:");
                                    sb.append(screening.getId());
                                    sb.append("_Ic:");
                                    sb.append(screening.getFk_ic());
                                    sb.append("_SoftDel:");
                                    sb.append(screening.isSoftDel());
                                    sb.append("\n");
                                }
                                text_view_display_db_screening.setText(sb.toString());
                            }
                        });
            }
        });


        // editTextGender EditText-Listener
        edit_text_smoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);

                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.smoker_yes:
                                edit_text_smoker.setText(R.string.menu_smoker_yes);
                                return true;
                            case R.id.smoker_no:
                                edit_text_smoker.setText(R.string.menu_smoker_no);
                                return true;
                            default:
                                return false;
                        }
                    }
                };
                popup.setOnMenuItemClickListener(onMenuItemClickListener);
                popup.inflate(R.menu.menu_smoker);
                popup.show();
            }
        });



    }


    // method for validation of form on sign up button click -> New-Kospenuser-Registration-Form
    private int checkValidation() {
        if (!validation.isEditTextFilled(edit_text_ic, text_input_layout_ic, getString(R.string.error_message_screening_ic))) {
            return 1;
        }

        if (!validation.isEditTextFilled(edit_text_weight, text_input_layout_weight, getString(R.string.error_message_screening_weight))) {
            return 1;
        }

        if (!validation.isEditTextFilled(edit_text_height, text_input_layout_height, getString(R.string.error_message_screening_height))) {
            return 1;
        }

        if (!validation.isEditTextFilled(edit_text_systolic, text_input_layout_systolic, getString(R.string.error_message_screening_systolic))) {
            return 1;
        }

        if (!validation.isEditTextFilled(edit_text_diastolic, text_input_layout_diastolic, getString(R.string.error_message_screening_diastolic))) {
            return 1;
        }

        if (!validation.isEditTextFilled(edit_text_dxt, text_input_layout_dxt, getString(R.string.error_message_screening_dxt))) {
            return 1;
        }

        if (!validation.isEditTextFilled(edit_text_smoker, text_input_layout_smoker, getString(R.string.error_message_screening_smoker))) {
            return 1;
        }

        Toast.makeText(getContext(), getString(R.string.success_screening_input_validation_message), Toast.LENGTH_LONG).show();
        return 0;
    }


    // Insert new kospenuser data into Android-DB
    private void insertNewScreening() {

        mNewScreeningFormViewModel.insertNewScreening(edit_text_ic, edit_text_weight, edit_text_height,
                edit_text_systolic, edit_text_diastolic, edit_text_dxt, edit_text_smoker);
    }






}
