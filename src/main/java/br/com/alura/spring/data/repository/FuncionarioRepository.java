package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

// Seria como a classe DAO da JPA, so que com metodos prontos e alguns criados
@Repository
// Crud repository precisa do tipo do Objeto que vamos fazer o repository e o tipo do id
// Depois trocamos para PagingAndSortingRepository para paginacao e ordenacao (ele extende crud repository)
// Depois extendemos a interface JpaSpecificationExecuter para podermos fazer consultas dinamicas pelo Spring
// Ele utiliza a Criteria API por baixo dos panos
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario>  {

	// Exemplo de Derived Query
	// Eh necessario apenas o findBy e o nome do atributo da classe
	// Outros comandos para o Spring gerar a consulta:
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	List<Funcionario> findByNome(String nome);

	// Aqui para uma pesquisa com o like
	// (Lembrando que as % devem ser inseridas no comeco e no final,
	// mas o metodo que EU criei faz isso ja)
	List<Funcionario> findByNomeLike(String nome);

	// Derived Query iria ficar com o nome do metodo muito grande, entao usamos JPQL
	// Anotamos com @Query e passamos a query
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND dataContratacao = :data")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate data);

	// Aqui a entidade UnidadesDeTrabalho tem 3 palavras, entao temos que colocar um
	// underline entre seu nome e o do atributo que estamos usando como parametro
	List<Funcionario> findByUnidadesDeTrabalho_Descricao(String descricao);
	
	// Agora vamos fazer uma query nativa, completando as 3 formas de queries no Spring Data
	// Precisamos informar que eh uma nativeQuery, se nao o Spring pensa que eh JPQL
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true)
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	// Essa query se assemelha a query que retorna um relatorio em JPA, no Spring isso eh chamado de projecao
	@Query(value = "SELECT f.id, f.nome, f.salario FROM Funcionarios f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();

}
