package com.example.todolist;

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

import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;




public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG="AddNewTask";

    //widgets
    private EditText mEditText;
    private Button mSaveButton;
    private DataBaseHelper myDB;

    public  static  AddNewTask newInstance(){
        return  new AddNewTask();
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_newtask,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText=view.findViewById(R.id.edittext);
        mSaveButton=view.findViewById(R.id.button_save);
        myDB=new DataBaseHelper(getActivity());

        boolean isUpdate=false;

        Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task=bundle.getString("task");
            mEditText.setText(task);

            if(task.length()>0){
                mSaveButton.setEnabled(false);

            }

        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s.toString().equals(" ")){
                   mSaveButton.setEnabled(false);
                   mSaveButton.setTextColor(Color.GRAY);

               }else{
                   mSaveButton.setEnabled(true);
                   mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(view1 -> {
            String text=mEditText.getText().toString();
            if(finalIsUpdate){
                myDB.updateTask(bundle.getInt("id"),text);

            }else{
                ToDoModel item=new ToDoModel();
                item.setTask(text);
                item.setStatus(0);
                myDB.insertTask(item);


            }
            dismiss();
        });
    }

    @Override
    public void onDismiss(@NonNull  DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).OnDialogClose(dialog);

        }
    }
}


