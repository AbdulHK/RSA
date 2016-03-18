
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame {

	private final JTextField nTF = new JTextField(40); // set TextFielg for 40
														// symbols
	private final JTextField publicKeyTF = new JTextField(40);
	private final JTextField privateKeyTF = new JTextField(40);
	private final JTextArea plainTextTA = new JTextArea();// create text area
	private final JTextArea cipherTextTA = new JTextArea();
	private final String[] types = { "message", "file" };
	private final JComboBox<String> type = new JComboBox<String>(types);

	public GUI() {
		setSize(new Dimension(730, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit the program when
														// window close
		setTitle("RSA");// set window title
		addComponentsToPane(getContentPane()); // add components
		setResizable(false); // window is not resizable
		setVisible(true); // show window
	}

	private void addComponentsToPane(Container pane) {
		pane.setLayout(new GridBagLayout()); // set Layout to pane
		GridBagConstraints c = new GridBagConstraints(); // constraints for
															// placing items

		JLabel nLabel = new JLabel("n:");// create label
		c.insets = new Insets(5, 5, 5, 5); // set paddings
		c.gridx = 0;// set x coordinate (horizontal)
		c.gridy = 0;// set y coordinate (vertical)
		pane.add(nLabel, c);// add to pane

		c.gridx = 1;
		c.gridy = 0;
		pane.add(nTF, c);

		JLabel publicKeyLabel = new JLabel("Public key:");
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(publicKeyLabel, c);

		c.gridx = 1;
		c.gridy = 1;
		pane.add(publicKeyTF, c);

		JLabel privateKeyLabel = new JLabel("Private key:");
		c.gridx = 0;
		c.gridy = 2;
		pane.add(privateKeyLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		pane.add(privateKeyTF, c);

		JButton generateKeys = new JButton("Generate Keys"); // add "Generate
																// Keys" button
		generateKeys.addActionListener(new ActionListener() {
			// when click on button
			@Override
			public void actionPerformed(ActionEvent e) {
				BigInteger[] keys = RSA.generateKeys(); // generate public key,
														// private key and n;
				publicKeyTF.setText(keys[0].toString(16));// show public key as
															// hex String
				privateKeyTF.setText(keys[1].toString(16));// show private key
															// as hex String
				nTF.setText(keys[2].toString(16));// show n as hex String
			}

		});
		c.gridx = 0;
		c.gridy = 3;
		pane.add(generateKeys, c);

		c.gridx = 1;
		c.gridy = 3;
		pane.add(type, c);

		JPanel textPanel = new JPanel(); // create panel to add there text areas
											// with seted size

		JScrollPane plainTextSP = new JScrollPane(plainTextTA);// add text area to scroll pane
																// for enabling scrolling
		plainTextSP.setPreferredSize(new Dimension(300, 100));// set size of scroll pane
		plainTextTA.setLineWrap(true);// set line wrap in text area
		textPanel.add(plainTextSP);// add text area to panel

		JScrollPane cipherTextSP = new JScrollPane(cipherTextTA);// add text area to scroll pane
																// for enabling scrolling
		cipherTextSP.setPreferredSize(new Dimension(300, 100));// set size of
																// scroll pane
		cipherTextTA.setLineWrap(true);
		textPanel.add(cipherTextSP);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2; // will take 2 columns;
		pane.add(textPanel, c);// add panel to pane

		JPanel buttonPanel = new JPanel();// create panel to add there buttons
											// with seted size

		JButton encrypt = new JButton("Encrypt");// create button
		encrypt.setPreferredSize(new Dimension(300, 30));// set button size
		encrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (type.getSelectedItem().toString()) {
				case "message":
					/*
					 * If selected type of data is "message" get text from
					 * plainTextTA text area, public key from publicKeyTF text
					 * field, n from nTF text field, encrypt text and show
					 * encrypted text in cipherTextTA text area as hex String
					 */
					cipherTextTA.setText(RSA.encrypt(plainTextTA.getText(), publicKeyTF.getText(), nTF.getText()));
					break;
				case "file":
					/*
					 * If selected type of data is "file" get file name from
					 * plainTextTA text area, public key from publicKeyTF text
					 * field, n from nTF text field, encrypt file and show
					 * "Completed" in cipherTextTA text area if there is no
					 * exception or error message, if there is some exceptions
					 */
					try {
						RSA.encryptFile(plainTextTA.getText(), publicKeyTF.getText(), nTF.getText());
						cipherTextTA.setText("Completed");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						cipherTextTA.setText("ERROR! There is not such file!");
					}

				}

			}
		});
		buttonPanel.add(encrypt);// add button to panel

		JButton decrypt = new JButton("Decrypt");
		decrypt.setPreferredSize(new Dimension(300, 30));
		decrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				switch (type.getSelectedItem().toString()) {
				case "message":
					/*
					 * If selected type of data is "message" get text from
					 * cipherTextTA text area, private key from privateKeyTF
					 * text field, n from nTF text field, decrypt it and show
					 * plain text in plainTextTA text area
					 */
					plainTextTA.setText(RSA.decrypt(cipherTextTA.getText(), privateKeyTF.getText(), nTF.getText()));
					break;
				case "file":
					/*
					 * If selected type of data is "file" get file name from
					 * cipherTextTA text area, private key from privateKeyTF
					 * text field, n from nTF text field, decrypt file and show
					 * "Completed" in plainrTextTA text area if there is no
					 * exception or error message, if there is some exceptions
					 */
					try {
						RSA.decryptFile(cipherTextTA.getText(), privateKeyTF.getText(), nTF.getText());
						plainTextTA.setText("Completed");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						plainTextTA.setText("ERROR! There is not such file!");
					}

				}
			}
		});
		buttonPanel.add(decrypt);// add button to panel

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		pane.add(buttonPanel, c);// add button panel to pane
	}

}
