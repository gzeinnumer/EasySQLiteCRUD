package com.gzeinnumer.easysqlitecrud.entity;

import android.database.sqlite.SQLiteDatabase;

import com.gzeinnumer.esc.SQLiteLIB;
import com.gzeinnumer.esc.struck.SQLiteTable;
import com.gzeinnumer.esc.typeData.IntegerTypeData;
import com.gzeinnumer.esc.typeData.PrimaryKeyTypeData;
import com.gzeinnumer.esc.typeData.VarcharTypeData;
import com.gzeinnumer.sb.struct.CreateTableQuery;

import java.util.List;

@CreateTableQuery(
        query = "CREATE TABLE table2 (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "id_table1 INTEGER)"
)
@SQLiteTable(tableName = "table2")
public class Table2 extends SQLiteLIB<Table2> {

    @PrimaryKeyTypeData
    private int id;

    @VarcharTypeData
    private String name;

    @IntegerTypeData
    private int id_table1;

    public Table2() {}

    private SQLiteDatabase sqLiteDatabase;

    public Table2(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public Table2(int id, String name, int id_table1) {
        this.id = id;
        this.name = name;
        this.id_table1 = id_table1;
    }

    public boolean insert(Table2 data) {
        return false;
    }

    public boolean update(Table2 data, String whereCondition) {
        return false;
    }

    public boolean delete(String whereCondition) {
        return false;
    }

    public int count() {
        return 0;
    }

    public List<Table2> read() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_table1() {
        return id_table1;
    }

    public void setId_table1(int id_table1) {
        this.id_table1 = id_table1;
    }

    //type 2 SELECT * FROM table1;
    public List<Table2> readAll() {
        return readData(Table2.class, sqLiteDatabase);
    }

    public boolean insertOrIgnoreForTesting(int id) {
        Table2 data = new Table2();
        data.setId(id);
        data.setName("GZE8");
        data.setId_table1(id);

        String iqnoreIfQuery = "WHERE id='"+id+"';";

        return insertDataOrIgnore(Table2.class, sqLiteDatabase, data, iqnoreIfQuery);
    }

    @Override
    public String toString() {
        return "Table2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", id_table1=" + id_table1 +
                ", sqLiteDatabase=" + sqLiteDatabase +
                '}';
    }
}
