package br.com.leonardomiyagi.gridwithheaders;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.leonardomiyagi.gridwithheaders.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CustomAdapter adapter;

    private List<SampleObject> sampleObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        seedData();
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.search_item);
        search((SearchView) searchMenuItem.getActionView());
        return super.onCreateOptionsMenu(menu);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void seedData() {
        Random random = new Random();
        sampleObjects = new ArrayList<>();
        int someInt = sampleObjects.size() + random.nextInt(40) + 1;
        for (int i = 0; i < someInt; i++) {
            sampleObjects.add(new SampleObject(i, "Sample Category"));
        }
        someInt = sampleObjects.size() + random.nextInt(40) + 1;
        for (int i = sampleObjects.size(); i < someInt; i++) {
            sampleObjects.add(new SampleObject(i, "Another Category"));
        }
        someInt = sampleObjects.size() + random.nextInt(40) + 1;
        for (int i = sampleObjects.size(); i < someInt; i++) {
            sampleObjects.add(new SampleObject(i, "Third Category"));
        }
        someInt = sampleObjects.size() + random.nextInt(40) + 1;
        for (int i = sampleObjects.size(); i < someInt; i++) {
            sampleObjects.add(new SampleObject(i, "Just Category"));
        }
    }

    private void setupRecyclerView() {
        adapter = new CustomAdapter(sampleObjects);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case CustomAdapter.TYPE_HEADER:
                        return 4;
                    default:
                    case CustomAdapter.TYPE_ITEM:
                        return 1;
                }
            }
        });
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}
