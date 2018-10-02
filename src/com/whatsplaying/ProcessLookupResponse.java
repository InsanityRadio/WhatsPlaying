package com.whatsplaying;

class ProcessLookupResponse implements IProcessLookupResponse {
	Track previousTrack = new Track(null, null, null, 0);
	Track previousPublishedTrack = new Track(null, null, null, 0);

	@Override
	public void processLookupResponse(Track[] tracks, TempFile file) {
		file.delete();

		if(tracks.length >0) {
			// Although multiple tracks may be returned, here we are only caring about the first track returned.
			if( tracks[0].getTrackName().equals(previousPublishedTrack.getTrackName()) && tracks[0].getAlbumArtist().equals(previousPublishedTrack.getAlbumArtist()) ) {
				//Ignore as same track still playing
			} else if( tracks[0].getTrackName().equals(previousTrack.getTrackName()) && tracks[0].getAlbumArtist().equals(previousTrack.getAlbumArtist()) ){
				publishTrack(tracks[0]);
			}
			previousTrack = tracks[0];
		}
	}
	
	void publishTrack(Track track) {
		previousPublishedTrack = track;
		System.out.println( "{ \"song\" : \"" + track.getTrackName() + "\", \"artist\" : \"" + track.getAlbumArtist() + "\" }" );
	}

}
