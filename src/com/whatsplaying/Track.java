package com.whatsplaying;

public class Track {
	private String albumArtist;
	private String trackArtist;
	private String trackName;
	private float trackDuration;

	public Track(String albumArtist, String trackArtist, String trackName, float trackDuration) {
		this.albumArtist = albumArtist;
		this.trackArtist = trackArtist;
		this.trackName = trackName;
		this.trackDuration = trackDuration;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public String getTrackArtist() {
		return trackArtist;
	}

	public String getTrackName() {
		return trackName;
	}

	public float getTrackDuration() {
		return trackDuration;
	}

}
