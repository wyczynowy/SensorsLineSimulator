package secondTab;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import firstTab.FirstTabCenterContainer;

public class SendData implements Runnable {
	
	private ArrayList<Integer> receiveBuffer = new ArrayList<Integer>();
	private ArrayList<Integer> toSendBuffer = new ArrayList<Integer>();
	Crc16Calc crc = new Crc16Calc();
	
	//*********************************************************************************//
	//
	//   				Obsluga komendy v do czujnika 
	//
	//*********************************************************************************//
	
	private void command_v_toSensorRequest() {
//		System.out.println("command_v_toSensorRequest()");
		if(FirstTabCenterContainer.getSensorPresence(receiveBuffer.get(2))) {	// Jezeli ten czujnik jest obecny
			toSendBuffer.add(0x10);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x07);												// Programmed program year
			toSendBuffer.add(0x07);												// Programmed program month
			toSendBuffer.add(0x07);												// Programmed program day
			toSendBuffer.add(0x07);												// Major program version
			toSendBuffer.add(0x07);												// Minor program version
			toSendBuffer.add(0x07);												// Programmed bootloader first time - year
			toSendBuffer.add(0x07);												// Programmed bootloader first time - month
			toSendBuffer.add(0x07);											 	// Programmed bootloader first time - day
			toSendBuffer.add(0x07);												// Major bootloader version
			toSendBuffer.add(0x07);												// Minor bootloader version
			toSendBuffer.add(0x2E);												// Device serial number HI
			toSendBuffer.add(0x5B);												// Device serial number
			toSendBuffer.add(0xF2);												// Device serial number
			toSendBuffer.add(0x71);												// Device serial number LO
			toSendBuffer.add(0x70);												// 'b' - device in bootloader, 'p' - device in program
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Obsluga komendy c do czujnika 
	//
	//*********************************************************************************//
	
	private void command_c_toSensorRequest() {
//		System.out.println("command_c_toSensorRequest()");
		if(FirstTabCenterContainer.getSensorPresence(receiveBuffer.get(2))) {	// Jezeli ten czujnik jest obecny
			toSendBuffer.add(0x09);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.addAll(FirstTabCenterContainer.getSensor_c_commandFrame(receiveBuffer.get(2)));	// Pobieramy dane o stanie czujnika
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Obsluga komendy C do czujnika 
	//
	//*********************************************************************************//
	
	private void command_C_toSensorRequest() {
		if(FirstTabCenterContainer.getSensorPresence(receiveBuffer.get(2))) {	// Jezeli ten czujnik jest obecny
			ArrayList<Integer> newSensorConfig = new ArrayList<Integer>();
			for(int i = 0; i < 10; i++) {
				newSensorConfig.add(receiveBuffer.get(i+4));
			}
			FirstTabCenterContainer.setSensorConfig(newSensorConfig, receiveBuffer.get(2));
			
			toSendBuffer.add(0x02);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x00);												// Informacja o braku bledu
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga komendy t do czujnika 
	//
	//*********************************************************************************//
	
	private void command_t_toSensorRequest() {
		if(FirstTabCenterContainer.getSensorPresence(receiveBuffer.get(2))) {	// Jezeli ten czujnik jest obecny

			FirstTabCenterContainer.setSensorExtortMode(receiveBuffer.get(2), receiveBuffer.get(4));
			
			toSendBuffer.add(0x02);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x00);												// Informacja o braku bledu
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga komendy l do czujnika 
	//
	//*********************************************************************************//
	
	private void command_l_toSensorRequest() {
		if(FirstTabCenterContainer.getSensorPresence(receiveBuffer.get(2))) {	// Jezeli ten czujnik jest obecny

			FirstTabCenterContainer.setSensorLightMode(receiveBuffer.get(2), receiveBuffer.get(4));
			
			toSendBuffer.add(0x02);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x00);												// Informacja o braku bledu
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga komendy e do czujnika 
	//
	//*********************************************************************************//
	
	private void command_e_toSensorRequest() {
		if(FirstTabCenterContainer.getSensorPresence(receiveBuffer.get(2))) {	// Jezeli ten czujnik jest obecny

			FirstTabCenterContainer.setSensorEcoMode(receiveBuffer.get(2), receiveBuffer.get(4));
			
			toSendBuffer.add(0x02);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x00);												// Informacja o braku bledu
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga komendy e do tablicy
	//
	//*********************************************************************************//
	
	private void command_e_toDisplayPanelRequest() {
		if(FirstTabCenterContainer.getDisplayPanelPresence(receiveBuffer.get(2))) {	// Jezeli ten czujnik jest obecny

			FirstTabCenterContainer.setDisplayPanelEcoMode(receiveBuffer.get(2), receiveBuffer.get(4));
			
			toSendBuffer.add(0x02);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x00);												// Informacja o braku bledu
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga komendy v do tablicy
	//
	//*********************************************************************************//
	
	private void command_v_toDisplayPanelRequest() {
		if(FirstTabCenterContainer.getDisplayPanelPresence(receiveBuffer.get(2))) {	// Jezeli ta tablica jest obecna
			toSendBuffer.add(0x10);													// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));						// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));									// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));									// Komenda
			toSendBuffer.add(0x07);													// Programmed program year
			toSendBuffer.add(0x07);													// Programmed program month
			toSendBuffer.add(0x07);													// Programmed program day
			toSendBuffer.add(0x07);													// Major program version
			toSendBuffer.add(0x07);													// Minor program version
			toSendBuffer.add(0x07);													// Programmed bootloader first time - year
			toSendBuffer.add(0x07);													// Programmed bootloader first time - month
			toSendBuffer.add(0x07);												 	// Programmed bootloader first time - day
			toSendBuffer.add(0x07);													// Major bootloader version
			toSendBuffer.add(0x07);													// Minor bootloader version
			toSendBuffer.add(0x2E);													// Device serial number HI
			toSendBuffer.add(0x5B);													// Device serial number
			toSendBuffer.add(0xF2);													// Device serial number
			toSendBuffer.add(0x71);													// Device serial number LO
			toSendBuffer.add(0x70);													// 'b' - device in bootloader, 'p' - device in program
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());		// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);								// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);									// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);												// Znacznik poczatku ramki
			toSendBuffer.add(0x56);													// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();													// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga komendy c do tablicy
	//
	//*********************************************************************************//
	
	private void command_c_toDisplayPanelRequest() {
		if(FirstTabCenterContainer.getDisplayPanelPresence(receiveBuffer.get(2))) {	// Jezeli ta talica jest obecna
			toSendBuffer.add(0x06);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.addAll(FirstTabCenterContainer.getDisplayPanel_c_commandFrame(receiveBuffer.get(2)));	// Pobieramy dane o stanie tablicy
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   						Obsluga komendy C do tablicy
	//
	//*********************************************************************************//
	
	private void command_C_toDisplayPanelRequest() {
		if(FirstTabCenterContainer.getDisplayPanelPresence(receiveBuffer.get(2))) {	// Jezeli ta tablica jest obecna
			ArrayList<Integer> newSensorConfig = new ArrayList<Integer>();
			for(int i = 0; i < 87; i++) {
				newSensorConfig.add(receiveBuffer.get(i+4));
			}
			FirstTabCenterContainer.setDisplayPanelConfig(newSensorConfig, receiveBuffer.get(2));
			
			toSendBuffer.add(0x02);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x00);												// Informacja o braku bledu
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga komendy D do tablicy
	//
	//*********************************************************************************//
	
	private void command_D_toDisplayPanelRequest() {
		if(FirstTabCenterContainer.getDisplayPanelPresence(receiveBuffer.get(2))) {	// Jezeli ta tablica jest obecna
			ArrayList<Integer> newValuesToDisplay = new ArrayList<Integer>();
			for(int i = 0; i < 4; i++) {
				newValuesToDisplay.add(receiveBuffer.get(i+4));
			}
			FirstTabCenterContainer.valuesToDisplay(newValuesToDisplay, receiveBuffer.get(2));
			
			toSendBuffer.add(0x02);												// Ilosc bajtow uzytecznych w ramce
			toSendBuffer.add((receiveBuffer.get(1) | 0x80));					// Klasa urzadzenia z ustawionym bitem 7 -> odpowiedz
			toSendBuffer.add(receiveBuffer.get(2));								// Adres urzadzenia
			toSendBuffer.add(receiveBuffer.get(3));								// Komenda
			toSendBuffer.add(0x00);												// Informacja o braku bledu
			int crcCounted = crc.MakeCrc(toSendBuffer, toSendBuffer.size());	// Liczymy CRC ramki
			toSendBuffer.add((crcCounted >> 8) & 0xFF);							// Starszy bajt CRC
			toSendBuffer.add(crcCounted & 0xFF);								// Mlodszy bajt CRC
			toSendBuffer.add(0, 0x55);											// Znacznik poczatku ramki
			toSendBuffer.add(0x56);												// Znacznik konca ramki
			byte[] frameToSend = convertToByteArray(toSendBuffer);
			SecondTabCenterContainer.usbPort.writeBytes(frameToSend, frameToSend.length);	// Wyslanie danych na port COM
			toSendBuffer.clear();												// Czyscimy bufor nadawczy
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   				Konwersja ArrayList<Integer> na byte[]
	//
	//*********************************************************************************//
	
	private byte[] convertToByteArray(ArrayList<Integer> list)
	{
		byte[] bytes = new byte[list.size()];
		int oneInt = 0;
		for(int i = 0; i < bytes.length; i++) {
			oneInt = list.get(i);
			bytes[i] = (byte)oneInt;
		}
		return bytes;
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga odpowiedzi od czujnikow  
	//
	//*********************************************************************************//
	
	private void sensorRequest() {
		switch(receiveBuffer.get(3)) {
		case 0x63: command_c_toSensorRequest(); break;	// Obsluga komendy c do czujnika
		case 0x76: command_v_toSensorRequest(); break;	// Obsluga komendy v do czujnika
		case 0x43: command_C_toSensorRequest(); break;	// Obsluga komendy C do czujnika
		case 0x74: command_t_toSensorRequest(); break;	// Obsluga komendy t do czujnika
		case 0x6C: command_l_toSensorRequest(); break;	// Obsluga komendy l do czujnika
		case 0x65: command_e_toSensorRequest(); break;	// Obsluga komendy e do czujnika
		}
	}
	
	//*********************************************************************************//
	
	//*********************************************************************************//
	//
	//   					Obsluga odpowiedzi od tablic  
	//
	//*********************************************************************************//
	
	private void displayPanelRequest() {
		switch(receiveBuffer.get(3)) {
		case 0x76: command_v_toDisplayPanelRequest(); break;	// Obsluga komendy v do tablicy
		case 0x63: command_c_toDisplayPanelRequest(); break; 	// Obsluga komendy c do tablicy
		case 0x43: command_C_toDisplayPanelRequest(); break;	// Obsluga komendy C do tablicy
		case 0x44: command_D_toDisplayPanelRequest(); break;	// Obsluga komendy D do tablicy
		case 0x65: command_e_toDisplayPanelRequest(); break;	// Obsluga komendy e do tablicy
		}
	}
	
	//*********************************************************************************//

	@Override
	public void run() {
		while(true) {
			if(ReceiveData.isNewFrameRedyToRead()) {
				receiveBuffer.addAll(ReceiveData.getNewFrame());	// Kopiujemy bufor z danymi
		
				switch(receiveBuffer.get(1)) {
				case 0x02: sensorRequest(); break;					// Obsluga odpowiedzi od czujnikow
				case 0x03: displayPanelRequest(); break;			// Obsluga odpowiedzi od tablic
				}
				receiveBuffer.clear(); 								// Czyscimy bufor z danymi
			}
	        try {
	            TimeUnit.MILLISECONDS.sleep(1);
	          } catch(InterruptedException e) {
	            System.out.println("InterruptException -> secondTab.SendData");
	            return;
	          }
		}
	}
}
