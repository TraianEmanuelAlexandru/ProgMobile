package com.example.mygym.ui.schedaEsercizi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.Esercizio
import com.example.mygym.R
import com.example.mygym.databinding.FragmentListaUtentiBinding
import com.example.mygym.ui.schedaEsercizi.ListaUtentiFragmentDirections
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject


class ListaUtentiFragment : Fragment() {

    private var _binding: FragmentListaUtentiBinding? = null
    private lateinit var firestore: FirebaseFirestore
    lateinit var testArr: JSONArray
    lateinit var testArr2: JSONArray
    lateinit var testArr3: JSONArray
    private lateinit var secondaryMuscles: String
    private lateinit var instructions: String
    private lateinit var listaEsercizi: MutableList<Esercizio>
    private val binding get() = _binding!!

    //val database = initializeDatabase()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listaUtentiViewModel : ListaUtentiViewModel by viewModels()
        _binding = FragmentListaUtentiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firestore = FirebaseFirestore.getInstance()

        val listaUtenti = mutableListOf<String>()
        val recyclerView = binding.recyclerViewListaUtenti
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        firestore.collection(getString(R.string.collectionUtenti)).get().addOnSuccessListener {
            documents->
            for (document in documents){
                listaUtenti.add(document.id)
            }
            recyclerView.adapter = ListaUtentiAdapter(listaUtenti)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "DataBase Non raggiungibile", Toast.LENGTH_SHORT).show()
        }


        binding.editTextInserireEmail.setOnClickListener{
        /*   val email = binding.editTextInserireEmail.text.toString()
            if (email != null) {
                val listaGiorni = db.collection("scheda_utente")
                    .document(email)
                    .get()as List<Giorno>
                for(G in listaGiorni){
                    //G.esercizi.
                    val listaEsercizi = db.collection("Giorno")
                        .document(G.toString())
                        .get()as List<Giorno>
                    for (E in listaEsercizi){
                       // E.
                    }

                }
                /*val idEsercizio = dbRef.push().key!!
                val esercizio = Esercizio(idEsercizio,"leg_press", 3, 12, 30, email)
                dbRef.child(idEsercizio).setValue(esercizio)
                    .addOnCompleteListener{
                        Toast.makeText(activity, "Esercizio Inserito con successo", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{err->
                        Toast.makeText(activity, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                    }*/
            }   */
        }


        binding.buttonAggiungiGiornata.setOnClickListener{
            /*db.collection("Esercizio")
                .add(esercizio)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        Toast.makeText(activity, "Esercizio Inserito con successo", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(activity, "Esercizio Non Inserito", Toast.LENGTH_SHORT).show()
                }*/
            val runnable = Worker()
            val thread = Thread(runnable)
            thread.start()
        }

        return root


    }

    inner class Worker: Runnable {
        override fun run(){
                GetEsercizi()
        }
    }

private fun GetEsercizi(){
    listaEsercizi = mutableListOf()
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://exercisedb.p.rapidapi.com/exercises?limit=10")
        .get()
        .addHeader("X-RapidAPI-Key", "84ab417584msh7f427ba81894f50p137679jsn46005b4710f3")
        .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
        .build()

    /* val response = client.newCall(request).execute() */
    client.newCall(request).execute().use { response ->
        val myResponse = response.body!!.string()
        testArr = JSONArray(myResponse)
        for (i in 0 until testArr.length()) {
            var jsonObject1: JSONObject
            jsonObject1 = testArr.getJSONObject(i)
            testArr2 = jsonObject1.getJSONArray("secondaryMuscles")
            testArr3 = jsonObject1.getJSONArray("instructions")
            for (j in 0 until testArr2.length()){
                // var jsonObject2: JSONObject
               // jsonObject2 = testArr2.getJSONObject(j)
                var muscoliSecondari: String = ""
//                muscoliSecondari = testArr2.getString(j)
                Log.d("error3", "${response.body} ---------------------------------------")
                muscoliSecondari += testArr2.getString(j)
                secondaryMuscles = muscoliSecondari
            }
            for (k in 0 until testArr3.length()){
               // var jsonObject3: JSONObject
                //jsonObject3 = testArr3.getJSONObject(k)
                //instructions += jsonObject3.optString(k.toString()) + "\n"
                var istruzioni: String = ""
                istruzioni += testArr3.getString(k)
                instructions = istruzioni
            }
            var id: String = jsonObject1.optString("id")
            var nome: String = jsonObject1.optString("name")
            var parti: String = jsonObject1.optString("bodyPart")
            Log.d("Errore json", "$secondaryMuscles, $instructions, $id, $nome, $parti  ----------------------------------------------------------------------------")
            val esercizio = Esercizio(
                jsonObject1.optString("id"),
                jsonObject1.optString("name"),
                jsonObject1.optString("bodyPart"),
                jsonObject1.optString("equipment"),
                jsonObject1.optString("gifUrl"),
                jsonObject1.optString("target"),
                secondaryMuscles,
                instructions
            )


            listaEsercizi.add(esercizio)
            if (firestore.collection("Esercizi").whereEqualTo("name", esercizio.name).get().isSuccessful){
                Log.d("Esercizi", "Esercizio Gia Presente----------------------------------------------------------------------------------------------------------------------------------------------------------------------")
            }else {
                firestore.collection("Esercizi").add(esercizio)
            }
        }
    }

}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*suspend fun listAll(){
        val db =  initializeDatabase()
        db.listAll()

    }
     fun initializeDatabase(): DatabaseService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mygym-c1d3c-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()

        val databaseService = retrofit.create(DatabaseService::class.java)
        return databaseService
    }*/
}
