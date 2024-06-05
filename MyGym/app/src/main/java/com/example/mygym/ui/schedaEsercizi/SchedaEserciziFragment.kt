package com.example.mygym.ui.schedaEsercizi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mygym.DatabaseService
import com.example.mygym.Esercizio
import com.example.mygym.Giorno
import com.example.mygym.databinding.FragmentSchedaEserciziBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit


class SchedaEserciziFragment : Fragment() {

    private var _binding: FragmentSchedaEserciziBinding? = null
    //lateinit var dbRef :DatabaseReference
    //lateinit var testArr: ResponseBody
    lateinit var testArr: JSONArray
    private lateinit var esercizioList: MutableList<Esercizio>
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val schedaEserciziViewModel : SchedaEserciziViewModel by viewModels()
        _binding = FragmentSchedaEserciziBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //dbRef = FirebaseDatabase.getInstance().getReference("Esercizio")
        initializeDatabase()



        //val esercizio = Giorno.Esercizio("1" ,"leg_press",3, 12, 30,"email")
        binding.editTextInserireEmail.setOnClickListener{
            val email = binding.editTextInserireEmail.text.toString()
            /*if (email != null) {
                val listaGiorni = db.collection("scheda_utente")
                    .document(email)
                    .get()as List<Giorno>
                for(G in listaGiorni){
                    //G.esercizi.
                    val listaEsercizi = db.collection("Giorno")
                        .document(G.toString())
                        .get()as List<Esercizio>
                    for (E in listaEsercizi){
                       // E.
                    }

                }*/



                /*val idEsercizio = dbRef.push().key!!
                val esercizio = Esercizio(idEsercizio,"leg_press", 3, 12, 30, email)
                dbRef.child(idEsercizio).setValue(esercizio)
                    .addOnCompleteListener{
                        Toast.makeText(activity, "Esercizio Inserito con successo", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{err->
                        Toast.makeText(activity, "Error ${err.message}", Toast.LENGTH_SHORT).show()

                    }*/
            //}
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
        }

        return root


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    suspend fun listAll(){
        val db =  initializeDatabase()
        db.listAll()

    }
    */
    public fun initializeDatabase(){
        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://mygym-c1d3c-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()

        val databaseService = retrofit.create(DatabaseService::class.java)
        return databaseService*/
        val db = FirebaseFirestore.getInstance()


        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://exercisedb.p.rapidapi.com/exercises?limit=10")
            .get()
            .addHeader("X-RapidAPI-Key", "84ab417584msh7f427ba81894f50p137679jsn46005b4710f3")
            .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
            .build()

        /* val response = client.newCall(request).execute() */
        client.newCall(request).execute().use { response ->
            val myResponse = response.body().toString()
            testArr = JSONArray(myResponse)
            for (i in 0 until testArr.length()) {
                var jsonObject1: JSONObject
                jsonObject1 = testArr.getJSONObject(i)
                val esercizio = Esercizio(
                    jsonObject1.optString("id"),
                    jsonObject1.optString("nome"),
                    jsonObject1.optString("bodyPart"),
                    jsonObject1.optString("equipment"),
                    jsonObject1.optString("gifUrl"),
                    jsonObject1.optString("target"),
                    jsonObject1.optJSONArray("secondaryMuscles"),
                    jsonObject1.optJSONArray("instructions")
                )
                db.collection("EserciziDaAPI")
                    .add(esercizio)
                esercizioList.add(esercizio)
            }
        }
    }
}
