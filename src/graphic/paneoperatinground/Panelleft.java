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
import material.BasicSave;
import material.Corporation;
import xml.XMLBasicSave;

public class Panelleft extends JPanel{
	private final static int tabpos = 1;

	JPanel panelleftmiddel = new JPanel(new GridBagLayout());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPanel(Basic basic) {
		if (startmethods.Play1830.verbose) {
			System.out.println("paneoperatinground.Panelleft.setPanel");
		}

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		if(basic.getGameplay().isOperationRound()) {
			JLabel label = new JLabel("Operation Round Number " + basic.getGameplay().getOperationroundCounter() + " of "
					+ basic.getGameplay().getMaxOperationrounds());
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.PAGE_START;
			c.insets = new Insets(20, 0, 0, 0);
			this.add(label,c);
			
			setPanelleftmiddel(basic);
			c.weighty = 1.0;
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			this.add(panelleftmiddel, c);
			
		}
		else {
			JLabel label = new JLabel("Stockmarket Round is running....");
			c.weighty = 1.0;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.PAGE_START;
			c.insets = new Insets(20, 0, 0, 0);
			this.add(label,c);
		}
		// Bottom of LEFTPANEL -> Speichern
		JButton save = new JButton("Speichern");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(0, 0, 0, 0);
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
		this.add(save, c);
		// Bottom of LEFTPANEL -> LADEN
		JButton load = new JButton("Laden");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(0, 0, 20, 0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 21;
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicSave savedata = new BasicSave(basic);
				savedata.load(basic);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		this.add(load, c);

	}
	
	public void setPanelleftmiddel(Basic basic) {
		GridBagConstraints c = new GridBagConstraints();
		
		if (basic.getGameplay().getPhase() < 5) {
			JButton privates = new JButton("Privates");
			c.weighty = 0.1;
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
			c.insets = new Insets(10, 0, 0, 0);
			privates.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					basic.getTP().getPOR().getPanemiddel().removeAll();
					basic.getTP().getPOR().getPanemiddel().setPanelPrivate(basic);
					graphic.PaneOperatingRound.setPaneOperatingRound(basic);
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			if(basic.getGameplay().getPrivatesDone().equals("done")) {privates.setEnabled(false);}
			panelleftmiddel.add(privates, c);
			/*			JLabel label = new JLabel(basic.getGameplay().getPrivatesDone());
			c.gridx = 2;
			c.gridy = 1;
			c.gridwidth = 1;
			c.insets = new Insets(10, 0, 0, 0);
			panelleftmiddel.add(label, c);*/
		}
		
		
		//Corporation Buttons
		JButton corpbutton = new JButton();
		int COUNTER = 2;
		for (Corporation corp : basic.getGameplay().getOperationroundCorpOrder()) {
			c.weighty = 0.1;
			c.gridx = 0;
			c.gridy = COUNTER;
			c.gridwidth = 2;
			c.insets = new Insets(10, 0, 0, 0);
			corpbutton = new JButton(corp.getName());
			corpbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					basic.getTP().getPOR().getPanemiddel().removeAll();
					basic.getTP().getPOR().getPanemiddel().setPanelCorporation(basic, corp);
					basic.getTP().getPOR().getPanelright().removeAll();
					graphic.PaneOperatingRound.setPaneOperatingRound(basic);
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			if(corp.getOpRoundDone().equals("done")) { corpbutton.setEnabled(false); }
			panelleftmiddel.add(corpbutton, c);
/*			JLabel label = new JLabel(corp.getOpRoundDone());
			c.weighty = 0.1;
			c.gridx = 2;
			c.gridy = COUNTER;
			c.gridwidth = 1;
			c.insets = new Insets(10, 0, 0, 0);
			panelleftmiddel.add(label, c);*/
			COUNTER = COUNTER + 1;
		}
				
		JButton done = new JButton("Operation Round Ends");
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = COUNTER;
		c.gridwidth = 2;
		c.insets = new Insets(10, 0, 0, 0);
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (basic.getGameplay().getOperationroundCounter() < basic.getGameplay().getMaxOperationrounds()) {
//					basic.getGameplay().setOperationroundCounter(basic.getGameplay().getOperationroundCounter() + 1);
//					refreshAll();
//					basic.buildGraphics();
//					basic.getTP().setSelectedIndex(tabpos);
				} else {
//					basic.getGameplay().setOperationroundCounter(1);
//					basic.getGameplay().setPassNumber(0);
//					refreshAll();
//					basic.buildGraphics();
//					basic.getTP().setSelectedIndex(0);
				}
			}
		});
		panelleftmiddel.add(done,c);
	}
	
	public JPanel getPanelleftmiddel() {
		return panelleftmiddel;
	}
}
