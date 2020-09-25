package LorisAula18082020;

public class Dados {
	private Long   id;
	private String titulo;
	private Double numeros;
	private String descricao;


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Dados(Long id, String titulo, Double numeros, String descricao) {
		this.titulo = titulo;
		this.id = id;
		this.numeros = numeros;
		this.descricao = descricao;
	}
	
	public Dados(Long id, String titulo, Double numeros) {
		this.titulo = titulo;
		this.id = id;
		this.numeros = numeros;
	}
	

	public String getTitulo() {
		return titulo;
	}
	public Double getNumeros() {
		return numeros;
	}
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Dados [id=" + id + ", Titulo=" + titulo + ", Emissao reduzida em T=" + numeros + ", Descricao=" + descricao + " ]";
	}
}
