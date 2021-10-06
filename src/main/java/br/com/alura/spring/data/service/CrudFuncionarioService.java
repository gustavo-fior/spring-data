package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeDeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeDeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	// Como nao podemos dar new em uma interface, temos que usar a injecao de
	// dependecias no construtor dessa classe
	// Ai ele devolve uma instancia de CargoRepository e salva na variavel abaixo
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeDeTrabalhoRepository unidadeRepository;

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository,
			UnidadeDeTrabalhoRepository unidadeRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeRepository = unidadeRepository;
	}

	public void inicial(Scanner scanner) {

		System.out.println("Atualizar(1), Salvar(2), Visualizar(3) ou Apagar(4)?");
		System.out.println("Para sair qualquer numero!");

		int acao = scanner.nextInt();

		if (acao == 1) {
			atualizar(scanner);
		} else if (acao == 2) {
			salvar(scanner);
		} else if (acao == 3) {
			visualizar(scanner);
		} else if (acao == 4) {
			deletar(scanner);
		} else {
			System.out.println("Voltando");
		}

	}

	private void deletar(Scanner scanner) {

		System.out.println("Id que quer apagar:");

		long id = scanner.nextLong();

		funcionarioRepository.deleteById(id);

		System.out.println("Removido");

	}

	private void salvar(Scanner scanner) {

		System.out.println("Digite o nome");
		String nome = scanner.next();

		System.out.println("Digite o cpf");
		String cpf = scanner.next();

		System.out.println("Digite o salario");
		BigDecimal salario = new BigDecimal(scanner.next());

		System.out.println("Digite o cargoId");
		Long cargoId = scanner.nextLong();

		List<UnidadeDeTrabalho> unidades = unidade(scanner);

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(funcionario.setDataContratacaoParaHoje());
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadesDeTrabalho(unidades);

		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");

	}

	private List<UnidadeDeTrabalho> unidade(Scanner scanner) {
		Boolean isTrue = true;
		List<UnidadeDeTrabalho> unidades = new ArrayList<>();

		while (isTrue) {
			System.out.println("Digite o unidadeId (Para sair digite 0)");
			Long unidadeId = scanner.nextLong();

			if (unidadeId != 0) {
				Optional<UnidadeDeTrabalho> unidade = unidadeRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}

		return unidades;
	}

	private void atualizar(Scanner scanner) {

		System.out.println("Qual funcionario vc quer atualizar? (Use o Id)");

		Optional<Funcionario> funcionario = funcionarioRepository.findById(scanner.nextLong());

		System.out.println("Digite o nome:");
		funcionario.get().setNome(scanner.next());

		System.out.println("Digite o cpf:");
		funcionario.get().setCpf(scanner.next());

		System.out.println("Digite o salario:");
		funcionario.get().setSalario(new BigDecimal(scanner.next()));

		System.out.println("Digite o cargoId:");
		funcionario.get().setCargo(cargoRepository.findById(scanner.nextLong()).get());

		funcionarioRepository.save(funcionario.get());

		System.out.println("Funcionario atualizado");

	}

	private void visualizar(Scanner scanner) {

		// Estrutura da paginacao
		System.out.println("Qual pagina deseja visualizar?");
		Integer pagina = scanner.nextInt();

		// Pegando um pageable com pagina, tamanho da pagina e ordenacao com direcao
		// ascendente (A-Z) para String nome
		Pageable pageable = PageRequest.of(pagina, 10, Sort.by(Sort.Direction.ASC, "nome"));

		// No construtor do findAll podemos passar um pageable, apenas um sort ou nenhum
		// Com o pageable o metodo retorna uma Page
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

		System.out.println("Total de Paginas = " + funcionarios.getTotalPages());
		System.out.println("Total de Funcionarios = " + funcionarios.getTotalElements());
		System.out.println("Pagina Atual = " + funcionarios.getNumber());

		funcionarios.forEach(f -> System.out.println(f));
	}

}
