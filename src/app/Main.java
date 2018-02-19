package app;

import static util.SwingConsole.runWithNameVersionAndLogo;
import firstTab.FirstTab;
import secondTab.SecondTab;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class Main extends JFrame {
	private static final long serialVersionUID = -130737910397146677L;
	
	private String[] tabsNames = { "Emulacja", "Ustawienia", "Inne"};
	private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	
	private FirstTab ft = new FirstTab();
	private SecondTab st = new SecondTab();

	public Main() {
		tabs.addTab(tabsNames[0], ft);
		tabs.addTab(tabsNames[1], st);
//		tabs.addTab(tabsNames[2], new JLabel("Jeszcze nic"));
		add(tabs);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			throw new RuntimeException();
		}
		Main main = new Main();
		runWithNameVersionAndLogo(main, 1190, 600, "Sensors Line Simulator", "0.003", "LogoEL_blue.png");
	}

}
