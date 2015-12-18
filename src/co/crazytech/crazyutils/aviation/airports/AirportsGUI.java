package co.crazytech.crazyutils.aviation.airports;



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

public class AirportsGUI extends JFrame {

	private JPanel _contentPane;
	private MySqlUtil mySql;
	private final String TABLE = "airports";
	private Airport airport;
	private String zoom = "13";
	private String mapType = "satellite";
	private final String AIRPORTDIR = "C:/crazytech/airports/";
	private final String GMAPS= "http://maps.googleapis.com/maps/api/staticmap";
	private Integer mapWidth = 640, mapHeight = 640;
	private JLabel lblVarName,lblVarCity,lblVarCountry,lblVarIata,lblVarIcao,lblVarLat,lblVarLong,lblVarAlt,lblVarTz,lblVarDst,lblMap;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AirportsGUI frame = new AirportsGUI();
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
	public AirportsGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(new BorderLayout(0, 0));
		
		airport = new Airport("city","kuching");
		
		Configuration.setValue(AVKey.INITIAL_LATITUDE, airport.getLat());
		Configuration.setValue(AVKey.INITIAL_LONGITUDE, airport.getLng());
		Configuration.setValue(AVKey.INITIAL_ALTITUDE, 4000);
		final WorldWindowGLCanvas wwCanvas = new WorldWindowGLCanvas();
		wwCanvas.setModel(new BasicModel());
		lblMap = new JLabel("");
//		lblMap.setHorizontalAlignment(SwingConstants.CENTER);
//		lblMap.setVerticalAlignment(SwingConstants.CENTER);
		_contentPane.add(wwCanvas, BorderLayout.CENTER);
		
		JPanel panelEast = new JPanel();
		_contentPane.add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][][][][][][grow]"));
		
		mySql = new MySqlUtil("crazytech.co:3306", "crazytech", "root", "");
		
		JComboBox airportSearch = new JComboBox();
		airportSearch.setPrototypeDisplayValue("xxxxxxxxxxxxxxx");
		airportSearch.setEditable(true);
		final DefaultComboBoxModel<Airport> model = (DefaultComboBoxModel<Airport>) airportSearch.getModel();
		final JTextField searchText = (JTextField)airportSearch.getEditor().getEditorComponent();
		searchText.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				searchText.selectAll();
			}
		});
		searchText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					String text = searchText.getText();
					try {
						String json = Airport.getJsonString("where city like '"+text+"%' "
								+ "or name like '"+text+"%' "
								+ "or country like '"+text+"%' "
								+ "or iata like '"+text+"%' "
								+ "or icao like '"+text+"%'");
						JsonObject jsonObj = new Gson().fromJson(json, JsonObject.class);
						if (jsonObj.get("success").getAsInt()==1) {
							JsonArray jsonArr = jsonObj.get("results").getAsJsonArray();
							model.removeAllElements();
							for (JsonElement jsonElement : jsonArr) {
								model.addElement(new Airport(jsonElement.getAsJsonObject()));
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		airportSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				airport = model.getElementAt(model.getIndexOf(model.getSelectedItem()));
				if (airport!=null) {
					setFields(airport);
					((OrbitView)wwCanvas.getView()).goTo(Position.fromDegrees(airport.getLat(), airport.getLng()), 4000);
				}
			}
		});
		panelEast.add(airportSearch, "cell 0 0 2 1,growx");
		
		JLabel lblName = new JLabel("Name");
		panelEast.add(lblName, "cell 0 1");
		lblVarName = new JLabel("Airport's Name");
		panelEast.add(lblVarName, "cell 1 1");
		
		JLabel lblCity = new JLabel("City");
		panelEast.add(lblCity, "cell 0 2");
		lblVarCity = new JLabel("Airport's City");
		panelEast.add(lblVarCity, "cell 1 2");
		
		JLabel lblCountry = new JLabel("Country");
		panelEast.add(lblCountry, "cell 0 3");
		lblVarCountry = new JLabel("Airport's Country");
		panelEast.add(lblVarCountry, "cell 1 3");
		
		JLabel lblIata = new JLabel("IATA/FAA");
		panelEast.add(lblIata, "cell 0 4");
		lblVarIata = new JLabel("IATA code");
		panelEast.add(lblVarIata, "cell 1 4");
		
		JLabel lblIcao = new JLabel("ICAO");
		panelEast.add(lblIcao, "cell 0 5");
		lblVarIcao = new JLabel("ICAO code");
		panelEast.add(lblVarIcao, "cell 1 5");
		
		JLabel lblLat = new JLabel("Latitude");
		panelEast.add(lblLat, "cell 0 6");
		lblVarLat = new JLabel("Airport's Latitude");
		panelEast.add(lblVarLat, "cell 1 6");
		
		JLabel lblLong = new JLabel("Longitude");
		panelEast.add(lblLong, "cell 0 7");
		lblVarLong = new JLabel("Airport's Longitude");
		panelEast.add(lblVarLong, "cell 1 7");
		
		JLabel lblAlt = new JLabel("Altitude");
		panelEast.add(lblAlt, "cell 0 8");
		lblVarAlt = new JLabel("Airport's Altitude");
		panelEast.add(lblVarAlt, "cell 1 8");
		
		JLabel lblTz = new JLabel("Timezone");
		panelEast.add(lblTz, "cell 0 9");
		lblVarTz = new JLabel("Airport's Timezone");
		panelEast.add(lblVarTz, "cell 1 9");
		
		JLabel lblDst = new JLabel("DST");
		panelEast.add(lblDst, "cell 0 10");
		lblVarDst = new JLabel("Airport's DST");
		panelEast.add(lblVarDst, "cell 1 10");
		
		JPanel panel = new JPanel();
		panelEast.add(panel, "cell 0 11 2 1,grow");
		
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
		
		setFields(airport);
		
	}
	
	private void setFields(Airport airport){
		String lat = airport.getLat()+"";
		String lng = airport.getLng()+"";
		lblVarName.setText(airport.getName());
		lblVarCity.setText(airport.getCity());
		lblVarCountry.setText(airport.getCountry());
		lblVarIata.setText(airport.getIata());
		lblVarIcao.setText(airport.getIcao());
		lblVarLat.setText(lat);
		lblVarLong.setText(lng);
		lblVarAlt.setText(airport.getAlt()+"");
		lblVarTz.setText(airport.getTimezone()+"");
		lblVarDst.setText(airport.getDst());
	}
}
