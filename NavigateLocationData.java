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

public class NavigateLocationData {
	
	private BorderPane n ;
	private TextArea ta=new TextArea();
	private Button next;
	private Button previous;
	private Label location_name=new Label();
	boolean again=false;
	private DNode moving=MainStage.dlist.getFront();
	private LNode l;
	
	public NavigateLocationData() {
		
		 n =new BorderPane();

			 if(moving!=null && moving.getNext()!=null ) {
				 l=moving.getLocations().getHead();

				 setResponse(l);
			 }
		 
		 next=new Button("next location");
		  previous=new Button("previous district");
	}
	
	public BorderPane getPane(){

		next.setAlignment(Pos.CENTER_RIGHT);
		next.setPrefSize(100, 20);
		

		
		HBox hb=new HBox(10);
		hb.getChildren().addAll(next);
		hb.setAlignment(Pos.CENTER);
		
		
		n.setCenter(ta);
		n.setBottom(hb);
		n.setTop(location_name);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		
		
		next.setOnAction(e->{
			location_name.setFont(new Font(15));
			
			
			
			if(moving!=null && l.getNext()!=null ) {
				if(again==false) {  //in order not to skip the first loation when we move to the next district's locations
				l=l.getNext();}
				again=false;
				
				setResponse(l);
				
			}
			else if(moving!=null && l.getNext()==null){ //this means that the traversing in the current district's location is finished , so we will move to the next district
				moving=moving.getNext();
				if(moving!=null) {
					again=true;
					l=moving.getLocations().getHead();
					if(l!=null && l.getNext()==null ) {   //because ther maybe a district which contains only one location , so the next of that location is null
						
						setResponse(l);
					}	
				}
		}
			});
		
		
		return n ;	
		}
	
	
	
	public void setResponse(LNode l) {
		try {
		location_name.setText("Location name: "+(String) l.getData());
		ta.setText("Total Martyrs = "+(l.getMartyrs().getSize())+
				"\n Male Martyrs = "+l.getMartyrs().getMale_counter()+"\nFemale Martyrs = "+l.getMartyrs().getFemale_counter()+
				"\nAvergae of martyrs ages = "+(l.getMartyrs().getAgeSum()/(double)l.getMartyrs().getSize())+
				"\nSmallest martyr= "+((Martyr)l.getMartyrs().getFirst()).getName()+
				"\nLargest martyr= "+((Martyr)l.getMartyrs().getLastElement()).getName());
		}catch(NullPointerException ex) {
			location_name.setText((String) l.getData());
			ta.setText("Total Martyrs = 0"
					+"\n Male Martyrs = 0"+"\nFemale Martyrs = 0"
					+"\nAvergae of martyrs ages=  0"
					+"\nSmallest martyr= no martyrs data"
					+"\nLargest martyr= no martyrs data");
		}
	}
	
}

	
	