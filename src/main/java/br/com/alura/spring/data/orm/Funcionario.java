package br.com.alura.spring.data.orm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private BigDecimal salario;
	private LocalDate dataContratacao;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cargo cargo;

	@ManyToMany
	@JoinTable(name = "funcionario_unidades", joinColumns = {
			@JoinColumn(name = "fk_funcionario") }, inverseJoinColumns = { @JoinColumn(name = "fk_unidade") })
	private List<UnidadeDeTrabalho> unidadesDeTrabalho;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public LocalDate getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(LocalDate dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public LocalDate setDataContratacaoParaHoje() {
		return this.dataContratacao = LocalDate.now();
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<UnidadeDeTrabalho> getUnidadesDeTrabalho() {
		return unidadesDeTrabalho;
	}

	public void setUnidadesDeTrabalho(List<UnidadeDeTrabalho> unidadesDeTrabalho) {
		this.unidadesDeTrabalho = unidadesDeTrabalho;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", salario=" + salario
				+ ", dataContratacao=" + dataContratacao + "]";
	}

}
