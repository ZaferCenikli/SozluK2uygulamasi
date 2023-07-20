package com.info.retrofitkullanimi

import com.first.sozluk2uygulamasi.KelimelerDaointerface

class ApiUtils {

    companion object{

        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getKelimelerDaointerface(): KelimelerDaointerface {
            return RetrofitClient.getClient(BASE_URL).create(KelimelerDaointerface::class.java)
        }
    }
}



