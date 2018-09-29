package com.whatsplaying;

import java.util.TimerTask;

class RunOnTimer extends TimerTask {
	Config config;
	Sampler sampler;
	GNSDK gnsdk;
	IProcessLookupResponse processLookupResponse;
	
	public RunOnTimer(Config config) {
		this.config = config;
		sampler = new Sampler(config.getProperty("ffmpegPath"), config.getProperty("ffprobePath"));
		gnsdk = new GNSDK(config.getProperty("gnsdkCLib"), config.getProperty("gnsdkJavaLib"), config.getProperty("gnsdkClientId"), config.getProperty("gnsdkClientIdTag"), config.getProperty("gnsdkLicense"));
		processLookupResponse = new ProcessLookupResponse();
		System.err.println("Ready.");
	}
	
	@Override
	public void run() {
		TempFile sample = new TempFile();
		System.err.println(sample.getFilePath());
		sampler.sample(config.getProperty("inputStream"), Integer.parseInt(config.getProperty("sampleLength")), sample.getFilePath());
		gnsdk.lookupStream(sample.getFilePath(), sample, processLookupResponse);
	}
}
