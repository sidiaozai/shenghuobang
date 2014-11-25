package sqliteDataBase.Bll;

import sqliteDataBase.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Unforget {
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public Unforget(Context context){
		helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
	}
	
	public int insert(sqliteDataBase.Model.Unforget modelUnforget){
		ContentValues values = new ContentValues(); 
		values.put("year", modelUnforget.getYear());
		values.put("month",modelUnforget.getMonth());
		values.put("day", modelUnforget.getDay());
		values.put("hour", modelUnforget.getHour());
		values.put("minute", modelUnforget.getMinute());
		values.put("second", modelUnforget.getSecond());
		values.put("name", modelUnforget.getName());
		values.put("soundFileName", modelUnforget.getSoundFileName());
		
		db.insert("tableUnforget", null, values); 
		return 0;
	}
	
	public int update(sqliteDataBase.Model.Unforget modelUnforget){
		return 0;
	}
	public Cursor query()
    {
        Cursor c=db.query("tableUnforget", null, null, null, null, null, null);
        return c;
    }

	
	public int delete(int id){
		db.delete("tableUnforget", "id=?",new String[]{String.valueOf(id)});
		return 0;
	}
}