package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Produto;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String caminho = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

			List<Produto> lista = new ArrayList<>();

			String linha = br.readLine();
			while (linha != null) {
				String[] campos = linha.split(",");
				lista.add(new Produto(campos[0], Double.parseDouble(campos[1])));
				linha = br.readLine();
			}

			double pMedio = lista.stream()
					.map(p -> p.getPreco())
					.reduce(0.0, (x, y) -> x + y) / lista.size();

			System.out.println("Average price: " + String.format("%.2f", pMedio));

			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

			List<String> nomes = lista.stream()
					.filter(p -> p.getPreco() < pMedio)
					.map(p -> p.getNome())
					.sorted(comp.reversed()) //inverte a ordem de comparação
					.collect(Collectors.toList());

			nomes.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		sc.close();

	}

}
