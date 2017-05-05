package zz.zept.yczd.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * com.york.yorkbbs.db.DBHelper
 *
 * @author 王少雷 <br/>
 *         create at 2015年4月7日 上午8:17:16
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance = null;
    private static final String DBNAME = "yczd.db";

    private DBHelper(Context context) {
        super(context, DBNAME, null, 11);
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE COMPANY([FACTORYID] VARCHAR,[FACTORYNAME] VARCHAR,[CODE] VARCHAR)");

    }

    //2.2=3
    //2.31 2.4=4
    //2.51=5
    //2.6 2.61=6
    //4.1=10
    //4.2=11
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
//        L.d("SQLiteDatabase Version:" + "oldVersion=" + oldVersion + "---" + "newVersion" + newVersion);
        // 迭代升级(跨版本升级)
        for (int i = oldVersion + 1; i <= newVersion; i++) {

        }
    }


}