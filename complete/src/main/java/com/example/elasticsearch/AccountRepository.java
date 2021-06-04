package com.example.elasticsearch;

import com.example.elasticsearch.entity.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AccountRepository extends ElasticsearchRepository<Account, Integer> {

}
