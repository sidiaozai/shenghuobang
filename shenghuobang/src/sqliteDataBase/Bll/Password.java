package sqliteDataBase.Bll;

import sqliteDataBase.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Password {
	
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public Password(Context context){
		helper = DatabaseHelper.getInstance(context);
        db = helper.getWritableDatabase();
	}
	public int insert(sqliteDataBase.Model.Password modelPassword){
		ContentValues values = new ContentValues(); 
		values.put("name", modelPassword.getName());
		values.put("passWord",modelPassword.getPassWord());
		values.put("soundFileName", modelPassword.getSoundFileName());
		
		
		db.insert("tablePassword", null, values); 
		return 0;
	}
	public int update(sqliteDataBase.Model.Password modelPassword){
		db.execSQL("update tablePassword set name=?,passWord=?,soundFileName=? where id=?;",
                new Object[] {modelPassword.getName(),modelPassword.getPassWord(),modelPassword.getSoundFileName(),modelPassword.getId()});
		return 0;
	}
	public Cursor query()
    {
        //Cursor c=db.query("tablePassword", null, null, null, null, null, null);
		Cursor c=db.rawQuery("select * from tablePassword", null);
        return c;
    }
	
	public sqliteDataBase.Model.Password query(int id){
		Cursor cursor=db.rawQuery("select * from tablePassword where id =?", new String[]{String.valueOf(id)});
		if(cursor.getCount()!=1)
			return null;
		
		cursor.moveToFirst();
		
		int idIndex =  cursor.getColumnIndex("id");
		int nameIndex = cursor.getColumnIndex("name");
		int passwordIndex = cursor.getColumnIndex("passWord");
		int soundFileNameIndex = cursor.getColumnIndex("soundFileName");
		
		String name = cursor.getString(nameIndex);
		String password = cursor.getString(passwordIndex);
		String soundFileName = cursor.getString(soundFileNameIndex);

		sqliteDataBase.Model.Password modelPassword = new sqliteDataBase.Model.Password(id,name, password, soundFileName);
		
		return modelPassword;
	}

	
	public int delete(int id){
		Log.i("tag", "ɾ�����ݣ�"+id);
		db.delete("tablePassword", "id=?",new String[]{String.valueOf(id)});
		return 0;
	}
	public int delete(){
		
		db.delete("tablePassword", "",null);
		return 0;
	}
	public int getMaxId(){
		int maxid=0;
		Cursor cr = db.rawQuery("select last_insert_rowid() from tablePassword;", null);  
		if(cr.moveToFirst())  
			maxid  = cr.getInt(0); 
		return maxid;
	}
	public int getCount(){
		int count=0;
		Cursor c = db.rawQuery("select count(*) from tablePassword ",null); 
		if(c.moveToNext()){
			count = c.getInt(0);
		}
		return count;
		   
	}

}
