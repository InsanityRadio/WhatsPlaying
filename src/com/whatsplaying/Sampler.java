package com.whatsplaying;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class Sampler {
	private FFmpeg ffmpeg;
	private FFmpegExecutor executor;
	
	public Sampler(String ffmpegPath, String ffprobePath) {
		try {
			ffmpeg = new FFmpeg(ffmpegPath);
			executor = new FFmpegExecutor(ffmpeg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sample(String input, int sampleLength, String output) {
		FFmpegBuilder builder = new FFmpegBuilder();
		builder.setInput(input);
		builder.addOutput(output)
		    .setDuration(sampleLength, TimeUnit.SECONDS);
		executor.createJob(builder).run();
	}

}
