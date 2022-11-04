package com.example.sqlitequanlysinhvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sqlitequanlysinhvien.Model.Lop;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvLop;
    List<Lop> lopList;
    Database db;
    ArrayAdapter<Lop> lopArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this,"QLSV.sqlite",null,1);
        lvLop = (ListView) findViewById(R.id.listviewlop);
        getDataLop();
        lvLop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SinhVienActivity.class);
                intent.putExtra("malop", lopList.get(i).getMalop());
                System.out.println(lopList.get(i).getTenlop());
                startActivity(intent);
            }
        });
        registerForContextMenu(lvLop);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulongclik,menu);
    }

    private void getDataLop() {
        lopList = new ArrayList<Lop>();
        Cursor cursor = db.GetData("select * from Lop");
        System.out.println("ABC " + cursor.getCount());
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                @SuppressLint("Range") String idCa = cursor.getString(cursor.getColumnIndex("maLop"));
                @SuppressLint("Range") String nameCa = cursor.getString(cursor.getColumnIndex("tenLop"));
                lopList.add(new Lop(idCa,nameCa));
            }
        }
        lopArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,lopList);
        lvLop.setAdapter(lopArrayAdapter);
        lopArrayAdapter.notifyDataSetChanged();
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
                addLop();
                break;
            case R.id.menuexit:
                break;
        }
        return true;
    }

    private void addLop() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoglopcustom);
        dialog.show();
        TextView tv1 = (TextView) dialog.findViewById(R.id.isIDCa);
        TextView tv2 = (TextView) dialog.findViewById(R.id.isNameCa);
        Button btok = (Button) dialog.findViewById(R.id.btn_okCa);
        Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelCa);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.insert("lop",tv1.getText().toString().trim(),tv2.getText().toString().trim());
                dialog.dismiss();
                getDataLop();
                lopArrayAdapter.notifyDataSetChanged();
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}