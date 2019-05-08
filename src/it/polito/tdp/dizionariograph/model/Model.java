package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;
public class Model {
	
	private WordDAO dao;
	private List<String> wordsList;
	private Graph<String, DefaultEdge> grafo;
	
	public Model() {
		super();
		this.dao = new WordDAO();
	
	}

	public void createGraph(int numeroLettere) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		wordsList = new LinkedList<String>(dao.getAllWordsFixedLength(numeroLettere));
		
		//Aggiungo i vertici
		Graphs.addAllVertices(grafo, wordsList);
		
		
		for( String parola1: this.grafo.vertexSet() ) {
			for(String parola2: this.grafo.vertexSet()) {
				int contatore = 0;
				if(parola1.equals(parola2)== false) {
					for(int i = 0; i<numeroLettere; i++) {
						if(parola1.charAt(i) != parola2.charAt(i))
							contatore ++;
					}
					if(contatore ==1 ) {
						this.grafo.addEdge(parola1, parola2);
					}
				}
			}
		}
		
		
	}

	public List<String> displayNeighbours(String parolaInserita) {
		
		List<String> listaVicini = new ArrayList<String>(Graphs.neighborListOf(grafo, parolaInserita));
		return listaVicini;
	}

	public String findMaxDegree() {
		
		int gradoMax = 0;
		List<String> viciniDiMax = new LinkedList<String>();
		String parolaMax = null;
		for(String s : grafo.vertexSet()) {
			if(grafo.degreeOf(s)>gradoMax) {
				gradoMax = grafo.degreeOf(s);
				viciniDiMax = this.displayNeighbours(s);
				parolaMax=s;
			}
			
		}
		
		return parolaMax;
	}

	public boolean esiste(String parolaInserita) {
		
		if(wordsList.contains(parolaInserita)) {
			return true;
		}
		return false;
	}

	public Graph<String, DefaultEdge> getGrafo() {
		return grafo;
	}

	public List<String> getWordsList() {
		return wordsList;
	}
}
