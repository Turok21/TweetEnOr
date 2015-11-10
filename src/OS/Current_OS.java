package OS;

import ihm.Accueil_IHM;


public class Current_OS {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	
	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}
	
	public static void main(String[] args) {
		
		System.out.println(Current_OS.isUnix());
       
	}
	
	

}
