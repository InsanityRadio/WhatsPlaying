package com.whatsplaying;

import com.gracenote.gnsdk.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class GNSDK {
	private String clientAppVersion = "1.0.0.0";
	private GnUser gnUser;

	public GNSDK(String libPath, String javaLibPath, String clientId, String clientIdTag, String license) {
		//Attempt to load gnsdk_java
		try {
			System.load(javaLibPath + "/libgnsdk_java_marshal.dylib");
		} catch (UnsatisfiedLinkError unsatisfiedLinkError) {
			System.err.println("Failed to load gnsdk_java: \n" + unsatisfiedLinkError.getMessage());
			System.exit(1);
		}

		//Setup GNSDK Manager & User
		try {
			@SuppressWarnings("unused")
			GnManager gnManager = new GnManager(libPath, license, GnLicenseInputMode.kLicenseInputModeString);
			gnUser = new GnUser(new UserStore(), clientId, clientIdTag, clientAppVersion);
			gnUser.options().lookupMode( GnLookupMode.kLookupModeOnline);
			GnLocale locale = new GnLocale(GnLocaleGroup.kLocaleGroupMusic, GnLanguage.kLanguageEnglish, GnRegion.kRegionDefault, GnDescriptor.kDescriptorSimplified, gnUser);
			locale.setGroupDefault();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void lookupStream(String path, TempFile file, IProcessLookupResponse callback) {
		try {
			AudioSource audioSource = new AudioSource(path);
			GnMusicIdStream idStream = new GnMusicIdStream(gnUser, GnMusicIdStreamPreset.kPresetRadio, new IGnMusicIdStreamEvents() {

				@Override
				public void musicIdStreamAlbumResult(GnResponseAlbums response, IGnCancellable arg1) {
					try {
						int matchCounter = 0;
						GnAlbumIterator itr = response.albums().getIterator();
						Track[] tracks = new Track[(int) response.albums().count()];
						while ( itr.hasNext() ) {
							GnAlbum album = itr.next();
							GnTrack track = album.trackMatched();
							tracks[matchCounter] = new Track(album.artist().name().display(), track.artist().name().display(), track.title().display(), track.duration());
							matchCounter++;
						}
						callback.processLookupResponse(tracks, file);
					} catch ( GnException gnException ) {
						System.err.println("GnException \t" + gnException.getMessage());
					}
				}

				@Override
				public void musicIdStreamIdentifyCompletedWithError(GnError arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void musicIdStreamIdentifyingStatusEvent(GnMusicIdStreamIdentifyingStatus arg0, IGnCancellable arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void musicIdStreamProcessingStatusEvent(GnMusicIdStreamProcessingStatus arg0, IGnCancellable arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void statusEvent(GnStatus arg0, long arg1, long arg2, long arg3, IGnCancellable arg4) {
					// TODO Auto-generated method stub

				}

			});
			idStream.options().resultSingle(true);
			idStream.audioProcessStart(audioSource);
			idStream.identifyAlbum();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}


	class UserStore implements IGnUserStore {

		@Override
		public GnString loadSerializedUser( String clientId ) {

			try {
				InputStream userFileInputStream = new FileInputStream( "user.txt" );

				Scanner scanner = new java.util.Scanner( userFileInputStream ).useDelimiter("\\A");
				GnString serializeUser = new GnString( scanner.hasNext() ? scanner.next() : "" );

				userFileInputStream.close();

				return serializeUser;

			} catch (IOException e) {
				// ignore
			}

			return null;
		}

		@Override
		public boolean storeSerializedUser( String clientId, String serializedUser ) {

			try{
				File userFile = new File( "user.txt" );
				if ( !userFile.exists() )
					userFile.createNewFile();
				PrintWriter out = new PrintWriter( userFile );
				out.print( serializedUser );
				out.close();
			} catch (IOException e) {
				return false;
			}

			return true;
		}
	}

	class AudioSource implements IGnAudioSource {

		RandomAccessFile wavFile;
		FileChannel wavFileChannel;

		String _path;

		public AudioSource (String path) {
			super();
			this._path = path;
		}

		@Override
		public long sourceInit() {
			try {
				wavFile = new RandomAccessFile(_path, "r");
				wavFile.seek(44);
				wavFileChannel = wavFile.getChannel();
				return 0;
			} catch (IOException e) {
				return -1;
			}
		}

		@Override
		public void sourceClose() {
			try {
				wavFileChannel.close();
				wavFile.close();
			} catch (IOException e) {
				// ignore
			}
		}

		@Override
		public long samplesPerSecond() {
			return 44100;
		}

		@Override
		public long sampleSizeInBits() {
			return 16;
		}

		@Override
		public long numberOfChannels() {
			return 2;
		}

		@Override
		public long getData(java.nio.ByteBuffer dataBuffer, long dataSize) {
			int bytesRead = 0;
			try {
				bytesRead = wavFileChannel.read(dataBuffer);
				if (bytesRead == -1) {
					return 0;
				} else {
					return bytesRead;
				}
			} catch (IOException e) {
				return 0;
			}
		}
	}


}