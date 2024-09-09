package application;


import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DeleteUpdateMartyrPane {

	private TextField tf;
	static BorderPane mainPane;
	private Button search;
	private Label response;
	static VBox vb;
	private String name;
	private Martyr r;
	private Martyr copied_martyr;
	private LNode location_of_the_martyr;
	
	public DeleteUpdateMartyrPane() {
		response=new Label();
		tf=new TextField("Enter the name of that Martyr");
		mainPane=new BorderPane();
		search=new Button("search");		
	}
	
	public BorderPane getPane() {
		vb =new VBox(15);
		vb.getChildren().addAll(tf,search);
		vb.setAlignment(Pos.CENTER);
		
		mainPane.setCenter(vb);
		mainPane.setTop(response);
		mainPane.setStyle("-fx-background-color:LIGHTBLUE");
		
		search.setOnAction(e->{
			name=tf.getText();
			
			if(!tf.getText().isEmpty()) {
				DNode d=MainStage.dlist.getFront();
				if(d!=null) {
					
					for(int i=0;i<MainStage.dlist.getSize();i++){
							System.out.println(name);
						r=(Martyr)d.getLocations().searchMartyrByName(name.toLowerCase());		//we search the martyr name in the locations linked list for the district			
						
						if(r!=null) break;	//if it found we will stop searching	
						
						d=d.getNext();						
					}
				}
				
				if(r!=null) { //if thed martyr name exist
					location_of_the_martyr=d.getLocations().getNodeByData(r.getLocation()); 
					copied_martyr=(Martyr)r.clone();  //copy the searched martyr data
					
					VBox del_update=new VBox(10);
					del_update.setAlignment(Pos.CENTER);
					Button del=createButtonWithAction("Delete martyrs record");
					Button update=createButtonWithAction("Update martyrs record");
					del_update.getChildren().addAll(del,update);
					
					mainPane.setCenter(del_update);
				}
				else {  //if the martyr name does not exist
					response.setText("THAT MARTYR DOES NOT EXIST\nIN THE WHOLE SYSTEM !!");
				}
			
			}else {
				response.setText("PUT SOMETHING INSIDE THAT TEXT FIELD !!");
			}	
			
		});
		
		return mainPane;
		
	}
	
	public Button createButtonWithAction(String txt) { //this method to create a button and set an action for it 
		Button bt=new Button(txt);
		
		if(txt.equalsIgnoreCase("Delete martyrs record")) {
			
			bt.setOnAction(e->{  
					response.setText("");
					
					DNode d=MainStage.dlist.getFront();
					//O(n^3)
					//user just entered the name of that martyr , and i shuld search for that martyr in the whole system....
					for(int i=0;i<MainStage.dlist.getSize();i++) {  
						
						LNode ld=d.getLocations().getHead();
						while(ld!=null) {
							if(ld.getMartyrs().deleteMartyrByData(name) ) {
								response.setText("Martyr record deleted successfully");
								mainPane.setCenter(vb);  //the selete operation finished successfully so i will open the main screen again , in order to let the user search for another martyrs...
							}
							
							ld=ld.getNext();
						}
						
						d=d.getNext();
					}
					
					
			});
			
		}
		else if(txt.equalsIgnoreCase("Update martyrs record")) {
			bt.setOnAction(e->{
				response.setText("");
				location_of_the_martyr.getMartyrs().deleteMartyrByData(name);// delete the martyr with its previous data
				UpdateMartyrPane upd=new UpdateMartyrPane(copied_martyr);  // add the new data for that martyr by the update martyr pane...
				mainPane.setCenter(upd.getPane());

			});
		}
		
		return bt;
	}
	
}
