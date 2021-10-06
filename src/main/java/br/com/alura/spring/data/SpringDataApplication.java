package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CrudCargoService;
import br.com.alura.spring.data.service.CrudFuncionarioService;
import br.com.alura.spring.data.service.CrudUnidadeDeTrabalhoService;
import br.com.alura.spring.data.service.RelatorioFuncionarioDinamicoService;
import br.com.alura.spring.data.service.RelatoriosService;

// Varre a aplicacao para ler as anotacoes do Spring
// CommandLineRunner implementa o metodo run, que eh executado apos o main
@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	// Todas as classes de service que fazem o meio entre os repositorys(DAOs) e
	// essa "view"
	private final CrudCargoService cargoService;
	private final CrudFuncionarioService funcionarioService;
	private final CrudUnidadeDeTrabalhoService unidadeService;
	private final RelatoriosService relatoriosService;
	private final RelatorioFuncionarioDinamicoService funcionarioDinamicoService;

	// Variavel para o loop da classe
	private Boolean system = true;

	// Injecao de dependencias
	public SpringDataApplication(CrudCargoService cargoService, CrudFuncionarioService funcionarioService,
			CrudUnidadeDeTrabalhoService unidadeService, RelatoriosService relatoriosService,
			RelatorioFuncionarioDinamicoService funcionarioDinamicoService) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeService = unidadeService;
		this.relatoriosService = relatoriosService;
		this.funcionarioDinamicoService = funcionarioDinamicoService;
	}

	public static void main(String[] args) {
		// Inicia o framework junto com a aplicação
		SpringApplication.run(SpringDataApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		Scanner scanner = new Scanner(System.in);

		while (system) {
			System.out.println("Qual acao vc quer executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Funcionario");
			System.out.println("3 - Unidade de Trabalho");
			System.out.println("4 - Relatorios");
			System.out.println("5 - Relatorios dinamicos");

			int action = scanner.nextInt();

			switch (action) {

			case 0:
				system = false;
				break;

			case 1:
				cargoService.inicial(scanner);
				break;

			case 2:
				funcionarioService.inicial(scanner);
				break;

			case 3:
				unidadeService.inicial(scanner);
				break;
				
			case 4:
				relatoriosService.inicial(scanner);
				break;
				
			case 5:
				funcionarioDinamicoService.inicial(scanner);
				break;

			default:
				system = false;
				break;
			}
		}

	}

}
