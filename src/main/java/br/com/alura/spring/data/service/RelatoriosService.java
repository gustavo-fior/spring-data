package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private final FuncionarioRepository funcionarioRepository;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {

		System.out.println("ListaFuncionarioPorNome(1), listaFuncionarioPorNomeParecido(2)");
		System.out.println("ListaFuncionarioPorNomeSalarioMaiorData(3), ListaFuncionarioPorDescricaoDaUnidade(4)");
		System.out.println("ListaFuncionarioPorDataContratacaoMaior(5),  listaFuncionarioPorSalario(6)");
		System.out.println("Para sair qualquer numero!");

		int acao = scanner.nextInt();

		if (acao == 1) {

			listaFuncionarioPorNome(scanner);

		} else if (acao == 2) {

			listaFuncionarioPorNomeLike(scanner);

		} else if (acao == 3) {

			listaFuncionarioPorNomeSalarioMaiorData(scanner);

		} else if (acao == 4) {

			listaFuncionarioPorDescricaoDaUnidade(scanner);

		} else if (acao == 5) {

			listaFuncionarioPorDataContratacaoMaior(scanner);

		} else if (acao == 6) {

			listaFuncionarioPorSalario(scanner);

		} else {
			System.out.println("Voltando");
		}

	}

	private void listaFuncionarioPorNome(Scanner scanner) {

		System.out.println("Qual o nome do funcionario?");
		String nome = scanner.next();

		List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);

		funcionarios.forEach(System.out::println);

	}

	private void listaFuncionarioPorNomeLike(Scanner scanner) {

		System.out.println("Qual o nome do funcionario?");
		String nome = scanner.next();
		nome = "%" + nome + "%";

		List<Funcionario> funcionarios = funcionarioRepository.findByNomeLike(nome);

		funcionarios.forEach(System.out::println);

	}

	private void listaFuncionarioPorNomeSalarioMaiorData(Scanner scanner) {

		System.out.println("Qual o nome do funcionario?");
		String nome = scanner.next();

		System.out.println("Qual o salario do funcionario?");
		BigDecimal salario = scanner.nextBigDecimal();

		System.out.println("Qual a data de contratacao?");
		String dataEmString = scanner.next();
		LocalDate data = LocalDate.parse(dataEmString, formatter);

		List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, data);

		funcionarios.forEach(System.out::println);

	}

	private void listaFuncionarioPorDescricaoDaUnidade(Scanner scanner) {

		System.out.println("Qual a descricao da unidade?");
		String descricao = scanner.next();

		List<Funcionario> funcionarios = funcionarioRepository.findByUnidadesDeTrabalho_Descricao(descricao);

		funcionarios.forEach(System.out::println);

	}

	private void listaFuncionarioPorDataContratacaoMaior(Scanner scanner) {

		System.out.println("Qual a data?");
		String data = scanner.next();
		LocalDate dataFormatada = LocalDate.parse(data, formatter);

		List<Funcionario> funcionarios = funcionarioRepository.findDataContratacaoMaior(dataFormatada);

		funcionarios.forEach(System.out::println);

	}
	
	private void listaFuncionarioPorSalario(Scanner scanner) {
		
		List<FuncionarioProjecao> funcionarios = funcionarioRepository.findFuncionarioSalario();
		
		// O Spring consegue converter para a interface
		funcionarios.forEach(f -> System.out.println("Funcionario:" + f.getId() + " | " + "Nome: " + f.getNome() + " | " + " Salario: " + f.getSalario()));
		
	}

}
