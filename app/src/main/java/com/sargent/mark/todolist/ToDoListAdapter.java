package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);

        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, String category, String complete, long id);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null){
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descr;
        String description;

        TextView due;
        String duedate;

        CheckBox completed;
        String complete;

        TextView cate;
        String category;
        long id;

        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            cate = (TextView) view.findViewById(R.id.category);
            completed = (CheckBox) view.findViewById(R.id.completed);
            view.setOnClickListener(this);

        }
        public void bind(ItemHolder holder, int pos) {

            //Cursor goes through each item in database.

            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));
            complete = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_COMPLETED));

            descr.setText(description);
            due.setText(duedate);
            cate.setText(category);

            /*
                If the user closes the app and re-opens, this should check the database if an item was marked as completed or not. It changed the checkbox to checked or not checked.
             */

            if (complete.contains("0")) {
                due.setTextColor(Color.BLACK);
                descr.setTextColor(Color.BLACK);
                cate.setTextColor(Color.BLACK);
                completed.setText(R.string.notCompleted);
                completed.setChecked(false);
            } else {
                due.setTextColor(Color.GREEN);
                descr.setTextColor(Color.GREEN);
                cate.setTextColor(Color.GREEN);
                completed.setText(R.string.completed);
                completed.setTextColor(Color.GREEN);
                completed.setChecked(true);
            }


            holder.itemView.setTag(id);

            /*
             * Holder handles the checkbox for each item in it. Marks an item as completed or not completed and changes the text color.
             */
            holder.completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(completed.isChecked() ) {
                        due.setTextColor(Color.GREEN);
                        descr.setTextColor(Color.GREEN);
                        cate.setTextColor(Color.GREEN);
                        completed.setText(R.string.completed);
                        completed.setTextColor(Color.GREEN);
                        complete = "1";
                        MainActivity.refresh(duedate, description, category, complete, id);

                    }else{
                        due.setTextColor(Color.BLACK);
                        descr.setTextColor(Color.BLACK);
                        cate.setTextColor(Color.BLACK);
                        completed.setText(R.string.notCompleted);
                        completed.setTextColor(Color.BLACK);
                        complete = "0";
                        MainActivity.refresh(duedate, description, category, complete, id);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, category, complete, id);
        }
    }

}
