package firstTab;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import secondTab.Crc16Calc;

public class FirstTabCenterContainer extends Container {
	private static final long serialVersionUID = 1L;
	
	private static Crc16Calc crc = new Crc16Calc();
	
	private String[] extortionTypeNames = {"Brak", "Force", "Reserve", "Longstop"};
	private String[] sensorStateNames = {"Wolny", "Zajęty"};
	private String[] caseStateNames = {"Zamknięta", "Otwarta"};
	private String[] ecoStateNames = {"Świeci", "Mruga", "Defilada", "Wyłączony"};
	private String[] ecoModeNames = {"Nieaktywny", "Aktywny"};
	private String[] actualDistanceNames = {"100 cm", "150 cm", "200 cm", "250 cm", "300 cm",};
	private static int[] deviceStartRunNames = {0,1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,80,90,100};
	private String[] damageStateNames = {"Sprawny", "Uszkodzony"};
	private int[] defaultSensorConfig = {0xFF, 0x00, 0x00, 0x64, 0x00, 0xC8, 0x00, 0x00, 0x00, 0x03, 0x0C, 0xED};
	private int []	defaultDisplayPanelConfig = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
												 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x0F, 0x1A};
	private static String[] logoForNormalPlace= {"P", "P-1", "P-2", "P-3", "P-4", "P-5", "P-6", "P-7", "P-8", "P-9", "P 0",
			"P+1", "P+2", "P+3", "P+4", "P+5", "P+6", "P+7", "P+8", "P+9", "P 0",
			"L-1", "L-2", "L-3", "L-4", "L-5", "L-6", "L-7", "L-8", "L-9", "L 0",
			"L+1", "L+2", "L+3", "L+4", "L+5", "L+6", "L+7", "L+8", "L+9", "L 0",
			"D", "F", "E"};
	
	private JCheckBox setAllSensorsCheckBox = new JCheckBox();												// Ptaszek wyboru obecnosci wszystkich urzadzen
	private static ArrayList<JToggleButton> sensorPresent = new ArrayList<JToggleButton>();					// Przyciski obecnosci czujnika
	private static ArrayList<JComboBox<String>> extortionType = new ArrayList<JComboBox<String>>();			// Rodzaj wymuszenia
	private static ArrayList<JComboBox<String>> occupyState = new ArrayList<JComboBox<String>>();			// Zajetosc czujnika
	private static ArrayList<JComboBox<String>> caseState = new ArrayList<JComboBox<String>>();				// Stan obudowy
	private static ArrayList<JComboBox<String>> lightMode = new ArrayList<JComboBox<String>>();				// Tryb swiecenia sygnalizatora
	private static ArrayList<JComboBox<String>> ecoMode = new ArrayList<JComboBox<String>>();				// Tryb ECO
	private static ArrayList<JComboBox<String>> actualDistance = new ArrayList<JComboBox<String>>();		// Aktualna wysokosc
	private static ArrayList<JComboBox<Integer>> deviceStartRun = new ArrayList<JComboBox<Integer>>();		// Ilosc uruchomien urzadzenia
	private static ArrayList<JComboBox<String>> damageState = new ArrayList<JComboBox<String>>();			// Uszkodzony piezo
	private static JTextField[][] sensorConfig = new JTextField[80][12];									// Konfiguracja czujnikow
	private static int[][] sensorState = new int[80][6];													// Stany czujnikow
	private static boolean[] sensorPresentTab = new boolean[80];											// Obecnosc czujnika (obecny -> emulator odpowiada, nieobecny -> nieodpowiada)
	
	private static ArrayList<JToggleButton> displayPanelPresent = new ArrayList<JToggleButton>();			// Przyciski obecnosci tablicy
	private static ArrayList<JComboBox<String>> displayPanelEcoMode = new ArrayList<JComboBox<String>>();	// Tryb ECO tablicy
	private static ArrayList<JTextArea> displayPanelStatement1 = new ArrayList<JTextArea>();				// Pole w ktorym beda wyswietlane komunikaty tablicy - strefa 1
	private static ArrayList<JTextArea> displayPanelStatement2 = new ArrayList<JTextArea>();				// Pole w ktorym beda wyswietlane komunikaty tablicy - strefa 2
	private static JTextField[][] displayPanelConfig = new JTextField[80][89];								// Konfiguracja tablic
	private static boolean[] displayPanelPresentTab = new boolean[16];										// Obecnosc talicy (obecny -> emulator odpowiada, nieobecny -> nieodpowiada)
	private static ArrayList<JComboBox<Integer>> displayPanelStartRun = new ArrayList<JComboBox<Integer>>();	// Ilosc uruchomien tablic
	private static int[][] displayPanelState = new int[16][3];												// Stany tablic
	
	//*********************************************************************************//
	//
	//   		Ustawia nowa konfiguracje dla czujnika o adresie sensorAddress 
	//
	//*********************************************************************************//
	
	public synchronized static void setSensorConfig(ArrayList<Integer> newSensorConfig, int sensorAddress) {
		sensorConfig[sensorAddress-1][0].setText(String.format("%02X", newSensorConfig.get(0)));
		sensorConfig[sensorAddress-1][1].setText(String.format("%02X", newSensorConfig.get(1)));
		sensorConfig[sensorAddress-1][2].setText(String.format("%02X", newSensorConfig.get(2)));
		sensorConfig[sensorAddress-1][3].setText(String.format("%02X", newSensorConfig.get(3)));
		sensorConfig[sensorAddress-1][4].setText(String.format("%02X", newSensorConfig.get(4)));
		sensorConfig[sensorAddress-1][5].setText(String.format("%02X", newSensorConfig.get(5)));
		sensorConfig[sensorAddress-1][6].setText(String.format("%02X", newSensorConfig.get(6)));
		sensorConfig[sensorAddress-1][7].setText(String.format("%02X", newSensorConfig.get(7)));
		sensorConfig[sensorAddress-1][8].setText(String.format("%02X", newSensorConfig.get(8)));
		sensorConfig[sensorAddress-1][9].setText(String.format("%02X", newSensorConfig.get(9)));

		int crcCounted = crc.MakeCrc(newSensorConfig, newSensorConfig.size());
		sensorConfig[sensorAddress-1][10].setText(String.format("%02X", ((crcCounted >> 8) & 0xFF)));
		sensorConfig[sensorAddress-1][11].setText(String.format("%02X", (crcCounted & 0xFF)));
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   		Ustawia nowa konfiguracje dla tablicy o adresie sensorAddress 
	//
	//*********************************************************************************//
	
	public synchronized static void setDisplayPanelConfig(ArrayList<Integer> newDisplayPanelConfig, int displayPanelAddress) {
		for(int i = 0; i < 87; i++)
		displayPanelConfig[displayPanelAddress-1][i].setText(String.format("%02X", newDisplayPanelConfig.get(i)));


		int crcCounted = crc.MakeCrc(newDisplayPanelConfig, newDisplayPanelConfig.size());
		displayPanelConfig[displayPanelAddress-1][87].setText(String.format("%02X", ((crcCounted >> 8) & 0xFF)));
		displayPanelConfig[displayPanelAddress-1][88].setText(String.format("%02X", (crcCounted & 0xFF)));
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Wartosci liczbowe wolnych miejsc do wyswietlenia 
	//
	//*********************************************************************************//
	
	public synchronized static void valuesToDisplay(ArrayList<Integer> newValuesToDisplay, int displayPanelAddress) {
		
		displayPanelStatement1.get(displayPanelAddress - 1).setText(" ");												// Czyscimy pole
		displayPanelStatement2.get(displayPanelAddress - 1).setText(" ");												// Czyscimy pole
		
		StringBuilder sb = new StringBuilder();
		/*********** PIERWSZA STREFA ***********/
		int logoNumber = Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][15].getText(), 16);				// Pobieramy z konfiguracji numer loga jaki ma zostac wyswietlony - strefa 1
		if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][43].getText(), 16)) & 0x01) == 0x01 ) 		// Jezeli ma byc wyswietlone logo dla strefy 1
			sb.append(logoForNormalPlace[logoNumber < 43 ? logoNumber : 0]);											// Zabezpieczenie przed zla konfiguracja, dodanie loga
		int val = (newValuesToDisplay.get(0) * 256 + newValuesToDisplay.get(1));										// Pobieramy wartosc liczbowa dla strefy pierwszej
		if(val < 10) sb.append(" 00" + val + " ");																		// Jezeli wartosc mniejsza od 10
		if(val > 9 && val < 100) sb.append(" 0" + val + " ");															// Jezeli wartosc > 9 i < 100
		if(val > 99) sb.append(" " + val + " ");																		// Jezeli wartosc > 99
		if(val > 0) {																									// Jezeli wartosc > 0
			if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][43].getText(), 16)) & 0x0C) == 0x00 )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w gore
				sb.append("^");
			if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][43].getText(), 16)) & 0x0C) == 0x04 )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w dol
				sb.append("v");
			if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][43].getText(), 16)) & 0x0C) == 0x08 )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w prawo
				sb.append(">");
			if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][43].getText(), 16)) & 0x0C) == 0x0C )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w lewo
				sb.append("<");
		} else {
			sb.append("X");																								// Wyswietla krzyz
		}
		displayPanelStatement1.get(displayPanelAddress - 1).setText(sb.toString());										// Wyswietlamy dla pierwszej strefy
		
		/*********** DRUGA STREFA ***********/
		
		if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][5].getText(), 16)) & 0x01) == 0x01) {
			sb.delete(0, sb.toString().length());
			logoNumber = Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][51].getText(), 16);					// Pobieramy z konfiguracji numer loga jaki ma zostac wyswietlony - strefa 2
			if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][79].getText(), 16)) & 0x01) == 0x01 ) 		// Jezeli ma byc wyswietlone logo dla strefy 2
				sb.append(logoForNormalPlace[logoNumber < 43 ? logoNumber : 0]);											// Zabezpieczenie przed zla konfiguracja, dodanie loga
			
			val = (newValuesToDisplay.get(2) * 256 + newValuesToDisplay.get(3));											// Pobieramy wartosc liczbowa dla strefy drugiej
			if(val < 10) sb.append(" 00" + val + " ");																		// Jezeli wartosc mniejsza od 10
			if(val > 9 && val < 100) sb.append(" 0" + val + " ");															// Jezeli wartosc > 9 i < 100
			if(val > 99) sb.append(" " + val + " ");																		// Jezeli wartosc > 99
			if(val > 0) {																									// Jezeli wartosc > 0
				if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][79].getText(), 16)) & 0x0C) == 0x00 )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w gore
					sb.append("^");
				if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][79].getText(), 16)) & 0x0C) == 0x04 )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w dol
					sb.append("v");
				if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][79].getText(), 16)) & 0x0C) == 0x08 )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w prawo
					sb.append(">");
				if(((Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][79].getText(), 16)) & 0x0C) == 0x0C )		// Jezeli ma byc wyswietlona strzalka kierunku jazdy w lewo
					sb.append("<");
			} else {
				sb.append("X");																								// Wyswietla krzyz
			}
					displayPanelStatement2.get(displayPanelAddress - 1).setText(sb.toString());
		}

	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   		Zwraca ArrayList<Integer> z danymi czujnika o adresie sensorAddress 
	//
	//*********************************************************************************//
	
	public synchronized static ArrayList<Integer> getSensor_c_commandFrame(int sensorAddress) {
		ArrayList<Integer> frame = new ArrayList<Integer>();
		frame.add(sensorState[sensorAddress-1][0]);
		frame.add(sensorState[sensorAddress-1][1]);
		frame.add(sensorState[sensorAddress-1][2]);
		frame.add(sensorState[sensorAddress-1][3]);
		frame.add(sensorState[sensorAddress-1][4]);
		frame.add(sensorState[sensorAddress-1][5]);
		frame.add(Integer.parseInt(sensorConfig[sensorAddress-1][10].getText(),16));
		frame.add(Integer.parseInt(sensorConfig[sensorAddress-1][11].getText(),16));
		return frame;
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   		Zwraca ArrayList<Integer> z danymi tablicy o adresie displayPanelAddress
	//
	//*********************************************************************************//
	
	public synchronized static ArrayList<Integer> getDisplayPanel_c_commandFrame(int displayPanelAddress) {
		ArrayList<Integer> frame = new ArrayList<Integer>();
		frame.add(displayPanelState[displayPanelAddress - 1][0]);
		frame.add(displayPanelState[displayPanelAddress - 1][1]);
		frame.add(displayPanelState[displayPanelAddress - 1][2]);
		frame.add(Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][87].getText(), 16));
		frame.add(Integer.parseInt(displayPanelConfig[displayPanelAddress - 1][88].getText(), 16));
		return frame;
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   		Zwraca informacje o obecnosci czujnika (true -> symulator odpowiada) 
	//												   (false -> symulator nie odpowiada)
	//
	//*********************************************************************************//
	
	public synchronized static boolean getSensorPresence(int sensorAddress) {
//		System.out.println("getSensorPresence(): " + sensorPresentTab[sensorAddress-1]);
		return sensorPresentTab[sensorAddress-1];
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   		Zwraca informacje o obecnosci tablicy  (true -> symulator odpowiada) 
	//												   (false -> symulator nie odpowiada)
	//
	//*********************************************************************************//
	
	public synchronized static boolean getDisplayPanelPresence(int displayPanelAddress) {
		return displayPanelPresentTab[displayPanelAddress-1];
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   						Zmienia typ wymuszenia czujnika
	//
	//*********************************************************************************//
	
	public synchronized static void setSensorExtortMode(int sensor_address, int extortType) {
		switch(extortType) {
		case 0x6E: extortionType.get(sensor_address - 1).setSelectedIndex(0); break;	// 'n' - normal mode (brak wymuszenia)
		case 0x66: extortionType.get(sensor_address - 1).setSelectedIndex(1); break;	// 'f' - force mode (forsowanie)
		case 0x72: extortionType.get(sensor_address - 1).setSelectedIndex(2); break;	// 'r' - reserve mode (rezerwacja)
		case 0x6C: extortionType.get(sensor_address - 1).setSelectedIndex(3); break;	// 'l' - reserve mode (longstop)
		}
		updateSensorStates();	// Aktualizujemy tablice stanow
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   						Zmienia tryb swiecenia sygnalizatora
	//
	//*********************************************************************************//
	
	public synchronized static void setSensorLightMode(int sensor_address, int lightType) {
		switch(lightType) {
		case 0x73: lightMode.get(sensor_address - 1).setSelectedIndex(0); break;	// 's' - swieci
		case 0x62: lightMode.get(sensor_address - 1).setSelectedIndex(1); break;	// 'b' - mruga
		case 0x70: lightMode.get(sensor_address - 1).setSelectedIndex(2); break;	// 'p' - defilada
		case 0x6F: lightMode.get(sensor_address - 1).setSelectedIndex(3); break;	// 'o' - wygaszony
		}
		updateSensorStates();	// Aktualizujemy tablice stanow
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   						Zmienia tryb ECO w czujniku
	//
	//*********************************************************************************//
	
	public synchronized static void setSensorEcoMode(int sensor_address, int ecoType) {
		switch(ecoType) {
		case 0x00: ecoMode.get(sensor_address - 1).setSelectedIndex(0); break;	// wylaczony tryb ECO
		case 0x01: ecoMode.get(sensor_address - 1).setSelectedIndex(1); break;	// wlaczony tryb ECO
		}
		updateSensorStates();	// Aktualizujemy tablice stanow
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   						Zmienia tryb ECO w tablicy
	//
	//*********************************************************************************//
	
	public synchronized static void setDisplayPanelEcoMode(int displayPanel_address, int ecoType) {
		switch(ecoType) {
		case 0x00: displayPanelEcoMode.get(displayPanel_address - 1).setSelectedIndex(0); break;	// wylaczony tryb ECO
		case 0x01: displayPanelEcoMode.get(displayPanel_address - 1).setSelectedIndex(1); break;	// wlaczony tryb ECO
		}
		updateDisplayPanelStates();	// Aktualizujemy tablice stanow
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Aktualizuje tablice obecnosci urzadzen
	//					 (czujnikow i tablic wyswietlajacych)
	//
	//*********************************************************************************//
	
	private synchronized void updateDevicesPresentTab() {
		for(int i = 0; i < 80; i++) {
			sensorPresentTab[i] = sensorPresent.get(i).isSelected();
		}
		for(int i = 0; i < 16; i++) {
			displayPanelPresentTab[i] = displayPanelPresent.get(i).isSelected();
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Aktualizuje tablice stanow czujnikow
	//
	//*********************************************************************************//
	
	private synchronized static void updateSensorStates() {
		for(int i = 0; i < 80; i++) {															// Wykonujemy dla wszystkich czujnikow
			/*********** Byte 1 *********/
			sensorState[i][0] = occupyState.get(i).getSelectedIndex();							// Ustawiamy bit 0 (Wolny/Zajety)
			switch(extortionType.get(i).getSelectedIndex()) {									// Ustawiamy bit 1, 2 i 4 (Forced/Reserved;Longstop)
			case 1: sensorState[i][0] |= 0x02; break;											// Bit 1 -> Forced
			case 2: sensorState[i][0] |= 0x10; break;											// Bit 4 -> Reserve
			case 3: sensorState[i][0] |= 0x04; break;											// Bit 2 -> Longstop
			}
			sensorState[i][0] |= (caseState.get(i).getSelectedIndex() > 0 ? 1 : 0) << 3;		// Ustawiamy bit 3 (Tamper)
			switch(lightMode.get(i).getSelectedIndex()) {										// Ustawiamy bit 5 i 6 (tryb swiecenia sygnalizatora)
			case 1: sensorState[i][0] |= 0x20; break;											// Bit 5 -> Mruga
			case 2: sensorState[i][0] |= 0x40; break;											// Bit 6 -> Defilada
			case 3: sensorState[i][0] |= 0x60; break;											// Bit 5 i 6 -> Wylaczony
			}
			sensorState[i][0] |= (damageState.get(i).getSelectedIndex() > 0 ? 1 : 0) << 7;		// Ustawiamy bit 7 (Uszkodzony piezo)
			/************ Byte 2 **********/
			sensorState[i][1] = ecoMode.get(i).getSelectedIndex();								// Ustawiamy bit 0 (0 - tryb eco nieaktywny, 1 - tryb eco aktywny)
			/*********** Byte 3,4 *********/	// Aktualna odleglosc widziana przez czujnik
			switch(actualDistance.get(i).getSelectedIndex()) {
			case 0: sensorState[i][2] = 0x00; sensorState[i][3] = 0x64; break;
			case 1: sensorState[i][2] = 0x00; sensorState[i][3] = 0x96; break;
			case 2: sensorState[i][2] = 0x00; sensorState[i][3] = 0xC8; break;
			case 3: sensorState[i][2] = 0x00; sensorState[i][3] = 0xFA; break;
			case 4: sensorState[i][2] = 0x01; sensorState[i][3] = 0x2C; break;
			}
			/*********** Byte 5,6 *********/	// Ilosc uruchomien urzadzenia
			sensorState[i][4] = 0x00;
			sensorState[i][5] = deviceStartRunNames[deviceStartRun.get(i).getSelectedIndex()];
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Aktualizuje tablice stanow tablic wyswietlajacych
	//
	//*********************************************************************************//
	
	private synchronized static void updateDisplayPanelStates() {
		for(int i = 0; i < 16; i++) {
			displayPanelState[i][0] = displayPanelEcoMode.get(i).getSelectedIndex();						// Eco mode
			displayPanelState[i][1] = 0x00;																	// Ilosc uruchomien urzadzenia
			displayPanelState[i][2] = deviceStartRunNames[displayPanelStartRun.get(i).getSelectedIndex()];	// Ilosc uruchomien urzadzenia
					}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Ustawia obecnosc wszystkich czujnikow i tablic
	//
	//*********************************************************************************//
	
	private synchronized  void setAllDevicesPresence() {
		if(setAllSensorsCheckBox.isSelected()) {	// Jezeli setAllSensorsCheckBox jest wcisniety
			for(int i = 0; i < 80; i++) {
				sensorPresent.get(i).setSelected(true);
				sensorPresentTab[i] = true;
			}
			for(int i = 0; i < 16; i++) {
				displayPanelPresent.get(i).setSelected(true);
				displayPanelPresentTab[i] = true;
			}
		} else {
			for(int i = 0; i < 80; i++) {			// Jezeli setAllSensorsCheckBox jest wycisniety
				sensorPresent.get(i).setSelected(false);
				sensorPresentTab[i] = false;
			}
			for(int i = 0; i < 16; i++) {
				displayPanelPresent.get(i).setSelected(false);
				displayPanelPresentTab[i] = false;
			}
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Ustawia obecnosc okreslonego czujnika
	//
	//*********************************************************************************//
	
	private synchronized void setSensorPrecence(ActionEvent e) {
		int sensorNumber = Integer.parseInt(e.getSource().toString().substring(e.getSource().toString().indexOf("Czujnik") + 8, e.getSource().toString().indexOf("Czujnik") + 9));
		sensorPresentTab[sensorNumber - 1] = sensorPresent.get(sensorNumber - 1).isSelected();
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Ustawia obecnosc okreslonej tablicy
	//
	//*********************************************************************************//
	
	private synchronized void setDisplayPanelPresence(ActionEvent e) {
		int displayPanelNumber = Integer.parseInt(e.getSource().toString().substring(e.getSource().toString().indexOf("Tablica") + 8, e.getSource().toString().indexOf("Tablica") + 9));
		displayPanelPresentTab[displayPanelNumber - 1] = displayPanelPresent.get(displayPanelNumber - 1).isSelected();
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Aktualizuje konfiguracje urzadzen
	//
	//*********************************************************************************//
	
	private synchronized void updateDevicesConfig() {
		int crcCounted = 0;
		ArrayList<Integer> configList = new ArrayList<Integer>();
		
		/* AKTUALIZACJA KONFIGURACJI CZUJNIKOW */
		for(int i = 0; i < 80; i++) {
			for(int j = 0; j < 10; j++) {	// Konwersja konfiguracji z JTextField na ArrayList<Integer>
				configList.add(Integer.parseInt(sensorConfig[i][j].getText(), 16));
			}
			crcCounted = crc.MakeCrc(configList, configList.size());
			sensorConfig[i][10].setText(String.format("%02X", ((crcCounted >> 8) & 0xFF)));
			sensorConfig[i][11].setText(String.format("%02X", (crcCounted & 0xFF)));
			crcCounted = 0;
			configList.clear();
		}
		
		/* AKTUALIZACJA KONFIGURACJI TABLIC */
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 87; j++) {	// Konwersja konfiguracji z JTextField na ArrayList<Integer>
				configList.add(Integer.parseInt(displayPanelConfig[i][j].getText(), 16));
			}
				crcCounted = crc.MakeCrc(configList, configList.size());
				displayPanelConfig[i][87].setText(String.format("%02X", ((crcCounted >> 8) & 0xFF)));
				displayPanelConfig[i][88].setText(String.format("%02X", (crcCounted & 0xFF)));
				crcCounted = 0;
				configList.clear();
		}
	}
	
	//*********************************************************************************//
	
	private ActionListener setAllSensorsCheckBoxListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			setAllDevicesPresence();
		}
	};
	
	private ActionListener sensorPresentListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			setSensorPrecence(e);
		}
	};
	
	private ActionListener displayPanelPresentListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {	
			setDisplayPanelPresence(e);
		}
	};
	
	private ActionListener allJTextFieldListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			updateDevicesConfig();
		}
	};
	
	private ActionListener allJComboBoxListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			updateSensorStates();
			updateDisplayPanelStates();
		}
	};
	
	public FirstTabCenterContainer() {		
		for(int i = 0; i < 80; i++) {
			
			sensorPresent.add(new JToggleButton("Czujnik " + (i+1)));
			extortionType.add(new JComboBox<String>());
			occupyState.add(new JComboBox<String>());
			caseState.add(new JComboBox<String>());
			lightMode.add(new JComboBox<String>());
			ecoMode.add(new JComboBox<String>());
			actualDistance.add(new JComboBox<String>());
			deviceStartRun.add(new JComboBox<Integer>());
			damageState.add(new JComboBox<String>());
			for(int j = 0; j < 12; j++) {
				sensorConfig[i][j] = new JTextField(3);
				sensorConfig[i][j].setText(String.format("%02X", defaultSensorConfig[j]));
				sensorConfig[i][j].addActionListener(allJTextFieldListener);
			}
		}
		
		for(int i = 0; i < 16; i++) {
			displayPanelPresent.add(new JToggleButton("Tablica " + (i + 1)));
			displayPanelEcoMode.add(new JComboBox<String>());
			displayPanelStartRun.add(new JComboBox<Integer>());
			displayPanelStatement1.add(new JTextArea(1, 6));		// Jeden wiersz, szesc kolumn
			displayPanelStatement2.add(new JTextArea(1, 6));		// Jeden wiersz, szesc kolumn
			for(int j = 0; j < 89; j++) {
				displayPanelConfig[i][j] = new JTextField(3);
				displayPanelConfig[i][j].setText(String.format("%02X", defaultDisplayPanelConfig[j]));
				if(j <87) displayPanelConfig[i][j].setToolTipText("Byte: " + (j + 1));
				else if(j == 87) displayPanelConfig[i][j].setToolTipText("CRC HI");
				else if(j == 88) displayPanelConfig[i][j].setToolTipText("CRC LO");
				displayPanelConfig[i][j].addActionListener(allJTextFieldListener);
			}
		}
		
		for(int i = 0; i < 80; i++) {
			
			sensorPresent.get(i).addActionListener(sensorPresentListener);
			sensorPresent.get(i).setToolTipText("Obecność czujnika na linii");
			
			occupyState.get(i).addItem(sensorStateNames[0]);
			occupyState.get(i).addItem(sensorStateNames[1]);
			occupyState.get(i).addActionListener(allJComboBoxListener);
			occupyState.get(i).setToolTipText("Zajętość");
			
			extortionType.get(i).addItem(extortionTypeNames[0]);
			extortionType.get(i).addItem(extortionTypeNames[1]);
			extortionType.get(i).addItem(extortionTypeNames[2]);
			extortionType.get(i).addItem(extortionTypeNames[3]);
			extortionType.get(i).addActionListener(allJComboBoxListener);
			extortionType.get(i).setToolTipText("Typ wymuszenia");
			
			caseState.get(i).addItem(caseStateNames[0]);
			caseState.get(i).addItem(caseStateNames[1]);
			caseState.get(i).addActionListener(allJComboBoxListener);
			caseState.get(i).setToolTipText("Stan obudowy");
			
			lightMode.get(i).addItem(ecoStateNames[0]);
			lightMode.get(i).addItem(ecoStateNames[1]);
			lightMode.get(i).addItem(ecoStateNames[2]);
			lightMode.get(i).addItem(ecoStateNames[3]);
			lightMode.get(i).addActionListener(allJComboBoxListener);
			lightMode.get(i).setToolTipText("Tryb świecenia");
			
			ecoMode.get(i).addItem(ecoModeNames[0]);
			ecoMode.get(i).addItem(ecoModeNames[1]);
			ecoMode.get(i).addActionListener(allJComboBoxListener);
			ecoMode.get(i).setToolTipText("Tryb ECO");
			
			actualDistance.get(i).addItem(actualDistanceNames[0]);
			actualDistance.get(i).addItem(actualDistanceNames[1]);
			actualDistance.get(i).addItem(actualDistanceNames[2]);
			actualDistance.get(i).addItem(actualDistanceNames[3]);
			actualDistance.get(i).addItem(actualDistanceNames[4]);
			actualDistance.get(i).addActionListener(allJComboBoxListener);
			actualDistance.get(i).setToolTipText("Aktualna odległość");
			
			damageState.get(i).addItem(damageStateNames[0]);
			damageState.get(i).addItem(damageStateNames[1]);
			damageState.get(i).addActionListener(allJComboBoxListener);
			damageState.get(i).setToolTipText("Uszkodzony piezo");
			
			for(int j = 0; j < 20; j++)
				deviceStartRun.get(i).addItem(deviceStartRunNames[j]);
			deviceStartRun.get(i).addActionListener(allJComboBoxListener);
			deviceStartRun.get(i).setToolTipText("Ilość uruchomień");
		}
		
		setAllSensorsCheckBox.addActionListener(setAllSensorsCheckBoxListener);
		
		for(int i = 0; i < 16; i++) {
			
			displayPanelPresent.get(i).addActionListener(displayPanelPresentListener);
			displayPanelPresent.get(i).setToolTipText("Obecność tablicy na linii");
			
			displayPanelEcoMode.get(i).addItem(ecoModeNames[0]);
			displayPanelEcoMode.get(i).addItem(ecoModeNames[1]);
			displayPanelEcoMode.get(i).addActionListener(allJComboBoxListener);
			displayPanelEcoMode.get(i).setToolTipText("Tryb ECO");
			
			for(int j = 0; j < 20; j++)
				displayPanelStartRun.get(i).addItem(deviceStartRunNames[j]);
			displayPanelStartRun.get(i).addActionListener(allJComboBoxListener);
			displayPanelStartRun.get(i).setToolTipText("Ilość uruchomień");
			
			displayPanelStatement1.get(i).setFont(new Font("Dialog", 0, 23)); //Ustawiamy nazwe fontu, typ(normalny, kursywa, pogrubiony itd), rozmiar czcionki
			displayPanelStatement2.get(i).setFont(new Font("Dialog", 0, 23)); 
			
		}
		
		setLayout(new MigLayout("","",""));
		
		Container container1 = new Container();
		container1.setLayout(new MigLayout("","","5[25]10[25]0[25]0"));
		container1.add(new JLabel("Obecność"), "split 2");
		container1.add(setAllSensorsCheckBox);
		container1.add(new JLabel("Zajętość"), "center align");
		container1.add(new JLabel("Wymuszenie"), "center align");
		container1.add(new JLabel("Obudowa"), "center align");
		container1.add(new JLabel("Tryb świecenia"), "center align");
		container1.add(new JLabel("Tryb ECO"), "center align");
		container1.add(new JLabel("Stan piezo"), "center align");
		container1.add(new JLabel("Odległość"), "center align");
		container1.add(new JLabel("Il. uruch."), "gapright 10, center align");
		container1.add(new JLabel(""), "center align");
		container1.add(new JLabel("Green"), "gapleft 10, center align");
		container1.add(new JLabel("Red"), "center align");
		container1.add(new JLabel("Blue"), "center align");
		container1.add(new JLabel("Brig."), "center align");
		container1.add(new JLabel("Height"), "center align");
		container1.add(new JLabel("Height"), "center align");
		container1.add(new JLabel("S/L"), "center align");
		container1.add(new JLabel("nDta"), "center align");
		container1.add(new JLabel("lMode"), "center align");
		container1.add(new JLabel("Side"), "center align");
		container1.add(new JLabel("CRC H"), "center align");
		container1.add(new JLabel("CRC L"), "center align, wrap");
		for(int i = 1; i <= 80; i++) {
			container1.add(sensorPresent.get(i-1), "center align");
			container1.add(occupyState.get(i-1), "center align");
			container1.add(extortionType.get(i-1), "center align");
			container1.add(caseState.get(i-1), "center align");
			container1.add(lightMode.get(i-1), "center align");
			container1.add(ecoMode.get(i-1), "center align");
			container1.add(damageState.get(i-1), "center align");
			container1.add(actualDistance.get(i-1), "center align");
			container1.add(deviceStartRun.get(i-1), "gapright 10, center align");
			container1.add(new JLabel("---->"), "center align");
			container1.add(sensorConfig[i-1][0], "gapleft 10, center align");
			container1.add(sensorConfig[i-1][1], "center align");
			container1.add(sensorConfig[i-1][2], "center align");
			container1.add(sensorConfig[i-1][3], "center align");
			container1.add(sensorConfig[i-1][4], "center align");
			container1.add(sensorConfig[i-1][5], "center align");
			container1.add(sensorConfig[i-1][6], "center align");
			container1.add(sensorConfig[i-1][7], "center align");
			container1.add(sensorConfig[i-1][8], "center align");
			container1.add(sensorConfig[i-1][9], "center align");
			sensorConfig[i-1][10].setEditable(false);
			container1.add(sensorConfig[i-1][10], "center align");
			sensorConfig[i-1][11].setEditable(false);
			container1.add(sensorConfig[i-1][11], "center align, wrap");
		}
		
		Container container2 = new Container();
		container2.setLayout(new MigLayout("","[][]10[]10[][]","10[]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]20[][][][][]"));
		container2.add(container1, "left align");
		container2.add(new JLabel("Obecność"), "center align");
		container2.add(new JLabel("Tryb ECO"), "center align");
		container2.add(new JLabel("Il. uruch."), "center align");
		container2.add(new JLabel("Komunikaty tablicy"), "center align");;
		container2.add(new JLabel("Konfiguracja"), "span 18, center align, wrap");
//		container2.add(new JLabel("CRC H"), "center align");
//		container2.add(new JLabel("CRC L"), "center align, wrap");
		for(int i = 1; i <= 16; i++) {
			container2.add(displayPanelPresent.get(i - 1), "center align, span 1 5");
			container2.add(displayPanelEcoMode.get(i - 1), "center align, span 1 5");
			container2.add(displayPanelStartRun.get(i - 1), "center align, span 1 5");
			JPanel jp = new JPanel();
			jp.setLayout(new MigLayout());
			jp.add(displayPanelStatement1.get(i - 1), "wrap");
			jp.add(displayPanelStatement2.get(i - 1));
			TitledBorder tb = new TitledBorder("Wyświetlacz");
			tb.setTitlePosition(3);	// Ustawia tytul wewnatrz ramki
			tb.setTitleJustification(2);
			jp.setBorder(tb);
			container2.add(jp, "center align, span 1 5");
			
			for(int j = 1; j <= 87; j++) {
				if((j%18 == 0) && (j > 0)) {
					container2.add(displayPanelConfig[i - 1][j - 1], "center align, wrap");
				} else {
					container2.add(displayPanelConfig[i - 1][j - 1], "center align");
				}
				
			}
			container2.add(new JLabel(""), "center align");
			container2.add(displayPanelConfig[i - 1][87], "center align");
			container2.add(displayPanelConfig[i - 1][88], "center align, wrap");
		}
		
		add(container1, "left align, wrap");
		add(container2, "left align");
		
		updateSensorStates();		// Uaktualnienie bazy stanow czujnikow
		updateDisplayPanelStates();	// Uaktualnienie bazy stanow tablic
		updateDevicesPresentTab();	// Uaktualnienie bazy obecnosci urzadzen
	}
}
