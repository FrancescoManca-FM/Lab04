package it.polito.tdp.lab04.DAO;

import java.util.List;

import it.polito.tdp.lab04.model.Studente;

public class TestDB {

	public static void main(String[] args) {

		/*
		 * 	This is a main to check the DB connection
		 */
		
		CorsoDAO cdao = new CorsoDAO();
		cdao.getTuttiICorsi();
		
		StudenteDAO sdao = new StudenteDAO();
		List<Studente> studenti = sdao.getTuttiStudenti();
		
		for(Studente a : studenti) {
			System.out.println(a.getMatricola());
		}
	}

}
