package com.example.mnotes.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnotes.R;
import com.example.mnotes.adapter.adapter;
import com.example.mnotes.classes.note;
import com.example.mnotes.database.notedatabase;
import com.example.mnotes.notesview;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements notesview {
    ImageView add_but,b_more,b_done,b_s_all,b_delete;
    public static final int add_note=1;
    public static final int updata_note=2;
    public static final int show_all_note=3;
    private RecyclerView recyclerView;
    private List<note> noteList;
    private adapter adapter;
    EditText search;
    private int cilcknote=-1,count;
    TextView note_count;
    String cnote=" Notes";
    int m=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_but=findViewById(R.id.but_add_note);
        b_more=findViewById(R.id.more);
        b_done=findViewById(R.id.done);
        b_delete=findViewById(R.id.delete);
        b_s_all=findViewById(R.id.select_all);
        recyclerView=findViewById(R.id.show_notes);
        search=findViewById(R.id.input_text_search);
        note_count=findViewById(R.id.note_count);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteList = new ArrayList<>();
        adapter = new adapter(noteList,this);
        recyclerView.setAdapter(adapter);
        add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), createnotes.class);
                startActivityForResult(in,add_note);
            }
        });
        b_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_done.setVisibility(View.VISIBLE);
                b_more.setVisibility(View.GONE);
                b_delete.setVisibility(View.VISIBLE);
                b_s_all.setVisibility(View.VISIBLE);
            }
        });
        b_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_done.setVisibility(View.GONE);
                b_more.setVisibility(View.VISIBLE);
                b_delete.setVisibility(View.GONE);
                b_s_all.setVisibility(View.GONE);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                search_text(s.toString());
            }
        });
        getnonts(show_all_note,false);


    }

    @Override
    public void oncilcknote(note note, int i) {
        cilcknote=i;
        Intent in2=new Intent(getApplicationContext(),createnotes.class);
        in2.putExtra("updata",true);
        in2.putExtra("note",note);
        startActivityForResult(in2,updata_note);
    }

    private void search_text(String s) {
        ArrayList<note> text=new ArrayList<>();
        for(note da:noteList){
            if(da.getTitle().toLowerCase().contains(s.toLowerCase())){
                text.add(da);
            }
        }
        count=text.size();
        note_count.setText(count+cnote);
        adapter.search(text);

    }

    private void getnonts(final int requestCode,final boolean deleted){
        @SuppressLint("StaticFieldLeak")
        class gettnotes extends AsyncTask<Void,Void, List<note>> {
            @Override
            protected List<note> doInBackground(Void... voids) {
                return notedatabase.getdatabase(getApplicationContext()).notedao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<note> notes) {
                super.onPostExecute(notes);
                if(requestCode==show_all_note){
                    noteList.addAll(notes);
                    count=noteList.size();
                    note_count.setText(count+cnote);
                    adapter.notifyDataSetChanged();
                }else if(requestCode==add_note){
                    noteList.add(0,notes.get(0));
                    count=noteList.size();
                    note_count.setText(count+cnote);
                    adapter.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
                }else if(requestCode==updata_note){
                    noteList.remove(cilcknote);
                    if(deleted){
                        adapter.notifyItemRemoved(cilcknote);
                    }else {
                        noteList.add(cilcknote,notes.get(cilcknote));
                        adapter.notifyItemChanged(cilcknote);
                    }
                }

            }
        }
        new gettnotes().execute();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==add_note && resultCode==RESULT_OK){
           getnonts(add_note,false);
       }
       else if(requestCode==updata_note && resultCode==RESULT_OK){
          if(data!=null){
              getnonts(updata_note,data.getBooleanExtra("isnotedeleted",false));
          }
       }
    }


}