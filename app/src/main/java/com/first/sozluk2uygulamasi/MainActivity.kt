package com.first.sozluk2uygulamasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var kelimelerliste:ArrayList<Kelimeler>
    private lateinit var adapter: KelimelerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title ="Sozlük Uygulaması"
      setSupportActionBar(toolbar)



     rv.setHasFixedSize(true)
     rv.layoutManager= LinearLayoutManager(this@MainActivity)
        kelimelerliste= ArrayList()



        var k1=Kelimeler(1,"cat","kedi")
        var k2=Kelimeler(2,"dog","köpek")
        var k3=Kelimeler(3,"hello","merhaba")

        kelimelerliste.add(k1)
        kelimelerliste.add(k2)
        kelimelerliste.add(k3)
        adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
        rv.adapter=adapter



    }
}