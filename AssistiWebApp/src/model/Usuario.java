package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import util.ExcluirJSON;

@XmlRootElement
@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String nome;
	private String senha;
	
	@Column(unique=true)
	private String email;
	private String login;
	
	@ExcluirJSON
	@OneToMany(mappedBy="usuario")
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Filme_Usuario> getFilmesUsuarios() {
		return filmesUsuarios;
	}
	public void setFilmesUsuarios(Set<Filme_Usuario> filmesUsuarios) {
		this.filmesUsuarios = filmesUsuarios;
	}
}
