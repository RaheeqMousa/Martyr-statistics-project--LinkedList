package application;

public class DNode {
	private Object data;
	private DNode next;
	private DNode previous;
	private LocationLinkedList locations;

	
	
	public DNode() {
		super();
		this.locations=new LocationLinkedList();
	}
	public DNode(Object data) {
		this(data,null,null);
		locations=new LocationLinkedList();
	}
	public DNode(Object data, DNode next,DNode p) {
		super();
		this.data = data;
		this.next = next;
		this.previous=null;
		locations=new LocationLinkedList();
	}


	public DNode getPrevious() {
		return previous;
	}
	public void setPrevious(DNode previous) {
		this.previous = previous;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public DNode getNext() {
		return next;
	}
	public void setNext(DNode next) {
		this.next = next;
	}
	public LocationLinkedList getLocations() {
		return locations;
	}
	public void setLocations(LocationLinkedList locations) {
		this.locations = locations;
	}
	
	
	@Override
	public String toString() {
		return "data="+data;
	}

}
