package com.example.mnotes.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mnotes.R;
import com.example.mnotes.activitys.MainActivity;
import com.example.mnotes.classes.note;
import com.example.mnotes.notesview;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder>{

   private List<note> notes;
   private notesview notesview;

    public adapter(List<note> notes, com.example.mnotes.notesview notesview) {
        this.notes = notes;
        this.notesview = notesview;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notes
                        ,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.MyViewHolder holder, int position) {
        holder.setnotes(notes.get(position));
        holder.layout_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesview.oncilcknote(notes.get(position),position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void  search(ArrayList<note> a){
        notes=a;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tt,ti;
        LinearLayout layout_color,layout_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_color=itemView.findViewById(R.id.layout_card);
            tt=itemView.findViewById(R.id.title_text);
            ti=itemView.findViewById(R.id.time_text);
        }

        void setnotes(note note){
            tt.setText(note.getTitle());
            ti.setText(note.getTime());
            GradientDrawable drawable=(GradientDrawable) layout_color.getBackground();
            if(note.getColor() != null) {
                drawable.setColor(Color.parseColor(note.getColor()));
            }
            else {
                drawable.setColor(Color.parseColor("#8F8585"));
            }
        }
    }

}
