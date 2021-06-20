package com.gzeinnumer.easysqlitecrud.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.gzeinnumer.sb.SQLiteBuilder;

public class DBInstance extends SQLiteBuilder {

    private static SQLiteDatabase sqLiteDatabase;
    public static String DB_NAME = "MyLibSQLiteSimple.db";

    public static SQLiteDatabase getDataBase(Context context) {
        String DB_PATH_EXTERNAL = Environment.getExternalStorageDirectory().toString()
                + "/EasySQLiteCRUD/MyLibSQLiteSimple.db";

        sqLiteDatabase = SQLiteBuilder.builder(DBInstance.class, context)
                .setDatabaseName(DB_NAME)
                .loadDatabaseFromExternal(DB_PATH_EXTERNAL)
                .setDatabaseVersion(1)
                .build();
        return sqLiteDatabase;
    }
}
