### Spring Boot Console (non-web) Workspan Cloner

This is a console application written in Java and Spring Boot, 
which designed to take a JSON file of Entities and its related Entities
(links between entities) as well as numerical ID of the entity as 
the starting point and clone them.
Process is cloning all the entities that are related to that node
(direct or inderect descendents).
The output of the process is also a JSON file with new relationship
graph connected to initial input graph.

- Entity Node representation
```
ID
Name
Description (optional)
```
- Related Entities are represented as links from one Entity to another. A link entry has the following fields:
```
From Entity ID
To Entity ID
```
*Note:* Links are directional, i.e. they go from one entity to another. You can think of this as a directed graph 
where Entities are the vertices and the Links are edges.

#### Specifications:

The program take in as an input as JSON file representing the entities and the links in the system and the id of 
the entity that needs to be cloned. These inputs should be taken in on the command line as follows:
```text
<program_name> <inputfile> <entityid>
```
Here is an example how to run a program from console or terminal:
```text
./bin/cloner.sh ./input.json 5
```
#### How to Compile and Execute:
```text
mvn clean compile package
```

execute via Java:
```text
java -jar target/qv2-1.0.0.jar ./input.json 5
```

execute via script:
```text
./bin/cloner.sh ./input.json 3
```

The JSON file contains the following information:

> entities: A list of all the entities in the system. 
> You can assume that the ids are unique integers.
> links: a list of all the links in the system

#### Sample Input Json File example:

```json
{
	"entities": [{
		"entity_id": 3,
		"name": "EntityA"
	}, {
		"entity_id": 5,
		"name": "EntityB"
	}, {
		"entity_id": 7,
		"name": "EntityC",
		"description": "More details about entity C"
	}, {
		"entity_id": 11,
		"name": "EntityD"
	}],
	"links": [{
		"from": 3,
		"to": 5
	}, {
		"from": 3,
		"to": 7
	}, {
		"from": 5,
		"to": 7
	}, {
		"from": 7,
		"to": 11
	}]
}
```

Here is the simple output example from the above example with 
starting point as Node ID = 5

```json
{
	"entities": [{
		"entity_id": 3,
		"name": "EntityA"
	}, {
		"entity_id": 5,
		"name": "EntityB"
	}, {
		"entity_id": 7,
		"name": "EntityC",
		"description": "More details about entity C"
	}, {
		"entity_id": 11,
		"name": "EntityD"
	}, {
		"entity_id": 13,
		"name": "EntityB"
	}, {
		"entity_id": 17,
		"name": "EntityC",
		"description": "More details about entity C"
	}, {
		"entity_id": 19,
		"name": "EntityD"
	}],
	"links": [{
		"from": 3,
		"to": 5
	}, {
		"from": 3,
		"to": 7
	}, {
		"from": 5,
		"to": 7
	}, {
		"from": 7,
		"to": 11
	}, {
		"from": 3,
		"to": 13
	}, {
		"from": 13,
		"to": 17
	}, {
		"from": 17,
		"to": 19
	}]
}
```