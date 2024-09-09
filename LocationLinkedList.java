package application;
public class LocationLinkedList {
	
	private LNode head;
	private int size=0;
	private LNode last;
	private int fCount;
	private int mCount;
	private long sum;
	private int total=0;
	private String date_has_max_martyrs="";
	private int max=0;
	
	public LocationLinkedList(){
		this.head=null;   //empty linked list
		this.last=null;
		this.size=0;
		this.fCount=0;
		this.mCount=0;
		this.sum=0;
		this.date_has_max_martyrs="";

	}

	public Object getFirstElement(){
		return head.getData();
	}
	public Object getLastElement(){
		return last.getData();
	}

	public int size(){
		return size;
	}
//......................................................................................
	
	public void addToLast(Object s){
		LNode n=new LNode(s);
		if(head==null){  //empty linked list
			head=n;
			last=n;
			size++;
		}
		else{
			last.setNext(n);
			last=last.getNext();
			size++;
		}
	}

//......................................................................................
	
	public LNode getNodeByData(Object obj) {
		if(head==null) return null;
		else {
			LNode current =head;
			for(int j=0;j<size;j++) {
				if(((String)current.getData()).equalsIgnoreCase((String)obj)) {
					return current;
				}
				current=current.getNext();
			}
			return null;
		}
	}

//.......................................................................................
	public void calculate() {
		LNode moving =head;
		this.fCount=0;
		this.	mCount=0;
		this.	sum=0;
		this.	total=0;
		while(moving !=null) {
				
			
			this.mCount+=moving.getMartyrs().getMale_counter();
			this.fCount+=moving.getMartyrs().getFemale_counter();
			this.total+=moving.getMartyrs().getSize();
			this.sum+=moving.getMartyrs().getAgeSum();
			
			
			Node r=(moving.getMartyrs().getHead());
			while(r!=null) {
				compareMartyrWithAllMartyrs((Martyr)r.getData());
				r=r.getNext();
			}
				
				moving=moving.getNext();
		}
		
		
		
	}

//...................................................................................
	
	public void compareMartyrWithAllMartyrs(Martyr r) {
		LNode move=head;
		int count=0;
		for(int i=0;i<size;i++) {
			Node f=move.getMartyrs().getHead();
			while(f!=null) {
				if(r.getDateOfDeath().equals( ((Martyr)f.getData()).getDateOfDeath()  )   ) {
					count++;
				}
				f=f.getNext();
			}
			move=move.getNext();
		}
		
		if(count>this.max) {
			max=count;
			this.date_has_max_martyrs=r.getDateOfDeath();
		}
		
	}

//..............................................................................................................
	
	public boolean checkLocationHaveMartyrs(String loc_name) {
		LNode l =getNodeByData(loc_name);
		if(l.getMartyrs().getHead()==null) return false;
		
		return true;
	}

//...............................................................................................................
	
	public boolean checkALLLOcationDoesntHave() {  //to check that all location in the district does have martyrs
		LNode move=head;
		while(move!=null) {
			if(checkLocationHaveMartyrs((String)move.getData()) ==true )
				return false;
			move=move.getNext();
		}
		
		return true;
	}

//..................................................................................................................	
	
	public Object searchMartyrByName(String n ) {
		if(head==null) return null;
		else {
			LNode l =head;
			
			//O(N^3)
			while(l!=null) {
				for(int j=0;j<l.getMartyrs().size();j++) {
					Martyr f=((Martyr)l.getMartyrs().get(j));
					if( f.getName().toLowerCase().contains(n.toLowerCase())  ) {
						return f;
					}
				}
				l=l.getNext();
			}
			
			return null;
		}
	}

//.................................................................................................	
	
	public void addToFirst(Object s){
		LNode node=new LNode(s);
		if(head==null){  //empty linked list
			head=node;
			last=node;
			size++;
		}
		else{
			node.setNext(head);
			head=node;
			size++;
		}
	}

//....................................................................................................	
	
	public boolean deleteFirst(){
		if(head==null) return false;
		else{
			size--;
			head=head.getNext();
			return false;
		}
	}

//....................................................................................................	
	
