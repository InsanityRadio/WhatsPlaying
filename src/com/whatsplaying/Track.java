package com.whatsplaying;

public class Track {
	String albumArtist;
	String trackArtist;
	String trackName;
	float trackDuration;

	public Track(String albumArtist, String trackArtist, String trackName, float trackDuration) {
		this.albumArtist = albumArtist;
		this.trackArtist = trackArtist;
		this.trackName = trackName;
		this.trackDuration = trackDuration;
	}

}
