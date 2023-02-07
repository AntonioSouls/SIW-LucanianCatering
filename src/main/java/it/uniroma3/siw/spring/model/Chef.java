package it.uniroma3.siw.spring.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "cognome", "nazionalita"}))
public class Chef {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private String cognome;
	private String nazionalita;
	
	@OneToMany(mappedBy = "chefProprietario", cascade = {CascadeType.ALL},
			   fetch = FetchType.EAGER)
	private List<Buffet> listaBuffet;
	
	
	/* INIZIO METODI */
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getNazionalita() {
		return nazionalita;
	}
	
	public void setNazionalita(String nazionalità) {
		this.nazionalita = nazionalità;
	}
	
	public List<Buffet> getListaBuffet() {
		return listaBuffet;
	}
	
	public void setListaBuffet(List<Buffet> listaBuffet) {
		this.listaBuffet = listaBuffet;
	}
}
