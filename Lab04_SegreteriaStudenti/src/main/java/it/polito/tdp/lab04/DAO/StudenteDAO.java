package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public List<Studente> getTuttiStudenti(){
		String sql = "SELECT * FROM studente";
		List<Studente> studenti = new LinkedList<>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Integer matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");
				
				studenti.add(new Studente(matricola, nome, cognome, cds));
			}
			
			conn.close();
			return studenti;
			
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<Corso> getCorsiStudente(Integer matricola){
		String sql ="SELECT c.codins, c.crediti, c.nome, c.pd \r\n" + 
				"FROM corso AS c, studente AS s, iscrizione AS i \r\n" + 
				"WHERE i.matricola = ? AND i.codins = c.codins\r\n" + 
				"GROUP BY c.codins";
		List<Corso> corsiStudente = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String codIns = rs.getString("codins");
				Integer crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				Integer pd = rs.getInt("pd");
				
				corsiStudente.add(new Corso(codIns, crediti, nome, pd));
				
			}
			
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
		
		return corsiStudente;
	}
	
	public boolean isIscrittoCorso(Integer matricola, Corso corso) {
		String sql = "SELECT i.matricola FROM studente AS s, corso AS c, iscrizione AS i\r\n" + 
				"WHERE i.matricola = ? AND i.codins = ?" + 
				"GROUP BY i.matricola";
		Integer matricolaS = 0;
		boolean iscritto = false;
		try{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			st.setString(2, corso.getCodIns());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				matricolaS= rs.getInt("matricola");
				if(matricola.equals(matricolaS)) {
					iscritto = true;
				}
			}
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return iscritto;
	}
	
	
}
