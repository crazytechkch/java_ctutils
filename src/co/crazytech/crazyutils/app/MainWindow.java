package co.crazytech.crazyutils.app;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import co.crazytech.crazyutils.aviation.airports.AirportsGUI;
import co.crazytech.crazyutils.bincon.binconPanel;
import co.crazytech.crazyutils.geoip.GeoIp;
import co.crazytech.crazyutils.geoip.GeoIpGUI;

import java.awt.BorderLayout;import java.awt.Component;

public class MainWindow {

	private JFrame frmCrazyutils;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmCrazyutils.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCrazyutils = new JFrame();
		frmCrazyutils.setTitle("CrazyUtils");
		frmCrazyutils.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/co/crazytech/crazyutils/app/boxhead_normal.png")));
		frmCrazyutils.setBounds(100, 100, 450, 300);
		frmCrazyutils.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("bincon", new binconPanel());
		tabbedPane.addTab("Airports", new AirportsGUI().getContentPane());
		tabbedPane.addTab("IP Locator", new GeoIpGUI().getContentPane());
;		frmCrazyutils.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		//tabbedPane.addTab("bincon", );
	}

}
