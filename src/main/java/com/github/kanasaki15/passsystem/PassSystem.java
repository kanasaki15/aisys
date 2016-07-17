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

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PassSystem extends JavaPlugin {
	final private Charset CONFIG_CHAREST=StandardCharsets.UTF_8;
	String ver = "1.0";
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
					Player p = (Player)sender;
					List<String> pexList = PermissionsEx.getUser(p).getPermissions(p.getWorld().getName());
					boolean pexFlag = false;
					for (String pex : pexList){
						if (pex.toString().equals("aisys.pin")){
							pexFlag = true;
							break;
						}
					}
					if (!pexFlag){
						getLogger().info("[pin] "+p.getName()+" さんは認証済みです！");
						sender.sendMessage(ChatColor.RED+"[pin] すでに認証しています！");
						return true;
					}

					String confFilePath=getDataFolder() + File.separator + "config.yml";
					try(Reader reader=new InputStreamReader(new FileInputStream(confFilePath),CONFIG_CHAREST)){
						FileConfiguration conf=new YamlConfiguration();
						conf.load(reader);
						List<String> list = conf.getStringList("word");
						boolean flag = false;
						for (String w : list){
							// 比較
							if (args[0].toString().equals(w)){
								flag = true;
								break;
							}
							/*
							// デバッグ
							sender.sendMessage("取り込んできた物："+w);
							sender.sendMessage("チャットからの入力："+args[0]);
							if (args[0].toString().equals(w)){
								sender.sendMessage("等しい");
							}else{
								sender.sendMessage("等しくない");
							}
							*/

						}

						if (flag){
							PermissionsEx.getUser(p).setParentsIdentifier(conf.getStringList("nextPex"),"");
							getLogger().info("[pin] "+p.getName()+" さんが認証成功しました！");
							sender.sendMessage(ChatColor.GREEN+"[pin] 認証成功しました！");

						}else{
							getLogger().info("[pin] "+p.getName()+" さんが認証失敗しました");
							getLogger().info("      合言葉不一致(入力されたもの："+args[0].toString()+")");
							sender.sendMessage(ChatColor.RED+"[pin] 合言葉が間違っています！");
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
