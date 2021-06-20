### Read Single Data
```java
public class Table1_OLD {

    ...

    public Table1_OLD readSingleData(){
        Cursor cursor;
        Table1_OLD current = new Table1_OLD();
        cursor = GblVariabel.myDb.rawQuery("SELECT * FROM table1 WHERE id='1';", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            current.id = cursor.getInt(cursor.getColumnIndex(this.KEY_ID));
            current.name = cursor.getString(cursor.getColumnIndex(this.KEY_NAME));
            current.rating = cursor.getInt(cursor.getColumnIndex(this.KEY_RATING));
            current.desc = cursor.getString(cursor.getColumnIndex(this.KEY_DESC));
            current.flag_active = cursor.getInt(cursor.getColumnIndex(this.KEY_FLAG_ACTIVE));
            current.created_at = cursor.getString(cursor.getColumnIndex(this.KEY_CREATED_AT));
        }
        cursor.close();
        return current;
    }
}
```

---

```
Copyright 2020 M. Fadli Zein
```