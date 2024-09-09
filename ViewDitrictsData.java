package application;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.*;

import javafx.collections.*;
public class ViewDitrictsData {
	
	private  TableView<Martyr> s=new TableView<>();
	private  ObservableList<Martyr> array= FXCollections.observableArrayList();
	
	public Pane getPane() {
		
		s.setItems(array);
		
		
		TableColumn name=new TableColumn("martyr name");
		name.setMinWidth(50);
		name.setCellValueFactory(
				new PropertyValueFactory<Martyr,String>("name")
				);
		
		TableColumn age=new TableColumn("martyr age");
		age.setMinWidth(50);
		age.setCellValueFactory(
				new PropertyValueFactory<Martyr,String>("age")
				);
		
		TableColumn gender=new TableColumn("gender");
		gender.setMinWidth(50);
		gender.setCellValueFactory(
				new PropertyValueFactory<Martyr,String>("gender")
				);
		
		TableColumn date=new TableColumn("date of death");
		date.setMinWidth(50);
		date.setCellValueFactory(
				new PropertyValueFactory<Martyr,String>("dateOfDeath")
				);
		
		TableColumn Location=new TableColumn("location name");
		Location.setMinWidth(50);
		Location.setCellValueFactory(
				new PropertyValueFactory<Martyr,String>("location")
				);
		
		TableColumn district=new TableColumn("ditrict name");
		district.setMinWidth(50);
		district.setCellValueFactory(
				new PropertyValueFactory<Martyr,String>("District")
				);
		
		read();
		
		s.getColumns().clear();
		//(String name, byte age, String location, String date_of_death, char gender,String dis)
		s.getColumns().addAll(name,age,date,gender,Location,district);
		
		VBox vb=new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(s);
		return vb;
	}	
	
	public void read() {
		DNode d=MainStage.dlist.getFront();
		array.clear();
		
		if(d!=null && d.getNext()==MainStage.dlist.getFront()) {  //just one district in the whole system
			
			LNode l=d.getLocations().getHead();
			
			if(l==null) { //district does not have locations
				Martyr r =new Martyr("NA",(byte)0,'F',"1/1/1","NA",(String)d.getData());
				array.add(r);
			}
			
			while(l!=null) {						
					if(l.getMartyrs().getHead()!=null) {//if there	are locations with martyrs
						Node m=l.getMartyrs().getHead();
						while(m!=null) {
							array.add( ((Martyr)m.getData()) );
							m=m.getNext();
						}
					}
					else if(l.getMartyrs().getHead()==null) {  //if there	are locations without martyrs (this case is logic...)	
						Martyr r =new Martyr("NA",(byte)0,'F',"1/1/1",(String)l.getData(),(String)d.getData());
						array.add(r);
					}
				l=l.getNext();
			}
			
		}
		else if(d!=null) { 
			
			for(int i=0;i<MainStage.dlist.getSize();i++) {	
				LNode l=d.getLocations().getHead();
				
				if(l==null) { //district does not have locations
					Martyr r =new Martyr("NA",(byte)0,'F',"1/1/1","NA",(String)d.getData());
					array.add(r);
				}
				
				while(l!=null) {						
						if(l.getMartyrs().getHead()!=null) {//if there	are locations with martyrs
							Node m=l.getMartyrs().getHead();
							while(m!=null) {
								array.add( ((Martyr)m.getData()) );
								m=m.getNext();
							}
						}
						else if(l.getMartyrs().getHead()==null) {  //if there	are locations without martyrs (this case is logic...)	
							Martyr r =new Martyr("NA",(byte)0,'F',"1/1/1",(String)l.getData(),(String)d.getData());
							array.add(r);
						}
					l=l.getNext();
				}

				d=d.getNext();
			}
			
			
		}
		else if(MainStage.dlist.getSize()==0) {
			array.add(null);
		}

	
		
	}
	

}
