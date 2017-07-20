package wh1spr.olympians.apollo.music;

import java.util.List;

public class OfflineMusicHandler {

	
	
	/**
	 * Returns all the artists (main artists) that are part of the offline music library.
	 */
	public static List<String> getArtists() {
		return null;
	}
	
	/**
	 * Returns a list of all songs in the offline library in the following format:
	 * {@code <Artist - Song name>}
	 */
	public static List<String> getSongs() {
		return null;
	}
	
	/**
	 * Returns a list of all songs of the given artists in the following format:
	 * {@code <Song name>}
	 * @return null if there are no songs from this artist.
	 * @return a list of songs from that artist if there are songs.
	 */
	public static List<String> getSongsFromArtists(String Artist) {
		return null;
	}
	
	/**
	 * Returns a list of songs it found in in the offline library in the following format:
	 * {@code <Artist - Song name>}
	 * @param toCheck String to check
	 * @return null if nothing was found
	 * @return a list of songs that contained the parameter string in their names.
	 */
	public static List<String> search(String toCheck) {
		return null;
	}
//	File f = new File("C:\\");
//	ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
//	
//	File file = new File("/path/to/directory");
//	String[] directories = file.list(new FilenameFilter() {
//	  @Override
//	  public boolean accept(File current, String name) {
//	    return new File(current, name).isDirectory();
//	  }
//	});
//	System.out.println(Arrays.toString(directories));
}
