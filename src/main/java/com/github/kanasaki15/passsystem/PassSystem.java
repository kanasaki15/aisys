package com.github.kanasaki15.passsystem;

import org.bukkit.plugin.java.JavaPlugin;

public class PassSystem extends JavaPlugin {
	String ver = "0.1";
	@Override
	public void onEnable() {
		getLogger().info("あいしす(仮) Ver"+ver+" 起動しました");
		getLogger().info("by かなさき鯖( http://goo.gl/D4SEkA )");
	}
	@Override
	public void onDisable() {
		getLogger().info("あいしす(仮) Ver"+ver+" 終了しました");

	}
}
