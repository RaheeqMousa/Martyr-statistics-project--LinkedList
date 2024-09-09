package application;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FunctionsStage extends Application {
	
	private NavigatePane navigate ;
	private ViewDitrictsData viewpane ;
	private PrintDistrict pd;
	private SearchByDatePane searchByDate;
	private UpdateLocationPane updateP;
	
	private NavigateLocationData navigateLocation;
	private InsertMartyrPane addMartyr;
	private DeleteUpdateMartyrPane dp;
 
	
	BorderPane mainPane;
	
	
	Stage stage;
//...................................................................................................
	
	@Override
	public void start(Stage s){
		this.stage=s;
		MenuBar m =new MenuBar();   		
		
		
		m.getMenus().addAll(menuDistrict(),menuLocation(),FileOpenAndSave());
		
		mainPane =new BorderPane();
		mainPane.setTop(m);
		mainPane.setStyle("-fx-background-color:LIGHTBLUE");
		
		 Scene sc =new Scene(mainPane,350,300);
		s.setScene(sc);

		s.setTitle("Palestenian Martyrs Statistics Program");
		s.show();
		
	}
	
//...................................................................................................	
	public Menu FileOpenAndSave() {	
		Menu fileMenu=new Menu("file");
		
		
		MenuItem open=new MenuItem("open");
		open.setOnAction(e->{
			MainStage main=new MainStage();
			mainPane.setCenter(main.getPane());
		});
		
		MenuItem save=new MenuItem("save");
		save.setOnAction(e->{
			try {
				FileChooser fc=new FileChooser();
				File f = fc.showOpenDialog(stage);
				fc.setTitle("choose a file to read from");
				MainStage.writeOnCSVFile(f);
			}catch(NullPointerException ex) {
				System.out.println("No File Choosen");
			}
		});
		
		
		fileMenu.getItems().addAll(open,save);
		
		return fileMenu;
	}
//...................................................................................................
	
	public Menu menuDistrict() {
		
		Menu district =new Menu("district");
		
	
		MenuItem viewDistricts=new MenuItem("View Districts");
		viewDistricts.setOnAction(e->{
			viewpane =new ViewDitrictsData();
			mainPane.setCenter(viewpane.getPane());
		});
		
		
		MenuItem addDistrict=new MenuItem("Add District");	
		addDistrict.setOnAction(e->{
			mainPane.setCenter(getPaneAddDistrict());
		});
				
		
		Menu deletedis=new Menu("delete district");	
		deletedis.setOnAction(e->{
			mainPane.setCenter(getPaneDeleteDistrict());
		});
		
		
		Menu navigateDistricts=new Menu("navigate");	
		navigateDistricts.setOnAction(e->{
			navigate =new NavigatePane();
			mainPane.setCenter(navigate.getPane());
		});
		
		
		Menu updateMenu =new Menu("update district");
		updateMenu.setOnAction(e->{
			UpdateDistrict update=new UpdateDistrict();
			mainPane.setCenter(update.getPane());
		});
		
		Menu printDistrictMenu=new Menu("print district");	
		printDistrictMenu.setOnAction(e->{
			pd=new PrintDistrict();
			mainPane.setCenter(pd.getPane());
		});
		
		Menu SearchByDateMenu=new Menu("count by date");	
		SearchByDateMenu.setOnAction(e->{
			searchByDate=new SearchByDatePane();
			mainPane.setCenter(searchByDate.getPane());
		});
				
		district.getItems().addAll(viewDistricts,addDistrict,deletedis,updateMenu,navigateDistricts,printDistrictMenu,SearchByDateMenu);
		
		return district;
		
	}
	
//...................................................................................................
	
	public Menu menuLocation() {
		

		Menu location =new Menu("location");		
		MenuItem addLocMenu=new MenuItem("Add Location");
		
		addLocMenu.setOnAction(e->{
			mainPane.setCenter(getPaneAddLocation());
		});
		
		
		MenuItem updateLocationMenu=new MenuItem("Update Location");
		updateLocationMenu.setOnAction(e->{
			 updateP=new UpdateLocationPane();
			mainPane.setCenter(updateP.getPane());
		});
		
		MenuItem deleteLocationMenu=new MenuItem("Delete Location");
		deleteLocationMenu.setOnAction(e->{
			mainPane.setCenter(getPaneDeleteLocation());
		});
		
		
		MenuItem navigateLocationMenu=new MenuItem("navigate location");
		navigateLocationMenu.setOnAction(e->{
			navigateLocation=new NavigateLocationData(); 
			mainPane.setCenter(navigateLocation.getPane());
		});

		
		MenuItem searchLocation_showDetailsMenu=new MenuItem("search Location");
		searchLocation_showDetailsMenu.setOnAction(e->{
			mainPane.setCenter(getPaneSearchLocation());
		});
		
		
		MenuItem addMartyrMenu=new MenuItem("add martyr ");	
		addMartyrMenu.setOnAction(e->{
			addMartyr= new InsertMartyrPane();
			mainPane.setCenter(addMartyr.getPane());
		});
		
		
		MenuItem searchMartyrRecordMenu=new MenuItem("search martyr");
		searchMartyrRecordMenu.setOnAction(e->{	
			mainPane.setCenter(getPaneSearchMartyr());
		});
		
		MenuItem deleteUpdateMartyrMenu=new MenuItem("delete/update martyr");
		deleteUpdateMartyrMenu.setOnAction(e->{
			dp=new DeleteUpdateMartyrPane();
			mainPane.setCenter(dp.getPane());
		});

		location.getItems().addAll(addLocMenu,updateLocationMenu,deleteLocationMenu,navigateLocationMenu,searchLocation_showDetailsMenu,addMartyrMenu,
				searchMartyrRecordMenu,deleteUpdateMartyrMenu);
		
		
		return location;
		
	}

//...................................................................................................
	
	public Pane getPaneSearchMartyr() {
		
		VBox mainPane=new VBox(10);
		mainPane.setPadding(new Insets(10,20,10,20));
		TextField name=new TextField("Enter Martyr Name to see its record");
		HBox searchPane=new HBox(5);
		Label response=new Label("Enter Martyr name to see its record:");
		response.setFont(new Font(15));
		Button search=new Button("search");
		
		searchPane.getChildren().addAll(search);
		searchPane.setAlignment(Pos.BASELINE_RIGHT);
		search.setOnAction(e->{
			
			Martyr r=new Martyr();
			DNode d=MainStage.dlist.getFront();
				if(d!=null) {
					
					for(int i=0;i<MainStage.dlist.getSize();i++){

						r=(Martyr)d.getLocations().searchMartyrByName(name.getText().toLowerCase());
						if(r!=null) break;		
						
						d=d.getNext();						
					}
				}
				
				if(r==null) {
					response.setText("THAT MARTYR NAME DOES NOT EXIST!!");
				}
				else if(name.getText().isEmpty())
					response.setText("ENTER SOMETHING INSIDE THAT TEXT FIELD!");
				else {
					response.setText("name: "+r.getName()+""
							+ "\nAge: "+r.getAge()+""
							+ "\nGender: "+r.getGender()+""
							+ "\nDate: "+r.getDateOfDeath()+""
							+ "\nLocation: "+r.getLocation()+""
							+ "\nDistrict: "+r.getDistrict());
				}
			
		});
		
		
		mainPane.getChildren().addAll(response,name,searchPane);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color:LIGHTBLUE");
		
		return mainPane;
	}	
	
//..................................................................................................
	
	public Pane getPaneSearchLocation() {
		
		BorderPane main=new BorderPane();
		main.setPadding(new Insets(10,30,10,30));
		TextField tf=new TextField("Enter Location name");
		Button search=new Button("search");
		Label response=new Label();
		
		main.setCenter(tf);
		main.setStyle("-fx-background-color:LIGHTBLUE");
		
		HBox o=new HBox();
		o.getChildren().add(search);
		o.setAlignment(Pos.BASELINE_RIGHT);
		
		main.setBottom(o);
		main.setTop(response);
		
		search.setOnAction(e->{
		
			LNode l =null;
			l=MainStage.dlist.searchLocationByName(tf.getText()) ;
			
		if(tf.getText().isEmpty()) {
			response.setText("ENTER SOMETHING INSIDE THE TEXT FIELD!!");			
			}
		else if(l==null) {
			response.setText("THAT LOCATION NAME DOES NOT EXIST");	
		}
		else {
			try {
			response.setText("name: "+l.getData()+""
					+ "\nMartyrs counter: "+(l.getMartyrs().getSize())+""
					+ "\nFemale counter: "+(l.getMartyrs().getFemale_counter())+""
					+"\nmale counter: "+(l.getMartyrs().getMale_counter())+""
					+ "\nAverage age "+(l.getMartyrs().getAverage())+""
					+ "\nyoungest martyr: "+((Martyr)l.getMartyrs().getFirst()).getName() +""
					+ "\nlargest martyr: "+((Martyr)l.getMartyrs().getLastElement()).getName() );
			
			}catch(NullPointerException exc) {  //this means that the location does not have martyrs records
				response.setText("name: "+l.getData()+""
						+ "\nMartyrs counter: 0"
						+ "\nFemale counter: 0"
						+"\nmale counter: 0"
						+ "\nAverage age: 0"
						+ "\nyoungest martyr: no martyrs data inside this location"
						+ "\nlargest martyr: no martyrs data inside this location");
			}
		}
			
		});
		
		return main;
	}
//.................................................................................................
	
	public BorderPane getPaneDeleteDistrict(){

		 BorderPane n =new BorderPane();
		 TextField tf=new TextField("Enter the name of that district");
		Label response =new Label();
		Button  b=new Button("Delete District");
		
		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(100, 20);
		
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
			else {
				MainStage.dlist.deleteByData(tf.getText());
				response.setText("DISTRICT DELETED");
			}
			
		});
		
		
		return n ;
	}

