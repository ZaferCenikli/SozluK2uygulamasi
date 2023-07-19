package com.first.sozluk2uygulamasi

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.info.sqlitekullanimihazirveritabani.DatabaseCopyHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

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
        tumkelimeler()



      /*  kelimelerliste= ArrayList()
        var k1=Kelimeler(1,"cat","kedi")
        var k2=Kelimeler(2,"dog","köpek")
        var k3=Kelimeler(3,"hello","merhaba")

        kelimelerliste.add(k1)
        kelimelerliste.add(k2)
        kelimelerliste.add(k3)



        adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
        rv.adapter=adapter  */



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val item=menu?.findItem(R.id.ActionAra)
        val searchView=item?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(this@MainActivity)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        kelimeAra(query)

        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        kelimeAra(newText)
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
    fun tumkelimeler(){
        val url = "http://kasimadalan.pe.hu/sozluk/tum_kelimeler.php"
        val istek=StringRequest(Method.GET,url,Response.Listener {
            cevap->
            kelimelerliste= ArrayList()
            try {
                val Jsonobject=JSONObject(cevap)
                val kelimeler=Jsonobject.getJSONArray("kelimeler")
                for (i in 0 until kelimeler.length()){
                    val k =kelimeler.getJSONObject(i)

                    val kelime=Kelimeler(k.getInt("kelime_id"),
                        k.getString("ingilizce"),
                        k.getString("turkce"))
                    kelimelerliste.add(kelime)
                }
                adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
                rv.adapter=adapter

            }catch (e:Exception){
                e.printStackTrace()



            }

        },Response.ErrorListener {

        })
        Volley.newRequestQueue(this).add(istek)
    }
    fun kelimeAra(aramaKelime: String){
        val url = "http://kasimadalan.pe.hu/sozluk/kelime_ara.php"
        val istek=object:StringRequest(Method.POST,url,Response.Listener {
                cevap->
            kelimelerliste= ArrayList()
            try {
                val Jsonobject=JSONObject(cevap)
                val kelimeler=Jsonobject.getJSONArray("kelimeler")
                for (i in 0 until kelimeler.length()){
                    val k =kelimeler.getJSONObject(i)

                    val kelime=Kelimeler(k.getInt("kelime_id"),
                        k.getString("ingilizce"),
                        k.getString("turkce"))
                    kelimelerliste.add(kelime)
                }
                adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
                rv.adapter=adapter

            }catch (e:Exception){
                e.printStackTrace()



            }

        },Response.ErrorListener {

        }){
            override fun getParams(): MutableMap<String, String>? {
                val params=HashMap<String,String>()
                params["ingilizce"]=aramaKelime
                return params
            }
        }
        Volley.newRequestQueue(this).add(istek)
    }

}