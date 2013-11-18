package searchgui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import shared.communication.*;
import shared.model.Field;
import shared.model.Project;
import client.ClientCommunicator;

@SuppressWarnings("serial")
public class ViewProjectsForm extends JFrame {

	String username;
	String password;
	
	String desiredimage = null;
	
	GetProjects_result project_results = new GetProjects_result();
	ClientCommunicator cc = null;
	JPanel panel = new JPanel();
	JList<String> list;
	DefaultListModel<String> projectlist;
	ArrayList<Project> ps = new ArrayList<Project>();

	DefaultListModel<String> fieldlist;
	ArrayList<Field> fields = new ArrayList<Field>();

	DefaultListModel<String> searchfields;
	ArrayList<Field> sf = new ArrayList<Field>();
	JList<String> sfields;
	DefaultListModel<String> urllistmodel;
	JList<String> urls;

	JTextArea svalues = new JTextArea();
	
	JList<String> flist;
	ProjectsPanel pp;
	
	JButton search_button = new JButton();
	

	
	int cur_selected_project = 0;
	

	/**
	 * @return the project_results
	 */
	public GetProjects_result getProject_results() {
		return project_results;
	}

	/**
	 * @param project_results
	 *            the project_results to set
	 */
	public void setProject_results(GetProjects_result project_results) {
		this.project_results = project_results;
	}


	ViewProjectsForm(GetProjects_result projects, ClientCommunicator cc) {

		super("Search GUI");
		
		username = "test1";
		password = "test1";
		this.cc = cc;
		this.setProject_results(projects);
		this.setSize(850, 500);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		ps = projects.getProjects();
		list = new JList<String>();
		flist = new JList<String>();
		sfields = new JList<String>();
		urls = new JList<String>();
		
		projectlist = new DefaultListModel<String>();
		fieldlist = new DefaultListModel<String>();
		searchfields = new DefaultListModel<String>();
		urllistmodel = new DefaultListModel<String>();
		
		list.addMouseListener(projectmouselistener);
		flist.addMouseListener(fieldmouselistener);
		urls.addMouseListener(urllistlistener);
		
		for (Project project : ps) {
			projectlist.addElement(project.getTitle());
		}

		list.setModel(projectlist);
		flist.setModel(fieldlist);
		sfields.setModel(searchfields);
		urls.setModel(urllistmodel);
		sfields.ensureIndexIsVisible(6);
		svalues.setText("Enter Search Values here seperated with a comma");
		
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		flist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sfields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		//lets make our image viewer in this part
		
		////////////////////

		// list.setVisibleRowCount(5);
		//one pane with project and fields
		
		JSplitPane projects_fields_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,list,flist);
		
		JSplitPane sfields_svalues_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sfields, svalues);
		sfields_svalues_pane.setDividerLocation(200);
		search_button.setText("Search");

		search_button.addActionListener(searchlistener);
		
		JSplitPane sfields_values_and_button = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sfields_svalues_pane, search_button);
		sfields_values_and_button.setDividerLocation(360);
		
		JSplitPane search_url = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sfields_values_and_button, urls);
				
		JSplitPane whole = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projects_fields_pane, search_url);
		
		this.add(whole);
	}

	private MouseListener projectmouselistener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				String selectedItem = (String) list.getSelectedValue();
				//ok we have the project now see what its fiels are..
				//ps = project_results.getProjects();
				for(Project p : ps) {
					if(selectedItem.equals(p.getTitle())) {
						cur_selected_project = p.getID();
						fields.clear();
						fieldlist.clear();
						int pid = p.getID();
						GetFields_param params = new GetFields_param();
						params.setUsername(username);
						params.setPassword(password);
						params.setProjectid(pid);
						GetFields_result results = new GetFields_result();
						results = cc.getFields(params);
						fields = results.getFields();
					}
				}
				// add selectedItem to your second list.
				
				for(Field f : fields) {
					fieldlist.addElement(f.getTitle());
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	};
	
	private MouseListener fieldmouselistener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				String selectedItem = (String) flist.getSelectedValue();
				//ok we have the project now see what its fiels are..
				//ps = project_results.getProjects();
				for(Field f : fields) {
					//ok we can get the title and the project id..
					if(selectedItem.equals(f.getTitle())) {
						//we got a selected field now add it to the sfields.
						boolean add = true;
						for(int i = 0; i < sf.size(); i++) {
							if(f.getID() == sf.get(i).getID()) { //if its already there set add to false;
								add = false;
							}
						}
						if(add) {
							System.out.println(f.getID());
							searchfields.addElement(f.getTitle());
							sf.add(f);
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	};

	private ActionListener searchlistener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Integer> fieldids = new ArrayList<Integer>();
			for(Field f: sf) {
				fieldids.add(f.getID());
			}
			//OK if the search gets clicked what do we do?
			ArrayList<String> search_values = new ArrayList<String>();
			//lets split up the text area
			String sv = svalues.getText();
			String[] split_sv = sv.split(",");
			
			for(String s: split_sv) {
				search_values.add(s);
			}
			
			Search_param sp = new Search_param();
			sp.setUsername(username);
			sp.setPassword(password);
			sp.setFields(fieldids);
			sp.setSearch_values(search_values);
			Search_result sr = new Search_result();
			
			sr = cc.search(sp);
			
			//lets add them to the urllist
			ArrayList<String> myurls = new ArrayList<String>();
			myurls.clear();
			urllistmodel.removeAllElements();
			//urls.setModel(urllistmodel);
			myurls = sr.getURLS();
			
			for(String url : myurls) {
				urllistmodel.addElement(url);
			}
		}
	};
	
	private MouseListener urllistlistener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				String selectedItem = (String) urls.getSelectedValue();
				//ok double click lets load the image.
				desiredimage = selectedItem;
				//Ok lets run a get on this to download the file...
				cc.downloadFile(desiredimage);
				DrawingFrame df;
				try {
					df = new DrawingFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	};
	
}
	
	