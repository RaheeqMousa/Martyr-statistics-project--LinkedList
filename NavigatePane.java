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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NavigatePane {
	
	private BorderPane n ;
	private TextArea ta=new TextArea();
	private Button next;
	private Button previous;
	private Label district_name=new Label();

	private DNode moving=MainStage.dlist.getFront();
	private DNode front_of_districts=MainStage.dlist.getFront();
	private DNode back_of_districts=MainStage.dlist.getLast();
	
	public NavigatePane() {
		
		 n =new BorderPane();
		 try{
			 district_name.setFont(new Font(15));
			 if(moving!=null && moving.getNext()!=null ) {
				 moving.getLocations().calculate();
				 district_name =new Label((String)moving.getData());
				 ta=new TextArea("\nFemale Martyrs = "+moving.getLocations().getFemale_count()+"\n Male Martyrs = "+moving.getLocations().getMale_count()+
						 "\nTotal Martyrs = "+(moving.getLocations().getToatl())+				
							"\nAvergae of martyrs ages = "+moving.getLocations().getAgeAverage()+
							"\n date has the largest date= "+moving.getLocations().getDate_has_max_martyrs());
			 }
			
		 } catch(NullPointerException exc) {
			 System.out.println("no districts to print their data"); }
		 
		 next=new Button("next district");
		  previous=new Button("previous district");
	}
	
	public BorderPane getPane(){

		next.setAlignment(Pos.CENTER_RIGHT);
		next.setPrefSize(100, 20);
		
		previous.setAlignment(Pos.CENTER_RIGHT);
		previous.setPrefSize(100, 20);
		
		HBox hb=new HBox(10);
		hb.getChildren().addAll(previous,next);
		hb.setAlignment(Pos.CENTER);
		
		
		n.setCenter(ta);
		n.setBottom(hb);
		n.setTop(district_name);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		
		next.setOnAction(e->{
			district_name.setFont(new Font(15));
			if(moving!=null && moving.getNext()!=front_of_districts) {
				moving=moving.getNext();
				moving.getLocations().calculate();
				district_name.setText( ((String)moving.getData()));
				ta.setText("\nFemale Martyrs = "+moving.getLocations().getFemale_count()+"\n Male Martyrs = "+moving.getLocations().getMale_count()+
						 "\nTotal Martyrs = "+moving.getLocations().getToatl()+				
							"\nAvergae of martyrs ages = "+moving.getLocations().getAgeAverage()+
							"\n date has the largest date= "+moving.getLocations().getDate_has_max_martyrs());
				
				
				
			}
		});
		
		previous.setOnAction(e->{
			district_name.setFont(new Font(15));
			if(moving!=null && moving.getPrevious()!=back_of_districts ) {
				moving=moving.getPrevious();
				moving.getLocations().calculate();
				district_name.setText( ((String)moving.getData()));
				ta.setText("\nFemale Martyrs = "+moving.getLocations().getFemale_count()+"\n Male Martyrs = "+moving.getLocations().getMale_count()+
						 "\nTotal Martyrs = "+moving.getLocations().getToatl()+				
							"\nAvergae of martyrs ages = "+moving.getLocations().getAgeAverage()+
							"\n date has the largest date= "+moving.getLocations().getDate_has_max_martyrs());			
			
				 
				
			}
		});
		
		
		return n ;
	}
	
}
