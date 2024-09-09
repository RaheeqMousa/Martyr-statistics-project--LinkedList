package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SearchByDatePane {
	

	public VBox getPane(){
		
		
		VBox mainPane=new VBox(10);
		HBox back_donehb=new HBox(5);
		TextField tf=new TextField("Enter date");
		Label response=new Label("Enter date to display the number of \n"
				+ "	Martyrs whose have same date:");
		response.setFont(new Font(20));
		Button done=new Button("done");
		
		done.setOnAction(e->{  //this will display the response for the user (number of martyrs with the same date of death)
			if(!tf.getText().isEmpty()) {   //check if the text fieldis empty or not
				
				if (searchByDate(tf.getText())>0) {
					response.setText("The number of Martyrs\n"
							+ "  whose date is "+tf.getText()+" is "+searchByDate(tf.getText()));
				}
				else {
					response.setText("The number of Martyrs\n"
							+ "  whose date is "+tf.getText()+" is "+searchByDate(tf.getText()) +"\n"
							+ "  or maybe the date is invalid");
				}
				
			}
			else { //if the text field is empty is will show the user a response to pay their attention !!
				response.setFont(new Font(20));
				response.setText("The text field is Empty, FILL IT!!");
			}
		});
		
		
		back_donehb.getChildren().addAll(done);
		back_donehb.setAlignment(Pos.BASELINE_RIGHT);
		
		mainPane.getChildren().addAll(response,tf,back_donehb);
		mainPane.setPadding(new Insets(10,20,10,20));
		mainPane.setStyle("-fx-background-color:LIGHTBLUE");
		mainPane.setAlignment(Pos.CENTER);
		
		return mainPane;
	}
	
	
	public int searchByDate(String a) {  
		int count=0;
		DNode n =MainStage.dlist.getFront();
		
		for(int i=0;i<MainStage.dlist.getSize();i++) {
			LNode l=n.getLocations().getHead();
			while(l!=null) {
				Node mar =l.getMartyrs().getHead();
				while(mar!=null) {
					if(((Martyr)mar.getData()).getDateOfDeath().equals(a)) {
						count++;  //if a martyr's date of death is eual to the entered date , the counter will increment
					}
					mar=mar.getNext();
				}
				l=l.getNext();
			}
			n=n.getNext();
		}
		
		return count;//the number of martyrs who are the same date
	}
	


}
