package com.nabilla.project.contohrealm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.nabilla.project.contohrealm.model.ModelBarang;
import com.nabilla.project.contohrealm.realm.RealmDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.realm.RealmResults;

public class TambahActivity extends AppCompatActivity {

    private Intent intent;
    private RealmDatabase realmDatabase;
    private List<ModelBarang> listBarang = new ArrayList<>();
    private EditText editNamaBarang, editHargaBarang, editJumlahBarang;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realmDatabase = new RealmDatabase(this);

        editNamaBarang = (EditText) findViewById(R.id.edt_nama_barang);
        editHargaBarang = (EditText) findViewById(R.id.edt_harga_barang);
        editJumlahBarang = (EditText) findViewById(R.id.edt_jumlah_barang);

        intent = getIntent();

        if (intent != null){
            if (intent.hasExtra("EDIT")){
                id = intent.getIntExtra("ID", id);

                ModelBarang modelBarang = new RealmDatabase(this).getDataById(ModelBarang.class, id);

                editNamaBarang.setText(modelBarang.getNamaBarang());
                editJumlahBarang.setText(String.valueOf(modelBarang.getJumlahBarang()));
                editHargaBarang.setText(String.valueOf(modelBarang.getHargaBarang()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finishAfterTransition();
                break;
            case R.id.menu_hapus:
                hapusData();
                break;
            case R.id.menu_simpan:
                ModelBarang modelBarang = new ModelBarang();
                modelBarang.setId((int) System.currentTimeMillis()/1000);
                modelBarang.setNamaBarang(editNamaBarang.getText().toString());
                modelBarang.setHargaBarang(Integer.valueOf(editHargaBarang.getText().toString()));
                modelBarang.setJumlahBarang(Integer.valueOf(editJumlahBarang.getText().toString()));
                listBarang.add(modelBarang);

                if (intent.hasExtra("EDIT")){
                    realmDatabase.editBarang(listBarang);
                }else {
                    realmDatabase.tambahBarang(listBarang);
                }

                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Berhasil menambahkan barang!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void hapusData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda yakin akan menghapus Barang?")
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new RealmDatabase(TambahActivity.this).hapusBarang(ModelBarang.class, id);
                        startActivity(new Intent(TambahActivity.this, MainActivity.class));
                        Toast.makeText(TambahActivity.this, "Berhasil menghapus barang!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
