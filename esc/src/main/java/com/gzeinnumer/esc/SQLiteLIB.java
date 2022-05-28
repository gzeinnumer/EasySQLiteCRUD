package com.gzeinnumer.esc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gzeinnumer.esc.helper.DefaultData;
import com.gzeinnumer.esc.helper.InterfaceDaoSQLite;
import com.gzeinnumer.esc.struck.HistoryTable;
import com.gzeinnumer.esc.struck.SQLiteTable;
import com.gzeinnumer.esc.typeData.DecimalTypeData;
import com.gzeinnumer.esc.typeData.IntegerTypeData;
import com.gzeinnumer.esc.typeData.OtherTableData;
import com.gzeinnumer.esc.typeData.PrimaryKeyTypeData;
import com.gzeinnumer.esc.typeData.TextTypeData;
import com.gzeinnumer.esc.typeData.TimeStampTypeData;
import com.gzeinnumer.esc.typeData.VarcharTypeData;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public abstract class SQLiteLIB<T> implements InterfaceDaoSQLite<T> {

    private static final String TAG = "SQLiteLIB";

    @Override
    public int countData(Class<T> clss, SQLiteDatabase myDb) {
        return countData(clss, myDb, "");
    }

    @Override
    public int countData(Class<T> clss, SQLiteDatabase myDb, String whereCondition) {
        int count = 0;

        StringBuilder query = new StringBuilder("SELECT * FROM ");

        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("countData: Annotation SQLiteTable Not Found");
                return count;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("countData: Annotation SQLiteTable Not Found");
            return count;
        }
        query.append(tableName);

        if (clss.getDeclaredFields().length == 0) {
            logD("countData: Annotation Entity Not Found");
            return count;
        }

        if (myDb == null) {
            logD("countData: SQLiteDatabase is null object references");
            return count;
        }

        if (whereCondition.length() > 0) {
            query.append(" ").append(whereCondition);
        }
        query.append(";");

        logDQuery(TAG, tableName + "_countData: " + query);

        Cursor cursor;
        try {
            cursor = myDb.rawQuery(query.toString(), null);
            count = cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            logD("countData: " + e.getMessage());
            return count;
        }
        cursor.close();
        return count;
    }

    @Override
    public List<T> readData(Class<T> clss, SQLiteDatabase myDb) {
        return readData(clss, myDb, "");
    }

    @Override
    @SuppressLint("Recycle")
    public List<T> readData(Class<T> clss, SQLiteDatabase myDb, String whereCondition) {
        List<T> list = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT ");

        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("readData: Annotation SQLiteTable Not Found");
                return list;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("readData: Annotation SQLiteTable Not Found");
            return list;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("readData: Annotation Entity Not Found");
            return list;
        }

        if (myDb == null) {
            logD("readData: SQLiteDatabase is null object references");
            return list;
        }

        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
        }

        if (query.length() > 0) {
            query.setLength(query.length() - 2);
        }

        query.append(" FROM ").append(tableName);

        if (whereCondition.length() > 0) {
            query.append(" ").append(whereCondition);
        }

        query.append(";");

        logDQuery(TAG, tableName + "_readData: " + query);

        Cursor cursor;

        cursor = myDb.rawQuery(query.toString(), null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    f.setAccessible(true);
                    PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
                    if (primaryKeyTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
                    if (varcharTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
                    if (decimalTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
                    if (textTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                }

                Gson gson = new Gson();

                JsonElement jsonElement = gson.toJsonTree(hashMap);
                list.add(gson.fromJson(jsonElement, (Type) clss));
            }
        }

        cursor.close();
        return list;
    }

    @Override
    @SuppressLint("Recycle")
    public List<T> queryData(Class<T> clss, SQLiteDatabase myDb, String query) {

        List<T> list = new ArrayList<>();
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("queryData: Annotation SQLiteTable Not Found");
                return list;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("queryData: Annotation SQLiteTable Not Found");
            return list;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("queryData: Annotation Entity Not Found");
            return list;
        }

        if (myDb == null) {
            logD("queryData: SQLiteDatabase is null object references");
            return list;
        }

        Cursor cursor;

        cursor = myDb.rawQuery(query, null);
        String[] temp = query.split("FROM");
        String querySelect = temp[0];
        querySelect= querySelect.replace("SELECT", "");

        String field = "";

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    f.setAccessible(true);
                    PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
                    if (primaryKeyTypeData != null) {
                        field = removeLast(press(f.toString()));
                        if (querySelect.contains(field) || query.contains(tableName + ".*")|| query.contains(tableName + "."+field))
                            hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        if (querySelect.contains(field) || query.contains(tableName + ".*")|| query.contains(tableName + "."+field))
                            hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
                    if (varcharTypeData != null) {
                        field = removeLast(press(f.toString()));
                        if (querySelect.contains(field) || query.contains(tableName + ".*")|| query.contains(tableName + "."+field))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        if (querySelect.contains(field) || query.contains(tableName + ".*")|| query.contains(tableName + "."+field))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
                    if (decimalTypeData != null) {
                        field = removeLast(press(f.toString()));
                        if (querySelect.contains(field) || query.contains(tableName + ".*")|| query.contains(tableName + "."+field))
                            hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
                    if (textTypeData != null) {
                        field = removeLast(press(f.toString()));
                        if (querySelect.contains(field) || query.contains(tableName + ".*")|| query.contains(tableName + "."+field))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
//                    OtherTableData otherTableData = f.getAnnotation(OtherTableData.class);
//                    if (otherTableData != null) {
//                        field = removeLast(press(f.toString()));
//                        if (querySelect.contains(field))
//                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
//                    }
                    OtherTableData joinColumn = f.getAnnotation(OtherTableData.class);
                    if (joinColumn != null) {
                        String withTable = joinColumn.withTable();

                        if (querySelect.contains(withTable) && querySelect.contains(field)) {
                            String columnName = removeLast(press(f.toString()));
                            String alias = joinColumn.alias();
                            String modelName = removeLast(press(f.toString()));
                            hashMap.put(modelName, cursor.getString(cursor.getColumnIndex(alias)));
                        }
                    }
                }

                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(hashMap);
                list.add(gson.fromJson(jsonElement, (Type) clss));
            }
        }

        cursor.close();
        return list;
    }

    @Override
    public boolean queryResult(Class<T> clss, SQLiteDatabase myDb, String query) {
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("queryResult: Annotation SQLiteTable Not Found");
                return false;
            }
        } else {
            logD("queryResult: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("queryResult: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("queryResult: SQLiteDatabase is null object references");
            return false;
        }

        try {
            myDb.execSQL(query);
            return true;
        } catch (Exception e) {
            logD("queryResult: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int queryCount(Class<T> clss, SQLiteDatabase myDb, String query) {
        int count = 0;
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("queryCount: Annotation SQLiteTable Not Found");
                return count;
            }
        } else {
            logD("queryCount: Annotation SQLiteTable Not Found");
            return count;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("queryCount: Annotation Entity Not Found");
            return count;
        }

        if (myDb == null) {
            logD("queryCount: SQLiteDatabase is null object references");
            return count;
        }

        return (int) DatabaseUtils.longForQuery(myDb, query, null);
    }

    @Override
    public boolean deleteData(Class<T> clss, SQLiteDatabase myDb, String whereCondition) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("deleteData: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("deleteData: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("deleteData: Annotation Entity Not Found");
            return false;
        }

        if (whereCondition.length() == 0) {
            whereCondition = "1";
        }

        whereCondition = removeWhere(whereCondition);

        if (myDb == null) {
            logD("deleteData: SQLiteDatabase is null object references");
            return false;
        }

        try {
            long res = myDb.delete(tableName, whereCondition, new String[]{});
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            logD("deleteData: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertData(Class<T> clss, SQLiteDatabase myDb, T data) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("insertData: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("insertData: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("insertData: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("insertData: SQLiteDatabase is null object references");
            return false;
        }

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            DefaultData defaultData = f.getAnnotation(DefaultData.class);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                if (!primaryKeyTypeData.autoGenerate()) {
                    field = removeLast(press(f.toString()));
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else {
                            if (defaultData != null)
                                value.add(defaultData.value());
                            else
                                value.add(null);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertData: " + e.getMessage());
                    }
                }
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else {
                        value.add(null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: " + e.getMessage());
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else {
                        if (defaultData != null)
                            value.add(defaultData.value());
                        else
                            value.add(null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: " + e.getMessage());
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else {
                        if (timestamp.currentTime())
                            value.add(getCurrentTime());
                        else
                            value.add(null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: " + e.getMessage());
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else {
                        value.add(null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: " + e.getMessage());
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else{
                        if (defaultData != null)
                            value.add(defaultData.value());
                        else
                            value.add(null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: " + e.getMessage());
                }
            }
        }

        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < key.size(); i++) {
                values.put(key.get(i), value.get(i));
            }

            long res = myDb.insert(tableName, null, values);
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            logD("insertData: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatedData(Class<T> clss, SQLiteDatabase myDb, T data, String whereCondition, String[] fieldToUpdate) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("updatedData: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("updatedData: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("updatedData: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("updatedData: SQLiteDatabase is null object references");
            return false;
        }

        if (whereCondition.length() == 0) {
            whereCondition = " 1";
        }

        whereCondition = removeWhere(whereCondition);

        ArrayList<String> fields = new ArrayList<>(Arrays.asList(fieldToUpdate));

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();
        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: " + e.getMessage());
                    }
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: " + e.getMessage());
                    }
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: " + e.getMessage());
                    }
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: " + e.getMessage());
                    }
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: " + e.getMessage());
                    }
                }
            }
        }

        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < key.size(); i++) {
                values.put(key.get(i), value.get(i));
            }
            long res = myDb.update(tableName, values, whereCondition, new String[]{});
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            logD("updatedData: " + e.getMessage());
            return false;
        }
    }

    /*
    3.1.0
     */
    @Override
    public T readSingleData(Class<T> clss, SQLiteDatabase myDb) {
        return readSingleData(clss, myDb, "");
    }

    @Override
    @SuppressLint("Recycle")
    public T readSingleData(Class<T> clss, SQLiteDatabase myDb, String whereCondition) {
        List<T> list = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT ");

        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("readSingleData: Annotation SQLiteTable Not Found");
                return list.get(0);
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("readSingleData: Annotation SQLiteTable Not Found");
            return list.get(0);
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("readSingleData: Annotation Entity Not Found");
            return list.get(0);
        }

        if (myDb == null) {
            logD("readSingleData: SQLiteDatabase is null object references");
            return list.get(0);
        }

        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
        }

        if (query.length() > 0) {
            query.setLength(query.length() - 2);
        }

        query.append(" FROM ").append(tableName);

        if (whereCondition.length() > 0) {
            query.append(" ").append(whereCondition);
        }

        query.append(" LIMIT 1;");

        Cursor cursor;

        cursor = myDb.rawQuery(query.toString(), null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    f.setAccessible(true);
                    PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
                    if (primaryKeyTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
                    if (varcharTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
                    if (decimalTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
                    if (textTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                }

                Gson gson = new Gson();

                JsonElement jsonElement = gson.toJsonTree(hashMap);
                list.add(gson.fromJson(jsonElement, (Type) clss));
            }
        }

        cursor.close();
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public boolean insertDataOrIgnore(Class<T> clss, SQLiteDatabase myDb, T data) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("insertDataOrIgnore: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("insertDataOrIgnore: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("insertDataOrIgnore: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("insertDataOrIgnore: SQLiteDatabase is null object references");
            return false;
        }

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        String field = "";
        String pKey = "";
        String pKeyValue = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                pKey = field;
                try {
                    if (f.get(data) != null) {
                        value.add(String.valueOf(f.get(data)));
                        pKeyValue = String.valueOf(f.get(data));
                    } else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
        }

        try {
            if (pKeyValue != null && pKeyValue.length() > 0 && !pKeyValue.equals("null") && !pKeyValue.equals("0")) {
                String query = "SELECT COUNT(" + pKey + ") FROM " + tableName + " WHERE " + pKey + "='" + pKeyValue + "';";

                int count = (int) DatabaseUtils.longForQuery(myDb, query, null);
                if (count == 0) {
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < key.size(); i++) {
                        values.put(key.get(i), value.get(i));
                    }
                    long res = myDb.insert(tableName, null, values);
                    return res > 0;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logD("insertDataOrIgnore: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertDataOrUpdate(Class<T> clss, SQLiteDatabase myDb, T data, String[] fieldToUpdate) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("insertDataOrUpdate: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("insertDataOrUpdate: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("insertDataOrUpdate: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("insertDataOrUpdate: SQLiteDatabase is null object references");
            return false;
        }

        ArrayList<String> fields = new ArrayList<>(Arrays.asList(fieldToUpdate));

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        List<String> valueUpdate = new ArrayList<>();
        List<String> keyUpdate = new ArrayList<>();

        String field = "";
        String pKey = "";
        String pKeyValue = "";

        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = removeLast(press(f.toString()));
                pKey = field;
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                        pKeyValue = String.valueOf(f.get(data));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                        pKeyValue = String.valueOf(f.get(data));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
        }

        try {
            if (pKeyValue != null && pKeyValue.length() > 0 && !pKeyValue.equals("null") && !pKeyValue.equals("0")) {
                String query = "SELECT COUNT(" + pKey + ") FROM " + tableName + " WHERE " + pKey + "='" + pKeyValue + "';";

                int count = (int) DatabaseUtils.longForQuery(myDb, query, null);

                if (count > 0) {
                    String whereCondition = "" + pKey + "='" + pKeyValue + "'";
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < keyUpdate.size(); i++) {
                        if (!valueUpdate.get(i).equals("null"))
                            values.put(keyUpdate.get(i), valueUpdate.get(i));
                        else
                            values.put(keyUpdate.get(i), (String) null);
                    }
                    long res = myDb.update(tableName, values, whereCondition, new String[]{});
                    return res > 0;
                } else {
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < key.size(); i++) {
                        values.put(key.get(i), value.get(i));
                    }
                    long res = myDb.insert(tableName, null, values);
                    return res > 0;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logD("insertDataOrUpdate: " + e.getMessage());
            return false;
        }
    }
    /*
    3.1.0
     */

    /*
    3.1.1
     */
    @Override
    public boolean insertDataOrIgnore(Class<T> clss, SQLiteDatabase myDb, T data, String where) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("insertDataOrIgnore: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("insertDataOrIgnore: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("insertDataOrIgnore: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("insertDataOrIgnore: SQLiteDatabase is null object references");
            return false;
        }

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        String field = "";
        String pKey = "";
        String pKeyValue = "";

        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                pKey = field;
                try {
                    if (f.get(data) != null) {
                        value.add(String.valueOf(f.get(data)));
                        pKeyValue = String.valueOf(f.get(data));
                    } else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrIgnore: " + e.getMessage());
                }
            }
        }

        try {
            if (where == null || where.length() == 0) {
                where = " WHERE " + pKey + "='" + pKeyValue + "';";
            }
            String query = "SELECT COUNT(" + pKey + ") FROM " + tableName + " " + where;

            int count = (int) DatabaseUtils.longForQuery(myDb, query, null);
            if (count == 0) {
                ContentValues values = new ContentValues();
                for (int i = 0; i < key.size(); i++) {
                    values.put(key.get(i), value.get(i));
                }
                long res = myDb.insert(tableName, null, values);
                return res > 0;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logD("insertDataOrIgnore: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertDataOrUpdate(Class<T> clss, SQLiteDatabase myDb, T data, String[] fieldToUpdate, String where) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("insertDataOrUpdate: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("insertDataOrUpdate: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("insertDataOrUpdate: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("insertDataOrUpdate: SQLiteDatabase is null object references");
            return false;
        }

        ArrayList<String> fields = new ArrayList<>(Arrays.asList(fieldToUpdate));

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        List<String> valueUpdate = new ArrayList<>();
        List<String> keyUpdate = new ArrayList<>();

        String field = "";
        String pKey = "";
        String pKeyValue = "";

        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = removeLast(press(f.toString()));
                pKey = field;
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                        pKeyValue = String.valueOf(f.get(data));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                        pKeyValue = String.valueOf(f.get(data));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }

                if (fields.contains(field)) {
                    try {
                        keyUpdate.add(field);
                        valueUpdate.add(String.valueOf(f.get(data)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertDataOrUpdate: " + e.getMessage());
                    }
                }
            }
        }

        try {
            if (pKeyValue != null && pKeyValue.length() > 0 && !pKeyValue.equals("null") && !pKeyValue.equals("0")) {
                if (where == null || where.length() == 0) {
                    where = " WHERE " + pKey + "='" + pKeyValue + "';";
                }
                String query = "SELECT COUNT(" + pKey + ") FROM " + tableName + " " + where;

                int count = (int) DatabaseUtils.longForQuery(myDb, query, null);

                Log.d(getClass().getSimpleName(), "insertDataOrUpdate: " + count);

                if (count > 0) {
                    String whereCondition = removeWhere(where);
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < keyUpdate.size(); i++) {
                        values.put(keyUpdate.get(i), valueUpdate.get(i));
                    }
                    long res = myDb.update(tableName, values, whereCondition, new String[]{});
                    return res > 0;
                } else {
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < key.size(); i++) {
                        values.put(key.get(i), value.get(i));
                    }
                    long res = myDb.insert(tableName, null, values);
                    return res > 0;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logD("insertDataOrUpdate: " + e.getMessage());
            return false;
        }
    }
    /*
    3.1.1
     */

    /*
    3.2.0
     */
    @Override
    public boolean lastDataOnHistory(Class<T> clss, SQLiteDatabase myDb, T data) {
        String tableName = "";
        String tableNameHistory = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("insertDataOrUpdate: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("insertDataOrUpdate: Annotation SQLiteTable Not Found");
            return false;
        }
        if (clss.isAnnotationPresent(HistoryTable.class)) {
            HistoryTable HistoryTable = clss.getAnnotation(HistoryTable.class);
            if (HistoryTable == null) {
                logD("insertDataOrUpdate: Annotation HistoryTable Not Found");
                return false;
            } else {
                tableNameHistory = HistoryTable.tableName();
            }
        } else {
            logD("insertDataOrUpdate: Annotation HistoryTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("insertDataOrUpdate: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null) {
            logD("insertDataOrUpdate: SQLiteDatabase is null object references");
            return false;
        }

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        String field = "";
        String pKey = "";
        String pKeyValue = "";

        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = removeLast(press(f.toString()));
                pKey = field;
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                        pKeyValue = String.valueOf(f.get(data));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = removeLast(press(f.toString()));
                try {
                    if (f.get(data) != null) {
                        key.add(field);
                        value.add(String.valueOf(f.get(data)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertDataOrUpdate: " + e.getMessage());
                }
            }
        }

        try {
            if (pKeyValue != null && pKeyValue.length() > 0 && !pKeyValue.equals("null") && !pKeyValue.equals("0")) {
                String query = "SELECT COUNT(" + pKey + ") FROM " + tableName + " WHERE " + pKey + "='" + pKeyValue + "';";

                int count = (int) DatabaseUtils.longForQuery(myDb, query, null);

                if (count > 0) {
                    String whereCondition = "" + pKey + "='" + pKeyValue + "'";
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < key.size(); i++) {
                        if (!value.get(i).equals("null"))
                            values.put(key.get(i), value.get(i));
                        else
                            values.put(key.get(i), (String) null);
                    }
                    long res = myDb.update(tableName, values, whereCondition, new String[]{});
                    long resInsert = myDb.insert(tableNameHistory, null, values);
                    return res > 0;
                } else {
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < key.size(); i++) {
                        values.put(key.get(i), value.get(i));
                    }
                    long res = myDb.insert(tableName, null, values);
                    return res > 0;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logD("insertDataOrUpdate: " + e.getMessage());
            return false;
        }
    }

    /*
    3.2.0
     */

    /*
    3.3.0
     */
    @Override
    @SuppressLint("Recycle")
    public T readLastData(Class<T> clss, SQLiteDatabase myDb) {
        List<T> list = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT ");

        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("readLastData: Annotation SQLiteTable Not Found");
                return list.get(0);
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("readLastData: Annotation SQLiteTable Not Found");
            return list.get(0);
        }

        if (clss.getDeclaredFields().length == 0) {
            logD("readLastData: Annotation Entity Not Found");
            return list.get(0);
        }

        if (myDb == null) {
            logD("readLastData: SQLiteDatabase is null object references");
            return list.get(0);
        }

        String field = "";
        String pKey = "";

        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = press(f.toString());
                pKey = field.replaceAll(",", "");
                query.append(tableName).append(".").append(field);
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
        }

        if (query.length() > 0) {
            query.setLength(query.length() - 2);
        }

        query.append(" FROM ").append(tableName);

        query.append(" ORDER BY " + pKey + " DESC LIMIT 1;");

        Cursor cursor;

        cursor = myDb.rawQuery(query.toString(), null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    f.setAccessible(true);
                    PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
                    if (primaryKeyTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
                    if (varcharTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
                    if (decimalTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
                    if (textTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                }

                Gson gson = new Gson();

                JsonElement jsonElement = gson.toJsonTree(hashMap);
                list.add(gson.fromJson(jsonElement, (Type) clss));
            }
        }

        cursor.close();
        return list.size() > 0 ? list.get(0) : null;
    }
    /*
    3.3.0
     */

    private String press(String s) {
        return s.substring(s.lastIndexOf('.') + 1) + ", ";
    }

    private String removeLast(String s) {
        return s.substring(0, s.length() - 2);
    }

    private void logD(String msg) {
        Log.e(TAG, "logD: " + msg, null);
    }

    private void logDQuery(String TAG, String msg) {
        //Log.d(TAG, msg);
    }

    private String removeWhere(String a) {
        String strTemp = a.toUpperCase();
        String toRemove = "WHERE";
        int x = strTemp.indexOf(toRemove);
        if (x != -1) a = a.substring(0, x) + a.substring(x + toRemove.length());
        return a;
    }

    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());//dd/MM/yyyy
        Date now = new Date();
        return format.format(now);
    }
}