package material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Gameplay implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private int MaxMoney;
	@XmlElement
	private int MaxCertificates;
	@XmlElement
	private int CurrentPlayer;
	@XmlElement
	private int PassNumber;
	@XmlElementWrapper(name = "Trains") 
	@XmlElement
	private List<Train> Trains = new ArrayList<Train>();
	@XmlElement
	private Train LastTrain = new Train(0, 0, 0);
	@XmlElement
	private boolean StockmarketRound;
	@XmlElement
	private int StockmarketRoundCounter;
	@XmlElement
	private boolean OperationRound;
	@XmlElement
	private int OperationroundCounter;
	@XmlElementWrapper(name = "OperationroundCorpOrder") 
	@XmlElement
	private List<Corporation> OperationroundCorpOrder = new ArrayList<Corporation>();
	@XmlElement
	private String PrivatesDone = "";

	public Gameplay() {
	}

	public Gameplay(int MaxMoney, int MaxCertificates, int[] TrainDistancesPrim, int[] TrainQuantities,
			int[] TrainCosts, int[] TrainRusts) {
		if (startmethods.Play1830.verbose) {
			System.out.println("Gameplay.Gameplay");
		}
		this.MaxMoney = MaxMoney;
		this.MaxCertificates = MaxCertificates;
		this.CurrentPlayer = 0;
		this.PassNumber = 0;
		this.StockmarketRoundCounter = 0;
		this.StockmarketRound = true;
		this.OperationRound = false;
		this.OperationroundCounter = 1;
		for (int i = 0; i < TrainDistancesPrim.length; i++) {
			for (int j = 0; j < TrainQuantities[i]; j++) {
				this.Trains.add(new Train(TrainDistancesPrim[i], TrainCosts[i], TrainRusts[i]));
			}
		}
	}

	public int getPhase() {
		int Phase = 0;

		if (getTrains().get(0).getDistancePrimary() >= 2) {
			Phase = 2;
		}
		if (getLastTrain().getDistancePrimary() >= 3) {
			Phase = 3;
		}
		if (getLastTrain().getDistancePrimary() >= 4) {
			Phase = 4;
		}
		if (getLastTrain().getDistancePrimary() >= 5) {
			Phase = 5;
		}
		if (getLastTrain().getDistancePrimary() >= 6) {
			Phase = 6;
		}
		if (getLastTrain().getDistancePrimary() >= 7) {
			Phase = 7;
		}

		return Phase;
	}

	public int getMaxOperationrounds() {
		int MaxOperationrounds = 0;
		int Phase = getPhase();
		if (Phase >= 2) {
			MaxOperationrounds = 1;
		}
		if (Phase >= 3) {
			MaxOperationrounds = 2;
		}
		if (Phase >= 5) {
			MaxOperationrounds = 3;
		}
		return MaxOperationrounds;
	}

	public int getMaxMoney() {
		return MaxMoney;
	}

	public void setMaxMoney(int maxMoney) {
		MaxMoney = maxMoney;
	}

	public int getMaxCertificates() {
		return MaxCertificates;
	}

	public void setMaxCertificates(int maxCertificates) {
		MaxCertificates = maxCertificates;
	}

	public int getCurrentPlayer() {
		return CurrentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		CurrentPlayer = currentPlayer;
	}

	public int getPassNumber() {
		return PassNumber;
	}

	public void setPassNumber(int passNumber) {
		PassNumber = passNumber;
	}

	public List<Train> getTrains() {
		return Trains;
	}

	public void setTrains(List<Train> trains) {
		Trains = trains;
	}

	public Train getLastTrain() {
		return LastTrain;
	}

	public void setLastTrain(Train lastTrain) {
		LastTrain = lastTrain;
	}

	public boolean isStockmarketRound() {
		return StockmarketRound;
	}

	public void setStockmarketRound(boolean stockmarketRound) {
		StockmarketRound = stockmarketRound;
	}

	public int getStockmarketRoundCounter() {
		return StockmarketRoundCounter;
	}

	public void setStockmarketRoundCounter(int stockmarketRoundCounter) {
		StockmarketRoundCounter = stockmarketRoundCounter;
	}

	public boolean isOperationRound() {
		return OperationRound;
	}

	public void setOperationRound(boolean operationRound) {
		OperationRound = operationRound;
	}

	public int getOperationroundCounter() {
		return OperationroundCounter;
	}

	public void setOperationroundCounter(int operationroundCounter) {
		OperationroundCounter = operationroundCounter;
	}

	public List<Corporation> getOperationroundCorpOrder() {
		return OperationroundCorpOrder;
	}

	public void setOperationroundCorpOrder(List<Corporation> operationroundCorpOrder) {
		OperationroundCorpOrder = operationroundCorpOrder;
	}
	
	public void refreshOperationroundCorpOrder(Basic basic) {
		List<Corporation> oco = new ArrayList<Corporation>();
		for(int i=0; i<this.OperationroundCorpOrder.size(); i++) {
			for(Corporation corp : basic.getCorporations()) {
				if(this.OperationroundCorpOrder.get(i).getName().equals(corp.getName())) {
					oco.add(corp);
				}
			}
		}
		this.OperationroundCorpOrder = oco;
	}

	public String getPrivatesDone() {
		return PrivatesDone;
	}

	public void setPrivatesDone(String privatesDone) {
		PrivatesDone = privatesDone;
	}
}
