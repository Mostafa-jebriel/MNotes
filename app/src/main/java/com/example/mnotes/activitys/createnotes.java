package com.example.mnotes.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnotes.R;
import com.example.mnotes.adapter.adapter;
import com.example.mnotes.classes.note;
import com.example.mnotes.database.notedatabase;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class createnotes extends AppCompatActivity {
    ImageView but_back,but_save;
    TextView time;
    EditText e_title,e_text;
    private String colorn;
    private View v1,v2,v3,v8,v5,v6,v7,v;
    private adapter adapter;
    private note show_note;
    private Dialog d_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnotes);
        but_back=findViewById(R.id.but_back);
        but_save=findViewById(R.id.but_done);
        time=findViewById(R.id.text_time);
        e_title=findViewById(R.id.title_of_note);
        e_text=findViewById(R.id.type_note);
        colorn="#8F8585";
        v=findViewById(R.id.show_color_now);
        v1=findViewById(R.id.view_c1);
        v2=findViewById(R.id.view_c2);
        v3=findViewById(R.id.view_c3);
        v8=findViewById(R.id.view_c8);
        v5=findViewById(R.id.view_c5);
        v6=findViewById(R.id.view_c6);
        v7=findViewById(R.id.view_c7);

        if(getIntent().getBooleanExtra("updata",false)){
            show_note=(note)getIntent().getSerializableExtra("note");
            show_text();
        }

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_text.getText().toString().trim().isEmpty()){
                    onBackPressed();
                }
                else {
                    save();
                    onBackPressed();
                }
            }
        });

        time.setText(new SimpleDateFormat("EEEE  dd,MMMM,yyyy  HH:mm a", Locale.getDefault()).format(new Date()));

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        pick_color();
    }

    private void show_text(){
        e_title.setText(show_note.getTitle());
        e_text.setText(show_note.getText());
        time.setText(show_note.getTime());


    }

    private void save(){
        if(e_title.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "enter title of note", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(e_text.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "type your note", Toast.LENGTH_SHORT).show();
            return;
        }

        final note note=new note();
        note.setTitle(e_title.getText().toString());
        note.setText(e_text.getText().toString());
        note.setTime(time.getText().toString());
        note.setColor(colorn);

        if(show_note !=null){
            note.setId(show_note.getId());
        }

        @SuppressLint("StaticFieldLeak")
        class savenotes extends AsyncTask<Void,Void,Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                notedatabase.getdatabase(getApplicationContext()).notedao().insertnotes(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new savenotes().execute();
    }

    private void pick_color(){
        final LinearLayout layoutcolor=findViewById(R.id.layout_color);
        final BottomSheetBehavior<LinearLayout> sheetBehavior=BottomSheetBehavior.from(layoutcolor);
        layoutcolor.findViewById(R.id.title_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        final ImageView i1=layoutcolor.findViewById(R.id.image_c1);
        final ImageView i2=layoutcolor.findViewById(R.id.image_c2);
        final ImageView i3=layoutcolor.findViewById(R.id.image_c3);
        final ImageView i4=layoutcolor.findViewById(R.id.image_c5);
        final ImageView i5=layoutcolor.findViewById(R.id.image_c6);
        final ImageView i6=layoutcolor.findViewById(R.id.image_c7);
        final ImageView i7=layoutcolor.findViewById(R.id.image_c8);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorn="#8F8585";
                i1.setImageResource(R.drawable.ic_done);
                i2.setImageResource(0);
                i3.setImageResource(0);
                i4.setImageResource(0);
                i5.setImageResource(0);
                i6.setImageResource(0);
                i7.setImageResource(0);
                set_color();
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorn="#03A9F4";
                i1.setImageResource(0);
                i2.setImageResource(R.drawable.ic_done);
                i3.setImageResource(0);
                i4.setImageResource(0);
                i5.setImageResource(0);
                i6.setImageResource(0);
                i7.setImageResource(0);
                set_color();
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorn="#13E31B";
                i1.setImageResource(0);
                i2.setImageResource(0);
                i3.setImageResource(R.drawable.ic_done);
                i4.setImageResource(0);
                i5.setImageResource(0);
                i6.setImageResource(0);
                i7.setImageResource(0);
                set_color();
            }
        });
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorn="#FFC107";
                i1.setImageResource(0);
                i2.setImageResource(0);
                i3.setImageResource(0);
                i4.setImageResource(R.drawable.ic_done);
                i5.setImageResource(0);
                i6.setImageResource(0);
                i7.setImageResource(0);
                set_color();
            }
        });
        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorn="#673AB7";
                i1.setImageResource(0);
                i2.setImageResource(0);
                i3.setImageResource(0);
                i4.setImageResource(0);
                i5.setImageResource(R.drawable.ic_done);
                i6.setImageResource(0);
                i7.setImageResource(0);
                set_color();
            }
        });
        v7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorn="#FFFFFFFF";
                i1.setImageResource(0);
                i2.setImageResource(0);
                i3.setImageResource(0);
                i4.setImageResource(0);
                i5.setImageResource(0);
                i6.setImageResource(R.drawable.ic_done);
                i7.setImageResource(0);
                set_color();
            }
        });
        v8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorn="#000000";
                i1.setImageResource(0);
                i2.setImageResource(0);
                i3.setImageResource(0);
                i4.setImageResource(0);
                i5.setImageResource(0);
                i6.setImageResource(0);
                i7.setImageResource(R.drawable.ic_done);
                set_color();
            }
        });

        if(show_note !=null && show_note.getColor() !=null && !show_note.getColor().trim().isEmpty()){
            switch (show_note.getColor()){
                case "#8F8585":
                    v1.performClick();
                    break;
                case "#03A9F4":
                    v2.performClick();
                    break;
                case "#13E31B":
                    v3.performClick();
                    break;
                case "#FFC107":
                    v5.performClick();
                    break;
                case "#673AB7":
                    layoutcolor.findViewById(R.id.view_c6).performClick();
                   // v6.performClick();
                    break;
                case "#FFFFFFFF":
                    v7.performClick();
                    break;
                case "#000000":
                    v8.performClick();
                    break;
            }
        }

        if(show_note!=null){
            layoutcolor.findViewById(R.id.layout_delete).setVisibility(View.VISIBLE);
            layoutcolor.findViewById(R.id.layout_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    show_delete_dialog();

                }
            });
        }


    }

    private void show_delete_dialog(){
            d_delete=new Dialog(this);
            d_delete.setContentView(R.layout.delete);
            d_delete.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            d_delete.setCancelable(false);
            TextView tc=d_delete.findViewById(R.id.text_cancel_but);
            TextView td=d_delete.findViewById(R.id.text_delete_but);
            tc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d_delete.dismiss();
                }
            });
            td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               class deletenotes extends AsyncTask<Void,Void,Void>{

                   @Override
                   protected Void doInBackground(Void... voids) {
                       notedatabase.getdatabase(getApplicationContext()).notedao()
                               .deletenotes(show_note);
                       return null;
                   }

                   @Override
                   protected void onPostExecute(Void unused) {
                       super.onPostExecute(unused);
                       Intent intent=new Intent();
                       intent.putExtra("isnotedeleted",true);
                       setResult(RESULT_OK,intent);
                       finish();
                   }
               }
               new deletenotes().execute();
            }
        });
            d_delete.show();

    }

    private void set_color(){
        GradientDrawable drawable=(GradientDrawable) v.getBackground();
        drawable.setColor(Color.parseColor(colorn));
    }
}