	public boolean deleteLast(){
		if(head==null) return false;
		else if(head==last) {
			head=last=null;
			return true;
		}
		else{			
			LNode current=head;
			for(int i=0;i<size-2;i++){
				current=current.getNext();
			}
			current.setNext(null);
			last=current;
			size--;
			return false;
		}
	}

////....................................................................................................	
	
	public Object get(int i){
		if(head==null) return null;
		else if(i>(size-1) || i<0) return null;
		else if(i==0) getFirstElement();
		else if(i==(size-1)) getLastElement();

			LNode current =head;
			for(int j=0;j<i;j++) {
				current=current.getNext();
			}
			return current.getData();

	}
//....................................................................................................	
	
	public boolean deleteByIndex(int index){
		if(head==null){  //empty linked list
			return false;
		}
		else if(index>(size-1) || index<0) return false; //invalis index
		else if(index==size-1){
			deleteLast();
			return true;
		}
		else if(index==0){
			deleteFirst();
			return true;
		}
		else{
			LNode current = head;
			for(int i=0;i<index-1;i++){
				current=current.getNext();
			}
			current.setNext(current.getNext().getNext());
			size--;
			return true;

		}
	}

//....................................................................................................		
	
	public boolean addByIndex(Object obj,int i){
		if(i>(size-1) || i<0)  return false;
		else if(i==0){
			addToFirst(obj);
			return true;
		}
		else if (i>=size()) {
			addToLast(obj);
			return true;	}
		else {
			LNode current =head;
			for(int j=0;j<i-1;j++){
				current=current.getNext();
			}
			LNode newNode=new LNode(obj);
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			size++;
			return true;
		}
	}

//....................................................................................................	
	
	public int lastIndexOf(Object o) {
		if(head==null) return -1;
		else {
			LNode current =head;
			int last_index=-1;
			for(int i=0;i<size;i++) {
				
				if( ((String)current.getData()).equalsIgnoreCase((String)o)  )
					last_index=i;
				
				current=current.getNext();
			}
			
			return last_index;
		}
		
	}

//....................................................................................................		
	
	public boolean contains(Object o) {
		if(head==null) return false;
		else {
			if(lastIndexOf(o) >=0) return true;
			
			else return false;
		}
		
	}

////....................................................................................................		
	
	public boolean deleteLocationByData(Object obj) {
		if(head==null) return false;
		else {
				if(((String)getHead().getData()).equalsIgnoreCase((String) obj) ) {
					deleteFirst();
					return true;
				}
				else if(((String)getLast().getData()).equalsIgnoreCase((String) obj) ) {
					deleteLast(); 
					return true;
				}
				else {
					LNode nn=head;
					LNode prev=head;
					for(int i=0;i<size;i++) {					
						if((  (String)nn.getData()  ).equalsIgnoreCase((String)obj))
							break;
						prev=nn;
						nn=nn.getNext();
					}
					
					try {
						prev.setNext(nn.getNext());
					}catch(NullPointerException ex) {
						return false;
					}
					
					size--;
					return true;
				}
			}
			
	}

//....................................................................................................		
	
	public void printInReverseOrder() {

		for(int i=size-1;i>=0;i--) {
			System.out.print(get(i)+" -> ");
		}
		System.out.print("null");
		System.out.println();
	}
	
//....................................................................................................		
	
	public void print(){
		LNode n =head;
		while(n!=null) {
			System.out.print(n.getData()+" city --> ");
			n.getMartyrs().print();
			
			n=n.getNext();
		}
		System.out.println();
	}

//....................................................................................................		
	
	public int getFemale_count() {

		return this.fCount;
	}
	
	public int getMale_count() {

		return this.mCount;
	}
	public int getToatl() {
		return this.total;
	}
	
	public double getAgeAverage() {
		return (sum/(double)total);
	}
	
	public String getDate_has_max_martyrs() {
		return date_has_max_martyrs;
	}

	public void setDate_has_max_martyrs(String date_has_max_martyrs) {
		this.date_has_max_martyrs = date_has_max_martyrs;
	}
	
	public LNode getHead() {
		return head;
	}

	public void setHead(LNode head) {
		this.head = head;
	}

	public void setLast(LNode last) {
		this.last = last;
	}
	public LNode getLast() {
		return last;
	}

//....................................................................................................
	
	@Override
	public String toString() {
		return "MyLinkedList [head=" + head + "]";
	}

}
