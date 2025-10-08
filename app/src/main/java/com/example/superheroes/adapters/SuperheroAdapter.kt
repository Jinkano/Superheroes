package com.example.superheroes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes.R
import com.example.superheroes.data.Superhero
import com.squareup.picasso.Picasso

class SuperheroAdapter(
    var items: List<Superhero>,
    val onClickListener: (Int)-> Unit
) : RecyclerView.Adapter<SuperheroViewHolder>()
{
    /*
    * CUAL ES LA LISTA PARA LOS ELEMENTOS
    * */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
    ): SuperheroViewHolder
    {
        //
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero,parent,false)
        return SuperheroViewHolder(view)
    }

    /*
    * CUALES SON LOS DATOS PARA EL ELEMENTO QUE ESTA EN LA POSICION
    * */
    override fun onBindViewHolder(
        holder: SuperheroViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.render(item)

        //LLAMAMOS A LA FUNCION LAMDA DECLARADA EN EL COMNSTRUCTOR
        holder.itemView.setOnClickListener { onClickListener(position) }
    }

    //ELEMENTOS QUE SE QUIEREN LISTAR
    override fun getItemCount(): Int {
        return items.size
    }

    /*
    * CREAMOS LA FUNCION updateItems QUE ACTUALIZARA LA LISTA CON LA BUSQUEDA
    * */
    fun updateItems(items: List<Superhero>){
        this.items = items
        notifyDataSetChanged()
    }
}

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view)
{
    //DECLARAMOS LAS VARIABLES Y ENLAZAMOS CON LOS VIEW
    val tvNameSuperhero: TextView = view.findViewById(R.id.idTvNameSuperhero)
    val ivImageSuperhero: ImageView = view.findViewById(R.id.idIvImageSuperhero)

    //
    fun render(superhero: Superhero)
    {
        tvNameSuperhero.text = superhero.name
        Picasso.get().load("https://preview.redd.it/ojmvsiemycp51.jpg?width=1080&crop=smart&auto=webp&s=a56b68fc3a8442a3e4f2cb7220d8624b0c5302af").into(ivImageSuperhero)
    }
}