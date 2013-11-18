/**
 * 
 */
package client;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/** This class will build all the components onto the mainclient gui
 * @author Kevin
 *
 */
public class BuildGUI {

	MainClientForm mcf;
	/**
	 * 
	 */
	public BuildGUI(MainClientForm mcf) {
		this.mcf = mcf;
	}
	
	private void createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem mi1 = new JMenuItem("Download Batch");
		JMenuItem mi2 = new JMenuItem("Logout");
		JMenuItem mi3 = new JMenuItem("Exit");

		file.add(mi1);
		file.add(mi2);
		file.add(mi3);
		menubar.add(file);
		
		mcf.setMenubar(menubar);
		mcf.setJMenuBar(menubar);
	}

}
