package pt.iade.games.detectiveribbitlayout.controllers

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import pt.iade.games.detectiveribbitlayout.models.Collectible
import pt.iade.games.detectiveribbitlayout.models.Evidence
import java.io.File

class APIRequest {
    val url = "http://10.0.2.2:3000/evidences/get?playerId=1"

    fun GetEvidences(
        onSuccess: (evidences: MutableList<Evidence>) -> Unit,
        onFailure: () -> Unit,
    ) {
        Fuel.get(url)
            .timeout(5000)
            .responseJson { request, response, result ->
                //println("Request: $request")
                //println("Response: $response")
                val (json, error) = result
                if (json != null) {
                    Log.d("APIRequests", "All Evidences Succeeded");

                    //loop stuff
                    var i = 0;
                    val responseArr = json.array();
                    val responseLength = responseArr.length();

                    var evidence: Evidence;

                    //list to send back
                    var evidences: MutableList<Evidence> = mutableListOf()

                    for (i in 0 until responseLength){
                        var currString = responseArr[i].toString()
                        val currJsonObject = JSONObject(currString)


                        evidence = Evidence(
                            id = currJsonObject.getInt("evidence_id"),
                            name = currJsonObject.getString("evidence_name"),
                            description = currJsonObject.getString("evidence_description")
                        )

                        evidences.add(i, evidence);
                    }

                    onSuccess(
                        //on success stuff sent back
                        evidences
                    )
                }else{
                    Log.e("APIRequests", "Error: ${error?.message}")
                }
            }
    }

    fun saveEvidencesToFile(context: Context, evidences: List<Evidence>) {
        val file = File(context.filesDir, "evidences.json")
        val gson = Gson()
        Log.v("API", evidences[0].name)
        file.writeText(gson.toJson(evidences))
    }

    fun loadEvidencesFromFile(context: Context): List<Evidence>? {
        val file = File(context.filesDir, "evidences.json")
        return if (file.exists()) {
            val gson = Gson()
            val type = object : TypeToken<List<Evidence>>() {}.type
            gson.fromJson(file.readText(), type)
        } else {
            null
        }
    }
}
