package com.whatsplaying;

import java.util.Timer;

public class WhatsPlaying {

	public static void main(String[] args) {
		Config config = new Config("config.cfg");
		Timer timer = new Timer();
		timer.schedule(new RunOnTimer(config), 0, Integer.parseInt(config.getProperty("sampleInterval")) * 1000);

	}
}
