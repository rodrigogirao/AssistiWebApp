package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Filme {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String nome;
	private String sinopse;
	private Date dataDeLancamento;
	private Date tempoDoFilme;
	private String classificacao;
	private double pontuacao;
	
//	private Set<String> genero;
//	private Set<Ator> atores;
	
	@ManyToMany
	@JoinTable(name="Filme_Usuario",
		joinColumns = { @JoinColumn (name="id_filme") },
		inverseJoinColumns = { @JoinColumn (name="id_usuario")})
	private Set<Usuario> usuarios = new HashSet<Usuario>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSinopse() {
		return sinopse;
	}
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}
	public Date getDataDeLancamento() {
		return dataDeLancamento;
	}
	public void setDataDeLancamento(Date dataDeLancamento) {
		this.dataDeLancamento = dataDeLancamento;
	}
	public Date getTempoDoFilme() {
		return tempoDoFilme;
	}
	public void setTempoDoFilme(Date tempoDoFilme) {
		this.tempoDoFilme = tempoDoFilme;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	public double getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
