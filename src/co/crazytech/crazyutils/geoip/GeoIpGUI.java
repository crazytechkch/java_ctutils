package co.crazytech.crazyutils.geoip;



import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.view.orbit.OrbitView;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
//import net.sf.jasperreports.engine.DefaultJasperReportsContext;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import com.crazytech.db.MySqlUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import co.crazytech.commons.json.JsonParser;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.Choice;

public class GeoIpGUI extends JFrame {

	private JPanel _contentPane;
	private GeoIp geoip;
	private JLabel lblIp,lblCountryCode,lblCountry,lblRegionCode,lblRegionName,lblCity,lblZip,
		lblTimezone,lblLat,lblLong,lblMetroCode;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeoIpGUI frame = new GeoIpGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GeoIpGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(new BorderLayout(0, 0));
		
		geoip = new GeoIp("");
		
		Configuration.setValue(AVKey.INITIAL_LATITUDE, geoip.getLatitude());
		Configuration.setValue(AVKey.INITIAL_LONGITUDE, geoip.getLongitude());
		Configuration.setValue(AVKey.INITIAL_ALTITUDE, 4000);
		final WorldWindowGLCanvas wwCanvas = new WorldWindowGLCanvas();
		wwCanvas.setModel(new BasicModel());
		lblMetroCode = new JLabel("");
//		lblMap.setHorizontalAlignment(SwingConstants.CENTER);
//		lblMap.setVerticalAlignment(SwingConstants.CENTER);
		_contentPane.add(wwCanvas, BorderLayout.CENTER);
		
		JPanel panelEast = new JPanel();
		_contentPane.add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][][][][][][grow]"));
		
		JTextField hostsearch = new JTextField();
		hostsearch.setEditable(true);
		hostsearch.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				hostsearch.selectAll();
			}
		});
		hostsearch.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					geoip = new GeoIp(hostsearch.getText());
					setFields(geoip);
					((OrbitView)wwCanvas.getView()).goTo(Position.fromDegrees(geoip.getLatitude(), geoip.getLongitude()), 4000);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		panelEast.add(hostsearch, "cell 0 0 2 1,growx");
		
		JLabel lblsIp = new JLabel("IP");
		panelEast.add(lblsIp, "cell 0 1");
		lblIp = new JLabel("IP");
		panelEast.add(lblIp, "cell 1 1");
		
		JLabel lblsCountryCode = new JLabel("Country Code");
		panelEast.add(lblsCountryCode, "cell 0 2");
		lblCountryCode = new JLabel("Country Code");
		panelEast.add(lblCountryCode, "cell 1 2");
		
		JLabel lblsCountry = new JLabel("Country");
		panelEast.add(lblsCountry, "cell 0 3");
		lblCountry = new JLabel("Country");
		panelEast.add(lblCountry, "cell 1 3");
		
		JLabel lblsRegionCode = new JLabel("Region Code");
		panelEast.add(lblsRegionCode, "cell 0 4");
		lblRegionCode = new JLabel("Region Code");
		panelEast.add(lblRegionCode, "cell 1 4");
		
		JLabel lblsRegionName = new JLabel("Region Name");
		panelEast.add(lblsRegionName, "cell 0 5");
		lblRegionName = new JLabel("Region Name");
		panelEast.add(lblRegionName, "cell 1 5");
		
		JLabel lblsCity = new JLabel("City");
		panelEast.add(lblsCity, "cell 0 6");
		lblCity = new JLabel("City");
		panelEast.add(lblCity, "cell 1 6");
		
		JLabel lblsZip = new JLabel("zipcode");
		panelEast.add(lblsZip, "cell 0 7");
		lblZip = new JLabel("zipcode");
		panelEast.add(lblZip, "cell 1 7");
		
		JLabel lblsTimezone = new JLabel("Timezone");
		panelEast.add(lblsTimezone, "cell 0 8");
		lblTimezone = new JLabel("Timezone");
		panelEast.add(lblTimezone, "cell 1 8");
		
		JLabel lblsLat = new JLabel("Latitude");
		panelEast.add(lblsLat, "cell 0 9");
		lblLat = new JLabel("Latitude");
		panelEast.add(lblLat, "cell 1 9");
		
		JLabel lblsLong = new JLabel("Longitude");
		panelEast.add(lblsLong, "cell 0 10");
		lblLong = new JLabel("Longitude");
		panelEast.add(lblLong, "cell 1 10");
		
		JLabel lblsMetroCode = new JLabel("Metro Code");
		panelEast.add(lblsMetroCode, "cell 0 11");
		lblMetroCode = new JLabel("Metro Code");
		panelEast.add(lblMetroCode, "cell 1 11");
		
		JPanel panel = new JPanel();
		panelEast.add(panel, "cell 0 12 2 1,grow");
		
		JButton btnZoomIn = new JButton("+");
		btnZoomIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		panel.add(btnZoomIn);
		
		JButton btnZoomOut = new JButton("-");
		btnZoomOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		panel.add(btnZoomOut);
		
		Choice choice = new Choice();
		choice.addItem("satellite");
		choice.addItem("hybrid");
		choice.addItem("roadmap");
		choice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				//setMapType(e.getItem().toString());
			}
		});
		panel.add(choice);
		setFields(geoip);
		//((OrbitView)wwCanvas.getView()).goTo(Position.fromDegrees(geoip.getLatitude(), geoip.getLongitude()), 4000);
	}
	
	private void setFields(GeoIp geoip){
		String lat = geoip.getLatitude()+"";
		String lng = geoip.getLongitude()+"";
		lblIp.setText(geoip.getIp());
		lblCountryCode.setText(geoip.getCountryCode());
		lblCountry.setText(geoip.getCountry());
		lblRegionCode.setText(geoip.getRegionCode());
		lblRegionName.setText(geoip.getRegion());
		lblTimezone.setText(geoip.getTimezone()+"");
		lblCity.setText(geoip.getCity()+"");
		lblMetroCode.setText(geoip.getMetroCode());
		lblLat.setText(lat);
		lblLong.setText(lng);
	}
}
