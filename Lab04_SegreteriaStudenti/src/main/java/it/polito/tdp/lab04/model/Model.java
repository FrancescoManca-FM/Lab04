package it.polito.tdp.lab04.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO cdao;
	private StudenteDAO sdao;
	
	public Model() {
		cdao = new CorsoDAO();
		sdao = new StudenteDAO();
	}

	public List<Corso> getCorsi(){
		return cdao.getTuttiICorsi();
		
	}
	
	public List<Studente> getStudenti(){
		return sdao.getTuttiStudenti();
	}
	
	public Studente getStudente(Integer matricola) {
		List<Studente> studenti = this.getStudenti();
		for(Studente a : studenti ) {
			if(a.getMatricola().equals(matricola)) {
				return a;
			}
		}
		return null;
		
	}
	
	public List<Studente> getStudentiCorso(Corso corso){
		return cdao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiStudente(Integer matricola){
		return sdao.getCorsiStudente(matricola);
	}
	
	public boolean StudentIscritto(Integer matricola, Corso corso) {
		return sdao.isIscrittoCorso(matricola, corso);
	}
	
	public boolean iscriviStudente(Studente studente, Corso corso) {
		return cdao.inscriviStudenteACorso(studente, corso);
	}
}