//..................................................................................................
	
	public BorderPane getPaneDeleteLocation(){
		
		 BorderPane n =new BorderPane();
		 TextField tf=new TextField("Enter the name of that loation");
		 Label response =new Label();
		 TextField dis=new TextField("Enter District of that Location");
		 Button b=new Button("Delete Location");
		
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
					try {
						DNode d=MainStage.dlist.getNodeByData(dis.getText());
						
						if(d!=null) {
							
							if(d.getLocations().contains(tf.getText())) {		
										response.setText("LOCATION DELETED");
										d.getLocations().deleteLocationByData(tf.getText());
								}
							else {
								response.setText("LOCATION DOES NOT EXIST");
							}
						}
						else {
						response.setText("THIS DISTRICT DOES NOT EXIST IN THE SYSTEM");
						}
					}catch(NullPointerException exception) {
						
					}
					
			}	
		});
		
		
		return n ;
	}
	
//.................................................................................................
	
	public BorderPane getPaneAddDistrict(){

		 BorderPane n =new BorderPane();
		 TextField tf=new TextField("Enter the name of that district");
		 Label response =new Label();
		 Button b=new Button("add district");
		
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
				response.setText("THAT DISTRICT NAME ALREADY EXIST");
			}
			else {
				if(isString(tf.getText())==false) {
					Alert numeric_string_not_allowed=new Alert(AlertType.ERROR);
					numeric_string_not_allowed.setTitle("NUMERIC DISTRICT NAMES ARE NOT ALLOWED");
					numeric_string_not_allowed.setContentText("NUMERIC DISTRICT NAMES ARE NOT ALLOWED\n THAT DISTRICT HAS NOT BEEN ADDED");
					numeric_string_not_allowed.show();
					return;
				}
				response.setText("DISTRICT ADDED SUCCESSFULLY");
				MainStage.addSortlyDistrict(tf.getText());
			}
			
		});
		
		
		return n ;
	}
	
