package pt.iade.games.detectiveribbitlayout.controllers

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONObject
import pt.iade.games.detectiveribbitlayout.models.Collectible
import pt.iade.games.detectiveribbitlayout.models.Evidence
import pt.iade.games.detectiveribbitlayout.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class APIRequest {
    private val url = "https://detective-ribbit-server.onrender.com"

    suspend fun postCollectibles(
        playerId: Int,
        collectible: Collectible
    ): Boolean = withContext(Dispatchers.IO) {
        val (_, _, result) = Fuel.post("$url/collectibles/add?playerId=$playerId&collectibleId=${collectible.id}")
            .timeout(5000)
            .responseJson()

        result.fold(
            success = { true },
            failure = { false }
        )
    }

    suspend fun getEvidences(
        playerId: Int
    ): MutableList<Evidence> = withContext(Dispatchers.IO) {
        val (_, _, result) = Fuel.get("$url/evidences/get?playerId=$playerId")
            .timeout(5000)
            .responseJson()

        result.fold(
            success = { data ->
                val evidences = mutableListOf<Evidence>()
                val responseArr = data.array()
                for (i in 0 until responseArr.length()) {
                    val obj = JSONObject(responseArr[i].toString())
                    evidences.add(
                        Evidence(
                            id = obj.getInt("evidence_id"),
                            name = obj.getString("evidence_name"),
                            description = obj.getString("evidence_description")
                        )
                    )
                }
                evidences
            },
            failure = {
                Log.e("GetEvidences", "Error: ${it.response}")
                mutableListOf()
            }
        )
    }

    suspend fun getPlayerId(
        code: Int
    ): Player? = withContext(Dispatchers.IO) {
        val (_, _, result) = Fuel.get("$url/player/get?code=$code")
            .timeout(5000)
            .responseJson()

        result.fold(
            success = { data ->
                val responseArr = data.array()
                if (responseArr.length() > 0) {
                    val obj = JSONObject(responseArr[0].toString())
                    Player(id = obj.getInt("player_id"), code = code)
                } else null
            },
            failure = {
                Log.e("GetPlayerId", "Error: ${it.message}")
                null
            }
        )
    }
}
