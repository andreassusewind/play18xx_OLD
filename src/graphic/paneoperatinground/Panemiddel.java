package graphic.paneoperatinground;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import material.Basic;
import material.Corporation;
import material.Private;

public class Panemiddel extends JPanel{
	private final static int tabpos = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPanel(Basic basic) {
		if (startmethods.Play1830.verbose) {
			System.out.println("paneoperatinground.Panemiddel.setPanel");
		}

		this.setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
	}

	public void setPanelPrivate(Basic basic) {
		if (startmethods.Play1830.verbose) {
			System.out.println("paneoperatinground.Panemiddel.setPanelPrivate");
		}

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		int COUNTER = 0;

		for (Private priv : basic.getPrivates()) {
			if (priv.getOwner() < 90) {
				JLabel label = new JLabel(priv.getOwner(basic) + " owns " + priv.getName());
				c.gridx = 0;
				c.gridy = COUNTER;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(20, 0, 0, 0);
				this.add(label, c);

				label = new JLabel("--> gets " + priv.getRevenue());
				c.gridx = 0;
				c.gridy = COUNTER + 1;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(0, 0, 0, 0);
				this.add(label, c);

				COUNTER = COUNTER + 2;
			}
		}

		JButton privates = new JButton("distribute revenue");
		c.gridx = 0;
		c.gridy = COUNTER;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		privates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Private priv : basic.getPrivates()) {
					priv.distributeRevenue(basic);
				}
				basic.getGameplay().setPrivatesDone("done");
				basic.getTP().getPOR().getPanemiddel().removeAll();
				graphic.PaneOperatingRound.setPaneOperatingRound(basic);
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		this.add(privates, c);
	}
	
	public void setPanelCorporation(Basic basic, Corporation corp) {
		if (startmethods.Play1830.verbose) {
			System.out.println("paneoperatinground.Panemiddel.setPanelCorporation");
		}

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		
		JLabel label = new JLabel(corp.getPresident().getName() + " is President for " + corp.getName());
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(label, c);

		JButton tile = new JButton("Tile placement");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		tile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basic.getTP().getPOR().getPanelright().removeAll();
				basic.getTP().getPOR().getPanelright().setPanelTile(basic, corp);
				graphic.PaneOperatingRound.setPaneOperatingRound(basic);
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		if((corp.getTileDone().equals("done"))) {tile.setEnabled(false);}
		this.add(tile, c);
/*		label = new JLabel(corp.getTileDone());
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(label, c);*/

		JButton station = new JButton("Station placement");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		station.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basic.getTP().getPOR().getPanelright().removeAll();
				basic.getTP().getPOR().getPanelright().setPanelStation(basic, corp);
				graphic.PaneOperatingRound.setPaneOperatingRound(basic);
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		if(corp.getStationDone().equals("done")) {station.setEnabled(false);}
		this.add(station, c);
/*		label = new JLabel(corp.getStationDone());
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(label, c);*/

		JButton dividend = new JButton("Run trains");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		dividend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basic.getTP().getPOR().getPanelright().removeAll();
				basic.getTP().getPOR().getPanelright().setPanelDividend(basic, corp);
				graphic.PaneOperatingRound.setPaneOperatingRound(basic);
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		if(corp.getDividendDone().equals("done")) {dividend.setEnabled(false);}
		this.add(dividend, c);
/*		label = new JLabel(corp.getDividendDone());
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(label, c);*/

		JButton train = new JButton("Buy trains");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		train.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basic.getTP().getPOR().getPanelright().removeAll();
				basic.getTP().getPOR().getPanelright().setPanelTrain(basic, corp);
				graphic.PaneOperatingRound.setPaneOperatingRound(basic);
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		if(corp.getTrainDone().equals("done")) {train.setEnabled(false);}
		this.add(train, c);
/*		label = new JLabel(corp.getTrainDone());
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(label, c);*/

		if (basic.getGameplay().getPhase() >= 3 && basic.getGameplay().getPhase() <= 4) {
			JButton buyprivate = new JButton("Buy Private");
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			buyprivate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					basic.getTP().getPOR().getPanelleft().getPanelleftmiddel().removeAll();
					basic.getTP().getPOR().getPanelright().removeAll();
					basic.getTP().getPOR().getPanelright().setPanelPrivate(basic, corp);
					graphic.PaneOperatingRound.setPaneOperatingRound(basic);
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			this.add(buyprivate, c);
			label = new JLabel(corp.getPrivateDone());
			c.gridx = 1;
			c.gridy = 5;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			this.add(label, c);
		}

		JButton corpdone = new JButton("Corporation finished");
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		corpdone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basic.getTP().getPOR().getPanelleft().getPanelleftmiddel().removeAll();
				basic.getTP().getPOR().getPanelright().removeAll();
				basic.getTP().getPOR().getPanemiddel().removeAll();
				corp.setOpRoundDone("done");
				graphic.PaneOperatingRound.setPaneOperatingRound(basic);
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		this.add(corpdone, c);
		
	}
}
