package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UpdateMartyrPane {
	
	private Martyr updated_martyr;
	private BorderPane addMartyrPane;
	private VBox tfPane;
	private TextField age;
	private TextField location;
	private HBox date_pane=new HBox();
	private ComboBox<String> month;
	private ComboBox<String> day;
	private ComboBox<String> year;
	private ComboBox<String> gender;
	private TextField District;
	private Button upd_bt;
	private HBox update;
	static Label response;
	
	//create objects in the constructors
	public UpdateMartyrPane() {
		date_pane=new HBox();
		month=new ComboBox<>();
		gender=new ComboBox<>();
		year=new ComboBox<>();
		day=new ComboBox<>();
		age=new TextField("Enter age");
		location =new TextField("Enter location");
		response=new Label("Enter name, age, location, date, and gender for Martyr:");
		response.setFont(new Font(15) );
		District=new TextField("enter district");

	}
	
	public UpdateMartyrPane(Martyr r) {
		this.updated_martyr=r;
		date_pane=new HBox();
		month=new ComboBox<>();
		gender=new ComboBox<>();
		year=new ComboBox<>();
		day=new ComboBox<>();
		age=new TextField("Enter age");
		location =new TextField("Enter location");
		response=new Label("Enter name, age, location, date, and gender for Martyr:");
		response.setFont(new Font(15) );
		District=new TextField("enter district");

	}


	public Pane getPane(){
		
		gender.setValue("choose gender");
		gender.getItems().add("M");
		gender.getItems().add("F");
			
		
		day.setValue("day");
		month.setValue("month");
		year.setValue("year");
		date_pane.getChildren().addAll(month,day,year);
		
		for(int i=1;i<=31;i++) {
			day.getItems().add(i+"");
		}
		
		for(int i=1;i<=12;i++) {
			month.getItems().add(i+"");
		}
		
		for(int i=2024;i>=1;i--) {
			year.getItems().add(i+"");
		}
		
		
		addMartyrPane =new BorderPane();
		addMartyrPane.setPadding(new Insets(30,10,30,10));
		addMartyrPane.setStyle("-fx-background-color:LIGHTBLUE");
		
		tfPane=new VBox(10);		
		tfPane.setAlignment(Pos.CENTER);
		
		for(int i=0;i<tfPane.getChildren().size();i++) {
			if((tfPane.getChildren().get(i)) instanceof TextField) { //the children in the pane are TextFields, but just to be sure...
			((TextField)tfPane.getChildren().get(i)).setPrefSize(20, 30); }
		}
		
		
		update=new HBox(5);	
		upd_bt=createButton("update martyr");
		tfPane.getChildren().addAll(age,location,District,date_pane,gender,upd_bt);
		
		
		addMartyrPane.setCenter(tfPane);
		addMartyrPane.setBottom(update);
		addMartyrPane.setTop(response);
		
		return addMartyrPane;
		
	}
	
	public Button createButton(String t) {
		/**
		 * @param 't' input parameter which refers to the label inside the button
		 * @return returns a button with the specified action for that button (The event has been approved  to button by the button's label)
		 */
		
		Button b =new Button(t);
		b.setOnAction(e->{ //this will add a martyr to the system using the texts which entered in the text fields
						  //and of course there is a check that the data is valid before adding the martyr object
			if(t.equals("update martyr")) {
				if(!age.getText().isEmpty() && !location.getText().isEmpty() && !gender.getValue().isEmpty() && !day.getValue().isEmpty() && !month.getValue().isEmpty() && !year.getValue().isEmpty()) {
					//first i should check if the text fields aren't empty
						try {
							byte a=Byte.parseByte(age.getText());
							String l=month.getValue()+"/"+day.getValue()+"/"+year.getValue();
							char g=gender.getValue().charAt(0);
							String d=location.getText();
							String f=District.getText();
							
							if(MainStage.dlist.contains(f) && MainStage.dlist.searchLocationByName(d)==null ) {
								response.setText("THAT LOCATION NAME DOESN'T EXIST ");
								return;
							}
							if(!MainStage.dlist.contains(f) ) {
								response.setText("THAT DISTRICT NAME DOESN'T EXIST ");
								return;
							}
							
							else {	
							//(String name, byte age,char gender, String date_of_death, String location, String dis)	
								updated_martyr.setAge(a);
								updated_martyr.setGender(Character.toUpperCase(g));
								updated_martyr.setLocation(d);
								updated_martyr.setDistrict(f);
								updated_martyr.setDateOfDeath(l);
								
								
								//MainStage.addSortlyDistrict(f);
								//MainStage.addSortlyLocation(l, f);
								MainStage.addSortlyMartyrs(updated_martyr, d, f);
								response.setText("Martyr updated successfully....");
								DeleteUpdateMartyrPane.mainPane.setCenter(DeleteUpdateMartyrPane.vb);
								return;
								}	
							}catch(NumberFormatException ex) {
								response.setText("oops you may entered string instead of integer");
							}catch(IllegalArgumentException x) {
								System.out.println(x.getCause()+ " "+x.getLocalizedMessage());
								response.setText("you may entered invalid gender , enter f or m\n"
										+ "or maybe the date is INVALID");
							}
						
				}
				else {
					response.setText("There is an empty text field!!");
				}
			}
		
		});
		
		
		return b;
	}
	
}
