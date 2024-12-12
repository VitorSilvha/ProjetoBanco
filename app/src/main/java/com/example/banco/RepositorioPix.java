package com.example.banco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPix extends SQLiteOpenHelper {
    public RepositorioPix(@Nullable Context context) { super(context, "pix", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table pix" +
                "(id integer not null primary key," +
                "cpf text," + "telefone text)";
        sqLiteDatabase.execSQL(sql);
        Log.i("log", "Criado a tabela PIX");
    }

    public void adcionarChave (Pix pix){
        String sql = "insert into pix values(null,'" + pix.cpf + "','" + pix.telefone + "')";
        Log.i("pix", "SQL insert pix:" + sql);
        super.getWritableDatabase().execSQL(sql);
    }

    @SuppressLint("Range")
    public List<Pix> listarChaves() {
        ArrayList<Pix> lista = new ArrayList<Pix>();
        String sql = "select * from pix";
        Cursor cursor = getWritableDatabase()
                .rawQuery(sql, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Pix pix = new Pix();
            pix.id = cursor.getInt(0); // coluna 0
            pix.cpf = cursor.getString(1); // coluna 1
            pix.telefone = cursor.getString(2);// coluna 2
            lista.add(pix);
            cursor.moveToNext();
        }
        cursor.close();
        return lista;
    }

    public void removerPix(Integer id){
        String sql = "delete from pix where id =" + id;
        getWritableDatabase().execSQL(sql);
        Log.i("pix","SQL delete pix: " + sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}