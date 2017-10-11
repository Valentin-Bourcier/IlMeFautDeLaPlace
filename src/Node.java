import java.io.File;
import java.util.ArrayList;

public class Node {
	File value;
	boolean type; //true == directory, false == file
	ArrayList<Node> sons;
	
	Node(){
		value = null;
		type = false;
		sons = new ArrayList<Node>();
	}
	
	Node(File f){
		value = f;
		type = f.isDirectory();
		sons = new ArrayList<Node>();
		File[] files;
		if ((files = f.listFiles()) != null) {
			for(File currentFile : files) {
				sons.add(new Node (currentFile));
			}
		}
	}
	
	Node(String filename){
		File f = new File(filename);
		value = f;
		type = f.isDirectory();
		sons = new ArrayList<Node>();
		File[] files = null;
		if((files = f.listFiles())!=null);
			for(File currentFile : files) {
				sons.add(new Node (currentFile));
			}
	}
	
	public String toString() {
		String s = "";
		s+=value.getName()+" "+value.getAbsolutePath();
		if (sons.size()>0) {
			for(Node n : sons) {
				s+= " "+n.toString()+"\n";
			}
		}
		return s;
	}
	
	public String  toGraphviz() {
		String s = "strict graph {\n";
		s+=this.addNode();
		s+="}";
		s = s.replace('.', '-');
		//File save = new File("/home/valentin/Documents/output");
		return s;
		
	}
	
	public String addNode() {
		String s = "";
		if(sons.size()>0) {
			for(Node n : sons) {
				s+="\""+this.value.getName()+"\" -- \""+n.value.getName()+"\"\n";
				s+=n.addNode();
			}
		}
		return s;
	}
	/*
	public static void main(String[] args) {
		Node root = new Node ("/home/valentin/Documents/Cours");
		//System.out.println(root);
		
		System.out.println(root.toGraphviz());
	}*/
	
}


