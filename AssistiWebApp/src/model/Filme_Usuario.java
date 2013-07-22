package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Filme_Usuario {
	
	@Id
	private long id;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_filme")
	private Filme filme;
	
	@Column
	private boolean paraAssistir;
	private boolean assistido;
	private boolean tenho;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Filme getFilme() {
		return filme;
	}
	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	public boolean isParaAssistir() {
		return paraAssistir;
	}
	public void setParaAssistir(boolean paraAssistir) {
		this.paraAssistir = paraAssistir;
	}
	public boolean isAssistido() {
		return assistido;
	}
	public void setAssistido(boolean assistido) {
		this.assistido = assistido;
	}
	public boolean isTenho() {
		return tenho;
	}
	public void setTenho(boolean tenho) {
		this.tenho = tenho;
	}
	
	

}
