package com.example.todoapp_georgi_dimitrov_1901682009.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp_georgi_dimitrov_1901682009.AddNewTodo;
import com.example.todoapp_georgi_dimitrov_1901682009.Context.DbContext;
import com.example.todoapp_georgi_dimitrov_1901682009.MainActivity;
import com.example.todoapp_georgi_dimitrov_1901682009.Model.ToDoModel;
import com.example.todoapp_georgi_dimitrov_1901682009.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> _list;
    private final MainActivity _activity;
    private final DbContext _db;

    public ToDoAdapter(DbContext db, MainActivity activity){
        this._activity = activity;
        this._db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todo, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item = this._list.get(position);
        holder.checkBox.setText(item.getName());
        holder.checkBox.setChecked(toBoolean(item.getIsDone()));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                _db.makeToDoDone(item.getId());
            } else {
                _db.makeToDoNotDone(item.getId());
            }
        });
    }

    public boolean toBoolean(int num) {
        return num != 0;
    }

    public Context getContext() {
        return this._activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<ToDoModel> mList) {
        this._list = mList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        ToDoModel item = this._list.get(position);
        this._db.deleteToDo(item.getId());
        this._list.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = this._list.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("todo" , item.getName());

        AddNewTodo task = new AddNewTodo();
        task.setArguments(bundle);
        task.show(this._activity.getSupportFragmentManager() , task.getTag());
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return this._list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
