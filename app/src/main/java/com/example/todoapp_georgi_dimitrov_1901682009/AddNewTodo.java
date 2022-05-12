package com.example.todoapp_georgi_dimitrov_1901682009;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp_georgi_dimitrov_1901682009.Context.DbContext;
import com.example.todoapp_georgi_dimitrov_1901682009.Model.ToDoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddNewTodo extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewToDo";

    private Button _saveButton;
    private EditText _editText;
    private DbContext _db;

    public static AddNewTodo newInstance(){
        return new AddNewTodo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_todo , container , false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this._editText = view.findViewById(R.id.editToDoName);
        this._saveButton = view.findViewById(R.id.buttonSave);

        this._db = new DbContext(getActivity());
        boolean isUpdate = false;
        final Bundle bundle = getArguments();

        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("todo");
            this._editText.setText(task);
            this._saveButton.setText("Submit");

            if (task.length() > 0) {
                this._saveButton.setEnabled(false);
            }
        }

        this._editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    _saveButton.setEnabled(false);
                    _saveButton.setBackgroundColor(Color.GRAY);
                } else {
                    _saveButton.setEnabled(true);
                    _saveButton.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        this._saveButton.setOnClickListener(v -> {
            String text = _editText.getText().toString();

            if (finalIsUpdate) {
                _db.updateToDo(bundle.getInt("id") , text);
            } else {
                ToDoModel item = new ToDoModel();
                item.setName(text);
                item.setIsDone(0);
                _db.insertToDo(item);
            }

            dismiss();
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
