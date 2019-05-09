package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;
public class Model {
	
	private WordDAO dao;
	private List<String> wordsList;
	private Graph<String, DefaultEdge> grafo;
	List<String> listaVicini;
	
	public Model() {
		super();
		this.dao = new WordDAO();
	
	}

	public void createGraph(int numeroLettere) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		wordsList = new LinkedList<String>(dao.getAllWordsFixedLength(numeroLettere));
		
		//Aggiungo i vertici
		Graphs.addAllVertices(grafo, wordsList);
		
		//Aggiugno gli archi: Per ogni parola aggiungo un arco di collegamento con le parole
		// che differiscono per una sola lettera (stessa lunghezza)
		
		/* Alternativa 1: uso il Database -- LENTO!
		//for (String parola :  this.grafo.vertexSet()) {
		// List<String> paroleSimili = wordDAO.getAllSimilarWords(parola, numeroLettere);
		// Creo l'arco
			for (String parolaSimile : paroleSimili) {
				graph.addEdge(parola, parolaSimile);
			}
		*/

		// Alternativa 2: uso il mio algoritmo in Java
		for( String parola1: this.grafo.vertexSet() ) {
			for(String parola2: this.grafo.vertexSet()) {
				int contatore = 0;
				if(parola1.toLowerCase().equals(parola2.toLowerCase())== false) {
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
		
		listaVicini = new ArrayList<String>(Graphs.neighborListOf(grafo, parolaInserita));
		return listaVicini;
	}

	public String findMaxDegree() {
		
		int gradoMax = 0;
		String parolaMax = null;
		for(String s : grafo.vertexSet()) {
			if(grafo.degreeOf(s)>gradoMax) {
				gradoMax = grafo.degreeOf(s);
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

	public boolean grafoCorretto(int numeroLettere) {
		if(wordsList.get(0).length()!=numeroLettere)
			return false;
		return true;
	}

	
}
