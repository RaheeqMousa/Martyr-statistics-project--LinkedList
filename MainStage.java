package application;



import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import java.io.*;

public class MainStage{
	
    public static District dlist=new District(); //i made it static in order to access it from other classes
    private static File file;
    private static DataInputStream input=null;
    private boolean isCSVFile=true;
    private Label response=new Label();
    
	VBox main;
	Button click;

//...................................................................................................
	
	public VBox getPane() {

		main=new VBox(10);
		main.setAlignment(Pos.CENTER);
		
		click=new Button("click here to choose a file!");
		main.getChildren().addAll(response,click);
		main.setStyle("-fx-background-color:LIGHTBLUE;");
		
			
		click.setOnAction(e->{  // i will let the user choose the file he want to read from
			try {
				response.setText("");
				FileChooser fc=new FileChooser();
				Stage s=new Stage();
				file= fc.showOpenDialog(s);
				fc.setTitle("choose a file to read from");
				
				readCSVFile();
				
				
			}catch(NullPointerException ex) {  //if the user opened the dialog and had not chose a file a null pointer exception will occur
				System.out.println("no file has chosennn");
			}		
		});
		
			return main;
	}
	
//...................................................................................................
	
	public void readCSVFile() {  //this method is to read data

			
		dlist=new District();
		isCSVFile=true;
		int count=0;
		int martyrs_counter=0;
		
		try {
		input=new DataInputStream(new FileInputStream(file) );
			String a=input.readLine(); //to skip the header line
			String line="";
			count=0;
			String file_path=file.getAbsolutePath();
			
			// to avoid any problems with reading a files not in csv 
			if(file_path.charAt(file_path.length()-1)!='v' &&  file_path.charAt(file_path.length()-2)!='s' && file_path.charAt(file_path.length()-3)!='c' ) {
				//the csv file path contains the characters c,s,v in the last of its path
				response.setFont(new Font(20));
				response.setText("this system reads only csv files!");
				isCSVFile=false;
				return; }
				
				
			while((line=input.readLine())!=null) {
				String[] array=line.split(",");
								
				if(array.length!=6) { //that means that the line (row) i read has missing information or more information
					System.out.println(line+" "+count);
					System.out.println("there is a martyr has not added since there is/are missing information");
					continue;
				}
					try {
						//Martyr(String name, byte age,char gender, String date_of_death, String location, String dis)
						//the arg constructor of the martyr may throw an exceptions "IllegalArgumentException"					
						Martyr r=new Martyr(array[0],Byte.parseByte(array[2]),array[5].charAt(0),array[1],array[3],array[4]);

						//note :NumberFormatException is an unchecked exception, and a subclass of IllegalArgumentException.
					
						addSortlyDistrict(array[4]);
						addSortlyLocation(array[3] , array[4] );					
						addSortlyMartyrs(r ,array[3], array[4]);
						martyrs_counter++;
						
					}catch(NumberFormatException ex) {						
						count++;
					}catch(IllegalArgumentException e) {
						count++;
					}

			}
			dlist.print();
			input.close();

		}catch(FileNotFoundException ex) {
			System.out.println(ex.getCause());
			System.out.println("file not found");
		}catch(IOException e) {
			System.out.println("IO exception");
		}finally {
		              //just to be sure that the input stream is closed!
		     try {
				input.close(); 
			 } catch (IOException e) {
				 e.printStackTrace();
				     }
	    }
		
		//this print statement just to know how many person hasn't problems with their information
		System.out.println("there are "+count+" martyr had problems with data, such as no age or invalid gender or ivalid date");
		System.out.println("Number of districts ="+dlist.getSize());
		System.out.println("Number of martyrs ="+martyrs_counter);
	}
	
	
//...................................................................................................
	
