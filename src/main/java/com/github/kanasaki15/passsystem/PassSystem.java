package com.github.kanasaki15.passsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
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
	String ver = "1.2";
	String verNum = "2";
	boolean NewFlag = false;
	@Override
	public void onEnable() {
		saveDefaultConfig();
		if (!(new File("./plugins/aisys/m.yml")).exists()){
			// メッセージ設定ファイルがなかったらデフォのメッセージ設定を保存する
			BufferedReader br = new BufferedReader(new InputStreamReader(getResource("m.yml"),CONFIG_CHAREST));
			try {
				String l = br.readLine();
				try(PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./plugins/aisys/m.yml"),CONFIG_CHAREST)))){
					while(l != null){
						pw.println(l);
						l = br.readLine();
					}
				}catch(IOException e){
					getLogger().info(""+e);
					onDisable();
				}
				br.close();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				getLogger().info(""+e1);
				onDisable();
			}
		}
		if (!(getServer().getPluginManager().isPluginEnabled("PermissionsEx"))){
			getLogger().info("あいしす(仮)を使うにはPermissionsExが必要です！");
			onDisable();
			getServer().getPluginManager().disablePlugin(this);
		}
		getLogger().info("あいしす(仮) Ver"+ver+" 起動しました");
		getLogger().info("by かなさき鯖( http://goo.gl/D4SEkA )");
		String confFilePath = getDataFolder() + File.separator + "config.yml";
		try(Reader reader=new InputStreamReader(new FileInputStream(confFilePath),CONFIG_CHAREST)){
			FileConfiguration conf=new YamlConfiguration();
			conf.load(reader);
			if (conf.getBoolean("UpdateCheck")){
				String NewVer = HttpGet("http://k7mc.xyz/aisys/ver.txt");
				String NewVer_Num = HttpGet("http://k7mc.xyz/aisys/ver2.txt");
				String ChangePoint = HttpGet("http://k7mc.xyz/aisys/ver3.txt");
				if (!NewVer.equals(verNum)){
					getLogger().info("新しいバージョンがあります！");
					getLogger().info("現在Ver:"+ver+"最新Ver:"+NewVer_Num);
					getLogger().info(""+ChangePoint);
					NewFlag = true;
				}
				String[] list = {ver,NewVer,NewVer_Num,ChangePoint};
				getServer().getPluginManager().registerEvents(new Event(this,NewFlag,list), this);
			}
		}catch(Exception e){
			getLogger().info(e.toString());
			onDisable();
		}
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

					// パーミッションノードがない＝認証済みってことで
					List<String> pexList = PermissionsEx.getUser(p).getPermissions(p.getWorld().getName());
					boolean pexFlag = false;
					for (String pex : pexList){
						if (pex.toString().equals("aisys.pin")){
							pexFlag = true;
							break;
						}
					}
					if (!pexFlag){
						/*
						getLogger().info("[pin] "+p.getName()+" さんは認証済みです！");
						sender.sendMessage(ChatColor.RED+"[pin] すでに認証しています！");
						*/
						try(Reader reader2=new InputStreamReader(new FileInputStream(getDataFolder()+File.separator +"m.yml"),CONFIG_CHAREST)){
							FileConfiguration conf2 = new YamlConfiguration();
							conf2.load(reader2);
							getLogger().info("[pin] "+conf2.getString("pin_ng1").replace("&Player", p.getName()));
							sender.sendMessage(ChatColor.RED+"[pin] "+conf2.getString("pin_ng2"));
						}catch(Exception e){
							getLogger().info(e.toString());
							onDisable();
						}
						return true;
					}

					String confFilePath = getDataFolder() + File.separator + "config.yml";
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

						}
						if ((flag && conf.getBoolean("wordFlag")) || (!flag && !conf.getBoolean("wordFlag"))){
							List<String> pex = conf.getStringList("nextPex");
							PermissionsEx.getUser(p).setParentsIdentifier(pex,"");
							PermissionsEx.getPermissionManager().resetUser(p);
							//getLogger().info("[pin] "+p.getName()+" さんが認証成功しました！");
							//sender.sendMessage(ChatColor.GREEN+"[pin] 認証成功しました！");
							try(Reader reader2=new InputStreamReader(new FileInputStream(getDataFolder()+File.separator +"m.yml"),CONFIG_CHAREST)){
								FileConfiguration conf2 = new YamlConfiguration();
								conf2.load(reader2);
								getLogger().info("[pin] "+conf2.getString("pin_ok1").replace("&Player", p.getName()));
								sender.sendMessage(ChatColor.GREEN+"[pin] "+conf2.getString("pin_ok2"));
								Collection<? extends Player> a = getServer().getOnlinePlayers();
								for (Player pl : a){
									List<String> pList = PermissionsEx.getUser(pl).getPermissions(pl.getWorld().getName());
									for (String pe : pList){
										if (pe.toString().equals("aisys.op")){
											pl.sendMessage(ChatColor.GREEN+"[pin]"+conf2.getString("pin_ok1").replace("&Player", p.getName()));
										}
									}
								}

							}catch(Exception e){
								getLogger().info(e.toString());
								onDisable();
							}
						}else{
							//getLogger().info("[pin] "+p.getName()+" さんが認証失敗しました");
							//getLogger().info("      合言葉不一致(入力されたもの："+args[0].toString()+")");
							//sender.sendMessage(ChatColor.RED+"[pin] 合言葉が間違っています！");
							try(Reader reader2=new InputStreamReader(new FileInputStream(getDataFolder()+File.separator +"m.yml"),CONFIG_CHAREST)){
								FileConfiguration conf2 = new YamlConfiguration();
								conf2.load(reader2);
								getLogger().info("[pin] "+conf2.getString("pin_ng3").replace("&Word", args[0].toString()).replace("&Player", p.getName()));
								sender.sendMessage(ChatColor.RED+"[pin] "+conf2.getString("pin_ng4"));
								Collection<? extends Player> a = getServer().getOnlinePlayers();
								for (Player pl : a){
									List<String> pList = PermissionsEx.getUser(pl).getPermissions(pl.getWorld().getName());
									for (String pe : pList){
										if (pe.toString().equals("aisys.op")){
											pl.sendMessage(ChatColor.GREEN+"[pin]"+conf2.getString("pin_ng3").replace("&Word", args[0].toString()).replace("&Player", p.getName()));
										}
									}
								}
							}catch(Exception e){
								getLogger().info(e.toString());
								onDisable();
							}
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
	public static String HttpGet(String u){
		 String a = "";
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
                    		a += line;
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
