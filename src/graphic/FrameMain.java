package graphic;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

public class FrameMain extends JTabbedPane {

	PaneOperatingRound POR = new PaneOperatingRound();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameMain() {
		if (startmethods.Play1830.verbose) {
			System.out.println("FrameMain.buildFrameMain");
		}

		this.setTabPlacement(JTabbedPane.LEFT);
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		JDialog frame = new JDialog();

		frame.setTitle(startmethods.Play1830.gamename);
		frame.setSize(1400, 900);
		frame.add(this);
		frame.setVisible(true);
	}

	public PaneOperatingRound getPOR() {
		return POR;
	}

	public void setPOR(PaneOperatingRound pOR) {
		POR = pOR;
	}
	
	public void refreshPOR() {
		this.POR = new PaneOperatingRound();
	}
}
