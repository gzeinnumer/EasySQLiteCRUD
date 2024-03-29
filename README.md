
| ![](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example1.JPG) | ![](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example2.JPG) |
|---|---|
|Before|Simple Code|

| ![](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example3.JPG) | ![](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example4.JPG) |
|---|---|
|Before|Simple Code|

<h1 align="center">
  EasySQLiteCRUD - Simple SQLite
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-3.3.2-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/Java-Suport-green?logo=java&style=flat"></a>
    <a><img src="https://img.shields.io/badge/Kotlin-Suport-green?logo=kotlin&style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <br>
    <p>Simple way to use CRUD SQLite</p>
</div>

---

`I just try to make Cursor and ContentValues disappeared but its still in there, I hate always having Typo on my code, specially on Cursor and ContentValues again and again. And take time to debug and fix it. arghh...`

`Than I made this so I don't hate it anymore, hope you enjoy it :)`

`please tell me if you find error.`

---
# Content List
* [Download](#download)
* [Feature List](#feature-list)
* [Tech stack and 3rd library](#tech-stack-and-3rd-library)
* [Usage](#usage)
* [Example Code/App](#example-codeapp)
* [Version](#version)
* [Contribution](#contribution)

---
# Download
Add maven `jitpack.io` and `dependencies` in `build.gradle (Project)` :
```gradle
// build.gradle project
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

// build.gradle app/module
dependencies {
  ...
  implementation 'com.github.gzeinnumer:EasySQLiteCRUD:version'
}
```

---
# Feature List
- [x] [1. Table](#1-table)
- [x] [2. Entity](#2-entity)
- [x] [3. Insert](#3-insert)
- [x] [4. Update](#4-update)
- [x] [5. Delete](#5-delete)
- [x] [6. Count](#6-count)
- [x] [7. Read](#7-read)
- [x] [8. Query Data](#8-query-data) for Complex Query. return List.
- [x] [9. Query Result](#9-query-result) return true/false.
- [x] [10. Read Single Data](#10-read-single-data)
- [x] [11. Insert Or Ignore](#11-insert-or-ignore)
- [x] [12. Insert Or Update](#12-insert-or-update)
- [x] [13. Update And Backup Last Data](#13-Update-and-backup-last-data)
- [x] [14. Get Last Data](#14-get-last-data)
- [ ] Create Table

---
# Tech stack and 3rd library
- [x] [SQLite](https://developer.android.com/training/data-storage/sqlite?hl=id)

---
# Usage

### Scenario Table
|<img src="https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example7.JPG" width="400"/>|<img src="https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example8.JPG" width="400"/>|
|---|---|

<p align="center">
  <img src="https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example5.JPG" width="400"/>
</p>

Please make sure you have access to your database with instance from `SQLiteDatabase`. example :
```java
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ...

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Table1 table1 = new Table1(database);

        List<Table1> listData = table1.read(); //example calling function

        ...

    }
}
```
Here is my [DBInstance](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/helper/DBInstance.java).

**Or you can use your own configuration to connect to Database, just make sure you have access to your `local database`. [ReadMore](https://developer.android.com/training/data-storage/sqlite?hl=id)**.

#
### 1. Table
You need to extends `SQLiteLIB<YourEntity>` to your `Entity Class`. And Use Annotation `@SQLiteTable(tableName = "your_table_name")`. Then make `contructor` like this:
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    private SQLiteDatabase sqLiteDatabase;

    public Table1() {}

    public Table1(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    ...

}
```

#
### 2. Entity
> Lets see [Boilerplate Code Entity](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Entity.md)

> Simple Code

Declare Entity. You can make it more simple with this `Annotation`
- `@PrimaryKeyTypeData` or `@VarcharTypeData` or `@IntegerTypeData` or `@TimeStampTypeData` or `@TextTypeData` or `@DoubleTypeData` or `@OtherTableData`
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {
    @PrimaryKeyTypeData private int id;              // for Primary key
                                                     // Default AutoIncrement true
                                                     // @PrimaryKeyTypeData(autoGenerate = false) to disable
    @VarcharTypeData    private String name;         // for Varchar
    @DecimalTypeData    private double rating;       // for Decimal/Real
    @TextTypeData       private String desc;         // for String
    @IntegerTypeData    private int flag_active;     // for Integer
    @TimeStampTypeData  private String created_at;   // for String
                                                     // To use Current time
                                                     // @TimeStampTypeData(currentTime = true)
                                                     // work only on insertData(..,..,..) and if created_at is null
                                                     // Format time yyyy-MM-dd HH:mm:ss

    @OtherTableData(withTable = "table2", alias = "id_table1")
    private String id_table1;

    private SQLiteDatabase sqLiteDatabase;

    public Table1() {}

    public Table1(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    //getter setter

    ...

}
```
**Notes :**
- `@PrimaryKeyTypeData` : Your variable type should `int`.
- `@VarcharTypeData` : Your variable type should `String`.
- `@IntegerTypeData` : Your variable type should `int`.
- `@TimeStampTypeData` : Your variable type should `String`.
- `@TextTypeData` : Your variable type should `String`.
- `@DoubleTypeData` : Your variable type should `double`.
- `@OtherTableData` :
  - `withTable` = other table to join with current table.
  - `alias` = columnname.
- `@DefaultData` : to set Default value.
  - Work only On `@VarcharTypeData`, `@TextTypeData`.
  - example
```java
@VarcharTypeData
@DefaultData(value = "default data")
private String name;

@TextTypeData
@DefaultData(value = "default data")
private String desc;
```

#
### 3. Insert
> Lets see [Boilerplate Code Insert Data](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Insert.md)

> Simple Code

```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //INSERT INTO table1 (name, rating, desc, flag_active, created_at)
    //VALUES ('Zein', '10.0.', 'Android Programmer', '1', '12-12-2020');
    public boolean insert() {
        Table1 data = new Table1();
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        return insertData(Table1.class, sqLiteDatabase, data);
    }
}
```

#
### 4. Update
> Lets see [Boilerplate Code Update Data](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Update.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

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
}
```

#
### 5. Delete
> Lets see [Boilerplate Code Delete Data](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Delete.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //DELETE FROM table1 WHERE id='1';
    public boolean delete() {
        //String condition = "";                                    //to delete all data
        //String condition = "WHERE 1";                             //to delete all data
        String condition = "WHERE id='1'";                          //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        return deleteData(Table1.class, sqLiteDatabase, condition);
    }
}
```

#
### 6. Count
> Lets see [Boilerplate Code Count Data](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Count.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

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
}
```

#
### 7. Read
> Lets see [Boilerplate Code Read Data](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Read.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

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
}
```

#
### 8. Query Data
> Lets see [Boilerplate Code Query Data](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Query.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //dont forget to write this to
    @OtherTableData(withTable = "table2", alias = "id_table1")
    private String id_table1;

    ...

    public List<Table1> query(){
        String query ="SELECT table1.*, table2.id_table1 FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
        return queryData(Table1.class, sqLiteDatabase, query);
    }
}
```

#
### 9. Query Result
> Lets see [Boilerplate Code Query Result](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Query_Result.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    public boolean queryResultUpdate() {
        String query = "UPDATE table1 SET flag_Active='2' WHERE id='1'";
        return queryResult(sqLiteDatabase, query); //return true/false
    }
}
```
**Notes :**
You can use it to excecute `update` or `delete` query and give you `true/false` as return.

#
### 10. Read Single Data
> Lets see [Boilerplate Code Read Single Data](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Read_Single.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

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
}
```
**Notes :**
You have to validate result is null or not.
```java
Table1 read3 = table1.read3();
if (read3!=null)
    Log.d(TAG, "onCreate_12: " + read3.getName());
else
    Log.d(TAG, "onCreate_12: " + "null");
```

#
### 11. Insert Or Ignore
> Lets see [Boilerplate Code Insert Or Ignore](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Insert_Or_Ignore.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //INSERT INTO table1 (id, name, rating, desc, flag_active, created_at) VALUES (6,'Zein', '10.0.', 'Android Programmer', '1', '12-12-2020');
    public boolean insertOrIgnore() {
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
}
```
**Notes :**
You can use it to excecute `insert` or `ignore` and give you `true/false` as return. `true` = inserted, `false` = ignored.

#
### 12. Insert Or Update
> Lets see [Boilerplate Code Insert Or Update](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/README_Insert_Or_Update.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

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
}
```
**Notes :**
You can use it to excecute `insert` or `update` and give you `true/false` as return.

#
### 13. Update And Backup Last Data
First you have to make table history, and put it in Annotation `@HistoryTable(tableName = "your_table_history_name")`.

Create `table1_his` that have same column with `table1` and add one extra column `id_edit` at the beggining of the `table1_his`. History table should be like this.
<p align="center">
  <img src="https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example9.JPG" width="400"/>
</p>

> Simple Code
```java
@SQLiteTable(tableName = "table1")
@HistoryTable(tableName = "table1_his")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //INSERT INTO table1 (id, name, rating, desc, flag_active, created_at) VALUES (10, 'Name 10', '1.6', 'Desc 10', '10', '12-12-2020');
    //or if value exists
    //UPDATE table1 SET name='Name 10', rating='1.6', desc='Desc 10', flag_active='10', created_at='12-12-2020' WHERE id='10';
    //INSERT INTO table1_his (id, name, rating, desc, flag_active, created_at) VALUES (10, 'Name 10', '1.6', 'Desc 10', '10', '12-12-2020');
    public boolean lastOnHistory() {
        Table1 data = new Table1();
        data.setId(10); //important line, please set your id first
        data.setName("Name 10");
        data.setRating(1.6);
        data.setDesc("Desc 10");
        data.setFlag_active(10);
        data.setCreated_at("12-12-2020");

        return lastDataOnHistory(Table1.class, sqLiteDatabase, data);
    }
}
```
**Notes :**
You can use it to insert new value. If value exists current data will be update and last data will insert to `History Table`.

#
### 14. Get Last Data

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //SELECT * FROM table1 ORDER BY id DESC LIMIT 1;
    public Table1 getLastData() {
        return readLastData(Table1.class, sqLiteDatabase);
    }
}
```
**Notes :**
You have to validate result is null or not.

---

Entity Old Verision
[Table1_OLD.java](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/entity/old/Table1_OLD.java)
 & [Table2_OLD.java](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/entity/old/Table2_OLD.java)

Entity New Verion
[Table1.java](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/entity/Table1.java)
 & [Table2.java](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/entity/Table2.java)

[DatabaseHelper.java](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/helper/DatabaseHelper.java)
 & [DBInstance.java](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/helper/DBInstance.java)
 & [TestActivity.java](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/java/com/gzeinnumer/easysqlitecrud/TestActivity.java)
 & [activity_test.xml](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/src/main/res/layout/activity_test.xml)
 & [gradle](https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/app/build.gradle)

---

#### Debug
<p align="center">
  <img src="https://github.com/gzeinnumer/EasySQLiteCRUD/blob/master/preview/example6.JPG" width="400"/>
</p>

---
# Example Code/App

**Example App [Java](https://github.com/gzeinnumer/EasySqliteCrudExample)**

[Sample Code And App](https://github.com/gzeinnumer/EasySqliteCrudExample)

---

You can combine this library with [SQLiteBuilder](https://github.com/gzeinnumer/SQLiteBuilder)

---
# Version
- **1.0.0**
  - First Release
- **1.0.6**
  - Kotlin Version
- **1.0.9**
  - Fixing Bug On Update
- **2.0.0**
  - Add Feature `queryResult()`
- **2.0.5**
  - Fixing Bug On Update Kotlin
- **2.0.6**
  - Remove Auto WHERE on All Query
- **2.0.7**
  - Add Feature `queryCount()`
- **2.0.8**
  - Bug Fixing
- **2.0.9**
  - Bug Fixing
- **2.1.0**
  - Bug Fixing
- **2.1.1**
  - Bug Fixing
- **3.0.0**
  - Support SDK 16
- **3.1.0**
  - Read Single Data
  - Insert Or Ignore
  - Insert Or Update
- **3.1.1**
  - Insert Or Ignore Condition
  - Insert Or Update Condition
- **3.2.0**
  - Insert And Backup To History
- **3.3.0**
  - Read Last Data
- **3.3.1**
  - add feature `@DefaultData`
  - add feature DefaultValue(CurrentTime) on `@TimeStampTypeData`
- **3.3.2**
  - remove feature `@JoinColumn`
  - add feature `@OtherTableData`

---
# Contribution
You can sent your constibution to `branch` `open-pull`.

---

```
Copyright 2020 M. Fadli Zein
```