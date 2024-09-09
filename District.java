package application;

public class District {
	
	private DNode front;
	private DNode last;
	private int size;
	
	public District() {
		super();
		this.front = null;
		this.last = null;
		this.size = 0;
	}
	
//....................................................................................................		
	
	public boolean deleteFirst(){
		if(front==null) return false;
		else if(size==1) {
			front=null;
			last=null;
			size--;
			return true;
		}
		else{
			
			front=front.getNext();
			last.setNext(front);
			front.setPrevious(last);
			size--;
			
			return true;
		}
	}
	
//....................................................................................................	
	
	public boolean deleteLast(){
		if(front==null) return false;
		else if(size==1) {
			front=null;
			last=null;
			size--;
			return true;
		}
		else{			
			last=last.getPrevious();
			last.setNext(front);
			front.setPrevious(last);
			size--;			
			return true;
		}
	}

//....................................................................................................		
	
	public Object getFirstElement() {
		return front.getData();
	}
	
	public Object getLastElement() {
		return last.getData();
	}

//....................................................................................................		
	
	public void addToFirst(Object g){
		DNode r=new DNode(g);
		if(front==null){
			front=r;   // the nodes front and last points on node 'r'
			last=r;
			front.setPrevious(last); // I set a previous for the node r
			last.setNext(front);	// I set a next for the node r	 
			size++;
		}
		else{
			front.setPrevious(r);
			r.setNext(front);
			front=r;
			last.setNext(front);
			front.setPrevious(last);
			size++;
		}
	}

//....................................................................................................		
	
	public Object get(int i){
		if(front==null) return null;
		else if(i>(size-1) || i<0) return null;
		else if(i==0) front.getData();
		else if(i==(size-1)) last.getData();

			DNode current =front;
			for(int j=0;j<i;j++) {
				current=current.getNext();
			}
			return current.getData();

	}

//....................................................................................................		
	
	public void addToLast(Object g){
		DNode r=new DNode(g);
		if(front==null){
			front=r;  //the nodes front and last points on r
			last=r;
			last.setNext(front);  //i set a next for the node r
			front.setPrevious(last);  //i set a previouss for the node r
			size++;
		}
		else{			
			r.setPrevious(last);
			last.setNext(r);
			last=last.getNext();
			last.setNext(front);
			front.setPrevious(last);
			size++;
		}
	}
	
//....................................................................................................		
	
	public void add(Object o,int index) {  
		if(index==0) {
			addToFirst(o);
		}
		else if (index>=size) {
			addToLast(o);
		}
		else {
			DNode newNode=new DNode(o);
			DNode current =front;
			for(int i=0;i<index-1;i++) {
				current=current.getNext();
			}
			
			newNode.setPrevious(current);
			newNode.setNext(current.getNext());
			current.getNext().setPrevious(newNode);
			current.setNext(newNode);
			size++;
			
		}
	}

//....................................................................................................		
	
	public LNode searchLocationByName(String n ) {
		if(front==null) return null;
		else {
			
			
			DNode d =front;
			
			for(int i=0;i<MainStage.dlist.size;i++) {
				LNode l=d.getLocations().getHead();
				while(l!=null) {
					if(((String)l.getData()).equalsIgnoreCase(n) ) {
						return l;
					}		
					l=l.getNext();
								
				}
				d=d.getNext();
			}
		
			
			return null;
		}
	}
	
//....................................................................................................		
	
	public int lastIndexOf(Object o) {
		if(front==null) return -1;
		else {
			DNode current =last;
			
			for(int i=0;i<size;i++) {	
				if( ((String)(current .getData())).equalsIgnoreCase((String)o ) ) {
					return i;
				}			
				current=current.getPrevious();
			}
			
			return -1;
		}
		
	}

//....................................................................................................		
	
	public boolean contains(Object o){
		if(front==null) return false;
		else {
			if(lastIndexOf(o) >=0) return true;
			
			else return false;
		}
	}
	
//....................................................................................................		
	
	public DNode getNodeByIndex(int i) {
		if(front==null) return null;
		else {
			DNode current =front;
			for(int j=0;j<i;j++) {
				current=current.getNext();
			}
			return current;
		}
	}

//....................................................................................................		
	
	public DNode getNodeByData(Object obj) {
		if(front==null) return null;
		else {
			DNode current =front;
			for(int j=0;j<size;j++) {
				if( ((String)current.getData()).equalsIgnoreCase((String)obj) ) {
					return current;
				}
				current=current.getNext();
			}
			return null;
		}
	}

//....................................................................................................		
	
	public void printInReversedOrder(){
		DNode n =last;
		while(n.getPrevious()!=last) {
			System.out.print(n.getData()+" -> ");
			n=n.getPrevious();
		}
		System.out.println("null");
		System.out.println();
	}
	
	//....................................................................................................	
	
	public boolean deleteByData(Object obj) {
		if(front==null) return false;
		else {
				if(((String)getFront().getData()).equalsIgnoreCase((String) obj) ) {
					deleteFirst();
					return true;
				}
				else if(((String)getLast().getData()).equalsIgnoreCase((String) obj) ) {
					deleteLast(); 
					return true;
				}
				else {
					DNode nn=front;
					DNode previous=front;
					for(int i=0;i<size;i++) {					
						if((  (String)nn.getData()  ).equalsIgnoreCase((String)obj))
							break;
						previous=nn;
						nn=nn.getNext();
					}
					
					previous.getNext().getNext().setPrevious(previous);
					previous.setNext(nn.getNext());
					size--;
					return true;
				}
			}
			
	}
	

//....................................................................................................		

	public void print(){
		DNode n =front;
		for(int i=0;i<size;i++){
			System.out.println(n.getData()+" ==> ");
    		n.getLocations().print();
			n=n.getNext();
		}
			
	
	}
	
	//....................................................................................................		
	
	public DNode getFront() {
		return front;
	}
	public void setFront(DNode front) {
		this.front = front;
	}
	
	public DNode getLast() {
		return last;
	}
	public void setLast(DNode last) {
		this.last = last;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	//....................................................................................................	
	
	@Override
	public String toString() {
		return "District [front=" + front + ", last=" + last + ", size=" + size + "]";
	}
	
	
	
	
}
