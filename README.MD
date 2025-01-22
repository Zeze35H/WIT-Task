# WIT Task

This is a coding challenge developed for [WIT Software](https://www.wit-software.com/). It consists of a RESTful API that provides the basic functionalities of a calculator. Built using Spring Boot, Maven, and Apache Kafka.
## Installation

```bash
mvn clean install
```
Setting up the docker environment:

```bash
docker-compose up
```

---

## Usage

Running the application
```bash
mvn spring:boot run
```

## Testing

### Testing the program

```bash
mvn test
```


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
