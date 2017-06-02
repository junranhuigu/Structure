package com.structure;

import java.util.ArrayList;
import java.util.List;

public class Tree<V>{
	private Node root;//根节点

	public Tree(V attachment) {
		this.root = new Node(null, attachment);
	}
	
	public Node getRoot() {
		return root;
	}

	public class Node{
		private V attachment;
		private Node parent;//父节点
		private List<Node> children;//子节点
		
		public Node(Node parent, V attachment) {
			this.attachment = attachment;
			this.parent = parent;
			this.children = new ArrayList<>();
		}
		
		/**
		 * 添加子节点
		 * */
		public void addChild(Node node){
			this.children.add(node);
		}
		/**
		 * 添加复数子节点
		 * */
		public void addChildren(List<Node> children){
			this.children.addAll(children);
		}
		/**
		 * 验证是否为叶子
		 * */
		public boolean isLeaf(){
			return this.children.isEmpty();
		}

		public V getAttachment() {
			return attachment;
		}

		public Node getParent() {
			return parent;
		}

		public List<Node> getChildren() {
			return children;
		}
	}
}
