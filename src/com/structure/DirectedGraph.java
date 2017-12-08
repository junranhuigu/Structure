package com.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 单边有向图 本类非线程安全
 * */
public class DirectedGraph {
	private List<Node> nodes;
	
	public DirectedGraph() {
		this.nodes = new ArrayList<>();
	}
	/**
	 * 添加节点
	 * */
	public void addNode(Object attachment){
		Node node = new Node(attachment);
		this.nodes.add(node);
	}
	/**
	 * 删除节点
	 * */
	public void removeNode(Object attachment){
		Node from = get(attachment);
		if(from != null){
			//关闭连接
			for(Connection c : from.getConnections2Others()){
				c.getTo().getConnections2Self().remove(c);
			}
			for(Connection c : from.getConnections2Self()){
				c.getFrom().getConnections2Others().remove(c);
			}
			from.getConnections2Others().clear();
			from.getConnections2Self().clear();
			//删除节点
			nodes.remove(from);
		}
	}
	/**
	 * 设置连接
	 * */
	public void connect(Node from, Node to){
		Connection c = new Connection(from, to);
		from.connect(c);
		to.connected(c);
	}
	/**
	 * 关闭连接
	 * */
	public void closeConnect(Node from, Node to){
		Connection connection = null;
		for(Connection c : from.getConnections2Others()){
			if(c.getTo().equals(to)){
				connection = c;
				break;
			}
		}
		if(connection != null){
			from.getConnections2Others().remove(connection);
			to.getConnections2Self().remove(connection);
		}
	}
	/**
	 * 查找某个节点
	 * */
	public Node get(Object attachment){
		for(Node n : nodes){
			if(attachment.equals(n.getAttachment())){
				return n;
			}
		}
		return null;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	/**
	 * 验证是否存在闭合
	 * */
	public boolean hasCircle(){
		if(size() == 0){
			return false;
		}
		Stack<Node> stack = new Stack<>();
		List<Node> ends = endNodes();
		if(ends.isEmpty()){//无终点 必然存在闭合
			return true;
		}
		List<Node> begins = beginNodes();
		if(begins.isEmpty()){//无起点 必然存在闭合
			return true;
		}
		for(Node begin : begins){
			stack.add(begin);
			if(hasCircle(stack)){
				return true;
			}
		}
		return false;
	}
	
	private boolean hasCircle(Stack<Node> stack){
		if(stack.isEmpty()){
			return false;
		}
		boolean hasCircle = false;
		Node node = stack.peek();
		if(!node.getConnections2Others().isEmpty()){//非终点
			for(Connection con : node.getConnections2Others()){
				if(stack.contains(con.getTo())){//验证是否存在闭环
					hasCircle = true;
					break;
				} else {
					stack.add(con.getTo());
					hasCircle = hasCircle || hasCircle(stack);
				}
			}
		}
		stack.pop();
		return hasCircle;
	}
	/**
	 * 获取有向图所有的起始节点
	 * */
	public List<Node> beginNodes() {
		//入度为0的节点
		List<Node> list = new ArrayList<>();
		for(Node node : nodes){
			if(node.getConnections2Self().isEmpty()){
				list.add(node);
			}
		}
		return list;
	}
	/**
	 * 获取有向图所有的终结节点
	 * */
	public List<Node> endNodes() {
		//出度为0的节点
		List<Node> list = new ArrayList<>();
		for(Node node : nodes){
			if(node.getConnections2Others().isEmpty()){
				list.add(node);
			}
		}
		return list;
	}
	/**
	 * 节点数量
	 * */
	public int size(){
		return nodes.size();
	}
	
	/**
	 * 最长路线
	 * */
	public int wayMaxLength(){
		if(hasCircle()){//存在闭环 则不存在最长路线
			return -1;
		}
		Stack<Node> stack = new Stack<>();
		int len = 0;
		for(Node begin : beginNodes()){
			stack.add(begin);
			len = Math.max(wayMaxLength(stack), len);
		}
		return len;
	}
	
	private int wayMaxLength(Stack<Node> stack){
		if(stack.isEmpty()){
			return 0;
		}
		Node node = stack.peek();
		int len = 0;
		if(node.getConnections2Others().isEmpty()){//终点
			len = Math.max(stack.size(), len);
		} else {
			for(Connection con : node.getConnections2Others()){
				stack.add(con.getTo());
				len = Math.max(len, wayMaxLength(stack));
			}
		}
		stack.pop();
		return len;
	}
	
	/**
	 * 有向图节点
	 * */
	public class Node{
		private Object attachment;//附件
		private ArrayList<Connection> cons_self2others;//连接 - 从自己到其他节点
		private ArrayList<Connection> cons_others2self;//连接 - 从其他节点到自己
		public Node(Object attachment) {
			this.attachment = attachment;
			this.cons_self2others = new ArrayList<>();
			this.cons_others2self = new ArrayList<>();
		}
		public Object getAttachment() {
			return attachment;
		}
		/**
		 * 获取从自己到其他节点的连接
		 * */
		public ArrayList<Connection> getConnections2Others() {
			return cons_self2others;
		}
		/**
		 * 获取从其他节点到自己的连接
		 * */
		public ArrayList<Connection> getConnections2Self() {
			return cons_others2self;
		}
		/**
		 * 记录连接到其他节点
		 * */
		public void connect(Connection c){
			cons_self2others.add(c);
		}
		/**
		 * 记录其他节点连接到自己
		 * */
		public void connected(Connection c){
			cons_others2self.add(c);
		}
	}
	/**
	 * 表示两个节点的连接
	 * */
	public class Connection{
		private Node from;
		private Node to;
		public Connection(Node from, Node to) {
			this.from = from;
			this.to = to;
		}
		public Node getFrom() {
			return from;
		}
		public Node getTo() {
			return to;
		}
	}
}
