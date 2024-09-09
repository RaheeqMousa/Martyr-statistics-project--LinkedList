package application;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrintDistrict {
	
	private BorderPane n ;
	private TextField tf;
	private Button b;
	private Label response;
	
	private TableView<Martyr> s=new TableView<>();
	private ObservableList<Martyr> array= FXCollections.observableArrayList();
	
	public PrintDistrict() {
		 n =new BorderPane();
		 tf=new TextField("Enter the name of that district");
		 response =new Label();
		 b=new Button("search district");
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
			else if(!MainStage.dlist.contains(tf.getText())) {
				response.setText("THAT DISTRICT NAME DOES NOT EXIST");
			}
			else if(MainStage.dlist.contains(tf.getText())) {
				DNode dn=MainStage.dlist.getNodeByData(tf.getText());
				n.setTop(tableDistrict(dn));
				
			}
			
		});
		
		
		return n ;
	}
	
	public VBox tableDistrict(DNode district_node) {
		
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
			
			district_node=MainStage.dlist.getNodeByData(tf.getText());
			read(district_node);
			
			DNode d=district_node;
			
			s.getColumns().clear();
			s.getColumns().addAll(name,age,date,gender,Location,district);
			
			Button print =new Button("print district");
			print.setOnAction(e->{
				printDistrict(d);
			});
			
			VBox vb=new VBox(10);
			vb.getChildren().addAll(s,print);
			vb.setAlignment(Pos.CENTER);
			
			return vb;
		}	
		
		public void read(DNode d) {  //this mehod is to add the data to the obsarvable list...
			array.clear();// every time i read i clear the array
					
					LNode l=d.getLocations().getHead();
					
					//district does not have locations
					if(l==null) {
						Martyr r =new Martyr("NA",(byte)0,'F',"1/1/1","NA",(String)d.getData());
						array.add(r);
					}
					
					//if the district have locations..
					while(l!=null) {							
							if(l.getMartyrs().getHead()!=null) {//if there	are locations with martyrs
								Node m=l.getMartyrs().getHead();
								while(m!=null) {
									array.add( ((Martyr)m.getData()) );
									m=m.getNext();
								}
							}
							else if(l.getMartyrs().getHead()==null) {  //if there are locations without martyrs (this case is logic...)	
								Martyr r =new Martyr("NA",(byte)0,'F',"1/1/1",(String)l.getData(),(String)d.getData());
								array.add(r);
							}
						l=l.getNext();
					}
					
					
		}
		
		public void printDistrict(DNode n ) {
			
			File f=new File((String)n.getData());
			
			if(!f.exists())
				try {
					f.createNewFile();  //create a file that is called with the name of the district
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			try(DataOutputStream s=new DataOutputStream(new FileOutputStream(f))){
				
				if(n.getLocations().checkALLLOcationDoesntHave() && n.getLocations().size()!=0) { //if there does not exist any martyr in the whole district !! 
					String str=n.getData()+" district : NO MARTYRS DATA IN THE LOCATIONS\n";
					byte[] b= str.getBytes();
					s.write(b);
				}
				
				else if(n.getLocations().getHead()!=null) {  //if there exist locations in the district
						LNode l=n.getLocations().getHead();
						while(l!=null) {
							Node m=l.getMartyrs().getHead();
							while(m!=null) {
								String str=m.getData().toString()+"\n";
								byte[] b= str.getBytes();
								s.write(b);
								m=m.getNext();
							}
							l=l.getNext();
						}
					}
					else if(n.getLocations().getHead()==null){  //if there does not exist locations in the district
						String str=n.getData()+" district : NO LOCATIONS AND MARTYRS DATA\n";
						byte[] b= str.getBytes();
						s.write(b);
						
					}					
					
					n.getLocations().calculate();
					String r="\nFemale Martyrs = "+n.getLocations().getFemale_count()+"\n Male Martyrs = "+n.getLocations().getMale_count()+
							 "\nTotal Martyrs = "+n.getLocations().getToatl()+				
								"\nAvergae of martyrs ages = "+n.getLocations().getAgeAverage()+
								"\n date has the largest date= "+n.getLocations().getDate_has_max_martyrs();
					byte[] b= r.getBytes();
					s.write(b);
				
			}catch(IOException ex) {
				System.out.println();
			}
			
		}
	
		
}
