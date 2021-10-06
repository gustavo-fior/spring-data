package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeDeTrabalho;
import br.com.alura.spring.data.repository.UnidadeDeTrabalhoRepository;

@Service
public class CrudUnidadeDeTrabalhoService {

	// Como nao podemos dar new em uma interface, temos que usar a injecao de
	// dependecias no construtor dessa classe
	// Ai ele devolve uma instancia de CargoRepository e salva na variavel abaixo
	// Seria como instanciar a classe DAO para podermos chamar os metodos
	private final UnidadeDeTrabalhoRepository repository;
	
	

	public CrudUnidadeDeTrabalhoService(UnidadeDeTrabalhoRepository repository) {
		this.repository = repository;
	}

	public void inicial(Scanner scanner) {

		System.out.println("Atualizar(1), Salvar(2), Visualizar(3) ou Apagar(4)?");
		System.out.println("Para sair qualquer numero!");

		int acao = scanner.nextInt();
		
		if (acao == 1) {
			atualizar(scanner);
		} else if (acao == 2){
			salvar(scanner);
		} else if (acao == 3){
			visualizar();
		} else if (acao == 4){
			deletar(scanner);
		} else {
			System.out.println("Voltando");
		}

	}

	private void deletar(Scanner scanner) {
		
		System.out.println("Id que quer apagar:");
		
		long id = scanner.nextLong();
		
		repository.deleteById(id);
		
		System.out.println("Removida");
		
	}

	private void salvar(Scanner scanner) {

		System.out.println("Descricao da unidade:");
		String descricao = scanner.next();
		
		System.out.println("Endereco da unidade:");
		String endereco = scanner.next();
		

		UnidadeDeTrabalho udt = new UnidadeDeTrabalho();

		udt.setDescricao(descricao);
		udt.setEndereco(endereco);
		
		// Isso seria como chamar o metodo criado na classe DAO da JPA,
		// porem aqui o metodo ja esta pronto
		repository.save(udt);

		System.out.println("Unidade salva");

	}

	private void atualizar(Scanner scanner) {

		System.out.println("Qual unidade vc quer atualizar? (Use o Id)");
		
		Optional<UnidadeDeTrabalho> unidade = repository.findById(scanner.nextLong());
		
		System.out.println("Digite a descricao:");
		unidade.get().setDescricao(scanner.next());
		
		System.out.println("Digite o endereco:");
		unidade.get().setEndereco(scanner.next());
		
		repository.save(unidade.get());
		
		System.out.println("Unidade atualizada");
		
	}
	
	private void visualizar() {
		
		Iterable<UnidadeDeTrabalho> unidades = repository.findAll();
		unidades.forEach(u -> System.out.println(u));
	}

}
