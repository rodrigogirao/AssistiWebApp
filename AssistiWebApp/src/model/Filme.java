package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import util.ExcluirJSON;

@XmlRootElement
@Entity
public class Filme {

	@Id
	private long id;

	@Column(columnDefinition="text")
	private String sinopse;
	@Column
	private String nome;
	private String dataDeLancamento;
	private String tempoDoFilme;
	private String classificacao;
	private String pontuacao;
	
//	private Set<String> genero;
//	private Set<Ator> atores;
	
	@ExcluirJSON
	@OneToMany(mappedBy="filme")
	private Set<Filme_Usuario> filmesUsuarios = new HashSet<Filme_Usuario>();
	
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
	public String getDataDeLancamento() {
		return dataDeLancamento;
	}
	public void setDataDeLancamento(String dataDeLancamento) {
		this.dataDeLancamento = dataDeLancamento;
	}
	public String getTempoDoFilme() {
		return tempoDoFilme;
	}
	public void setTempoDoFilme(String tempoDoFilme) {
		this.tempoDoFilme = tempoDoFilme;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	public String getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(String pontuacao) {
		this.pontuacao = pontuacao;
	}
	public Set<Filme_Usuario> getFilmesUsuarios() {
		return filmesUsuarios;
	}
	public void setFilmesUsuarios(Set<Filme_Usuario> filmesUsuarios) {
		this.filmesUsuarios = filmesUsuarios;
	}
}
