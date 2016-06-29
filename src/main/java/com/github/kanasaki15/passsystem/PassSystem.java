package com.github.kanasaki15.passsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PassSystem extends JavaPlugin {
	String ver = "0.1.1";
	@Override
	public void onEnable() {
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
					if ((sender instanceof Player)) {
						sender.sendMessage("開発中...");
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
