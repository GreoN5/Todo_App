package com.example.todoapp_georgi_dimitrov_1901682009;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.todoapp_georgi_dimitrov_1901682009.Adapter.ToDoAdapter;
import com.example.todoapp_georgi_dimitrov_1901682009.Context.DbContext;
import com.example.todoapp_georgi_dimitrov_1901682009.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    protected DbContext context;
    protected RecyclerView view;
    protected FloatingActionButton btnNew;
    protected List<ToDoModel> list;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.listView);
        btnNew = findViewById(R.id.add_todo);
        context = new DbContext(MainActivity.this);
        list = new ArrayList<>();
        adapter = new ToDoAdapter(context, MainActivity.this);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);

        list = context.getAllTasks();
        System.out.println(list);
        Collections.reverse(list);
        adapter.setTasks(list);

        btnNew.setOnClickListener(v -> AddNewTodo.newInstance().show(getSupportFragmentManager(), AddNewTodo.TAG));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new UpdateDelete(this.adapter));
        itemTouchHelper.attachToRecyclerView(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        this.list = this.context.getAllTasks();
        Collections.reverse(this.list);
        adapter.setTasks(this.list);
        adapter.notifyDataSetChanged();
    }
}