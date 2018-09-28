package com.whatsplaying;

class ProcessLookupResponse implements IProcessLookupResponse {

	@Override
	public void processLookupResponse(Track[] tracks, TempFile file) { 
		file.delete();
		for(int i=0; i<tracks.length; i++) {
			Track track = tracks[i];
			System.out.println( "{ \"song\" : \"" + track.trackName + "\", \"artist\" : \"" + track.albumArtist + "\" }" );
		}
		
	}
	
}