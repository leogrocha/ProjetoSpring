package com.example.cursomc;

import java.util.Arrays;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.domain.Cidade;
import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.Endereco;
import com.example.cursomc.domain.Estado;
import com.example.cursomc.domain.Produto;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.repositories.CidadeRepository;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.repositories.EnderecoRepository;
import com.example.cursomc.repositories.EstadoRepository;
import com.example.cursomc.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		// Instanciando cidades e já informando a relação com seus estados.
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		Cliente cli1 = new  Cliente(null, "Maria Silva", "maria@gmail.com","12345678899", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("35988774455", "35988554411"));

		Endereco e1 = new Endereco(null, "Rua Flores", 300, "Apto 30", "Jardim", "37950000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", 105, "Sala 800", "Centro", "37950000", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		// Informando quais os produtos que estão relacionados com as categorias
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		//Informando quais as categorias que estão relacionadas com os produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		// Informando quais os estados que estão relacionados com a cidade
		est1.getCidades().addAll(Arrays.asList(c1));
		est1.getCidades().addAll(Arrays.asList(c2, c3));

		

		// Salvando os estados no banco
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}