	static void writeOnCSVFile(File file) { //this method is to write the data of martyrs
	
		 
		try(DataOutputStream d=new DataOutputStream(new FileOutputStream(file) )){	
			
			String first_line = "Name,Date,Age,location,District,Gender"+ "\n"; //write the header again
	        byte[] first_bytes = first_line.getBytes();
	        d.write(first_bytes);
			
	        DNode dnode=dlist.getFront();
			for(int i=0;i<dlist.getSize();i++) {
				LNode l=dnode.getLocations().getHead();		
					while(l!=null) {
						Node r=l.getMartyrs().getHead();
						while(r!=null) {
							String object_record = ((Martyr)r.getData()).toString() + "\n";
					        byte[] bytes = object_record.getBytes();
					        d.write(bytes);
					        r=r.getNext();
						}
					l=l.getNext();
				}
				dnode=dnode.getNext();
			}
			
			System.out.println("Data saved to file successfully.");
			d.close();
		}catch(FileNotFoundException ex) {
			System.out.println("file is not found for write");
		}catch(IOException e) {
			System.out.println("io exception");
		}
	
}

//...................................................................................................
	
	public static void addSortlyDistrict(String dis_name  ) {
		
		if(dlist.contains(dis_name))
			return ;
		
		if(dlist.getSize()==0) {	
			dlist.addToFirst(dis_name); }		
		
		
		else {
				try {
					
//					char first_char;
//					if(dis_name.charAt(0)!='\'' ){      //example: 'Aqaba -> the first letter of this word with ignoring case is a
//						first_char=Character.toLowerCase(dis_name.charAt(0)); }
//					else {
//						first_char=Character.toLowerCase(dis_name.charAt(1));
//					}
//					
//					char first_char_first;	
//					if(((String)dlist.getFirstElement()).charAt(0)!='\'' ){
//						first_char_first=Character.toLowerCase( ((String)dlist.getFirstElement()).charAt(0)  ); }
//					else {
//						first_char_first=Character.toLowerCase( ((String)dlist.getFirstElement()).charAt(1)  );
//						}
//					
//					char first_char_last;
//					if(((String)dlist.getLastElement()).charAt(0)!='\'' ){
//						first_char_last=Character.toLowerCase( ((String)dlist.getLastElement()).charAt(0)  ); }
//					else {
//						first_char_last=Character.toLowerCase( ((String)dlist.getLastElement()).charAt(1)  );
//						}
//					
//					
//					
//						if( first_char <= first_char_first) {  //compare the first letter in the district name with the first letter of the first element in the list
//							dlist.addToFirst(dis_name);
//						}
//						else if( first_char >= first_char_last) {   //compare the first letter in the district name with the first letter of the last element in the list
//							dlist.addToLast(dis_name);
//						}
//						
//						else {				
//								for(int i=0;i<dlist.getSize();i++) {
//									char r;
//										if(((String)dlist.get(i)).charAt(0)!='\'' ){
//											r=Character.toLowerCase( ((String)dlist.get(i)).charAt(0)  ); }
//										else {
//											r=Character.toLowerCase( ((String)dlist.get(i)).charAt(1)  );
//											}
//										
//									if( first_char <= r    ) {
//										dlist.add(dis_name, i);
//										break;
//									}
//									
//								}
//				}
					
							if(dis_name.toLowerCase().compareTo(((String)dlist.getFirstElement()).toLowerCase()) <= 0) {
								dlist.addToFirst(dis_name);
							}else if(dis_name.toLowerCase().compareTo(((String)dlist.getLastElement()).toLowerCase()) >= 0) {
								dlist.addToLast(dis_name);
							}else {
								for(int i=0;i<dlist.getSize();i++) {
									if(dis_name.toLowerCase().compareTo(((String)dlist.get(i)).toLowerCase())<=0) {
										dlist.add(dis_name, i);
										break;
									}							
								}
							}
						dlist.print();
				}catch(NullPointerException n) {
					System.out.println("exception district");
				}
				
		}
		
		
	}
	
//...................................................................................................		
	public static void addSortlyLocation(String loc_name , String dis_name ) {
		
		DNode dis_node_by_name=dlist.getNodeByData(dis_name);
		
		
		
		if(dis_node_by_name.getLocations().contains(loc_name))
			return;
		
		
		if(dis_node_by_name.getLocations().size()==0)
			dis_node_by_name.getLocations().addToFirst(loc_name);
		
		else {
				try {
					
//					char loc_first_char;
//					if(loc_name.charAt(0)!='\'') {
//						loc_first_char=Character.toLowerCase(loc_name.charAt(0));
//					}
//					else {
//						loc_first_char=Character.toLowerCase(loc_name.charAt(1));
//					}
//					
//					char first;
//					if( ((String)dis_node_by_name.getLocations().getFirstElement()).charAt(0)!='\'' )
//						first=Character.toLowerCase(((String)dis_node_by_name.getLocations().getFirstElement()).charAt(0));
//					else
//						first=Character.toLowerCase(((String)dis_node_by_name.getLocations().getFirstElement()).charAt(1));
//					
//					char second;
//					if( ((String)dis_node_by_name.getLocations().getLastElement()).charAt(0)!='\'' )
//						second=Character.toLowerCase(((String)dis_node_by_name.getLocations().getLastElement()).charAt(0));
//					else
//						second=Character.toLowerCase(((String)dis_node_by_name.getLocations().getLastElement()).charAt(1));
//					
//						if( loc_first_char <=first ) {
//							dis_node_by_name.getLocations().addToFirst(loc_name);
//						}
//						else if( loc_first_char >= second  ) {
//							dis_node_by_name.getLocations().addToLast(loc_name);
//						}
//						else {
//								for(int i=0;i<dis_node_by_name.getLocations().size();i++) {
//									char r;
//									if(((String)dis_node_by_name.getLocations().get(i)).charAt(0)!='\'' ){
//										r=Character.toLowerCase( ((String)dis_node_by_name.getLocations().get(i)).charAt(0)  ); }
//									else {
//										r=Character.toLowerCase( ((String)dis_node_by_name.getLocations().get(i)).charAt(1)  ); 
//									}
//									if( loc_first_char <= r   ) {
//										dis_node_by_name.getLocations().addByIndex(loc_name, i);
//										break;
//									}
//							}
//						}
					
					if(loc_name.toLowerCase().compareTo(((String)dis_node_by_name.getLocations().getFirstElement()).toLowerCase()) <= 0  ) {
						// || ( ((String)dis_node_by_name.getLocations().getFirstElement()).toLowerCase().charAt(0)=='\''  && (loc_name.toLowerCase().compareTo(((String)dis_node_by_name.getLocations().getFirstElement()).substring(1).toLowerCase()) <= 0 ))
						dis_node_by_name.getLocations().addToFirst(loc_name);
					}else if(loc_name.toLowerCase().compareTo(((String)dis_node_by_name.getLocations().getLastElement()).toLowerCase()) >= 0 ) {
						// || ( ((String)dis_node_by_name.getLocations().getLastElement()).toLowerCase().charAt(0)=='\''  && (loc_name.toLowerCase().compareTo(((String)dis_node_by_name.getLocations().getFirstElement()).substring(1).toLowerCase()) >= 0 )) 
						dis_node_by_name.getLocations().addToLast(loc_name);
					}else {
						
						for(int i=0;i<dis_node_by_name.getLocations().size();i++) {
							String added_location_name=((String)dis_node_by_name.getLocations().get(i)).toLowerCase();
								
							if( loc_name.toLowerCase().compareTo(added_location_name)<=0) {
								dis_node_by_name.getLocations().addByIndex(loc_name, i);
								break;
							}
					}
							
					}
				}catch(NullPointerException ex) {
					System.out.println("exception loc");
				}	
		}	
		
	}
	
//...................................................................................................
	
	public static void addSortlyMartyrs(Martyr r , String loc_name, String dis_name) {
		LNode loc_node_by_name= dlist.getNodeByData(dis_name).getLocations().getNodeByData(loc_name);
		
		
		if(loc_node_by_name!=null && loc_node_by_name.getMartyrs().size()==0) {
			loc_node_by_name.getMartyrs().addToFirst(r);
		}
		else {
			try {
					if( r.compareTo((Martyr)loc_node_by_name.getMartyrs().getFirst()) <= 0 ) {
						loc_node_by_name.getMartyrs().addToFirst(r);
					}
					else if(r.compareTo((Martyr)loc_node_by_name.getMartyrs().getLastElement()) >=0 ) {
						loc_node_by_name.getMartyrs().addToLast(r);
					}
					else {
							for(int i=0;i<loc_node_by_name.getMartyrs().size();i++) {
								if( r.compareTo((Martyr)loc_node_by_name.getMartyrs().get(i)) <= 0 ) {
									loc_node_by_name.getMartyrs().addByIndex(r, i);
									break;
								}
								
						}
					}
			}catch(NullPointerException e) {
				System.out.println("exception mar");
			}
			
		}
		
	}



}
