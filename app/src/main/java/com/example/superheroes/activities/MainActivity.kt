package com.example.superheroes.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes.R
import com.example.superheroes.adapters.SuperheroAdapter
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity()
{
    //
    lateinit var rvSuperhero: RecyclerView
    lateinit var adapter: SuperheroAdapter
    var superheroList: List<Superhero> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /*Inicio del onCreate*/

        rvSuperhero = findViewById(R.id.idRvSuperhero)

        //PERSONALIZAR EL ACTIONBAR
        //        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_mn_back_24)
        supportActionBar?.setTitle(R.string.txt_menu_search)

        //
        adapter = SuperheroAdapter(superheroList) { position ->
            val superhero = superheroList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("SH_ID",superhero.id)
            startActivity(intent)

            //superheroList[it]
        }

        //
        rvSuperhero.adapter = adapter
        rvSuperhero.layoutManager = GridLayoutManager(this, 2)

        //
        searchSuperheroes("a")

        /*Fin del onCreate*/
    }

    //
    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchView = menu.findItem(R.id.idMnSearch).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchSuperheroes(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    //
    fun searchSuperheroes(query: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = SuperheroService.getInstance()
                val result = service.findSuperheroesByName(query)
                superheroList = result.results
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(superheroList)
                }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}