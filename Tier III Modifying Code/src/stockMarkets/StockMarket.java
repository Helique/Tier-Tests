package stockMarkets;

import java.util.ArrayList;
import java.util.Random;

public class StockMarket {
	ArrayList<Double> history;
	Random randomGenerator = new Random();
	public StockMarket(double initialValue){
		history = new ArrayList<Double>();
		history.add(initialValue);
		
	}
	
	public void calculateNextDay(){
		double lastValue =history.get(history.size()-1);
		
		history.add(lastValue * ((randomGenerator.nextDouble()*30 +85)/100));
		if(history.size()>200){
			history.remove(0);
		}
	}
	public Double[] getStockValues(){
		Double[] stockValues = new Double[1];
		return (history.toArray(stockValues));
	}
	
}
