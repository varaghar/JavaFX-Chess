package boardview;

import gamecontrol.AIChessController;
import gamecontrol.ChessController;
import gamecontrol.GameController;
import gamecontrol.NetworkedChessController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Main class for the chess application
 * Sets up the top level of the GUI
 * @author Gustavo
 * @version
 */
public class ChessFX extends Application {

    private GameController controller = new ChessController();
    private BoardView board;
    private Text state;
    private Text sideStatus;
    private VBox root;

    @Override
    public void start(Stage primaryStage) {
        // TODO: set up the main stage with a BoardView etc..
    	board = new BoardView(controller, state, new Text());
    	primaryStage.setTitle("Chess");
    	root = new VBox();
    	root.getChildren().add(board.getView());
    	
        Button reset = new Button("Reset");
    	Button playComputer = new Button("Play Computer");
    	Button host = new Button("Host");
    	TextField textField = new TextField();
    	Label ip = new Label();
		try {
			ip = new Label(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
		}
    	Button join = new Button("Join");
    	
    	//Button actions
    	reset.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	controller = new ChessController();
    	    	start(primaryStage);
    	    }
    	});
    	
    	playComputer.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	controller = new AIChessController();
    	    	start(primaryStage);
    	    }
    	});
    	
    	host.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	controller = new NetworkedChessController();
    	    	start(primaryStage);
    	    	
    	    }
    	});
    	
    	join.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	try {
					controller = new NetworkedChessController(InetAddress.getByName(textField.getText()));
				} catch (UnknownHostException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Incorrect address");
					alert.setHeaderText(null);
					alert.setContentText("Try another address!");

					alert.showAndWait();
				}
    	    	start(primaryStage);
    	    }
    	});
    	
    	
    	HBox but1 = new HBox();
    	but1.setPadding(new Insets(15));
    	but1.setSpacing(10);
    	but1.getChildren().add(playComputer);
    	but1.getChildren().add(reset);
    	root.getChildren().add(but1);
    	
    	board.stateLabel = new Label(controller.getCurrentState().toString());
    	board.turnLabel = new Label(controller.getCurrentSide() + "'s Turn");
    	
    	HBox but0 = new HBox();
    	but0.setSpacing(10);
    	but0.setPadding(new Insets(10));
    	but0.getChildren().add(board.turnLabel);
    	but0.getChildren().add(board.stateLabel);
    	root.getChildren().add(but0);

    	HBox but2 = new HBox();
    	but2.setSpacing(10);
    	but2.getChildren().add(ip);
    	but2.getChildren().add(textField);
    	but2.getChildren().add(host);
    	but2.getChildren().add(join);
    	root.getChildren().add(but2);

        primaryStage.setScene(new Scene(root, 815, 970));
        primaryStage.show();
    }

    private EventHandler<? super MouseEvent> makeHostListener() {
        return event -> {
            board.reset(new NetworkedChessController());
        };
    }

    private EventHandler<? super MouseEvent> makeJoinListener(TextField input) {
        return event -> {
            try {
                InetAddress addr = InetAddress.getByName(input.getText());
                GameController newController
                    = new NetworkedChessController(addr);
                board.reset(newController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


    public static void main(String[] args) throws FileNotFoundException {
    		launch(args);
    }
    
}
