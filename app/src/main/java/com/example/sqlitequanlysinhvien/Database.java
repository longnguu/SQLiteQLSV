package com.example.sqlitequanlysinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //query
    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    public long insert(String nametable,String id,String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("malop", id);
        values.put("tenlop", name);
        return db.insert(nametable, null, values);
    }
    public long insert(String nametable,String id,String name,String id2) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masv", id);
        values.put("tensv", name);
        values.put("malop",id2);
        return db.insert(nametable, null, values);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL="create table IF NOT EXISTS Lop(maLop VARCHAR(100),tenLop nVARCHAR(100))";
        sqLiteDatabase.execSQL(SQL);
        String SQL1="create table IF NOT EXISTS SinhVien(maSV VARCHAR(100),tenSV nVARCHAR(100),email varchar(100),diachi varchar(100),ngaysinh varchar(20),gioitinh varchar(50),malop varchar(100))";
        sqLiteDatabase.execSQL(SQL1);
        SQL = "INsert into lop values ('20T2','20T2')";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
