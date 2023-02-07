package it.uniroma3.siw.spring.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "buffet_id"}))
public class Piatto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private String descrizione;
	
	@OneToMany
	private List<Ingrediente> listaIngredienti;
	
	@ManyToOne
	private Buffet buffet;
	
	
	
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
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public List<Ingrediente> getListaIngredienti(){
		return this.listaIngredienti;
	}
	
	public void setListaIngredienti(List<Ingrediente> listaIngredienti) {
		this.listaIngredienti = listaIngredienti;
	}

	@Override
	public String toString() {
		return "Piatto [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", listaIngredienti="
				+ listaIngredienti + "]";
	}

	public Buffet getBuffet() {
		return buffet;
	}

	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}
	
	
	
}
