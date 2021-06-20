### Insert Or Update
```java
public class Table1_OLD {

    ...

    public boolean insertOrIgnore(){
        //set value
        Table1_OLD data = new Table1_OLD();
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        int count = 0;
        try {
            Cursor cursor = GblVariabel.myDb.rawQuery("SELECT COUNT(*) FROM table1 where id='1'", null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception e){
            Log.d(TAG, "count: "+e.getMessage());
        }

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, data.getName());
        values.put(KEY_RATING, data.getRating());
        values.put(KEY_DESC, data.getDesc());
        values.put(KEY_FLAG_ACTIVE, data.getFlag_active());
        values.put(KEY_CREATED_AT, data.getCreated_at());

        if(count==0){
            try {
                GblVariabel.myDb.insert(TABLE, null, values);
                return true;
            } catch (Exception e) {
                Log.d(TAG, "insert: " + e.getMessage());
                return false;
            }
        } else {
            String whereCondition = "id = '1'";

            long row = GblVariabel.myDb.update(TABLE, contentValues, whereCondition, null);

            if (row > 0) {
                Log.d(TAG, "update: success");
                return true;
            } else {
                Log.d(TAG, "update: failed");
                return false;
            }
        }
    }
}
```

---

```
Copyright 2020 M. Fadli Zein
```