package application;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UpdateDistrict {
	
	private BorderPane n ;
	private TextField tf;
	private Button b;
	private Label response;
	private String name;
	
	public UpdateDistrict() {
		 n =new BorderPane();
		 tf=new TextField("Enter the name of that district you want to update");
		 response =new Label();
		 b=new Button("update district");
	}
	
	public BorderPane getPane(){

		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(90, 20);
		
		tf.setPrefHeight(40);
		
		n.setCenter(tf);
		n.setBottom(b);
		n.setTop(response);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		b.setOnAction(e->{
			response.setFont(new Font(15));
			if(tf.getText().isEmpty()) {
				response.setText("ENTER SOMETHING INSIDE TEXT FIELD");
			}
			else if(MainStage.dlist.contains(tf.getText())) {
				name=tf.getText();
				 b=new Button("finish update");
				 tf.setText("enter the new name of that district");
				 n.setBottom(b);
				 n.setCenter(tf);
				 b.setOnAction(action->{
					 response.setText("");
					 DNode node =MainStage.dlist.getNodeByData(name);  //first i searched for the district that user want to update
	 				 
					 DNode node_with_updated_values=new DNode(tf.getText()); //created a new district node with the new name
					 node_with_updated_values.setLocations(node.getLocations());// i set the new node's locations the node's locations 
					 LNode loc=node_with_updated_values.getLocations().getHead();
					MainStage.dlist.deleteByData(name); //dlete the district node that have the previous name
					
					MainStage.addSortlyDistrict(tf.getText());  // add the new node that have the new name
					
					while(loc!=null) {	
						if(loc!=null) {
						MainStage.addSortlyLocation((String)loc.getData(), tf.getText());  
						}
						Node mar=loc.getMartyrs().getHead();
						while(mar!=null) {					
							((Martyr)mar.getData()).setDistrict(tf.getText()); //change the district name for all the martyrs in the node, because we updated the district node
							MainStage.addSortlyMartyrs((Martyr)mar.getData(),(String)loc.getData(),tf.getText());
						
							mar=mar.getNext();
						}

						loc=loc.getNext();
					}					
					
					MainStage.dlist.print();
					tf.setText("Enter the name of that district you want to update");
				 });
				 
			}
			else {				
				response.setText("DISTRICT DOES NOT EXIST");
			}
			
		});
		
		
		return n ;
	}
	
}
