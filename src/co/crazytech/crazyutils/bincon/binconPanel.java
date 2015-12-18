package co.crazytech.crazyutils.bincon;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;

public class binconPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public binconPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(.5d);
		add(splitPane, BorderLayout.CENTER);
		
		JTextPane textPaneBinary = new JTextPane();
		splitPane.setLeftComponent(textPaneBinary);
		
		JTextPane textPaneText = new JTextPane();
		splitPane.setRightComponent(textPaneText);
		
		Charset utf8 = Charset.forName("UTF-8");
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnToText = new JButton("TO TEXT");
		panel.add(btnToText);
		btnToText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = textPaneBinary.getText().getBytes(utf8);
				textPaneText.setText(new String(bytes,utf8));
			}
		});
		
		JButton btnToBin = new JButton("TO BINARY");
		panel.add(btnToBin);
		btnToBin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = textPaneText.getText().getBytes(utf8);
				String byteStr = "";
				for (byte b : bytes) {
					byteStr+=Integer.toBinaryString(b);
				}
				textPaneBinary.setText(byteStr);
			}
		});

	}

}
