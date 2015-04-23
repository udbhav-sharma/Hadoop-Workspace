package MapperIO;

import java.io.Serializable;

import VoronoiDiagram.Graph;


public class FirstMapperState implements Serializable {
	private static final long serialVersionUID = -8750823852430429802L;
	public int mapperId;
	public Graph G;
	
	public FirstMapperState( int mapperId, Graph G ){
		this.mapperId = mapperId;
		this.G = G;
	}
	
	public String toString(){
		String output = "-----Mapper State-----\n";
		output += "Mapper Id: "+mapperId+"\n";
		output += G.toString();
		
		return output;
	}
}
