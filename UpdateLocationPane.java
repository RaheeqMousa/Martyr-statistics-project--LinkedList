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

public class UpdateLocationPane {
	
	private BorderPane n ;
	private TextField tf;
	private TextField dis;
	private Button b;
	private Label response;
	
	public UpdateLocationPane() {
		 n =new BorderPane();
		 tf=new TextField("Enter the name of that loation");
		 response =new Label();
		 dis=new TextField("Enter District of that Location");
		 b=new Button("Update Location");
	}
	
	public BorderPane getPane(){
		
		VBox v =new VBox(10);
		v.setAlignment(Pos.CENTER);
		v.getChildren().addAll(tf,dis);
		
		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(100, 20);
		
		n.setCenter(v);
		n.setBottom(b);
		n.setTop(response);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		b.setOnAction(e->{
			
			response.setFont(new Font(15));
			if(tf.getText().isEmpty()|| dis.getText().isEmpty()) {
						response.setText("ENTER SOMETHING INSIDE TEXT FIELD");
					}			
			
			else {	
					boolean dis_contains=MainStage.dlist.contains( dis.getText()  );		
					if(dis_contains) {
					
					DNode d=MainStage.dlist.getNodeByData(dis.getText());	
		
					if(d.getLocations().contains(tf.getText())  ) {	
						response.setText("");
						//i will make another location node with the martyr linked list of the previous linked list , and then delete the last one an d add the new one with new data and updated martyrs location attribute
						String last_name=tf.getText();
						
						TextField f=new TextField("Enter the new name of the location");
						Button bt=new Button("update");
						n.setCenter(f);
						n.setBottom(bt);
									
	
						
						bt.setOnAction(action->{
							 LNode last_loc=d.getLocations().getNodeByData(last_name); //i got the location node that has the entered name
							LNode location_updated =new LNode(f.getText());  //create location node with the neww name
							MainStage.addSortlyLocation((String)f.getText(),(String) d.getData());  //added that new location to its district
							
							location_updated.setMartyrs( last_loc.getMartyrs());
							for(int i=0;i< location_updated.getMartyrs().getSize();i++) {
								((Martyr)location_updated.getMartyrs().get(i)).setLocation(f.getText()); //update the location name for the martyr with the new location name
								MainStage.addSortlyMartyrs((Martyr)location_updated.getMartyrs().get(i),f.getText(),(String)d.getData());
							}
							
							
						
							d.getLocations().deleteLocationByData(last_loc.getData());//dlete the last location node (that has the previous name)
							
							MainStage.dlist.print();
						
							response.setText("UPDATED SUCCESSFULLY");
							
							tf.setText("Enter the name of that loaction");  //to reset the text field to the original 
							dis.setText("Enter the district of that loaction");
							n.setCenter(v);
							n.setBottom(b);						
						});
						
								
						}
					else {
						response.setText("LOCATION DOES NOT EXIST");
					}
				}	
					else {
						response.setText("THIS DISTRICT DOES NOT EXIST IN THE SYSTEM");
					}
			}

			
		});
		
		
		return n ;
	}
	
}
