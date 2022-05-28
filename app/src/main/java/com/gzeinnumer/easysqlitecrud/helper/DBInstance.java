package com.gzeinnumer.easysqlitecrud.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.gzeinnumer.easysqlitecrud.entity.Table1;
import com.gzeinnumer.easysqlitecrud.entity.Table2;
import com.gzeinnumer.sb.SQLiteBuilder;
import com.gzeinnumer.sb.struct.SQLiteDatabaseEntity;

@SQLiteDatabaseEntity(entities = {
        Table1.class,
        Table2.class
})
public class DBInstance extends SQLiteBuilder {

    private static SQLiteDatabase sqLiteDatabase;
    public static String DB_NAME = "EasySQLiteCRUD.db";

    public static SQLiteDatabase getDataBase(Context context) {
        if (sqLiteDatabase==null)
            sqLiteDatabase = SQLiteBuilder.builder(DBInstance.class, context)
                    .setDatabaseName(DB_NAME)
                    .setDatabaseVersion(1)
                    .build();
        return sqLiteDatabase;
    }
}
