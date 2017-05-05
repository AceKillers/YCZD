package zz.zept.yczd.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import zz.zept.yczd.bean.Company;

/**
 * Created by HanChenxi on 2017/5/5.
 */

public class CompanyDBAction {
    private static DBHelper dbHelper = null;
    private static CompanyDBAction instance = null;

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    public CompanyDBAction() {

    }

    public static CompanyDBAction getInstance(Context context) {
        if (instance == null) {
            dbHelper = DBHelper.getInstance(context);
            instance = new CompanyDBAction();
        }
        return instance;
    }

    /**
     * 关闭数据库
     */
    public void closeDb() {
        dbHelper.close();
    }

    public int saveCompany(List<Company> list) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            if (list != null && list.size() > 0) {
                String sql = "insert into COMPANY (FACTORYID,FACTORYNAME,CODE) values (?,?,?)";
                SQLiteStatement stat = database.compileStatement(sql);
                for (Company c : list) {
                    stat.bindString(1, c.getFACTORYID());
                    stat.bindString(2, c.getFACTORYNAME());
                    stat.bindString(3, c.getCODE());
                    stat.executeInsert();
                }
            }
            database.setTransactionSuccessful();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return ERROR;
        } finally {
            // 事务处理完成
            database.endTransaction();
            database.close();
        }
    }

    public List<Company> searchCompany() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<Company> categories = new ArrayList<Company>();
        Company category;
        Cursor cursor = null;
        database.beginTransaction();
        try {
            cursor = database.rawQuery("select * from COMPANY", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    category = new Company();
                    category.setCODE(cursor.getString(cursor.getColumnIndex("CODE")));
                    category.setFACTORYID(cursor.getString(cursor.getColumnIndex("FACTORYID")));
                    category.setFACTORYNAME(cursor.getString(cursor.getColumnIndex("FACTORYNAME")));
                    categories.add(category);
                }
            }
            // 设置事务处理成功，不设置会自动回滚不提交
            database.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 处理完成
            database.endTransaction();
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return categories;
    }
}
