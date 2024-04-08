package br.com.alura.bytebank;

import java.util.Scanner;

import br.com.alura.bytebank.domain.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;
import br.com.alura.bytebank.domain.conta.ContaService;
import br.com.alura.bytebank.domain.conta.DadosAberturaConta;

public class BytebankApplication {

	private static ContaService service = new ContaService();
	private static Scanner teclado = new Scanner(System.in).useDelimiter("\n");

	public static void main(String[] args) {
		var opcao = exibirMenu();
		while (opcao != 7) {
			try {
				switch (opcao) {
					case 1:
						listarContas();
						break;
					case 2:
						abrirConta();
						break;
					case 3:
						encerrarConta();
						break;
					case 4:
						consultarSaldo();
						break;
					case 5:
						realizarSaque();
						break;
					case 6:
						realizarDeposito();
						break;
				}
			} catch (RegraDeNegocioException e) {
				System.out.println("Erro: " + e.getMessage());
				System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu");
				teclado.next();
			}
			opcao = exibirMenu();
		}
		System.out.println("Finalizando a aplicação.");
	}

	private static int exibirMenu() {
		System.out.println("""
				BYTEBANK - ESCOLHA UMA OPÇÃO:
				1 - Listar contas abertas
				2 - Abertura de conta
				3 - Encerramento de conta
				4 - Consultar saldo de uma conta
				5 - Realizar saque em uma conta
				6 - Realizar depósito em uma conta
				7 - Sair
				""");

		int opcao = 0;
		boolean inputValido = false;

		while (!inputValido) {
			try {
				String input = teclado.nextLine();
				opcao = Integer.parseInt(input);
				inputValido = true;
			} catch (NumberFormatException e) {
				System.out.println("Opção inválida. Por favor, insira um número válido.");
			}
		}

		return opcao;
	}

	private static void listarContas() {
		System.out.println("Contas cadastradas:");
		var contas = service.listarContasAbertas();
		contas.forEach(System.out::println);

		System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
		teclado.next();
	}

	private static void abrirConta() {
		System.out.println("Digite o número da conta:");
		int numeroDaConta = 0;
		boolean inputValido = false;

		while (!inputValido) {
			try {
				String input = teclado.nextLine();
				numeroDaConta = Integer.parseInt(input);
				inputValido = true;
			} catch (NumberFormatException e) {
				System.out.println("Número da conta inválido. Por favor, insira um número válido.");
			}
		}

		System.out.println("Digite o nome do cliente:");
		String nome = teclado.nextLine();

		System.out.println("Digite o cpf do cliente:");
		String cpf = teclado.nextLine();

		System.out.println("Digite o email do cliente:");
		String email = teclado.nextLine();

		service.abrir(new DadosAberturaConta(numeroDaConta, new DadosCadastroCliente(nome, cpf, email)));

		System.out.println("Conta aberta com sucesso!");
		System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
		teclado.next();
	}

	private static void encerrarConta() {
		System.out.println("Digite o número da conta:");
		var numeroDaConta = teclado.nextInt();

		service.encerrar(numeroDaConta);

		System.out.println("Conta encerrada com sucesso!");
		System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
		teclado.next();
	}

	private static void consultarSaldo() {
		System.out.println("Digite o número da conta:");
		var numeroDaConta = teclado.nextInt();
		var saldo = service.consultarSaldo(numeroDaConta);
		System.out.println("Saldo da conta: " + saldo);

		System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
		teclado.next();
	}

	private static void realizarSaque() {
		System.out.println("Digite o número da conta:");
		var numeroDaConta = teclado.nextInt();

		System.out.println("Digite o valor do saque:");
		var valor = teclado.nextBigDecimal();

		service.realizarSaque(numeroDaConta, valor);
		System.out.println("Saque realizado com sucesso!");
		System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
		teclado.next();
	}

	private static void realizarDeposito() {
		System.out.println("Digite o número da conta:");
		var numeroDaConta = teclado.nextInt();

		System.out.println("Digite o valor do depósito:");
		var valor = teclado.nextBigDecimal();

		service.realizarDeposito(numeroDaConta, valor);

		System.out.println("Depósito realizado com sucesso!");
		System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
		teclado.next();
	}
}
