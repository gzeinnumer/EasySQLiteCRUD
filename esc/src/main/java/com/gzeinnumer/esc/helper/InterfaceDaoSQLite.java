package com.gzeinnumer.esc.helper;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface InterfaceDaoSQLite<T> {
    int countData(Class<T> clss, SQLiteDatabase myDb);
    int countData(Class<T> clss, SQLiteDatabase myDb, String condition);
    List<T> readData(Class<T> clss, SQLiteDatabase myDb);
    List<T> readData(Class<T> clss, SQLiteDatabase myDb, String condition);
    List<T> queryData(Class<T> clss, SQLiteDatabase myDb, String query);
    boolean queryResult(Class<T> clss, SQLiteDatabase myDb, String query);
    boolean deleteData(Class<T> clss, SQLiteDatabase myDb, String condition);
    boolean insertData(Class<T> clss, SQLiteDatabase myDb, T data);
    boolean updatedData(Class<T> clss, SQLiteDatabase myDb, T data, String whereCondition, String[] filedToUpdate);
    int queryCount(Class<T> clss, SQLiteDatabase myDb, String query);
    T readSingleData(Class<T> clss, SQLiteDatabase myDb);
    T readSingleData(Class<T> clss, SQLiteDatabase myDb, String condition);
    boolean insertDataOrIgnore(Class<T> clss, SQLiteDatabase myDb, T data);

    boolean insertDataOrIgnore(Class<T> clss, SQLiteDatabase myDb, T data, String where);

    boolean insertDataOrUpdate(Class<T> clss, SQLiteDatabase myDb, T data, String[] fieldToUpdate);

    boolean insertDataOrUpdate(Class<T> clss, SQLiteDatabase myDb, T data, String[] fieldToUpdate, String where);
}
