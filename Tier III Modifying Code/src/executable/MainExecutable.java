package executable;

import javax.swing.JFrame;

import stockMarkets.StockMarket;
import stockMarketVisualizer.StockMarketGraph;

public class MainExecutable {

	public static void main(String[] args) {
		JFrame MarketWindow = new JFrame();
		MarketWindow.setSize(1000, 600);
		MarketWindow.setVisible(true);
		MarketWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		StockMarketGraph graph1 = new StockMarketGraph();
		
		MarketWindow.add(graph1);
		StockMarket sM1 = new StockMarket(200);
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sM1.calculateNextDay();
			graph1.paintGraph(sM1.getStockValues());
		}
	}

}
