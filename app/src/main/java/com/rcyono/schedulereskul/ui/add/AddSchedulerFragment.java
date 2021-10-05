package com.rcyono.schedulereskul.ui.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.schedule.Schedule;
import com.rcyono.schedulereskul.model.type.Type;
import com.rcyono.schedulereskul.model.user.User;
import com.rcyono.schedulereskul.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddSchedulerFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private final ArrayList<String> arrayTypeDrop = new ArrayList<>();
    private AutoCompleteTextView tvTypeEskul, tvDate, tvTimeStart, tvTimeEnd;
    private TextInputEditText edtDesc, edtPlace;
    private Button btnSave;
    private ImageButton btnDelete;
    private ProgressBar progressBar;

    private BottomAppBar bottomAppBar;
    private FloatingActionButton fabAdd;

    private AddViewModel addViewModel;
    private User user;
    private Schedule schedule;
    private int action;

    private final static int ALERT_ACTION_DELETE = 10;
    private final static int ALERT_ACTION_CLOSE = 20;
    private String titleDialog;

    private SweetAlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_scheduler, container, false);
        toolbar = view.findViewById(R.id.main_toolbar);
        tvTypeEskul = view.findViewById(R.id.tv_title_eskul);
        edtDesc = view.findViewById(R.id.edt_desc);
        edtPlace = view.findViewById(R.id.edt_place);
        tvDate = view.findViewById(R.id.edt_date);
        tvTimeStart = view.findViewById(R.id.edt_time_start);
        tvTimeEnd = view.findViewById(R.id.edt_time_end);
        btnSave = view.findViewById(R.id.btn_save);
        progressBar = view.findViewById(R.id.progress_bar);
        btnDelete = view.findViewById(R.id.btn_delete);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        btnSave.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTimeStart.setOnClickListener(this);
        tvTimeEnd.setOnClickListener(this);

        AppPreferences preferences = new AppPreferences(requireActivity());
        user = preferences.getUser();

        String title;
        String btnTitle;
        schedule = AddSchedulerFragmentArgs.fromBundle(getArguments()).getScheduler();
        if (schedule != null) {
            action = 1;
            tvTypeEskul.setText(schedule.getTitleType());
            edtDesc.setText(schedule.getDesc());
            edtPlace.setText(schedule.getPlace());
            tvDate.setText(schedule.getDate());
            tvTimeStart.setText(schedule.getTimeStart());
            tvTimeEnd.setText(schedule.getTimeEnd());
            titleDialog = getString(R.string.update_success);
            title = getString(R.string.update_title);
            btnTitle = getString(R.string.update);
            btnDelete.setVisibility(View.VISIBLE);

            if (getParentFragment() != null) {
                bottomAppBar = getParentFragment().requireActivity().findViewById(R.id.layout_bottom_app_bar);
                fabAdd = getParentFragment().requireActivity().findViewById(R.id.fab_add);
                bottomAppBar.setVisibility(View.GONE);
                fabAdd.setVisibility(View.GONE);
            }
        }else {
            schedule = new Schedule();
            titleDialog = getString(R.string.add_schedule);
            title = getString(R.string.add_title);
            btnTitle = getString(R.string.save);
        }

        toolbar.setTitle(title);
        btnSave.setText(btnTitle);
        btnDelete.setOnClickListener(this);

        addViewModel = new AddViewModel();
        addViewModel.getType().observe(requireActivity(), type -> {
            for (Type arr : type.getType()) {
                arrayTypeDrop.add(arr.getTitleType());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_list_drop, arrayTypeDrop);
                tvTypeEskul.setAdapter(adapter);
            }
        });

        addViewModel.addSchedule().observe(requireActivity(), response -> {
            if (response.getSuccess() == 1) {
                alertDialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setTitleText(titleDialog);
                alertDialog.setContentText(getString(R.string.next));
            } else {
                alertDialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE);
                alertDialog.setTitleText(getString(R.string.action_failed));
                alertDialog.setContentText(getString(R.string.message_error));
            }
            alertDialog.show();
            alertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setVisibility(View.GONE);
        });

        addViewModel.isLoading().observe(requireActivity(), load -> {
            if (load) {
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            schedule.setIdUser(user.getId());
            schedule.setTitleType(tvTypeEskul.getText().toString());
            String date = tvDate.getText().toString();
            String timeStart = tvTimeStart.getText().toString();
            String timeEnd = tvTimeEnd.getText().toString();
            boolean isEmptyField = false;
            if (Objects.requireNonNull(edtDesc.getText()).toString().isEmpty()) {
                edtDesc.setError(requireActivity().getResources().getString(R.string.field_not_empty));
                isEmptyField = true;
            } else if (Objects.requireNonNull(edtPlace.getText()).toString().isEmpty()) {
                edtPlace.setError(requireActivity().getResources().getString(R.string.field_not_empty));
                isEmptyField = true;
            } else if (date.isEmpty() || timeStart.isEmpty() || timeEnd.isEmpty()) {
                isEmptyField = true;
            }
            schedule.setDesc(Objects.requireNonNull(edtDesc.getText().toString()));
            schedule.setPlace(Objects.requireNonNull(edtPlace.getText()).toString());
            schedule.setDate(date);
            schedule.setTimeStart(timeStart);
            schedule.setTimeEnd(timeEnd);
            if (!isEmptyField) {
                if (action == 0) {
                    addViewModel.addSchedule(schedule);
                }else {
                    addViewModel.updateSchedule(schedule);
                }
            }
        } else if (view.getId() == R.id.edt_date) {
            Calendar calendar = Calendar.getInstance();
            final  int year;
            final int month;
            final int day;
            if (action == 0) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }else {
                String[] date = schedule.getDate().split("/");
                year = Integer.parseInt(date[2]);
                month = Integer.parseInt(date[1]);
                day = Integer.parseInt(date[0]);
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireActivity(), (datePicker, years, months, days) -> {
                months = months + 1;
                String date = days + "/" + months + "/" + years;
                tvDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        } else if (view.getId() == R.id.edt_time_start) {
            getTime(1);
        } else if (view.getId() == R.id.edt_time_end) {
            getTime(0);
        } else if (view.getId() == R.id.btn_delete) {
            titleDialog = getString(R.string.delete_title);
            showAlertDelete(ALERT_ACTION_DELETE);
        }else if (view.getId() == android.R.id.home) {
            showAlertDelete(ALERT_ACTION_CLOSE);
        }
    }

    private void showAlertDelete(int type) {
        boolean isDelete = type == ALERT_ACTION_CLOSE;
        alertDialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE);
        alertDialog.setTitleText(getString(R.string.are_you_sure));
        alertDialog.setContentText(getString(R.string.content_text));
        alertDialog.setConfirmText(getString(R.string.confirm_delete));
        alertDialog.setConfirmClickListener(sDialog -> {
            sDialog.dismissWithAnimation();
            if (!isDelete) {
                addViewModel.deleteSchedule(schedule.getId());
                tvTypeEskul.setText("");
                edtDesc.setText("");
                edtPlace.setText("");
                tvDate.setText("");
                tvTimeStart.setText("");
                tvTimeEnd.setText("");
            }
        }).setCancelButton(getString(R.string.cancel), SweetAlertDialog::dismissWithAnimation).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getTime(int action) {
        Calendar calendar = Calendar.getInstance();
        final int hour;
        final int minute;
        if (this.action == 0) {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        }else {
            String[] timeStart;
            if (action == 1) {
                timeStart = schedule.getTimeStart().split(":");
            }else {
                timeStart = schedule.getTimeEnd().split(":");
            }
            hour = Integer.parseInt(timeStart[0]);
            minute = Integer.parseInt(timeStart[1]);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireActivity(), (timePicker, hours, minutes) -> {
            String time = hours + ":" + minutes;
            if (action == 1) {
                tvTimeStart.setText(time);
            } else {
                tvTimeEnd.setText(time);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (schedule.getId() != null) {
            bottomAppBar.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.VISIBLE);
        }
    }
}