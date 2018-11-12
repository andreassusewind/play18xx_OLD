package material;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasicSave implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Player> Players = new ArrayList<Player>();
	private List<Corporation> Corporations = new ArrayList<Corporation>();
	private List<Private> Privates = new ArrayList<Private>();
	private Gameplay Gameplay;
	private Stockmarket Stockmarket;

	public BasicSave() {
	}

	public BasicSave(Basic basic) {
		this.Players = basic.getPlayers();
		this.Corporations = basic.getCorporations();
		this.Privates = basic.getPrivates();
		this.Gameplay = basic.getGameplay();
		this.Stockmarket = basic.getStockmarket();
	}

	public void save() {
		try {
			// write object to file
			FileOutputStream fos = new FileOutputStream("/home/andreas/eclipse-workspace/play18xx/save.1830");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void load(Basic basic) {
		try {
			// read object from file
			FileInputStream fis = new FileInputStream("/home/andreas/eclipse-workspace/play18xx/save.1830");
			ObjectInputStream ois = new ObjectInputStream(fis);
			BasicSave data = (BasicSave) ois.readObject();
			ois.close();

			basic.setPlayers(data.Players);
			basic.setCorporations(data.Corporations);
			basic.setPrivates(data.Privates);
			basic.setGameplay(data.Gameplay);
			basic.setStockmarket(data.Stockmarket);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Player> getPlayers() {
		return Players;
	}

	public void setPlayers(List<Player> players) {
		Players = players;
	}

	public List<Corporation> getCorporations() {
		return Corporations;
	}

	public void setCorporations(List<Corporation> corporations) {
		Corporations = corporations;
	}

	public List<Private> getPrivates() {
		return Privates;
	}

	public void setPrivates(List<Private> privates) {
		Privates = privates;
	}

	public Gameplay getGameplay() {
		return Gameplay;
	}

	public void setGameplay(Gameplay gameplay) {
		Gameplay = gameplay;
	}

	public Stockmarket getStockmarket() {
		return Stockmarket;
	}

	public void setStockmarket(Stockmarket stockmarket) {
		Stockmarket = stockmarket;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
