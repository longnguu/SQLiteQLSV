package com.example.sqlitequanlysinhvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqlitequanlysinhvien.Model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class SinhVienActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static SinhVienAdapter adapter;
    public static List<SinhVien> sinhViens=new ArrayList<SinhVien>();
    public static Database db;
    public static String idTruyen;
    TextView tv1,tv2,tv3;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien);


        recyclerView = findViewById(R.id.RecycleViewComputer);
        Intent intent = getIntent();
        idTruyen = intent.getStringExtra("malop");
        System.out.println(idTruyen.toString().trim());
        db = new Database(this, "COMPUTER.sqlite", null, 1);
        getDataComputer();
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ABCCC");
                TextView studentname = (TextView) findViewById(R.id.studentname);
                AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(view.getContext());
                alertDiaLog.setTitle("Thông báo");
                alertDiaLog.setMessage("Bạn có muốn xóa "+studentname.getText().toString().trim()+" ?"    );
                alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SinhVienActivity.db.QueryData("delete from Computer where idComputer ='"+studentname.getText().toString().trim()+"'");
                        SinhVienActivity.sinhViens = new ArrayList<SinhVien>();
                        Cursor cursor = SinhVienActivity.db.GetData("Select * from Computer where idCategory = '"+SinhVienActivity.idTruyen.toString().trim() +"'");
                        while (cursor.moveToNext()){
                            @SuppressLint("Range") String masv =cursor.getString(cursor.getColumnIndex("maSV"));
                            @SuppressLint("Range") String tensv = cursor.getString(cursor.getColumnIndex("tenSV"));
                            @SuppressLint("Range") String malop = cursor.getString(cursor.getColumnIndex("malop"));
                            SinhVienActivity.sinhViens.add(new SinhVien(masv,tensv,null,null,null,null,malop));
//            computers.add(new Computer(cursor.getString(cursor.getColumnIndex("idComputer")),cursor.getString(cursor.getColumnIndex("nameComputer")),cursor.getString(cursor.getColumnIndex("idCategory"))));
                        }
                        SinhVienActivity.adapter = new SinhVienAdapter(SinhVienActivity.sinhViens, view.getContext());
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(view.getContext(), 1);
                        SinhVienActivity.recyclerView.setAdapter(SinhVienActivity.adapter);
                        SinhVienActivity.recyclerView.setLayoutManager(linearLayoutManager);
                    }
                });
                alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDiaLog.show();
            }
        });
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("abcccc");
                TextView studentname = (TextView) findViewById(R.id.studentname);
                AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(view.getContext());
                alertDiaLog.setTitle("Thông báo");
                alertDiaLog.setMessage("Bạn có muốn xóa "+studentname.getText().toString().trim()+" ?"    );
                alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SinhVienActivity.db.QueryData("delete from Computer where idComputer ='"+studentname.getText().toString().trim()+"'");
                        SinhVienActivity.sinhViens = new ArrayList<SinhVien>();
                        Cursor cursor = SinhVienActivity.db.GetData("Select * from Computer where idCategory = '"+SinhVienActivity.idTruyen.toString().trim() +"'");
                        while (cursor.moveToNext()){
                            @SuppressLint("Range") String masv =cursor.getString(cursor.getColumnIndex("maSV"));
                            @SuppressLint("Range") String tensv = cursor.getString(cursor.getColumnIndex("tenSV"));
                            @SuppressLint("Range") String malop = cursor.getString(cursor.getColumnIndex("malop"));
                            SinhVienActivity.sinhViens.add(new SinhVien(masv,tensv,null,null,null,null,malop));
//            computers.add(new Computer(cursor.getString(cursor.getColumnIndex("idComputer")),cursor.getString(cursor.getColumnIndex("nameComputer")),cursor.getString(cursor.getColumnIndex("idCategory"))));
                        }
                        SinhVienActivity.adapter = new SinhVienAdapter(SinhVienActivity.sinhViens, view.getContext());
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(view.getContext(), 1);
                        SinhVienActivity.recyclerView.setAdapter(SinhVienActivity.adapter);
                        SinhVienActivity.recyclerView.setLayoutManager(linearLayoutManager);
                    }
                });
                alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDiaLog.show();
                return true;
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataComputer();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.headmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuadd:
                addSinhVien();
                break;
            case R.id.menuexit:
                break;
        }
        return true;
    }

    private void addSinhVien() {
        Dialog dialog = new Dialog(SinhVienActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogsinhviencustom);
        dialog.show();
        TextView tvt= (TextView)dialog.findViewById(R.id.topTV);
        tvt.setText("Thêm sinh viên");
        TextView tv1 = (TextView) dialog.findViewById(R.id.isIDC);
        TextView tv2 = (TextView) dialog.findViewById(R.id.isNameC);
        TextView tv3 = (TextView) dialog.findViewById(R.id.isIDCate);
        tv3.setText(idTruyen.toString().trim());
        Button btok = (Button) dialog.findViewById(R.id.btn_okC);
        Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelC);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.insert("sinhvien",tv1.getText().toString().trim(), tv2.getText().toString().trim(), tv3.getText().toString());
                getDataComputer();
                dialog.dismiss();

            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void getDataComputer() {
        SinhVienActivity.sinhViens = new ArrayList<SinhVien>();
        Cursor cursor = db.GetData("Select * from sinhvien where malop = '"+idTruyen.toString().trim() +"'");
        while (cursor.moveToNext()){
            @SuppressLint("Range") String masv =cursor.getString(cursor.getColumnIndex("maSV"));
            @SuppressLint("Range") String tensv = cursor.getString(cursor.getColumnIndex("tenSV"));
            @SuppressLint("Range") String malop = cursor.getString(cursor.getColumnIndex("malop"));
            sinhViens.add(new SinhVien(masv,tensv,null,null,null,null,malop));
//            computers.add(new Computer(cursor.getString(cursor.getColumnIndex("idComputer")),cursor.getString(cursor.getColumnIndex("nameComputer")),cursor.getString(cursor.getColumnIndex("idCategory"))));
        }
        adapter = new SinhVienAdapter(sinhViens, this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}