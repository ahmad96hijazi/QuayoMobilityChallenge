package com.clickround.quayomobilitychallenge.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clickround.quayomobilitychallenge.R;
import com.clickround.quayomobilitychallenge.data.local.room.model.Person;
import com.clickround.quayomobilitychallenge.view.adapter.PersonsAdapter;
import com.clickround.quayomobilitychallenge.view.callback.QueryListener;
import com.clickround.quayomobilitychallenge.viewModel.MainViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.empty)
    TextView emptyTextView;
    @BindView(R.id.persons)
    RecyclerView recyclerView;

    private MainViewModel viewModel;
    private PersonsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new PersonsAdapter(new ArrayList<>(), new PersonsAdapter.OnPersonListener() {
            @Override
            public void onClick(int position, Person person) {
                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                intent.putExtra("person", new Gson().toJson(person));
                startActivityForResult(intent, 999);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setClipToPadding(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new MainViewModel(this);
        viewModel.getPersons(new QueryListener<List<Person>>() {
            @Override
            public void onQueryComplete(List<Person> persons) {
                if (persons.isEmpty()) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    adapter.setPersons(persons);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_person) {
            startActivityForResult(new Intent(this, PersonActivity.class), 999);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 999) {
            if (resultCode == 1) {
                viewModel.getPersons(new QueryListener<List<Person>>() {
                    @Override
                    public void onQueryComplete(List<Person> persons) {
                        if (persons.isEmpty()) {
                            emptyTextView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            emptyTextView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            adapter.setPersons(persons);
                        }
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
