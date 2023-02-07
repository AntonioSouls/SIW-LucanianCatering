package it.uniroma3.siw.spring.model;

import java.util.List;
import javax.persistence.*;


@Entity
public class Buffet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nome;
	
	private String descrizione;
	
	@ManyToOne
	private Chef chefProprietario;
	
	@OneToMany(mappedBy="buffet")
	private List<Piatto> listaPiatti;
	
	
	
	/* INIZIO DEI METODI */
	
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
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public Chef getChefProprietario() {
		return chefProprietario;
	}
	
	public void setChefProprietario(Chef chefProprietario) {
		this.chefProprietario = chefProprietario;
	}
	
	public List<Piatto> getListaPiatti() {
		return listaPiatti;
	}
	
	public void setListaPiatti(List<Piatto> listaPiatti) {
		this.listaPiatti = listaPiatti;
	}

	@Override
	public String toString() {
		return "Buffet [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", chefProprietario="
				+ chefProprietario + ", listaPiatti=" + listaPiatti + "]";
	}
	
	
}
