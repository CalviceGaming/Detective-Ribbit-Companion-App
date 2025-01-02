package pt.iade.games.detectiveribbitlayout.controllers

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.google.gson.Gson
import org.json.JSONObject
import pt.iade.games.detectiveribbitlayout.models.Collectible
import java.io.File

class APIRequest {
    val url = "http://10.0.2.2:3000/collectibles/get?playerId=1"

    fun makeGetRequest(callback: (String?) -> Unit) {

        // Make the GET request
        Fuel.get(url)
            .responseJson { request, response, result ->
                // Handle the result
                result.fold(
                    success = { json ->
                        Log.d("APIRequests", "Response: ${json.obj()}")
                        callback(json.obj().toString()) // Pass the response back via callback
                    },
                    failure = { error ->
                        Log.e("APIRequests", "Error: ${error.message}")
                        callback(null) // Pass null in case of an error
                    }
                )
            }
    }

    public fun GetCollectibles(
        onSuccess: (collectables: MutableList<Collectible>) -> Unit,
        onFailure: () -> Unit,
    ) {
        Fuel.get(url)
            .timeout(5000)
            .responseJson { request, response, result ->
                //println("Request: $request")
                //println("Response: $response")
                val (json, error) = result
                if (json != null) {
                    Log.d("APIRequests", "All Challenges Succeeded");

                    //loop stuff
                    var i = 0;
                    val responseArr = json.array();
                    val responseLength = responseArr.length();

                    var collectibe: Collectible;

                    //list to send back
                    var collectibles: MutableList<Collectible> = mutableListOf()

                    while (i < responseLength) {
                        var currString = responseArr[i].toString()
                        val currJsonObject = JSONObject(currString)


                        collectibe = Collectible(
                            id = currJsonObject.getInt("collectibles_id"),
                            name = currJsonObject.getString("collectibles_name"),
                            image = 1,
                            description = currJsonObject.getString("collectibles_description"),
                            placeholderSize = 1,
                            isunlocked = true
                        )

                        collectibles.add(i, collectibe);

                        i++;
                    }

                    onSuccess(
                        //on success stuff sent back
                        collectibles
                    )
                }else{
                    Log.e("APIRequests", "Error: ${error?.message}")
                }
            }
    }

    fun saveCollectiblesToFile(context: Context, collectibles: List<Collectible>) {
        val file = File(context.filesDir, "collectibles.json")
        val gson = Gson()
        file.writeText(gson.toJson(collectibles))
    }
}
