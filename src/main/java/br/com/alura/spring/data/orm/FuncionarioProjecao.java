package br.com.alura.spring.data.orm;

import java.math.BigDecimal;

// Essa interface mapeia os atributos que queremos para a nossa projecao
// Interface based Projection
// Pode ser feito com uma classe tambem, colocando os atributos, os getters e setters e um construtor recebendo os atributos na ordem da query
// Nesse caso a classe recebe o sufixo DTO (Data Transfer Object) e pode ser util quando precisamos de metodos especificos como de formatacao
public interface FuncionarioProjecao {

	Long getId();

	String getNome();

	BigDecimal getSalario();

}
