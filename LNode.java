package application;

public class LNode {
	private Object data;
	private LNode next;
	private MyLinkedList martyrs;
	
	public LNode() {
		super();
		martyrs=new MyLinkedList();
	}
	public LNode(Object data) {
		this(data,null);
		this.martyrs=new MyLinkedList();
	}
	public LNode(Object data, LNode next) {
		super();
		this.data = data;
		this.next = next;
		this.martyrs=new MyLinkedList();
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public LNode getNext() {
		return next;
	}
	public void setNext(LNode next) {
		this.next = next;
	}
	public MyLinkedList getMartyrs() {
		return martyrs;
	}
	public void setMartyrs(MyLinkedList martyrs) {
		this.martyrs = martyrs;
	}
	
	
	@Override
	public String toString() {
		return "LNode [data=" + data + ", next=" + next + ", martyrs=" + martyrs + "]";
	}
	
	
	
	
	
}
