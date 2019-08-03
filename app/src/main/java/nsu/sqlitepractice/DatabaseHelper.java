package nsu.sqlitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "school.db";
    private static final String TABLE_NAME = "Taken";

    private static final String ID = "ID";
    private static final String NAME = "Name";

    private static final String DAY = "Day";
    private static final String SLOT = "Slot";

    private static final String CREATE_TABLE= "CREATE TABLE "+TABLE_NAME+"( ID VARCHAR(4) PRIMARY KEY , "+NAME+" VARCHAR (255),"+ DAY +" VARCHAR (10),"+ SLOT+" VARCHAR(1));";
    private static final String DROP_TABLE= "DROP TABLE "+TABLE_NAME+"";

    private static final String SELECT_ALL = "SELECT * FROM "+TABLE_NAME+"";


    private static final int VERSION_NUMBER = 1;

    private Context context;


    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);

        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{
            Toast.makeText(context,"Table is created" , Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }catch(Exception e){
            Toast.makeText(context,"Exception: " +e, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        try{
            Toast.makeText(context,"onUpgrade is created" , Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }catch(Exception e){
            Toast.makeText(context,"Exception: " +e, Toast.LENGTH_LONG).show();
        }


    }

    public long insertData(String name, String id, String day, String slot){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(ID,id);
        contentValues.put(DAY,day);
        contentValues.put(SLOT,slot);
       long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

       return rowId;
    }

    public Cursor searchData(String name, String day, String slot){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String tQUERY = "SELECT Name FROM "+TABLE_NAME+" WHERE Name = '"+name+"' AND Day="+day+" AND Slot="+slot+";";

        Cursor cursor = sqLiteDatabase.rawQuery(tQUERY,null);

        return cursor;
    }

    public Cursor displayAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);

        return cursor;
    }

    public int deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[]{ id});
    }

    public boolean updateData(String id, String name, String day, String slot)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(ID,id);
        contentValues.put(DAY,day);
        contentValues.put(SLOT,slot);
        sqLiteDatabase.update(TABLE_NAME,contentValues, ID+" = ?",new String[]{id});
        return true;

    }


}
