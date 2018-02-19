package secondTab;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SecondTab extends Container {
	private static final long serialVersionUID = 1L;

	public SecondTab() {
		JPanel panel = new JPanel(false);			// Tworzymy JPanel aby umiescic w nim wsszystkie elementy, ktore beda w zakladce
		panel.setLayout(new BorderLayout());		// Ustawiamy layout dla zawartosci panelu
			
		panel.add(BorderLayout.CENTER, new JScrollPane(new SecondTabCenterContainer()));
		
		
		setLayout(new BorderLayout());			// Ustawiamy Layout dla calego kontenera
		add(panel);								// Dodajemy panel do calego kontenera
	}
}
