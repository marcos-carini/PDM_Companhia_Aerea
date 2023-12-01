package br.com.ifsp.comissao_voo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ListItem> itemList;
    private ListItemAdapter adapter;

    private ActivityResultLauncher<Intent> startActivityForResultLauncher;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreate = findViewById(R.id.btn_create);

        ListView listView = findViewById(R.id.list_view);
        itemList = new ArrayList<>();
        adapter = new ListItemAdapter(this, itemList);
        listView.setAdapter(adapter);

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivityForResultLauncher.launch(intent);
        });

        startActivityForResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        reloadItemsFromDatabase();
                    }
                }
        );

        reloadItemsFromDatabase();

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            ListItem itemToDelete = itemList.get(position);
            deleteItemFromDatabase(itemToDelete.getId());
            itemList.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    private void reloadItemsFromDatabase() {
        itemList.clear();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("vouchers");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ListItem item = snapshot.getValue(ListItem.class);
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors if needed
            }
        });
    }

    private void deleteItemFromDatabase(int itemId) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("vouchers");
        databaseRef.child(String.valueOf(itemId)).removeValue();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            reloadItemsFromDatabase();
        }
    }
}
