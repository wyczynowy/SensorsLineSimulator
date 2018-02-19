package secondTab;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import com.fazecast.jSerialComm.SerialPort;

import firstTab.FirstTabCenterContainer;
import net.miginfocom.swing.MigLayout;

public class SecondTabCenterContainer extends Container {
	private static final long serialVersionUID = 1199351573820863996L;
	
	private ArrayList<String> baudRateListNames = new ArrayList<String>(Arrays.asList("115 200", "250 000", "57 600"));
	private int[] baudRateListValues = {115200, 250000, 57600};
	private SerialPort[] ports;
	public static SerialPort usbPort;
	private JComboBox<String> portList = new JComboBox<String>();
	private JComboBox<String> baudRateList = new JComboBox<String>();
	private JToggleButton connectButton1 = new JToggleButton("Połącz");
	private JLabel connection1InfoLabel = new JLabel("Niepodłączony");
	private JButton loadDefaultConfigButton = new JButton("Wczytaj domyślną konfigurację");
	private JTextField loadedPathField = new JTextField(270);
	private JFileChooser c = new JFileChooser();
	
	private ExecutorService executor1 = Executors.newSingleThreadExecutor();
	private ExecutorService executor2 = Executors.newSingleThreadExecutor();
	ReceiveData receiveData = new ReceiveData();								// Zadanie odbioru danych z portu COM
	SendData sendData = new SendData();
	
