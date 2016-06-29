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
	String ver = "0.2";
	@Override
	public void onEnable() {
		if (!(new File("./plugins/aisys/config.yml")).exists()){
			saveDefaultConfig();
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
						getLogger().info(ChatColor.RED+"Minecraft上で実行してください！");
					}
					boolean flag = false;
					boolean pexFlag = false;

					FileConfiguration conf = new YamlConfiguration();
					List<String> word = conf.getStringList("word");
					for (String w : word){
						if (args[0].equals(w)){
							flag = true;
							break;
						}
					}
					if (!(new File("./plugins/PermissionsEx/permissions.yml")).exists()){
						pexFlag = true;
					}else{
						try(Reader reader=new InputStreamReader(new FileInputStream("./plugins/PermissionsEx/permissions.yml"),CONFIG_CHAREST)){
							FileConfiguration pexconf = new YamlConfiguration();
							pexconf.load(reader);
//							pexconf.getStringList(); // まだここまで
						} catch (Exception e) {
							// TODO 自動生成された catch ブロック
							getLogger().info(""+e);
							sender.sendMessage(ChatColor.RED+"[pin] エラーが発生しました 管理者に知らせてください");

							onDisable();
						}

					}
					if (flag == true){
						sender.sendMessage(ChatColor.GREEN+"[pin] 認証に成功しました！");
					}else{
						sender.sendMessage(ChatColor.RED+"[pin] 合言葉が違います！");
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