//...................................................................................................
	
	
	public BorderPane getPaneAddLocation(){
		VBox vb=new VBox();
		 BorderPane n =new BorderPane();
		 TextField tf=new TextField("Enter the name of that loation");
		 Label response =new Label();
		 Button b=new Button("add location");
		 TextField dis=new TextField("distriction name");
		
		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(100,20);
		
		tf.setPrefHeight(40);
		dis.setPrefHeight(40);
		
		vb.getChildren().addAll(tf,dis);
		n.setCenter(vb);
		n.setBottom(b);
		n.setTop(response);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		b.setOnAction(e->{
			
			DNode d=MainStage.dlist.getFront();
			
			boolean same=false;
			if(d!=null) {
				LNode l=d.getLocations().getHead();
				 same=false;
				for(int i=0;i<MainStage.dlist.getSize();i++) {		
					for(int j=0;j<d.getLocations().size() && l!=null;j++) {
						
						if( ((String)l.getData()).equalsIgnoreCase(tf.getText()) ) {
							same=true;
						}
						
						l=l.getNext();
					}
					d=d.getNext();
					if(d!=null) {
					l=d.getLocations().getHead();}
				}
			}
			
			
			
			response.setFont(new Font(15));
			if(tf.getText().isEmpty() || dis.getText().isEmpty()) {
				response.setText("ENTER SOMETHING INSIDE TEXT FIELD");
			}		
			else if(same==true) {
				response.setText("THAT LOCATION NAME ALREADY EXISTS");
			}
			else if(same==false && !MainStage.dlist.contains(dis.getText())) {
				response.setText("DISTRICT NAME DOES NOT EXIST!!");
			}
			else {
				if(isString(tf.getText())==false) {
					Alert numeric_string_not_allowed=new Alert(AlertType.ERROR);
					numeric_string_not_allowed.setTitle("NUMERIC LOCATION NAMES ARE NOT ALLOWED");
					numeric_string_not_allowed.setContentText("NUMERIC LOCATION NAMES ARE NOT ALLOWED\n THAT LOCATION HAS NOT BEEN ADDED");
					numeric_string_not_allowed.show();
					return;
				}
				response.setText("LOCATION ADDED SUCCESSFULLY!");
				MainStage.addSortlyLocation(tf.getText(),dis.getText());
			}
			
		});
		
		
		return n ;
	}

//..................................................................................................
	
	public boolean isString(String r) {
		try {
			Integer.parseInt(r);
			return false;
		}catch(NumberFormatException e) {
			return true;
		}
	}
	
//..................................................................................................	
	public static void main(String[] args) {
		launch(args);
	}
	
}
