package boardview;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Position;

/**
 * View class for a tile on a chess board
 * A tile should be able to display a chess piece
 * as well as highlight itself during the game.
 *
 * @author <Yourname here>
 */
public class TileView implements Tile {

	private Position position;
	private StackPane root;
	private String symbol;
	private Color color;

    /**
     * Creates a TileView with a specified position
     * @param p
     */
    public TileView(Position p) {
    	position = p;
    	//Determine background color based on location
    	if((p.getRow() + p.getCol()) % 2 == 0) {
    		color = Color.WHITE;
    	} else {
    		color = Color.GREY;
    	}
    
    	root = new StackPane();
    	root.setCache(false);
    	//Add background
    	root.setMinWidth(100);
    	root.setMinHeight(100);
    	root.setStyle("-fx-background-color: #"+color.toString().substring(2));
    }


    @Override
    public Position getPosition() {
    	//TODO
        return position;
    }


    @Override
    public Node getRootNode() {
    	//TODO
        return root;
    }

    @Override
    public void setSymbol(String symbol) {
        // TODO
    	this.symbol = symbol;
    	if(symbol.equals("")) {
    		//Remove piece
    		root.getChildren().clear();
    	} else {
    		//Add piece
	    	Canvas symbolCanvas = new Canvas(100,100);
	    	GraphicsContext gc = symbolCanvas.getGraphicsContext2D();
	    	gc.setFont(new Font(100));
	    	gc.fillText(getSymbol(), 0, 90);
	    	root.getChildren().add(symbolCanvas);	
    	}	
    }


    @Override
    public String getSymbol() {
        // TODO
        return symbol;
    }

    @Override
    public void highlight(Color highlightColor) {
        // TODO
    	root.setStyle("-fx-background-color: #"+highlightColor.toString().substring(2));
    }

    @Override
    public void clear() {
        // TODO
    	root.setStyle("-fx-background-color: #"+color.toString().substring(2));
    }
}
