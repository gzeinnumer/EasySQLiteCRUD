package com.gzeinnumer.easysqlitecrud.entity;

import android.database.sqlite.SQLiteDatabase;

import com.gzeinnumer.esc.SQLiteLIB;
import com.gzeinnumer.esc.helper.DefaultData;
import com.gzeinnumer.esc.struck.HistoryTable;
//import com.gzeinnumer.esc.struck.JoinColumn;
import com.gzeinnumer.esc.struck.SQLiteTable;
import com.gzeinnumer.esc.typeData.DecimalTypeData;
import com.gzeinnumer.esc.typeData.IntegerTypeData;
import com.gzeinnumer.esc.typeData.OtherTableData;
import com.gzeinnumer.esc.typeData.PrimaryKeyTypeData;
import com.gzeinnumer.esc.typeData.TextTypeData;
import com.gzeinnumer.esc.typeData.TimeStampTypeData;
import com.gzeinnumer.esc.typeData.VarcharTypeData;
import com.gzeinnumer.sb.struct.CreateTableQuery;

import java.util.List;

@CreateTableQuery(
        query = "CREATE TABLE table1 (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "rating REAL, " +
                "desc TEXT, " +
                "flag_active INTEGER, " +
                "created_at TEXT)"
)
@SQLiteTable(tableName = "table1")
@HistoryTable(tableName = "table1_his")
public class Table1 extends SQLiteLIB<Table1> {

    @PrimaryKeyTypeData
    private int id;                 // for Primary key
    // Default AutoIncrement true
    // @PrimaryKeyTypeData(autoGenerate = false) to disable
    @VarcharTypeData
    @DefaultData(value = "1")
    private String name;            // for Varchar
    @DecimalTypeData
    private double rating;          // for Decimal/Real
    @TextTypeData
    @DefaultData(value = "3")
    private String desc;            // for String
    @IntegerTypeData
    private int flag_active;        // for Integer
    @TimeStampTypeData(currentTime = true)
    private String created_at;      // for String
    @OtherTableData(withTable = "table2" , alias = "id_table1")
    private String id_table1;            // for Varchar

    @OtherTableData(withTable = "table2", alias = "table2_name")
    private String table2_name;

    private SQLiteDatabase sqLiteDatabase;

    public Table1() {}

    public Table1(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    //INSERT INTO table1 (name, rating, desc, flag_active, created_at) VALUES ('Zein', '10.0.', 'Android Programmer', '1', '12-12-2020');
    public boolean insert() {
        Table1 data = new Table1();
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        return insertData(Table1.class, sqLiteDatabase, data);
    }

    //UPDATE table1 SET name='Name Update', desc='Desc Update', flag_active='0' WHERE id='1';
    public boolean update() {
        //set your value to update
        Table1 data = new Table1();
        data.setName("Name Update");
        data.setDesc("Desc Update");
        data.setFlag_active(0);

        //String condition = "";                                    //to update all data
        //String condition = "WHERE 1";                             //to update all data
        String condition = "WHERE id='1'";                          //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        String[] fieldToUpdate = new String[]{
                "name",
                "desc",
                "flag_active"
        }; // put all field that you want to update

        return updatedData(Table1.class, sqLiteDatabase, data, condition, fieldToUpdate);  // return true/false
    }

    //DELETE FROM table1 WHERE id='1';
    public boolean delete() {
        //String condition = "";                                    //to delete all data
        //String condition = "WHERE 1";                             //to delete all data
        String condition = "WHERE id='1'";                          //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        return deleteData(Table1.class, sqLiteDatabase, condition);
    }

    //type 1 SELECT COUNT(*) FROM table1;
    public int count() {
        return countData(Table1.class, sqLiteDatabase);
    }

    //type 2 SELECT COUNT(*) FROM table1 WHERE flag_Active='1';
    public int count2() {
        //String condition = "WHERE 1";                             //count all
        String condition = "WHERE flag_active='1'";                 //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition
        return countData(Table1.class, sqLiteDatabase, condition);
    }

    //type 3 Your Custom Query
    //SELECT COUNT(id) FROM table1;
    public int queryCount() {
        String query = "SELECT COUNT(id) FROM table1;";
        return queryCount(Table1.class, sqLiteDatabase, query);
    }

    //type 1 SElECT * FROM table1;
    public List<Table1> read() {
        return readData(Table1.class, sqLiteDatabase);
    }

    //type 2 SELECT * FROM table1 WHERE flag_active='1';
    public List<Table1> read2() {
        //String condition = "";                                    //read all
        //String condition = "WHERE 1";                             //read all
        String condition = "WHERE flag_active='1'";                 //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        return readData(Table1.class, sqLiteDatabase, condition);
    }

    //type 2 SELECT * FROM table1;
    public List<Table1> readAll() {
        return readData(Table1.class, sqLiteDatabase);
    }

    //type 1 SELECT * FROM table1 LIMIT 1;
    public Table1 read3() {
        return readSingleData(Table1.class, sqLiteDatabase);
    }

    //type 2 SELECT * FROM table1 WHERE flag_active='1' LIMIT 1;
    public Table1 read4() {
        //String condition = "";                                    //read single data
        //String condition = "WHERE 1";                             //read single data
        String condition = "WHERE flag_active='1'";                 //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        return readSingleData(Table1.class, sqLiteDatabase, condition);
    }

    public List<Table1> query(){
        String query ="SELECT table1.*, table2.id_table1 FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
        return queryData(Table1.class, sqLiteDatabase, query);
    }

