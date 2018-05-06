package com.scarabcoder.spigotanalytics.listener

import com.scarabcoder.spigotanalytics.SpigotAnalytics
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class ChatListener: Listener {

    @EventHandler
    fun onPlayerChat(e: ChatEvent) {
        if(e.sender !is ProxiedPlayer) return
        val req = SpigotAnalytics.getFilledRequest(e.sender as ProxiedPlayer)
        req.field("t", "event")
            .field("ec", "Interaction")
            .field("ea", "chat")
            .asStringAsync()
    }

}