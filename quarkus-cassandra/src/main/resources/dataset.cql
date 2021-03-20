CREATE KEYSPACE IF NOT EXISTS testkeyspace WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

DROP TABLE IF EXISTS testkeyspace.person;

CREATE TABLE IF NOT EXISTS testkeyspace.person (
    id int,
    name text,
    gender text,
    age int,
    PRIMARY KEY (id)
);


INSERT into testkeyspace.person(id, name, age,gender) values (1,'Kelly',34,'Male');