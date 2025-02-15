# WIT Task

This is a coding challenge developed for [WIT Software](https://www.wit-software.com/). It consists of a RESTful API that provides the basic functionalities of a calculator. Built using Spring Boot, Maven, and Apache Kafka.

## Features

This program allows users to use a RESTful API for sum, subtraction, multiplication and division of two arbitrary precision signed decimal numbers.

### Essential requirements
- [x] Maven project with 4 modules: controller, model, service and utils
- [x] Spring Boot as the foundation for all required modules
- [x] Apache Kafka for communication between modules
- [x] Docker for environment deployment
- [x] Unit testing

### Bonus requirements
- [x] *SLF4J:* Add SLF4J logger support to both modules so that log messages are generated for every input/output event and errors. Configure an actual logging implementation at your choice (Log4J, Logback, etc.) with an appender to file (+2)
- [x] *Unique identifiers:* Assigning a unique identifier to each individual REST request and communication to their respective customers via a response header (+3)
- [ ] ~~*MDC Propagation:* Propagation of the request identifier through the MDC in the inter-module communication and inclusion of it in each logging line that concerns an HTTP request in all modules (+5)~~

## Installation

Setting up the docker environment:
```bash
docker-compose up
```

Setting up the program:
```bash
mvn clean install
```
---

## Usage

Running the application
```bash
mvn spring:boot run
```

Send GET Requests using the following template:

```
http://localhost:8080/api/<operation>?a=<first_number>&b=<second_number>
```

Here are some predefined examples:

| Operation |   Example   | HTTP Method |                                            REST API Query                                            |
|:---------:|:-----------:|:---------:|:----------------------------------------------------------------------------------------------------:|
|    Sum    | 1.23 + 4.56 |    GET    |      [link](http://localhost:8080/api/sum?a=1.23&b=4.56)      |
|  Subtract | 1.23 - 4.56 |    GET    | [ink](http://localhost:8080/api/subtract?a=1.23&b=4.56) |
|  Multiply | 1.23 * 4.56 |    GET    |      [link](http://localhost:8080/api/multiply?a=1.23&b=4.56)      |
|   Divide  | 1.23 / 4.56 |    GET    |      [link](http://localhost:8080/api/divide?a=1.23&b=4.56)      |

## Testing

### Testing with Unit Tests

```bash
mvn test
```

#### Testing the Kafka Service

| Operation |   Example   | HTTP Method |                                            REST API Query                                            |
|:---------:|:-----------:|:---------:|:----------------------------------------------------------------------------------------------------:|
|    Sum    | 1.23 + 4.56 |    POST    |      [link](http://localhost:8080/api/kafka/send?operation=sum&a=1.23&b=4.56)      |
|  Subtract | 1.23 - 4.56 |    POST    | [ink](http://localhost:8080/api/kafka/send?operation=subtract&a=1.23&b=4.56) |
|  Multiply | 1.23 * 4.56 |    POST    |      [link](http://localhost:8080/api/kafka/send?operation=multiply&a=1.23&b=4.56)      |
|   Divide  | 1.23 / 4.56 |    POST    |      [link](http://localhost:8080/api/kafka/send?operation=divide&a=1.23&b=4.56)      |


### Testing Kafka individually

Listing all *Kafka* topics:
```bash
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list
```

Creating a *Kafka* Topic: (my-topic)
```bash
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --create --topic my-topic --partitions 1 --replication-factor 1
```

Deleting a *Kafka* Topic: (my-topic)
```bash
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --delete --topic my-topic
```

Producing a *Kafka* Message: (on my-topic)
```bash
docker exec -it kafka kafka-console-producer --bootstrap-server localhost:9092 --topic my-topic
```

Consuming a *Kafka* Message: (on my-topic)
```bash
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic my-topic --from-beginning
```
