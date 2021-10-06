package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.FuncionarioSpecification;

@Service
public class RelatorioFuncionarioDinamicoService {

	private final FuncionarioRepository funcionarioRepository;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public RelatorioFuncionarioDinamicoService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {

		System.out.println("Qual o nome do funcionario?");
		String nome = scanner.next();

		// Quando setamos uma das variaveis passadas para a specification para null
		// O Spring entende que ela nao deve fazer parte da consulta dinamica
		if (nome.equalsIgnoreCase("null")) {
			nome = null;
		}

		System.out.println("Qual o CPF do funcionario?");
		String cpf = scanner.next();

		// Quando setamos uma das variaveis passadas para a specification para null
		// O Spring entende que ela nao deve fazer parte da consulta dinamica
		if (cpf.equalsIgnoreCase("null")) {
			cpf = null;
		}

		System.out.println("Qual o salario do funcionario?");
		BigDecimal salario = scanner.nextBigDecimal();

		// Quando setamos uma das variaveis passadas para a specification para null
		// O Spring entende que ela nao deve fazer parte da consulta dinamica
		if (salario.equals(new BigDecimal(0))) {
			salario = null;
		}

		System.out.println("Qual a data de contratacao?");
		String dataString = scanner.next();

		LocalDate data;

		// Quando setamos uma das variaveis passadas para a specification para null
		// O Spring entende que ela nao deve fazer parte da consulta dinamica
		if (dataString.equalsIgnoreCase("null")) {
			data = null;
		} else {

			data = LocalDate.parse(dataString, formatter);

		}

		// O metodo findAll tambem pode receber uma Specification no construtor
		// Usamos o metodo where para dizer qual Specification queremos e pegamos o
		// metodo que colocamos nela com o parametro que o usuario passou
		List<Funcionario> funcionarios = funcionarioRepository
				.findAll(Specification.where(
						FuncionarioSpecification.nome(nome)
						.or(FuncionarioSpecification.cpf(cpf))
						.or(FuncionarioSpecification.salario(salario))
						.or(FuncionarioSpecification.dataContratacao(data))
						));
		
		funcionarios.forEach(System.out::println);

	}

}
