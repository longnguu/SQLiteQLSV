package com.example.sqlitequanlysinhvien;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitequanlysinhvien.Model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.ViewHolder> {
    private List<SinhVien> sinhViens222;
    // Lưu Context để dễ dàng truy cập
    private Context Contexts222;

    public SinhVienAdapter(List<SinhVien> computers, Context mContext) {
        this.sinhViens222 = computers;
        this.Contexts222 = mContext;
    }

    @NonNull
    @Override
    public SinhVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sinhvienitem, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SinhVienAdapter.ViewHolder holder, int position) {
        SinhVien computer =sinhViens222.get(position);
        holder.studentname.setText(computer.getMasv());
        holder.birthyear.setText(computer.getTensv());
        holder.itemview.setLongClickable(true);
    }


    @Override
    public int getItemCount() {
        if (sinhViens222!=null)
            return sinhViens222.size();
        else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView studentname;
        public TextView birthyear;
        public Button btnDel,btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            studentname = itemView.findViewById(R.id.studentname);
            birthyear = itemView.findViewById(R.id.birthyear);
            btnEdit = itemview.findViewById(R.id.btnEdit);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(view.getContext());
                    alertDiaLog.setTitle("Thông báo");
                    alertDiaLog.setMessage("Bạn có muốn xóa "+studentname.getText().toString().trim()+" ?"    );
                    alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SinhVienActivity.db.QueryData("delete from sinhvien where maSV ='"+studentname.getText().toString().trim()+"'");
                            System.out.println("delete from sinhvien where malop ='"+studentname.getText().toString().trim()+"'");
                            SinhVienActivity.sinhViens = new ArrayList<SinhVien>();
                            Cursor cursor = SinhVienActivity.db.GetData("Select * from sinhvien where maLop = '"+SinhVienActivity.idTruyen.toString().trim() +"'");
                            while (cursor.moveToNext()){
                                @SuppressLint("Range") String idC =cursor.getString(cursor.getColumnIndex("maSV"));
                                @SuppressLint("Range") String nameC = cursor.getString(cursor.getColumnIndex("tenSV"));
                                @SuppressLint("Range") String idCategory = cursor.getString(cursor.getColumnIndex("malop"));
                                System.out.println(idC+" "+nameC+" "+idCategory);
                                SinhVienActivity.sinhViens.add(new SinhVien(idC,nameC,idCategory));
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
            //Xử lý khi nút Chi tiết được bấm
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(view.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialogsinhviencustom);
                    dialog.show();
                    TextView tvt= (TextView)dialog.findViewById(R.id.topTV);
                    tvt.setText("Chỉnh sửa thông tin sinh viên");
                    TextView tv1 = (TextView) dialog.findViewById(R.id.isIDC);
                    TextView tv2 = (TextView) dialog.findViewById(R.id.isNameC);
                    TextView tv3 = (TextView) dialog.findViewById(R.id.isIDCate);
                    Button btok = (Button) dialog.findViewById(R.id.btn_okC);
                    Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelC);

                    btok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SinhVienActivity.db.QueryData("update sinhvien set maSV = '"+tv1.getText().toString().trim()+ "',tenSV='"+ tv2.getText().toString().trim()+ "'  where maSV ='"+studentname.getText().toString().trim()+"'");
                            SinhVienActivity.sinhViens = new ArrayList<SinhVien>();
                            Cursor cursor = SinhVienActivity.db.GetData("Select * from sinhvien where malop = '"+SinhVienActivity.idTruyen.toString().trim() +"'");
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
            });
        }
    }


}
