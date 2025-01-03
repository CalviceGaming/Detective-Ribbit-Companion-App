package pt.iade.games.detectiveribbitlayout.controllers

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONObject
import pt.iade.games.detectiveribbitlayout.models.Collectible
import pt.iade.games.detectiveribbitlayout.models.Evidence

class APIRequest {
    val url = "http://10.0.2.2:3000"

    fun PostCollectibles(
        playerId: Int,
        collectible: Collectible,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        Fuel.post("$url/collectibles/add?playerId=$playerId&collectibleId=${collectible.id}")
            .timeout(5000)
            .responseJson { request, response, result ->
                //println("Request: $request")
                //println("Response: $response")
                val (json, error) = result
                if (json != null) {
                    Log.d("PostCollectibles", "Collectible Send successfully")
                    onSuccess()
                }else{
                    Log.e("PostCollectibles", "Error: ${error?.response}")
                }
            }
    }

    fun GetEvidences(
        playerId: Int,
        onSuccess: (evidences: MutableList<Evidence>) -> Unit,
        onFailure: () -> Unit,
    ) {
        Fuel.get("$url/evidences/get?playerId=$playerId")
            .timeout(5000)
            .responseJson { request, response, result ->
                //println("Request: $request")
                //println("Response: $response")
                val (json, error) = result
                if (json != null) {
                    Log.d("GetEvidences", "All Evidences Succeeded");

                    //loop stuff
                    val responseArr = json.array();

                    var evidence: Evidence;

                    //list to send back
                    var evidences: MutableList<Evidence> = mutableListOf()

                    for (i in 0 until responseArr.length()){
                        val currJsonObject = JSONObject(responseArr[i].toString())


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
                    Log.e("GetEvidences", "Error: ${error?.message}")
                }
            }
    }
}
