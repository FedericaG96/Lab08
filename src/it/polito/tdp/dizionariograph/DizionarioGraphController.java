package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {

	Model model ;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumeroLettere;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnGraph;

    @FXML
    private Button btnVicini;

    @FXML
    private Button btnGradoMax;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void doGradoMax(ActionEvent event) {
    	int numeroLettere;
    	try {
    	numeroLettere = Integer.parseInt(txtNumeroLettere.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserisci un numero! ");
    		return;
    	}
    	
    	if(!model.grafoCorretto(numeroLettere)) {
    		txtResult.setText("Grafo non corretto! Cliccare sul bottone Crea grafo ");
    		return;
    	}
    	
    	if(numeroLettere == 0 ) {
    		txtResult.setText("Inserire un numero maggiore di 0! ");
    		return;
    	}
    	if(model.getWordsList()== null && model.getGrafo()== null) {
    		txtResult.setText("Grafo non esiste! Cliccare sul bottone Crea grafo ");
    		return;
    	}
    	
    	String parolaMax = model.findMaxDegree();
    	List<String>listaVicini = new LinkedList<String>(model.displayNeighbours(parolaMax));
    	
    	String result ="";
    	result += "Grado massimo: " + listaVicini.size() + " \n" + "Parola con grado massimo: " + parolaMax +" \nVicini : ";
    	
    	for(String s : listaVicini) {
    		result += s +", ";
    	}
    	result = result.substring(0, result.length()-2);
    	txtResult.setText(result);
    	
    	

    }

    @FXML
    void doGraph(ActionEvent event) {
    	txtResult.clear();
     	
    	int numeroLettere;
    	try {
    	numeroLettere = Integer.parseInt(txtNumeroLettere.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserisci un numero! ");
    		return;
    	}
    	if(numeroLettere == 0 ) {
    		txtResult.setText("Inserire un numero maggiore di 0! ");
    		return;
    	}
    	
    	model.createGraph(numeroLettere);
    	txtResult.setText("Grafo generato correttamente! ");
    }

    

    @FXML
    void doVicini(ActionEvent event) {
    	
     	String parolaInserita = txtParola.getText();
    	int numeroLettere;
    	try {
    	numeroLettere = Integer.parseInt(txtNumeroLettere.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserisci un numero! ");
    		return;
    	}
    	
    	if(!model.grafoCorretto(numeroLettere)) {
    		txtResult.setText("Grafo non corretto! Cliccare sul bottone Crea grafo ");
    		return;
    	}
    	
    	if(model.getWordsList()== null && model.getGrafo()== null) {
    		txtResult.setText("Grafo non esiste! Cliccare sul bottone Crea grafo ");
    		return;
    	}
    	    	
    	if(numeroLettere != parolaInserita.length()) {
    		txtResult.setText("Dati inseriti non corretti");
    		return;
    	}
    	 if(!parolaInserita.matches("[a-zA-Z]*")) {
    		 txtResult.setText("Inserire solo caratteri alfabetici");
     		return;
    	 }
    	
    	if(!model.esiste(parolaInserita)) {
    		 txtResult.setText("Parola non esistente");
      		return;
    	}
    	
    	String risultato = "";
    	List<String> listaVicini = new LinkedList<String>(model.displayNeighbours(parolaInserita));
    	for(String s : listaVicini) {
    		risultato += s +", ";
    	}
    	risultato = risultato.substring(0, risultato.length()-2);
    	txtResult.setText(risultato);
    	
    }
    
    @FXML
    void doReset(ActionEvent event) {
    	txtNumeroLettere.clear();
    	txtParola.clear();
    	txtResult.clear();
    }

    @FXML
    void initialize() {
        assert txtNumeroLettere != null : "fx:id=\"txtNumeroLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGraph != null : "fx:id=\"btnGraph\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGradoMax != null : "fx:id=\"btnGradoMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

