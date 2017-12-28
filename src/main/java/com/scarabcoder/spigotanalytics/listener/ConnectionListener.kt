package com.scarabcoder.spigotanalytics.listener

import com.mashape.unirest.http.Unirest
import com.scarabcoder.spigotanalytics.SpigotAnalytics
import com.sun.corba.se.impl.util.Version.asString
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.ServerConnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import sun.audio.AudioPlayer.player
import java.awt.SystemColor.info

/**
 * Created by owner on 12/28/2017.
 */
class ConnectionListener : Listener {


    @EventHandler fun onPlayerJoin(e: PostLoginEvent){
        var req = SpigotAnalytics.getFilledRequest(e.player)
                .field("sc", "start")
                .asStringAsync()

    }

    @EventHandler fun onPlayerQuit(e: PlayerDisconnectEvent){
        var req = SpigotAnalytics.getFilledRequest(e.player)
                .field("sc", "end")
                .asStringAsync()
    }

    @EventHandler fun onPlayerServerSwitch(e: ServerConnectEvent){
        SpigotAnalytics.getFilledRequest(e.player)
                .field("dp", e.target.name)
                .asStringAsync()
    }

}