package stockMarketVisualizer;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class StockMarketGraph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5239174224008171383L;
	
	private Double[] Graph =null;
	public void paintGraph(Double[] Graph){
		this.Graph = Graph;
		this.repaint();
	}
	public StockMarketGraph (){
		Graph =null;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		paintGraph(g);
	}
	
	public void paintGraph(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		int width = this.getWidth();
		int height = this.getHeight();
		g2d.setStroke(new BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!(this.Graph == null) && this.Graph.length > 1){
			for(int i = 0; i< Graph.length -1; i++){
				g2d.drawLine((width - (i*10)), height - Graph[Graph.length - i - 1].intValue(), (width - (i*10)-10), height - Graph[Graph.length -i -2].intValue());
			}
		}
	}

}
