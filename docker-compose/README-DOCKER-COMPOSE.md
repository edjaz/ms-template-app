# JHipster generated Docker-Compose configuration

## Usage

Launch all your infrastructure by running: `docker-compose up -d`.

## Configured Docker services

### Service registry and configuration server:
- [JHipster Registry](http://localhost:8761)

### Applications and dependencies:
- authent (uaa application)
- authent's mysql database
- backoffice (gateway application)
- backoffice's postgresql database
- customer (microservice application)
- customer's postgresql database
- document (microservice application)
- document's mongodb database
- document's elasticsearch search engine
- frontoffice (gateway application)
- frontoffice's postgresql database
- notification (microservice application)
- notification's mongodb database

### Additional Services:

- Kafka
- Zookeeper
- [JHipster Console](http://localhost:5601)
- [Zipkin](http://localhost:9411)
