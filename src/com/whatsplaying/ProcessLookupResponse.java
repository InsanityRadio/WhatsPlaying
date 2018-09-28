package com.whatsplaying;

class ProcessLookupResponse implements IProcessLookupResponse {
	Track previousTrack = new Track(null, null, null, 0);

	@Override
	public void processLookupResponse(Track[] tracks, TempFile file) { 
		file.delete();

		//Although multiple tracks may be returned, here we are only caring about the first track returned.
			if( tracks[0].getTrackName().equals(previousTrack.getTrackName()) && tracks[0].getAlbumArtist().equals(previousTrack.getAlbumArtist()) ) {
				//Ignore as same track still playing
			} else {
				System.out.println( "{ \"song\" : \"" + tracks[0].getTrackName() + "\", \"artist\" : \"" + tracks[0].getAlbumArtist() + "\" }" );
				previousTrack = tracks[0];
			}
	}

}
