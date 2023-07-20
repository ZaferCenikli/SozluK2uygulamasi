package com.first.sozluk2uygulamasi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KelimelerCevap(
    @SerializedName("kelimeler")
    @Expose
    var kelimeler:List<Kelimeler>
    ,
    @SerializedName("succes")
    @Expose
    var succes:Int) {

}