package application;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class InsertMartyrPane{	

	
	private BorderPane addMartyrPane;
	private VBox tfPane;
	private TextField name;
	private TextField age;
	private TextField location;
	private HBox date_pane=new HBox();
	private ComboBox<String> month;
	private ComboBox<String> day;
	private ComboBox<String> year;
	private ComboBox<String> gender;
	private TextField District;
	private Button add;
	private HBox add_back;
	private Label response;
	
	//create objects in the constructors
	public InsertMartyrPane() {
		date_pane=new HBox();
		month=new ComboBox<>();
		gender=new ComboBox<>();
		year=new ComboBox<>();
		day=new ComboBox<>();
		name=new TextField("Enter name");
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
		
		
		add_back=new HBox(5);	
		add=createButton("add martyr");
		tfPane.getChildren().addAll(name,age,location,District,date_pane,gender,add);
		
		
		addMartyrPane.setCenter(tfPane);
		addMartyrPane.setBottom(add_back);
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
			if(t.equals("add martyr")) {
				if(!name.getText().isEmpty() && !age.getText().isEmpty() && !location.getText().isEmpty() && !gender.getValue().isEmpty() && !day.getValue().isEmpty() && !month.getValue().isEmpty() && !year.getValue().isEmpty()) {
					//first i should check if the text fields aren't empty
						try {
							String n=name.getText();
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
								Martyr m =new Martyr(n,a,Character.toUpperCase(g),l,d,f);	

								MainStage.addSortlyMartyrs(m, d, f);
								response.setText("Martyr added successfully");
								//the add is successfull so i will empty the text field again..
								name.setText("Enter name");
								gender.setValue("choose gender");
								day.setValue("day");
								month.setValue("month");
								year.setValue("year");
								age.setText("enter age");
								location.setText("enter location");
								District.setText("enter district");
								
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
