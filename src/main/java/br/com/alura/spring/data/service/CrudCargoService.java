package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	// Como nao podemos dar new em uma interface, temos que usar a injecao de
	// dependecias no construtor dessa classe
	// Ai ele devolve uma instancia de CargoRepository e salva na variavel abaixo
	private final CargoRepository repository;
	
	

	public CrudCargoService(CargoRepository repository) {
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
		
		System.out.println("Removido");
		
	}

	// Logica para salvar um cargo no banco
	private void salvar(Scanner scanner) {

		System.out.println("Descricao do cargo:");
		String descricao = scanner.next();

		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);

		// Isso seria como chamar o metodo criado na classe DAO da JPA,
		// porem aqui o metodo ja esta pronto
		repository.save(cargo);

		System.out.println("Cargo salvo");

	}

	private void atualizar(Scanner scanner) {

		System.out.println("Qual cargo vc quer atualizar? (Use o Id)");
		
		Optional<Cargo> cargo = repository.findById(scanner.nextLong());
		
		System.out.println(cargo.get().getDescricao() + " - Cargo a ser alterado");
		System.out.println("Digite a descricao:");
		
		cargo.get().setDescricao(scanner.next());
		repository.save(cargo.get());
		
		System.out.println("Cargo atualizado");
		
	}
	
	private void visualizar() {
		
		Iterable<Cargo> cargos = repository.findAll();
		cargos.forEach(c -> System.out.println(c));
	}

}
