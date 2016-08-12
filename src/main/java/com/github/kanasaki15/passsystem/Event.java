package com.github.kanasaki15.passsystem;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Event implements Listener {
	JavaPlugin plugin;
	boolean flag;
	String[] list;
	Event(JavaPlugin plugin,boolean newFlag,String[] list){
		this.plugin = plugin;
		this.flag = newFlag;
		this.list = list;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		List<String> pexList = PermissionsEx.getUser(p).getPermissions(p.getWorld().getName());
		boolean pexFlag = false;
		for (String pex : pexList){
			if (pex.toString().equals("aisys.op")){
				if (pexFlag){
					p.sendMessage("[あいしす] 新しいバージョンがあります！");
					p.sendMessage("[あいしす] 現在Ver:"+list[0]+"最新Ver:"+list[2]);
					p.sendMessage("[あいしす] 変更点");
					p.sendMessage("[あいしす] "+list[3]);
				}
			}
		 }

	}
}
