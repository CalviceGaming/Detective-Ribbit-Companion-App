package pt.iade.games.detectiveribbitlayout.controllers

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pt.iade.games.detectiveribbitlayout.models.Evidence
import pt.iade.games.detectiveribbitlayout.models.Player
import java.io.File

class Saves {
    fun saveEvidencesToFile(context: Context, evidences: List<Evidence>) {
        val file = File(context.filesDir, "evidences.json")
        val gson = Gson()
        Log.v("saveEvidencesToFile", evidences.toString())
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


    fun savePlayerToFile(context: Context, player: Player) {
        val file = File(context.filesDir, "player.json")
        val gson = Gson()
        Log.v("savePlayerToFile", player.toString())
        file.writeText(gson.toJson(player))
    }

    fun loadPlayerFromFile(context: Context): Player? {
        val file = File(context.filesDir, "player.json")
        return if (file.exists()) {
            val gson = Gson()
            val type = object : TypeToken<Player>() {}.type
            gson.fromJson(file.readText(), type)
        } else {
            null
        }
    }
}