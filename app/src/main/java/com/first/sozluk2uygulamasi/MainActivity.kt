package com.first.sozluk2uygulamasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.info.sqlitekullanimihazirveritabani.DatabaseCopyHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var kelimelerliste:ArrayList<Kelimeler>
    private lateinit var adapter: KelimelerAdapter
    private lateinit var vt:VeritabaniYardimcisi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        veritabaniKopyala()

        toolbar.title ="Sozlük Uygulaması"
      setSupportActionBar(toolbar)



     rv.setHasFixedSize(true)
     rv.layoutManager= LinearLayoutManager(this@MainActivity)

        vt=VeritabaniYardimcisi(this)

        kelimelerliste =KelimelerTablosuDao().tümkelimeler(vt)



      /*  kelimelerliste= ArrayList()
        var k1=Kelimeler(1,"cat","kedi")
        var k2=Kelimeler(2,"dog","köpek")
        var k3=Kelimeler(3,"hello","merhaba")

        kelimelerliste.add(k1)
        kelimelerliste.add(k2)
        kelimelerliste.add(k3)


       */
        adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
        rv.adapter=adapter



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val item=menu?.findItem(R.id.ActionAra)
        val searchView=item?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(this@MainActivity)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        arama(query)

        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        arama(newText)
        return true
    }
    fun veritabaniKopyala(){
        val copyHelper=DatabaseCopyHelper(this)

        try {
            copyHelper.createDataBase()
            copyHelper.openDataBase()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun arama(aramaKelime:String){
        kelimelerliste =KelimelerTablosuDao().aramaYap(vt,aramaKelime)

        adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
        rv.adapter=adapter
    }

}