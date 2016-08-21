package com.github.kanasaki15.passsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Event implements Listener {
	Plugin plugin;

	Event(Plugin plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		List<String> pexList = PermissionsEx.getUser(p).getPermissions(p.getWorld().getName());
		for (String pex : pexList){
			String NewVer = PassSystem.HttpGet("http://k7mc.xyz/aisys/ver2.txt");
			int NewVer_Num = HttpGetInt("http://k7mc.xyz/aisys/ver.txt");
			List<String> ChangePoint = HttpGet("http://k7mc.xyz/aisys/ver3.txt");
			if (NewVer_Num > PassSystem.getVerNum()){
				plugin.getLogger().info("新しいバージョンがあります！");
				plugin.getLogger().info("現在Ver:"+PassSystem.getVer()+"最新Ver:"+NewVer);
				for (String Point : ChangePoint){
					plugin.getLogger().info(Point);
				}
				if (pex.toString().equals("aisys.op") || pex.toString().equals("*")){
					String[] a = {
							"新しいバージョンがあります！",
							"現在Ver:"+PassSystem.getVer()+"最新Ver:"+NewVer
					};
					p.getPlayer().sendMessage(a);
					for (int r = 0; r < ChangePoint.toArray().length; r++){
						p.getPlayer().sendMessage(ChangePoint.get(r));
					}
					break;
				}
			}
		}

	}

	private int HttpGetInt(String u) {
		 try {
			 URL url = new URL(u);
           HttpURLConnection connection = null;
           try {
           connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");

           if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8);
                   BufferedReader reader = new BufferedReader(isr)) {
            	   try{
            		   return Integer.parseInt(reader.readLine());
            	   }catch (Exception e){
            		   return 0;
            	   }

               }
           }
       } finally {
           if (connection != null) {
               connection.disconnect();
           }
       }
		 } catch (IOException e) {
	            e.printStackTrace();
	     }
		return 0;
	}


	public List<String> HttpGet(String u){
		 List<String> a = new ArrayList<>();
		 try {
			 URL url = new URL(u);
           HttpURLConnection connection = null;
           try {
           connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");

           if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8);
                   BufferedReader reader = new BufferedReader(isr)) {
                   String line;
                   while ((line = reader.readLine()) != null) {
                   	if (line != null){
                   		a.add(line);
                   	}
                   }
               }
           }
       } finally {
           if (connection != null) {
               connection.disconnect();
           }
       }
		 } catch (IOException e) {
	            e.printStackTrace();
	     }
		 return a;
	 }
}
