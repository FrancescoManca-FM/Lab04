package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	private ObservableList<Corso> listaCorsi;
	
	public void setModel(Model model) {
		this.model = model;
		listaCorsi = FXCollections.observableArrayList();
        List<Corso> temp =  model.getCorsi();
        listaCorsi.addAll(temp);
        boxCorsi.setItems(listaCorsi);
		
	}
	

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Corso> boxCorsi;

    @FXML
    private Button btnIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private CheckBox btnSearch;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button btnReset;

    @FXML
    void doIscriviStudente(ActionEvent event) {
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	String smatricola = txtMatricola.getText();
    	Integer matricola;
    	try {
    		matricola = Integer.parseInt(smatricola);
    	}catch(NumberFormatException nfe) {
    		txtRisultato.setText("Nel campo matricola puoi inserire solo caratteri numerici");
    		return;
    	}
    	Studente trovato = model.getStudente(matricola);
    	if(trovato == null) {
    		txtRisultato.setText("Non è presente nessuno studente con questa matricola");
    		return;
    	}
    	txtNome.setText(trovato.getNome());
    	txtCognome.setText(trovato.getCognome());
    	Corso corso = boxCorsi.getValue();
    	if(corso == null) {
    		txtRisultato.setText("Non hai selezionato nessun corso");
    		return;
    	}
    	Studente studente = this.model.getStudente(matricola);
    	if(this.model.StudentIscritto(matricola, corso)) {
    		txtRisultato.setText("Lo studente è già iscritto al corso");
    		return;
    	}else {
    		this.model.iscriviStudente(studente, corso);
    		txtRisultato.setText("Lo studente con matricola: "+studente.getMatricola()+" è stato iscritto al corso: "+corso.getNome()+" ("+corso.getCodIns()+")");
    	}

    }
    
    @FXML
    void doNomeCognome(ActionEvent event) {
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	String smatricola = txtMatricola.getText();
    	Integer matricola;
    	try {
    		matricola = Integer.parseInt(smatricola);
    	}catch(NumberFormatException nfe) {
    		txtRisultato.setText("Nel campo matricola puoi inserire solo caratteri numerici");
    		return;
    	}
    	Studente trovato = model.getStudente(matricola);
    	if(trovato == null) {
    		txtRisultato.setText("Non è presente nessuno studente con questa matricola");
    		return;
    	}
    	txtNome.setText(trovato.getNome());
    	txtCognome.setText(trovato.getCognome());
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	
    	txtCognome.clear();
    	txtNome.clear();
    	txtMatricola.clear();
    	txtRisultato.clear();
    	boxCorsi.getSelectionModel().clearSelection();

    }

    @FXML
    void doRicercaCorsiStudente(ActionEvent event) {
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	String smatricola = txtMatricola.getText();
    	Integer matricola;
    	try {
    		matricola = Integer.parseInt(smatricola);
    	}catch(NumberFormatException nfe) {
    		txtRisultato.setText("Nel campo matricola puoi inserire solo caratteri numerici");
    		return;
    	}
    	Studente trovato = model.getStudente(matricola);
    	if(trovato == null) {
    		txtRisultato.setText("Non è presente nessuno studente con questa matricola");
    		return;
    	}
    	txtNome.setText(trovato.getNome());
    	txtCognome.setText(trovato.getCognome());
    	List<Corso> corsiStudente = this.model.getCorsiStudente(matricola);
    	for(Corso a : corsiStudente) {
    		txtRisultato.appendText(String.format("%-20s", a.getCodIns())+String.format("%-20s", a.getCrediti())
    		+String.format("%-50s", a.getNome())+a.getPd()+"\n");
    	}
    }

    @FXML
    void doStampaIscritti(ActionEvent event) {
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	List<Studente> listaStudenti;
    	try {
    		listaStudenti = this.model.getStudentiCorso(boxCorsi.getValue());
    		
    		StringBuilder sb = new StringBuilder();
    		
    		for(Studente studente : listaStudenti) {
        		sb.append(String.format("%-10s ", studente.getMatricola()));
    			sb.append(String.format("%-30s ", studente.getCognome()));
    			sb.append(String.format("%-30s ", studente.getNome()));
    			sb.append(String.format("%-10s ", studente.getCds()));
    			sb.append("\n");
        	}
    		
    		txtRisultato.appendText(sb.toString());
    		
    	}catch(NullPointerException npe) {
    		txtRisultato.setText("Devi selezionare un corso!");
    		return;
    		
    	}
    	

    }

    @FXML
    void initialize() {
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrittiCorso != null : "fx:id=\"btnIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsi != null : "fx:id=\"btnCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        

    }
}
