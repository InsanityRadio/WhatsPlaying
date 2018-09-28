package com.whatsplaying;

public class WhatsPlaying {
	private static Config config;
	private static IProcessLookupResponse processLookupResponse;

	public static void main(String[] args) {
		config = new Config("config.cfg");
		Sampler sampler = new Sampler(config.getProperty("ffmpegPath"), config.getProperty("ffprobePath"));
		GNSDK gnsdk = new GNSDK(config.getProperty("gnsdkCLib"), config.getProperty("gnsdkJavaLib"), config.getProperty("gnsdkClientId"), config.getProperty("gnsdkClientIdTag"), config.getProperty("gnsdkLicense"));
		processLookupResponse = new ProcessLookupResponse();
		System.err.println("Ready.");

		while(true) {
			TempFile sample = new TempFile();
			System.err.println(sample.getFilePath());
			sampler.sample(config.getProperty("inputStream"), 20, sample.getFilePath());
			gnsdk.lookupStream(sample.getFilePath(), sample, processLookupResponse);
			try {
				Thread.sleep(Integer.parseInt(config.getProperty("sampleInterval")) * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
