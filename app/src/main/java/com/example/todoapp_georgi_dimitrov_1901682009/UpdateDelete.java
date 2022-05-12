package com.example.todoapp_georgi_dimitrov_1901682009;

import android.app.AlertDialog;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp_georgi_dimitrov_1901682009.Adapter.ToDoAdapter;

public class UpdateDelete extends ItemTouchHelper.SimpleCallback {
    private final ToDoAdapter _adapter;

    public UpdateDelete(ToDoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this._adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();

        if (direction == ItemTouchHelper.RIGHT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this._adapter.getContext());
            builder.setTitle("Delete to-do?");
            builder.setMessage("Are you sure you want to do this action?");
            builder.setPositiveButton("Yes", (dialog, which) -> _adapter.deleteTask(position));
            builder.setNegativeButton("No", (dialog, which) -> _adapter.notifyItemChanged(position));

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            this._adapter.editItem(position);
        }
    }
}
