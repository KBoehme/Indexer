package searchgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import server.Server;
import shared.communication.GetProjects_param;
import shared.communication.GetProjects_result;
import shared.communication.ValidateUser_param;
import shared.communication.ValidateUser_result;
import client.ClientCommunicator;

public class LoginForm extends JFrame {

	public static void main(String[] args) {
		LoginForm mainlogin = new LoginForm();
	}

	Server s = null;

	JButton blogin = new JButton("Login");
	JPanel panel = new JPanel();

	JTextField host = new JTextField(15);
	JTextField port = new JTextField(10);
	JTextField user = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);

	JTextPane hoststring = new JTextPane();
	JTextPane portstring = new JTextPane();
	JTextPane userstring = new JTextPane();
	JTextPane passwordstring = new JTextPane();

	LoginForm() {
		super("Login");
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(350, 300);
		setLocation(500, 280);
		panel.setLayout(null);

		// testpanes
		hoststring.setText("Host");
		hoststring.setBounds(30, 20, 40, 20);
		hoststring.setEditable(false);

		portstring.setText("Port");
		portstring.setBounds(200, 20, 40, 20);
		portstring.setEditable(false);

		userstring.setText("Username");
		userstring.setBounds(30, 130, 70, 20);
		userstring.setEditable(false);

		passwordstring.setText("Password");
		passwordstring.setBounds(30, 165, 70, 20);
		passwordstring.setEditable(false);

		// x,y,width,height
		// textframes
		host.setBounds(80, 20, 100, 20);
		host.setText("localhost");

		port.setBounds(260, 20, 50, 20);
		port.setText("39640");

		user.setBounds(120, 130, 150, 20);
		user.setText("test1");
		pass.setBounds(120, 165, 150, 20);
		pass.setText("test1");

		blogin.setBounds(150, 210, 80, 20);
		blogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// OK this is where we start writing our action code..
				String host_str = host.getText();
				Integer port_int = Integer.valueOf(port.getText());
				String username_str = user.getText();
				char[] pass_char = pass.getPassword();
				// ok got the info.. now lets check it.
				try {
					if (s == null || s.getPort() != port_int) {
						s = new Server(port_int);
						s.run();
					}

					ClientCommunicator cc = new ClientCommunicator(host_str, port_int);
					ValidateUser_param param = new ValidateUser_param();

					GetProjects_param p_param = new GetProjects_param();

					ValidateUser_result result = new ValidateUser_result();

					GetProjects_result project_results = new GetProjects_result();

					param.setUsername(username_str);
					p_param.setUsername(username_str);
					String password = String.valueOf(pass_char);
					param.setPassword(password);
					p_param.setPassword(password);

					result = cc.validateUser(param);
					project_results = cc.getProjects(p_param);
					if (result.getSuccess() == 1) { // we got a good user..
						JOptionPane.showMessageDialog(null, "Successfully logged on!");
						// we need to open a new box to show the projects and stuff..
						dispose();
						ViewProjectsForm vpf = new ViewProjectsForm(project_results, cc);
						vpf.setProject_results(project_results);
						vpf.setVisible(true);

					} else if (result.getSuccess() == 2) { // false bad user..
						JOptionPane.showMessageDialog(null, "Wrong Password / Username");

					} else { // failed
						JOptionPane.showMessageDialog(null, "error: FAILED");
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error, probably with the host or port");
				}
			}
		});

		// add all the strings.
		panel.add(hoststring);
		panel.add(portstring);
		panel.add(userstring);
		panel.add(passwordstring);

		// add all the textfields
		panel.add(host);
		panel.add(port);
		panel.add(user);
		panel.add(pass);

		// add the login button..
		panel.add(blogin);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		// actionlogin();
	}
}

// ////////////////////////////////////////////////////////////////////////////////////////////////////
// ------------------ADD all the listeners associated with this form below....
// ----------------------//
// ////////////////////////////////////////////////////////////////////////////////////////////////////
