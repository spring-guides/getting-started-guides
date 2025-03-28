package com.example.elasticsearch;

import com.example.elasticsearch.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Iterator;

@EnableElasticsearchRepositories (basePackageClasses = AccountRepository.class)
@SpringBootApplication
public class ElasticsearchApplication implements CommandLineRunner {

	@Autowired  private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//clear all the documents in the index.
		{
			accountRepository.deleteAll();
		}

		System.out.println("Count of documents in Accounts Index");
		System.out.println("-------------------------------");
		System.out.println(accountRepository.count());

		accountRepository.save(new Account(5,32533.0,"Aravind","Putrevu",25,"M","800 W El Camino Real","Elastic","aravind@example.com","Sanfrancisco","CA"));
		accountRepository.save(new Account(9,65537.0,"","Guduru",28,"F","800 W El Camino Real","Rubrik","siri@example.com","Sanfrancisco","CA"));

		System.out.println("Find Accounts by ID");
		System.out.println("-------------------------------");
		System.out.println(accountRepository.findById(5).get().toString());
		System.out.println("\n");

		System.out.println("Count of documents in Accounts Index");
		System.out.println("-------------------------------");
		System.out.println(accountRepository.count());

		System.out.println("-------------------------------");
		System.out.println("List all Accounts");
		System.out.println("-------------------------------");
		Iterator it = accountRepository.findAll().iterator();
		while (it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
}