	private ActionListener connectButton1Listener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			usbPort = ports[portList.getSelectedIndex()];
			if(connectButton1.isSelected()) {						// Jezeli przycisk zostal wcisniety
				if(usbPort.openPort()) {							// Jeżeli uda się podlaczyc
					usbPort.setComPortParameters(baudRateListValues[baudRateList.getSelectedIndex()], 8, 1, 0);
					connection1InfoLabel.setText("Podłączony");
					executor1.execute(receiveData);					// Rozpoczecie zadania odbioru danych z portu COM
					executor2.execute(sendData);					// Rozpoczecie zadania przetwarzania i wysylania danych na port COM
				} else {											// Jezeli nie uda sie podlaczyc
					connection1InfoLabel.setText("Niepodłączony");
					connectButton1.setSelected(false); 				// Wyciskamy przycisk
				}
			} else {												// Jezeli przycisk zostal wycisniety
//				executor1.shutdown(); 								// Zakonczenie zadania
//				executor2.shutdown(); 								// Zakonczenie zadania

				usbPort.closePort();								// Rozlaczamy sie
				connection1InfoLabel.setText("Rozłączony");
			}
			
		}
	};
	
	private ActionListener loadDefaultConfigButtonListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
		      // Demonstrate "Open" dialog:
		      int rVal = c.showOpenDialog(SecondTabCenterContainer.this);
		      if(rVal == JFileChooser.APPROVE_OPTION) {
//		    	  loadedPathField.setText(c.getSelectedFile().getName());
		        loadedPathField.setText(c.getCurrentDirectory().toString());
						try {
							
							BufferedReader br = new BufferedReader(new FileReader(c.getSelectedFile()));	// Tworzymy strumien, do konstruktora podajemy obiekt FileRider a w jego konstruktorze obiekt File bedacy wybranym plikiem, ktory chcemy odczytac
							String defaultSensorConfig = br.readLine();
							String defaultDisplayPanelConfig = br.readLine();
							
							br.close();
							int errorCode = 0;
							if((defaultSensorConfig.indexOf("Default sensor config:") < 0)) errorCode = 1;					// Brak wymaganego ciagu znakow
							if((defaultDisplayPanelConfig.indexOf("Default display panel config:") < 0)) errorCode = 1;		// Brak wymaganego ciagu znakow
							if(defaultSensorConfig.indexOf("\"") < 0) errorCode = 2;										// Brak pierwszego znaku '"'
							if(defaultDisplayPanelConfig.indexOf("\"") < 0) errorCode = 2;									// Brak pierwszego znaku '"'
							
							if(errorCode == 0) {
								if(defaultSensorConfig.indexOf("\"", defaultSensorConfig.indexOf("\"") + 1) < 0) errorCode = 3;					// Brak drugiego znaku '"'
								if(defaultDisplayPanelConfig.indexOf("\"", defaultDisplayPanelConfig.indexOf("\"") + 1) < 0) errorCode = 3;		// Brak drugiego znaku '"'
							}
							
							if(errorCode == 0) { 	// Wchodzimy, jak plik przeszedl poprawna weryfikacje
								defaultSensorConfig = defaultSensorConfig.substring(defaultSensorConfig.indexOf("\"") + 1);
								defaultSensorConfig = defaultSensorConfig.substring(0, defaultSensorConfig.indexOf("\""));
								List<String> defaultSensorConfiglist = Arrays.asList(defaultSensorConfig.split(" "));
								if(defaultSensorConfiglist.size() < 10 || defaultSensorConfiglist.size() > 10) errorCode = 4;					// Niepoprawna ilosc bajtow z konfiguracja czujnika
								
								defaultDisplayPanelConfig = defaultDisplayPanelConfig.substring(defaultDisplayPanelConfig.indexOf("\"") + 1);
								defaultDisplayPanelConfig = defaultDisplayPanelConfig.substring(0, defaultDisplayPanelConfig.indexOf("\""));
								List<String> defaultDisplayPanelConfigList = Arrays.asList(defaultDisplayPanelConfig.split(" "));
								if(defaultDisplayPanelConfigList.size() < 85 || defaultDisplayPanelConfigList.size() > 85) errorCode = 4;	// Niepoprawna ilosc bajtow z konfiguracja tablicy
								
								if(errorCode == 0) {														// Wchodzimy jezeli jestesmy w stanie wyciagnac z pliku poprawna konfiguracje dla czujnika i tablicy
									ArrayList<Integer> readedSensorConfig = new ArrayList<Integer>();		// Lista z domyslna konfiguracja czujnika odczytana z pliku
									ArrayList<Integer> readedDisplayPanelConfig = new ArrayList<Integer>();	// Lista z komyslna konfiguracja tablicy odczytana z pliku
									
									for(String oneByte : defaultSensorConfiglist)
										readedSensorConfig.add(Integer.parseInt(oneByte, 16));
									
									for(String oneByte : defaultDisplayPanelConfigList)
										readedDisplayPanelConfig.add(Integer.parseInt(oneByte, 16));
									
									for(int i = 1; i <= 80; i++)
										FirstTabCenterContainer.setSensorConfig(readedSensorConfig, i);				// Wpisanie konfiguracji czujnika do pol JTextField pierwszej zakladki
									
									for(int i = 1; i <= 16; i++)
										FirstTabCenterContainer.setDisplayPanelConfig(readedDisplayPanelConfig, i); // Wpisanie konfiguracji tablicy do pol JTextField pierwszej zakladki
									
								} else
									JOptionPane.showMessageDialog(null,  "Plik niepoprawny\nNie wgrano konfiguracji\nNiepoprawa ilość bajtów z konfiguracją", "Błąd", JOptionPane.ERROR_MESSAGE);
							} else {	// W przeciwnym wypadku blad i wyswietlenie komunikatu
								JOptionPane.showMessageDialog(null,  "Plik niepoprawny\nNie wgrano konfiguracji", "Błąd", JOptionPane.ERROR_MESSAGE);

							}
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		      }
		      if(rVal == JFileChooser.CANCEL_OPTION) {
//		        loadedPathField.setText("You pressed cancel");
//		        loadedPathField.setText("");
		      }
			
		}
	};

	public SecondTabCenterContainer() {
		
		connectButton1.addActionListener(connectButton1Listener);
		loadDefaultConfigButton.addActionListener(loadDefaultConfigButtonListener);
		loadDefaultConfigButton.setToolTipText("Poprawny plik z konfiguracją dla czujników i tablic powinien zawierać ciąg znaków: \"Default sensor config:\" + kolejno bajty z konfiguracją w formie \"AA BB CC...\" oraz ciąg znaków: \"Default display panel config:\" + kolejno bajty z konfiguracją w formie \"AA BB CC...\"");
		ports = SerialPort.getCommPorts();
		for(SerialPort port : ports)
			portList.addItem(port.getSystemPortName());
		for(String baudRateVal : baudRateListNames)
			baudRateList.addItem(baudRateVal);
		
		setLayout(new MigLayout("", "[370, center]", ""));
		JPanel comSettings = new JPanel(new MigLayout("", "[100][100][70][100]", ""));
		comSettings.setBorder(new TitledBorder("Ustawienia komunikacji"));
		comSettings.add(new JLabel("Port"));
		comSettings.add(portList);
		comSettings.add(connectButton1, "span 1 2");
		comSettings.add(connection1InfoLabel, "span 1 2, wrap");
		comSettings.add(new JLabel("Baud rate"));
		comSettings.add(baudRateList);
		add(comSettings, "wrap");
		JPanel defaultSettingsConfiguration = new JPanel(new MigLayout("","[370]",""));
		defaultSettingsConfiguration.setBorder(new TitledBorder("Ustawienia konfiguracji urządzeń"));
		defaultSettingsConfiguration.add(loadedPathField, "center alignment, wrap");
		defaultSettingsConfiguration.add(loadDefaultConfigButton);
		
		add(defaultSettingsConfiguration);
		
	}
}