    public boolean queryResultUpdate() {
        String query = "UPDATE table1 SET flag_Active='2' WHERE id='1'";
        return queryResult(Table1.class, sqLiteDatabase, query);
    }

    //INSERT INTO table1 (id, name, rating, desc, flag_active, created_at) VALUES (6,'Zein', '10.0.', 'Android Programmer', '1', '12-12-2020');
    public boolean insertOrIgnoreForTesting() {
        Table1 data = new Table1();
        data.setId(6); //important line, please set your id first
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        return insertDataOrIgnore(Table1.class, sqLiteDatabase, data);
    }

    //INSERT INTO table1 (id, name, rating, desc, flag_active, created_at) VALUES (8, 'Zein', '10.0.', 'Android Programmer', '1', '12-12-2020');
    public boolean insertOrIgnoreQuery() {
        Table1 data = new Table1();
        data.setId(8);
        data.setName("GZE8");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        String iqnoreIfQuery = "WHERE name='GZE8' and flag_active='1';";

        return insertDataOrIgnore(Table1.class, sqLiteDatabase, data, iqnoreIfQuery);
    }

    //INSERT INTO table1 (id, name, rating, desc, flag_active, created_at) VALUES (7, 'Name Update', '1.6.', 'Desc Update', '1', '12-12-2020');
    //or
    //UPDATE table1 SET name='Name Update', rating='1.6', desc='Desc Update', flag_active='1', created_at='12-12-2020' WHERE id='7';
    public boolean insertOrUpdate() {
        Table1 data = new Table1();
        data.setId(7); //important line, please set your id first
        data.setName("Name Update 2");
        data.setRating(1.6);
        data.setDesc("Desc Update 1");
        data.setFlag_active(10);
        data.setCreated_at("12-12-2020");

        String[] fieldToUpdate = new String[]{
                "name",
                "rating",
                "desc",
                "flag_active",
                "created_at"
        }; // put all field that you want to update

        return insertDataOrUpdate(Table1.class, sqLiteDatabase, data, fieldToUpdate);
    }

    //INSERT INTO table1 (id, name, rating, desc, flag_active, created_at) VALUES (9, 'Name Update', '1.6', 'Desc Update', '1', '12-12-2020');
    //or
    //UPDATE table1 SET name='Name Update', rating='1.6', desc='Desc Update', flag_active='1', created_at='12-12-2020' WHERE id='9' and flag_active='10';
    public boolean insertOrUpdateQuery() {
        Table1 data = new Table1();
        data.setId(9);
        data.setName("Name Update 10");
        data.setRating(1.6);
        data.setDesc("Desc Update 10");
        data.setFlag_active(10);
        data.setCreated_at("12-12-2020");

        String[] fieldToUpdate = new String[]{
                "name",
                "rating",
                "desc",
                "flag_active",
                "created_at"
        }; // put all field that you want to update

        String where = "WHERE id='9' and flag_active='10'";

        return insertDataOrUpdate(Table1.class, sqLiteDatabase, data, fieldToUpdate, where);
    }

    //INSERT INTO table1 (id, name, rating, desc, flag_active, created_at) VALUES (10, 'Name 10', '1.6', 'Desc 10', '10', '12-12-2020');
    //or if value exists
    //UPDATE table1 SET name='Name 10', rating='1.6', desc='Desc 10', flag_active='10', created_at='12-12-2020' WHERE id='10';
    //INSERT INTO table1_his (id, name, rating, desc, flag_active, created_at) VALUES (10, 'Name 10', '1.6', 'Desc 10', '10', '12-12-2020');
    public boolean lastOnHistory() {
        Table1 data = new Table1();
//        data.setId(10); //important line, please set your id first
        data.setName("Name 10");
        data.setRating(1.6);
        data.setDesc("Desc 10");
        data.setFlag_active(10);
        data.setCreated_at("12-12-2020");

        return lastDataOnHistory(Table1.class, sqLiteDatabase, data);
    }

    //SELECT * FROM table1 ORDER BY id DESC LIMIT 1;
    public Table1 getLastData() {
        return readLastData(Table1.class, sqLiteDatabase);
    }

    //INSERT INTO table1 (name, rating, desc, flag_active, created_at) VALUES ('Zein', '10.0.', 'Android Programmer', '1', '12-12-2020');
    public boolean insertWithDefValue() {
        Table1 data = new Table1();
//        data.setName("Zein");
//        data.setRating(10.0);
//        data.setDesc("Android Programmer");
//        data.setFlag_active(1);
//        data.setCreated_at("12-12-2020");

        return insertData(Table1.class, sqLiteDatabase, data);
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getFlag_active() {
        return flag_active;
    }

    public void setFlag_active(int flag_active) {
        this.flag_active = flag_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTable2_name() {
        return table2_name;
    }

    public void setTable2_name(String table2_name) {
        this.table2_name = table2_name;
    }

    public String getId_table1() {
        return id_table1;
    }

    public void setId_table1(String id_table1) {
        this.id_table1 = id_table1;
    }

    public boolean insertOrIgnoreForTesting(int id) {
        Table1 data = new Table1();
        data.setId(id); //important line, please set your id first
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        return insertDataOrIgnore(Table1.class, sqLiteDatabase, data);
    }

    @Override
    public String toString() {
        return "Table1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", desc='" + desc + '\'' +
                ", flag_active=" + flag_active +
                ", created_at='" + created_at + '\'' +
                ", id_table1='" + id_table1 + '\'' +
                ", table2_name='" + table2_name + '\'' +
                ", sqLiteDatabase=" + sqLiteDatabase +
                '}';
    }
}
