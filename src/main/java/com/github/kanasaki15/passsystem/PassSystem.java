package com.github.kanasaki15.passsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PassSystem extends JavaPlugin {
	final private Charset CONFIG_CHAREST=StandardCharsets.UTF_8;
	String ver = "0.5";
	@Override
	public void onEnable() {
		if (!(new File("./plugins/aisys/config.yml")).exists()){
			saveDefaultConfig();
		}
		if (!(getServer().getPluginManager().isPluginEnabled("PermissionsEx"))){
			getLogger().info("あいしす(仮)を使うにはPermissionsExが必要です！");
			onDisable();
		}
		getLogger().info("あいしす(仮) Ver"+ver+" 起動しました");
		getLogger().info("by かなさき鯖( http://goo.gl/D4SEkA )");
	}
	@Override
	public void onDisable() {
		getLogger().info("あいしす(仮) Ver"+ver+" 終了しました");

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("pin")) {
			if (args.length < 1){
				if (!(sender instanceof Player)) {
					getLogger().info("------------------------------------------------");
					getLogger().info("  あいしす Ver "+ver);
					getLogger().info("  /pin -- コマンド一覧 <- イマココ");
					getLogger().info("  /pin ver -- バージョン");
					getLogger().info("------------------------------------------------");
				}else{
					sender.sendMessage("------------------------------------------------");
					sender.sendMessage("  あいしす Ver "+ver);
					sender.sendMessage("  /pin -- コマンド一覧 <- イマココ");
					sender.sendMessage("  /pin ver -- バージョン");
					sender.sendMessage("  /pin <合言葉> -- 合言葉入力");
					sender.sendMessage("------------------------------------------------");
				}
				return true;
			}else if (args.length == 1){
				if(args[0].equals("ver")){
					if (!(sender instanceof Player)) {
						getLogger().info("------------------------------------------------");
						getLogger().info("  あいしす Ver "+ver);
						getLogger().info("  開発： かなさき");
						getLogger().info("         Github  https://github.com/kanasaki15");
						getLogger().info("------------------------------------------------");
					}else{
						sender.sendMessage("------------------------------------------------");
						sender.sendMessage("  あいしす Ver "+ver);
						sender.sendMessage("  開発： かなさき");
						sender.sendMessage("         Github  https://github.com/kanasaki15");
						sender.sendMessage("------------------------------------------------");
					}
				}else{
					if (!(sender instanceof Player)) {
						getLogger().info("Minecraft上で実行してください！");
						return true;
					}
					// パーミッションがなければ認証済みとみなす
					Player p = (Player)sender;
					if (!p.hasPermission("aisys.pin")){
						getLogger().info("[pin] "+p.getName()+" さんは認証済みです！");
						sender.sendMessage(ChatColor.RED+"[pin] すでに認証しています！");
					}
					String confFilePath=getDataFolder() + File.separator + "config.yml";
					try(Reader reader=new InputStreamReader(new FileInputStream(confFilePath),CONFIG_CHAREST)){
						FileConfiguration conf=new YamlConfiguration();
						conf.load(reader);
						List<String> list = conf.getStringList("word");
						for (String w : list){
							sender.sendMessage(w);
						}
					}catch(Exception e){
						getLogger().info(e.toString());
						onDisable();
					}

				}
				return true;
			}else{
				if (!(sender instanceof Player)) {
					getLogger().info("パラメーターが多いです");
				}else{
					sender.sendMessage("パラメーターが多いです");
				}
			}
		}
		return false;
	}
}
