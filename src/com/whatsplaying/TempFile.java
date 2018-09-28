package com.whatsplaying;

import java.io.File;
import java.io.IOException;

public class TempFile {
	private File tempFile;

	public TempFile() {
		try {
			tempFile = java.io.File.createTempFile("WhatsPlaying-", ".wav");
			//tempFile.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delete() {
		tempFile.delete();
	}
	
	public String getFilePath() {
		try {
			return tempFile.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
