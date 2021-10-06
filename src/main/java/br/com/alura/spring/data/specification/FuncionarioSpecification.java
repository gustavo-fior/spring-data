package br.com.alura.spring.data.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.spring.data.orm.Funcionario;

public class FuncionarioSpecification {

	// O nome do metodo eh baseado no atributo que vc quer que o Spring faca
	// validacao para vc
	public static Specification<Funcionario> nome(String nome) {
		// Vc so precisa chamar as variaveis, o Spring cria elas pra vc
		// O criteriaBuilder usa o metodo like, para o parametro nome do root e com o
		// padrao entre %
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
	}

	// A estrutura dos metodos fica praticamente igual
	public static Specification<Funcionario> cpf(String cpf) {
		// Aqui temos um campo EQUALS, com o criteria chamando o metodo equal para
		// comparar com o cpf que o root retorna do banco
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("cpf"), cpf);
	}

	// A estrutura dos metodos fica praticamente igual
	public static Specification<Funcionario> salario(BigDecimal salario) {
		// Aqui temos um MAIOR QUE o salario informado
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("salario"), salario);
	}

	// A estrutura dos metodos fica praticamente igual
	public static Specification<Funcionario> dataContratacao(LocalDate data) {
		// Aqui temos um MAIOR QUE a data de contratacao informada
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("dataContratacao"), data);
	}

}
