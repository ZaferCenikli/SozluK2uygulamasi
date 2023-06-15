package com.first.sozluk2uygulamasi

import android.annotation.SuppressLint

class KelimelerTablosuDao {

    @SuppressLint("Range")
    fun tümkelimeler(vt:VeritabaniYardimcisi):ArrayList<Kelimeler>{
        val kelimelerLise=ArrayList<Kelimeler>()
        val db=vt.writableDatabase
        val c=db.rawQuery("SELECT * FROM kelimeler",null)


        while (c.moveToNext()){
            val kelime=Kelimeler(c.getInt(c.getColumnIndex("kelime_id")),
            c.getString(c.getColumnIndex("ingilizce")),
            c.getString(c.getColumnIndex("turkce")))
            kelimelerLise.add(kelime)

        }
        return kelimelerLise

    }



    fun aramaYap(vt:VeritabaniYardimcisi,aramaKelime:String):ArrayList<Kelimeler>{
        val kelimelerLise=ArrayList<Kelimeler>()
        val db=vt.writableDatabase
        val c=db.rawQuery("SELECT * FROM kelimeler WHERE ingilizce like '%$aramaKelime%' ",null)

        @SuppressLint("Range")
        while (c.moveToNext()){
            val kelime=Kelimeler(c.getInt(c.getColumnIndex("kelime_id")),
                c.getString(c.getColumnIndex("ingilizce")),
                c.getString(c.getColumnIndex("turkce")))
            kelimelerLise.add(kelime)

        }
        return kelimelerLise

    }
}