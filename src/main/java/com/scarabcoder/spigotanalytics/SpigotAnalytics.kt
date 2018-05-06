package com.scarabcoder.spigotanalytics

import com.mashape.unirest.http.Unirest
import com.mashape.unirest.request.body.MultipartBody
import com.scarabcoder.spigotanalytics.listener.ChatListener
import com.scarabcoder.spigotanalytics.listener.ConnectionListener
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Plugin
import java.io.File
import net.md_5.bungee.config.YamlConfiguration
import net.md_5.bungee.config.ConfigurationProvider
import java.io.IOException
import java.nio.file.Files


/**
 * Created by owner on 12/27/2017.
 */
class SpigotAnalytics : Plugin() {


    override fun onEnable() {
        proxy.pluginManager.registerListener(this, ConnectionListener())
        proxy.pluginManager.registerListener(this, ChatListener())
        if(!dataFolder.exists())
            dataFolder.mkdir()
        val cfg = File(dataFolder, "config.yml")
        if (!cfg.exists()) {
            try {
                Files.copy(getResourceAsStream("config.yml"), cfg.toPath())
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        val configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(cfg)
        code = configuration.getString("google-analytics-code")
    }

    companion object {

        var code: String? = null

        fun getFilledRequest(player: ProxiedPlayer) : MultipartBody {
            var req = Unirest.post("https://www.google-analytics.com/collect")
                    .field("v", 1)
                    .field("t", "pageview")
                    .field("tid", code)
                    .field("cid", player.uniqueId.toString())
                    .field("uip", player.pendingConnection.address.hostName)
            if(player.server != null){
                return req.field("dp", player.server.info.name)
            }

            return req
        }
    }

}