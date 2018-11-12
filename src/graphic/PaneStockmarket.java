package graphic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import material.Basic;
import material.Corporation;

public class PaneStockmarket {
	private final static int tabpos = 0;

	public static void setPaneStockmarket(Basic basic) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneStockmarket.setPaneStockmarket");
		}

		if (basic.getTP().getTabCount() > tabpos) {
			basic.getTP().removeTabAt(tabpos);
		}

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.01;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = basic.getCorporations().size() + 1;
		panel.add(new GraphicStockmarket(basic), c);

		if (basic.getGameplay().getPassNumber() < basic.getPlayers().size()) { // if not all players have passed in a
																				// row the buy/sell buttons are shown...
			JButton[] corpbuy = new JButton[basic.getCorporations().size()];
			JButton[] corpsell = new JButton[basic.getCorporations().size()];

			c.gridx = 1;
			c.gridy = 1;
			c.gridheight = 1;
			c.gridwidth = 2;
			panel.add(new GraphicPlayer(basic.getPlayers().get(basic.getGameplay().getCurrentPlayer())), c);

			// set buttons for stock rounds
			c.insets = new Insets(basic.getStockmarket().getRows() * 50, 0, 0, 0); // padding for the first button ->
																					// all buttons move to the button

			// PASS Button
			JButton pass = new JButton("pass");
			pass.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					basic.getStockmarket().pass(basic);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			if(basic.getGameplay().getStockmarketRoundCounter()==0) {pass.setEnabled(false);}
			panel.add(pass, c);
			c.gridx = 1;
			c.gridy = 1;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.insets = new Insets(0, 0, 0, 0);

			// BUY BUTTONS
			for (int i = 0; i < basic.getCorporations().size(); i++) {
				Corporation corp = basic.getCorporations().get(i);

				int buyoption = basic.getStockmarket().getBuyoption(basic, corp,
						basic.getPlayers().get(basic.getGameplay().getCurrentPlayer()));
				int selloption = basic.getStockmarket().getSelloption(basic, corp,
						basic.getPlayers().get(basic.getGameplay().getCurrentPlayer()));

				corpbuy[i] = new JButton(basic.getCorporations().get(i).getName() + " buy");
				corpbuy[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						graphic.WindowBuy.setWindowBuy(basic, corp,
								basic.getPlayers().get(basic.getGameplay().getCurrentPlayer()), buyoption);
					}
				});
				c.gridx = 1;
				c.gridy = i + 2;
				c.gridheight = 1;
				c.gridwidth = 1;
//				if(buyoption < 50) {panel.add(corpbuy[i], c);} // only buttons with a coded buyoption (value < 50) is shown
				if (buyoption < 50) {
					corpbuy[i].setEnabled(true);
				} else {
					corpbuy[i].setEnabled(false);
				}
				panel.add(corpbuy[i], c);

//					selloption function must be set
				c.gridx = 2;
				c.gridy = i + 2;
				c.gridheight = 1;
				c.gridwidth = 1;
				corpsell[i] = new JButton(basic.getCorporations().get(i).getName() + " sell");
				corpsell[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

					}
				});
				if (selloption < 50) {
					corpsell[i].setEnabled(true);
				} else {
					corpsell[i].setEnabled(false);
				}
				panel.add(corpsell[i], c);

			}
		} else {
			c.gridx = 1;
			c.gridy = 1;
			c.gridheight = 1;
			c.gridwidth = 2;

			JLabel label = new JLabel("All players have passed");
			panel.add(label, c);

			// set buttons for stock rounds
			c.insets = new Insets(basic.getStockmarket().getRows() * 50, 0, 0, 0); // padding for the first button ->
																					// all buttons move to the button

			JButton operatinground = new JButton("change to operating round");
			operatinground.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					basic.switchToOperationRound();
				}
			});
			panel.add(operatinground, c);
		}
		
		if(basic.getGameplay().getStockmarketRoundCounter()==0) {
			GraphicPrivates privates = new GraphicPrivates(basic);
			c.gridx = 1;
			c.gridy = 1;
			c.gridheight = 10;
			c.gridwidth = 2;
			
			
			
			panel.add(privates,c);
		}

		ImageIcon newicon = new ImageIcon(); // dummy ImageIcon maybe set in later versions
		basic.getTP().insertTab("Stock Market", newicon, panel, "Stock Market", tabpos);
	}
}
