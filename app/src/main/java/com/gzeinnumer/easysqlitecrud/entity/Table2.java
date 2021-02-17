package com.gzeinnumer.easysqlitecrud.entity;

import com.gzeinnumer.esc.SQLiteLIB;
import com.gzeinnumer.esc.struck.SQLiteTable;
import com.gzeinnumer.esc.typeData.IntegerTypeData;
import com.gzeinnumer.esc.typeData.PrimaryKeyTypeData;
import com.gzeinnumer.esc.typeData.VarcharTypeData;

import java.util.List;

@SQLiteTable(tableName = "table2")
public class Table2 extends SQLiteLIB<Table2> {

    @PrimaryKeyTypeData
    private int id;

    @VarcharTypeData
    private String name;

    @IntegerTypeData
    private int id_table1;

    public Table2() {}

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
}
