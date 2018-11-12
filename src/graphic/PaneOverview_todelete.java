package graphic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import material.Basic;
import material.BasicSave;
import xml.XMLBasicSave;

public class PaneOverview_todelete {

	public static void setPaneOverview(Basic basic) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOverview.setPaneOverview");
		}

		if (basic.getTP().getTabCount() > 1) {
			basic.getTP().removeTabAt(0);
		}

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.01;

		if (basic.getPrivates().get(0).getOwner() == 91) { // if first (therefore all) Privates are not sold
			setPhase1(basic, panel, c);
		} else {
			if (basic.getGameplay().getTrains().get(0).getDistancePrimary() == 2) {
				setPhase2(basic, panel, c);
			}
		}

		JButton save = new JButton("Speichern");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(10, 0, 0, 0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 20;
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicSave savedata = new BasicSave(basic);
				savedata.save();
				XMLBasicSave savexml = new XMLBasicSave(basic);
				savexml.savetoxml();
			}
		});
		panel.add(save, c);

		JButton load = new JButton("Laden");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(10, 0, 0, 0);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 20;
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicSave savedata = new BasicSave(basic);
				savedata.load(basic);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(0);
			}
		});
		panel.add(load, c);

		ImageIcon newicon = new ImageIcon(); // dummy imageicon mayby set in later versions
		basic.getTP().insertTab("Overview", newicon, panel, "Overview", 0);
	}

	private static JPanel setPhase1(Basic basic, JPanel panel, GridBagConstraints c) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOverview.setPhase1");
		}

		String[] combolist = new String[basic.getPlayers().size()];
		for (int i = 0; i < combolist.length; i++) {
			combolist[i] = basic.getPlayers().get(i).getName();
		}
		JTextField[] values = new JTextField[basic.getPrivates().size()];
		@SuppressWarnings("unchecked")
		JComboBox<Object>[] playerbox = new JComboBox[basic.getPrivates().size()];

		JLabel label = new JLabel("Set the end values of the privates and the bying player");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		panel.add(label, c);

		for (int i = 0; i < basic.getPrivates().size(); i++) { // for every private
			label = new JLabel(basic.getPrivates().get(i).getName());
			c.gridx = 0;
			c.gridy = i + 1;
			c.gridwidth = 1;
			panel.add(label, c);

			values[i] = new JTextField(Integer.toString(basic.getPrivates().get(i).getParValue()), 10);
			c.gridx = 1;
			c.gridy = i + 1;
			c.gridwidth = 1;
			panel.add(values[i], c);
			playerbox[i] = new JComboBox<Object>(combolist);
			c.gridx = 2;
			c.gridy = i + 1;
			c.gridwidth = 1;
			panel.add(playerbox[i], c);
		}

		JButton take = new JButton("Übernehmen");
		c.gridx = 1;
		c.gridy = 19;
		c.gridwidth = 2;
		c.insets = new Insets(10, 0, 0, 0); // top padding
		take.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < basic.getPrivates().size(); i++) { // for every private
					basic.getPlayers().get(playerbox[i].getSelectedIndex())
							.decreaseMoney(Integer.valueOf(values[i].getText())); // decrease Money of the player
					basic.getPlayers().get(playerbox[i].getSelectedIndex()).getPrivates()
							.add(basic.getPrivates().get(i)); // the player get the P-Certificate
					basic.getPrivates().get(i).setOwner(playerbox[i].getSelectedIndex()); // the P-Certificate get
																							// player as owner
					basic.getPrivates().get(i).BuyPrivate(basic, playerbox[i].getSelectedIndex()); // the P-Special
																									// Function is
																									// called
				}
				basic.getGameplay().setCurrentPlayer(
						(playerbox[basic.getPrivates().size() - 1].getSelectedIndex() + 1) % basic.getPlayers().size());

				basic.buildGraphics();
				basic.getTP().setSelectedIndex(0);
			}
		});

		panel.add(take, c);

		return (panel);
	}

	private static JPanel setPhase2(Basic basic, JPanel panel, GridBagConstraints c) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOverview.setPhase2");
		}

		JLabel label = new JLabel("Phase 2 rounds - Stockmarket Round - 1 Operation Round");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		panel.add(label, c);

		if (basic.getGameplay().getPassNumber() < basic.getPlayers().size()) {// if not all players have passed in a row
																				// in stockmarket round...
			JButton stockmarketround = new JButton("Stockmarket Round");
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.insets = new Insets(10, 0, 0, 0);
			stockmarketround.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					basic.switchToStockMarketRound();
				}
			});
			panel.add(stockmarketround, c);
		} else {
			JButton operatinground = new JButton("Operating Round");
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.insets = new Insets(10, 0, 0, 0);
			operatinground.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					basic.switchToOperationRound();
					;
				}
			});
			panel.add(operatinground, c);
		}

		return (panel);
	}

}
