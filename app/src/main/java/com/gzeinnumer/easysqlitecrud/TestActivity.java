package com.gzeinnumer.easysqlitecrud;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.easysqlitecrud.databinding.ActivityTestBinding;
import com.gzeinnumer.easysqlitecrud.entity.Table1;
import com.gzeinnumer.easysqlitecrud.helper.DBInstance;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    private ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SQLiteDatabase sqLiteDatabase = DBInstance.getDataBase(getApplicationContext());

        Table1 table1 = new Table1(sqLiteDatabase);

        binding.btnInsert.setOnClickListener(view -> {
            boolean istrue = table1.insert();
            Log.d(TAG, "onCreate_1: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnUpdate.setOnClickListener(view -> {
            boolean istrue = table1.update();
            Log.d(TAG, "onCreate_2: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnDelete.setOnClickListener(view -> {
            boolean istrue = table1.delete();
            Log.d(TAG, "onCreate_3: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnCount.setOnClickListener(view -> {
            int count = table1.count();
            Log.d(TAG, "onCreate_4: " + count);
            Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        });
        binding.btnCount2.setOnClickListener(view -> {
            int count = table1.count2();
            Log.d(TAG, "onCreate_5: " + count);
            Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        });
        binding.btnCount3.setOnClickListener(view -> {
            int count = table1.queryCount();
            Log.d(TAG, "onCreate_6: " + count);
            Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        });
        binding.btnRead.setOnClickListener(view -> {
            List<Table1> read = table1.read();
            Log.d(TAG, "onCreate_7: " + read.get(0).getName());
            Log.d(TAG, "onCreate_7: " + read.size());
            Toast.makeText(this, read.get(0).getName() + "\n" + read.size(), Toast.LENGTH_SHORT).show();
        });
        binding.btnRead2.setOnClickListener(view -> {
            List<Table1> read = table1.read2();
            Log.d(TAG, "onCreate_8: " + read.get(0).getName());
            Log.d(TAG, "onCreate_8: " + read.size());
            Toast.makeText(this, read.get(0).getName() + "\n" + read.size(), Toast.LENGTH_SHORT).show();
        });
        binding.btnQuery.setOnClickListener(view -> {
            List<Table1> read = table1.query();
            Log.d(TAG, "onCreate_9: " + read.get(0).getName());
            Log.d(TAG, "onCreate_9: " + read.get(0).getTable2_name());
            Log.d(TAG, "onCreate_9: " + read.size());
            Toast.makeText(this, read.get(0).getName() + "\n" + read.size(), Toast.LENGTH_SHORT).show();
        });
        binding.btnQueryResultUpdate.setOnClickListener(view -> {
            boolean queryUpdate = table1.queryResultUpdate();
            Log.d(TAG, "onCreate_10: " + queryUpdate);
            Toast.makeText(this, String.valueOf(queryUpdate), Toast.LENGTH_SHORT).show();
        });
        binding.btnRead3.setOnClickListener(view -> {
            Table1 read = table1.read3();
            if (read != null)
                Log.d(TAG, "onCreate_11: " + read.getName());
            else
                Log.d(TAG, "onCreate_11: " + "null");
            Toast.makeText(this, String.valueOf(read), Toast.LENGTH_SHORT).show();
        });
        binding.btnRead4.setOnClickListener(view -> {
            Table1 read = table1.read4();
            if (read != null)
                Log.d(TAG, "onCreate_12: " + read.getName());
            else
                Log.d(TAG, "onCreate_12: " + "null");
            Toast.makeText(this, String.valueOf(read), Toast.LENGTH_SHORT).show();
        });
        binding.btnInsertOrIgnore.setOnClickListener(view -> {
            boolean istrue = table1.insertOrIgnore();
            Log.d(TAG, "onCreate_13: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnInsertOrUpdate.setOnClickListener(view -> {
            boolean istrue = table1.insertOrUpdate();
            Log.d(TAG, "onCreate_14: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnInsertOrIgnoreQuery.setOnClickListener(view -> {
            boolean istrue = table1.insertOrIgnoreQuery();
            Log.d(TAG, "onCreate_15: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnInsertOrUpdateQuery.setOnClickListener(view -> {
            boolean istrue = table1.insertOrUpdateQuery();
            Log.d(TAG, "onCreate_16: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnLastDataOnHistory.setOnClickListener(view -> {
            boolean istrue = table1.lastOnHistory();
            Log.d(TAG, "onCreate_17: " + istrue);
            Toast.makeText(this, String.valueOf(istrue), Toast.LENGTH_SHORT).show();
        });
        binding.btnGetLastData.setOnClickListener(view -> {
            Table1 lastData = table1.getLastData();
            Log.d(TAG, "onCreate_18: " + lastData.getId());
        });
    }
}