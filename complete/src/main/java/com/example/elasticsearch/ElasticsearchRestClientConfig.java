package com.example.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchRestClientConfig extends AbstractElasticsearchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchRestClientConfig.class);

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration.MaybeSecureClientConfigurationBuilder clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200");

        logger.info("Elasticsearch server [{}:{}] ssl[{}] auth[{}]"+ClientConfiguration.localhost());
        return RestClients.create(clientConfiguration.build()).rest();
    }
}
