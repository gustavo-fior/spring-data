package br.com.alura.spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Cargo;

// Seria como a classe DAO da JPA, so que com metodos prontos
@Repository
// Crud repository precisa do tipo do Objeto que vamos fazer o repository e o tipo do id
public interface CargoRepository extends CrudRepository<Cargo, Long> {

	
	
}
