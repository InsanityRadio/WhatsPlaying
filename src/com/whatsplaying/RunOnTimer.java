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
	}
	
	@Override
	public void run() {
		//Try/Catch to keep running even when errors...bad practice
		try {
		TempFile sample = new TempFile();
		sampler.sample(config.getProperty("inputStream"), Integer.parseInt(config.getProperty("sampleLength")), sample.getFilePath());
		gnsdk.lookupStream(sample.getFilePath(), sample, processLookupResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
