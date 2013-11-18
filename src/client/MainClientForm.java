/**
 * 
 */
package client;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

/** This is the main form for the client GUI
 * @author Kevin
 *
 */
public class MainClientForm extends JFrame {

	BatchState state = new BatchState();
	
	JSplitPane bottomhorozontalsplit = new JSplitPane();
	JSplitPane middleverticalsplit = new JSplitPane();
	JMenuBar menubar = new JMenuBar();
	
	
	
	ClientCommunicator cc;
	/**
	 * 
	 */
	public MainClientForm(ClientCommunicator incc) {
		super("MainClientForm");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		cc = incc;
		//trying to make a class that would take care of putting all the components together..
		//BuildGUI builder = new BuildGUI(this);
		
		this.setLocation(250, 100);
		this.setSize(850,645);

		JMenu file = new JMenu("File");
		JMenuItem mi1 = new JMenuItem("Download Batch");
		JMenuItem mi2 = new JMenuItem("Logout");
		JMenuItem mi3 = new JMenuItem("Exit");

		file.add(mi1);
		file.add(mi2);
		file.add(mi3);
		menubar.add(file);
		
		this.setJMenuBar(menubar);
		OptionsPanel options = new OptionsPanel(this, state);
		
		this.add(options);
		
		//this.pack();
		//this.add(bottomhorozontalsplit);
		//this.add(middleverticalsplit);
	}
	/**
	 * @return the bottomhorozontalsplit
	 */
	public JSplitPane getBottomhorozontalsplit() {
		return bottomhorozontalsplit;
	}
	/**
	 * @param bottomhorozontalsplit the bottomhorozontalsplit to set
	 */
	public void setBottomhorozontalsplit(JSplitPane bottomhorozontalsplit) {
		this.bottomhorozontalsplit = bottomhorozontalsplit;
	}
	/**
	 * @return the middleverticalsplit
	 */
	public JSplitPane getMiddleverticalsplit() {
		return middleverticalsplit;
	}
	/**
	 * @param middleverticalsplit the middleverticalsplit to set
	 */
	public void setMiddleverticalsplit(JSplitPane middleverticalsplit) {
		this.middleverticalsplit = middleverticalsplit;
	}
	/**
	 * @return the menubar
	 */
	public JMenuBar getMenubar() {
		return menubar;
	}
	/**
	 * @param menubar the menubar to set
	 */
	public void setMenubar(JMenuBar menubar) {
		this.menubar = menubar;
	}
	/**
	 * @return the cc
	 */
	public ClientCommunicator getCc() {
		return cc;
	}
	/**
	 * @param cc the cc to set
	 */
	public void setCc(ClientCommunicator cc) {
		this.cc = cc;
	}

	
	
	
	
}
