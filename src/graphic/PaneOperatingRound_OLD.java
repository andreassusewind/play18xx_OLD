package graphic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import material.Basic;
import material.BasicSave;
import material.Corporation;
import material.Player;
import material.Private;
import material.Station;
import material.Train;
import xml.XMLBasicSave;

public class PaneOperatingRound_OLD extends JPanel {

	private final static int tabpos = 1;
	
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();

//	String privdone = "         ";
//	String stationdone = "         ";
//	String tiledone = "         ";
//	String divdone = "         ";
	String divincome = "50";
//	String traindone = "         ";
//	String buyprivdone = "         ";
	int trainbuyindex = 0;
//	String[] corpdone = { "", "", "", "", "", "", "", "", "", "" };
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPaneOperatingRound(Basic basic) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPaneOperatingRound");
		}

		if (basic.getTP().getTabCount() > tabpos) {
			basic.getTP().removeTabAt(tabpos);
		}

		JPanel panel = new JPanel(new GridLayout(1, 3));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel buttons = new JPanel(new GridBagLayout());
		
		
		if(basic.getGameplay().isOperationRound()) {
			JLabel label = new JLabel("Operation Round Number " + basic.getGameplay().getOperationroundCounter() + " of "
					+ basic.getGameplay().getMaxOperationrounds());
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.PAGE_START;
			panel1.add(label, c);

			if (basic.getGameplay().getPhase() < 5) {
				JButton privates = new JButton("Privates revenue");
				c.weighty = 0.1;
				c.gridx = 0;
				c.gridy = 1;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				privates.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setPanel2Private(basic);
						basic.buildGraphics();
						basic.getTP().setSelectedIndex(tabpos);
					}
				});
				buttons.add(privates, c);
				System.out.println(basic.getGameplay().getPrivatesDone());
				label = new JLabel(basic.getGameplay().getPrivatesDone());
				c.gridx = 2;
				c.gridy = 1;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				buttons.add(label, c);
			}

			int COUNTER = 2;
			for (Corporation corp : basic.getGameplay().getOperationroundCorpOrder()) {
				JButton privates = new JButton(corp.getName());
				c.weighty = 0.1;
				c.gridx = 0;
				c.gridy = COUNTER;
				c.gridwidth = 2;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				privates.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setPanel2Corp(basic, corp);
						basic.buildGraphics();
						basic.getTP().setSelectedIndex(tabpos);
					}
				});
				buttons.add(privates, c);
				label = new JLabel(corp.getOpRoundDone());
				c.weighty = 0.1;
				c.gridx = 2;
				c.gridy = COUNTER;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				buttons.add(label, c);
				COUNTER = COUNTER + 1;
			}

			JButton done = new JButton("Operation Round Ends");
			c.weighty = 0.1;
			c.gridx = 0;
			c.gridy = COUNTER;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			done.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (basic.getGameplay().getOperationroundCounter() < basic.getGameplay().getMaxOperationrounds()) {
						basic.getGameplay().setOperationroundCounter(basic.getGameplay().getOperationroundCounter() + 1);
						refreshAll();
						basic.buildGraphics();
						basic.getTP().setSelectedIndex(tabpos);
					} else {
						basic.getGameplay().setOperationroundCounter(1);
						basic.getGameplay().setPassNumber(0);
						refreshAll();
						basic.buildGraphics();
						basic.getTP().setSelectedIndex(0);
					}
				}
			});

			buttons.add(done, c);
			
			c.weighty = 1.0;
			c.gridx = 0;
			c.gridy = COUNTER;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(0, 0, 0, 0);
			panel1.add(buttons,c);
		}
		else {
			JLabel label = new JLabel("Stockmarket Round is running");
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.PAGE_START;
			panel1.add(label, c);
			
		}
		
		JButton save = new JButton("Speichern");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
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
		panel1.add(save, c);

		JButton load = new JButton("Laden");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 20;
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicSave savedata = new BasicSave(basic);
				savedata.load(basic);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel1.add(load, c);

		
		panel.add(panel1);
		panel.add(this.panel2);
		panel.add(this.panel3);

		
		

		ImageIcon newicon = new ImageIcon(); // dummy ImageIcon maybe set in later versions
		basic.getTP().insertTab("Operating Round", newicon, panel, "Operating Round", tabpos);
	}

	private void setPanel2Private(Basic basic) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPanel2Private");
		}
		this.panel2 = new JPanel();

		this.panel2.setLayout(new GridBagLayout());
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
				this.panel2.add(label, c);

				label = new JLabel("--> gets " + priv.getRevenue());
				c.gridx = 0;
				c.gridy = COUNTER + 1;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(0, 0, 0, 0);
				this.panel2.add(label, c);

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
				refreshPanel2();
				basic.getGameplay().setPrivatesDone("done");;
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel2.add(privates, c);
	}

	private void setPanel2Corp(Basic basic, Corporation corp) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPanel2Corp");
		}
		this.panel2 = new JPanel();

		this.panel2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel label = new JLabel(corp.getPresident().getName() + " is President for " + corp.getName());
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.panel2.add(label, c);

		JButton tile = new JButton("Tile placement");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		tile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanel3Tile(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel2.add(tile, c);
		label = new JLabel(corp.getTileDone());
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.panel2.add(label, c);

		JButton station = new JButton("Station placement");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		station.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanel3Station(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel2.add(station, c);
		label = new JLabel(corp.getStationDone());
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.panel2.add(label, c);

		JButton dividend = new JButton("Run trains");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		dividend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanel3Dividend(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel2.add(dividend, c);
		label = new JLabel(corp.getDividendDone());
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.panel2.add(label, c);

		JButton train = new JButton("Buy trains");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		train.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanel3Train(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel2.add(train, c);
		label = new JLabel(corp.getTrainDone());
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		this.panel2.add(label, c);

		if (basic.getGameplay().getPhase() >= 3 && basic.getGameplay().getPhase() <= 4) {
			JButton buyprivate = new JButton("Buy Private");
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			buyprivate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel3BuyPrivate(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			panel2.add(buyprivate, c);
			label = new JLabel(corp.getPrivateDone());
			c.gridx = 1;
			c.gridy = 5;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			this.panel2.add(label, c);
		}

		JButton corpdone = new JButton("Corporation finished");
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		corpdone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				corp.setOpRoundDone("done");
				refreshPanel2();
				refreshPanel3();
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel2.add(corpdone, c);
	}

	private void setPanel3Tile(Basic basic, Corporation corp) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPanel3Tile");
		}
		this.panel3 = new JPanel();
		this.panel3.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton tile80 = new JButton("River Tile - 80");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		tile80.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				corp.decreaseMoney(80);
				refreshPanel3();
				corp.setTileDone("done");;
				setPanel2Corp(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel3.add(tile80, c);

		JButton tile120 = new JButton("Mountain Tile - 120");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		tile120.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				corp.decreaseMoney(120);
				refreshPanel3();
				corp.setTileDone("done");
				setPanel2Corp(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel3.add(tile120, c);

	}

	private void setPanel3Station(Basic basic, Corporation corp) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPanel3Station");
		}
		this.panel3 = new JPanel();
		this.panel3.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		int stationcost = 9999;
		Station Stationsave = new Station(0);
		for (Station station : corp.getStations()) {
			if (station.isBuild()) {
			} else {
				stationcost = station.getCost();
				Stationsave = station;
				break;
			}
		}
		Station stationtouse = Stationsave;

		if (stationcost == 9999) {
			JLabel label = new JLabel("No station could be build");
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);
		} else {
			JLabel label = new JLabel("Build station for " + stationcost);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);

			JButton station = new JButton("Build station");
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			station.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					corp.decreaseMoney(stationtouse.getCost());
					stationtouse.setBuild(true);
					refreshPanel3();
					corp.setStationDone("done");;
					setPanel2Corp(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			panel3.add(station, c);
		}
	}

	private void setPanel3Dividend(Basic basic, Corporation corp) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPanel3Dividend");
		}
		this.panel3 = new JPanel();
		this.panel3.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		if (corp.getTrains().size() > 0) {
			JLabel label = new JLabel(corp.getName() + " has " + corp.getTrains().size() + " Train(s)");
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 4;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);

			String trains = "Distances:  ";
			for (Train train : corp.getTrains()) {
				trains = trains + train.getDistancePrimary() + "    ";
			}
			label = new JLabel(trains);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 4;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);

			label = new JLabel("Trains run with an income of: ");
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(40, 0, 0, 0);
			panel3.add(label, c);

			JTextField income = new JTextField(divincome, 5);
			c.gridx = 0;
			c.gridy = 3;
			c.gridwidth = 4;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(income, c);
			DocumentListener incomelistener = new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					divincome = income.getText();
					setPanel3Dividend(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					divincome = income.getText();
					setPanel3Dividend(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					divincome = income.getText();
					setPanel3Dividend(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			};
			income.getDocument().addDocumentListener(incomelistener);

			int[] revenue = corp.getPlayerShares(basic.getPlayers());
			int[] pincome = corp.getPlayerIncome(basic.getPlayers(), Integer.parseInt((String) income.getText()));

			JButton toplayer = new JButton("income to player");
			c.gridx = 0;
			c.gridy = 4;
			c.gridwidth = 4;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			toplayer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (Player player : basic.getPlayers()) {
						player.increaseMoney(pincome[player.getPlayernumber()]);
					}
					corp.getMarker().setRight(basic);
					corp.setDividendDone("done");
					refreshPanel3();
					setPanel2Corp(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			panel3.add(toplayer, c);

			JButton tocorp = new JButton("income to corporation");
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 4;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 40, 0);
			tocorp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					corp.increaseMoney(Integer.parseInt((String) income.getText()));
					corp.getMarker().setLeft(basic);
					corp.setDividendDone("done");
					refreshPanel3();
					setPanel2Corp(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			panel3.add(tocorp, c);

			for (int i = 0; i < revenue.length; i++) {
				label = new JLabel(basic.getPlayers().get(i).getName());
				c.gridx = 0;
				c.gridy = 6 + i;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				panel3.add(label, c);

				label = new JLabel("" + revenue[i] + " %");
				c.gridx = 1;
				c.gridy = 6 + i;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				panel3.add(label, c);

				label = new JLabel(" --> ");
				c.gridx = 2;
				c.gridy = 6 + i;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				panel3.add(label, c);

				label = new JLabel("" + pincome[i]);
				c.gridx = 3;
				c.gridy = 6 + i;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(10, 0, 0, 0);
				panel3.add(label, c);
			}

		} else {
			JLabel label = new JLabel("Corporation has no trains");
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);

			JButton nodiv = new JButton("Trains could not run");
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			nodiv.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					corp.getMarker().setLeft(basic);
					refreshPanel3();
					corp.setDividendDone("done");
					setPanel2Corp(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			panel3.add(nodiv, c);

		}
	}

	private void setPanel3Train(Basic basic, Corporation corp) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPanel3Train");
		}
		this.panel3 = new JPanel();
		this.panel3.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel label = new JLabel("Buy a " + basic.getGameplay().getTrains().get(0).getDistancePrimary()
				+ " train from the stack for " + basic.getGameplay().getTrains().get(0).getCost());
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		panel3.add(label, c);

		JButton stack = new JButton("Buy from stack");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		stack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				corp.decreaseMoney(basic.getGameplay().getTrains().get(0).getCost());
				corp.getTrains().add(basic.getGameplay().getTrains().get(0));
				basic.getGameplay().getTrains().remove(0);
				refreshPanel3();
				corp.setTrainDone("done");
				setPanel2Corp(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel3.add(stack, c);

		label = new JLabel("Buy a train from");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(50, 0, 0, 0);
		panel3.add(label, c);

		String[] corpnames = new String[basic.getCorporations().size()];
		for (Corporation corps : basic.getCorporations()) {
			corpnames[corps.getCorporationNumber(basic)] = corps.getName();
		}
		JComboBox<Object> corpbox = new JComboBox<Object>(corpnames);
		corpbox.setSelectedIndex(trainbuyindex);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		panel3.add(corpbox, c);

		Corporation buycorp = basic.getCorporations().get(corpbox.getSelectedIndex());
		if (buycorp.getTrains().size() == 0) {
			label = new JLabel(buycorp.getName() + " has no train");
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);
			label = new JLabel(" ");
			c.gridx = 0;
			c.gridy = 6;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(84, 0, 0, 0);
			panel3.add(label, c);
		} else {
			label = new JLabel("Select train from " + buycorp.getName());
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);

			String[] trainlist = new String[buycorp.getTrains().size()];
			int traincounter = 0;
			for (Train train : buycorp.getTrains()) {
				trainlist[traincounter] = "" + train.getDistancePrimary();
				traincounter = traincounter + 1;
			}

			JComboBox<Object> trainbox = new JComboBox<Object>(trainlist);
			trainbox.setSelectedIndex(0);
			c.gridx = 0;
			c.gridy = 6;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(trainbox, c);

			label = new JLabel("Value: ");
			JTextField trainvalue = new JTextField("50", 4);
			trainvalue.setText("50");
			c.gridx = 0;
			c.gridy = 7;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(label, c);
			c.gridx = 1;
			c.gridy = 7;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			panel3.add(trainvalue, c);

			JButton corpbuy = new JButton("Buy from " + buycorp.getName());
			c.gridx = 0;
			c.gridy = 8;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 0, 0, 0);
			corpbuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					corp.decreaseMoney(Integer.valueOf(trainvalue.getText()));
					buycorp.increaseMoney(Integer.valueOf(trainvalue.getText()));
					corp.getTrains().add(buycorp.getTrains().get(trainbox.getSelectedIndex()));
					buycorp.getTrains().remove(trainbox.getSelectedIndex());
					refreshPanel3();
					corp.setTrainDone("done");
					setPanel2Corp(basic, corp);
					basic.buildGraphics();
					basic.getTP().setSelectedIndex(tabpos);
				}
			});
			panel3.add(corpbuy, c);
		}
		corpbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTrainbuyindex(corpbox.getSelectedIndex());
				refreshPanel3();
				setPanel3Train(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
	}

	private void setPanel3BuyPrivate(Basic basic, Corporation corp) {
		if (startmethods.Play1830.verbose) {
			System.out.println("PaneOperatingRound.setPanel3BuyPrivate");
		}
		this.panel3 = new JPanel();
		this.panel3.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel label = new JLabel("Buy a Private");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		panel3.add(label, c);

		String[] privlist = new String[basic.getPrivates().size()];
		int privcounter = 0;
		for (Private priv : basic.getPrivates()) {
			privlist[privcounter] = priv.getName();
			privcounter = privcounter + 1;
		}

		JComboBox<Object> privbox = new JComboBox<Object>(privlist);
		privbox.setSelectedIndex(0);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		panel3.add(privbox, c);

		label = new JLabel("Value: ");
		JTextField privvalue = new JTextField("50", 4);
		privvalue.setText("50");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		panel3.add(label, c);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		panel3.add(privvalue, c);

		JButton privbuy = new JButton("Buy Private");
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		privbuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Private buypriv = basic.getPrivates().get(privbox.getSelectedIndex());

				if (buypriv.getOwner() < 10) {
					corp.decreaseMoney(Integer.valueOf(privvalue.getText()));
					basic.getPlayers().get(buypriv.getOwner()).increaseMoney(Integer.valueOf(privvalue.getText()));

					basic.getPlayers().get(buypriv.getOwner()).getPrivates()
							.remove(basic.getPlayers().get(buypriv.getOwner()).findPrivate(buypriv));
					buypriv.setOwner(corp.getCorporationNumber(basic) + 30);
					corp.getPrivates().add(buypriv);
				}
				if (buypriv.getOwner() > 20 && buypriv.getOwner() < 50) {
					corp.decreaseMoney(Integer.valueOf(privvalue.getText()));
					basic.getCorporations().get(buypriv.getOwner() - 30)
							.increaseMoney(Integer.valueOf(privvalue.getText()));

					basic.getCorporations().get(buypriv.getOwner() - 30).getPrivates()
							.remove(basic.getCorporations().get(buypriv.getOwner() - 30).findPrivate(buypriv));
					buypriv.setOwner(corp.getCorporationNumber(basic) + 30);
					corp.getPrivates().add(buypriv);
				}

				refreshPanel3();
				corp.setPrivateDone("done");
				setPanel2Corp(basic, corp);
				basic.buildGraphics();
				basic.getTP().setSelectedIndex(tabpos);
			}
		});
		panel3.add(privbuy, c);
	}

	private void setTrainbuyindex(int index) {
		this.trainbuyindex = index;
	}

	public void refreshAll() {
		refreshPanel2();
		refreshPanel3();
	}

	private void refreshPanel2() {
		this.panel2 = new JPanel();
	}

	private void refreshPanel3() {
		this.panel3 = new JPanel();
	}

}
