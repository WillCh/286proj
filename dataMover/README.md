This program will be run on two separate machines
On each machine, clone the files under kafka repo

--------------------------------------------------------------------------------------------------------------

On the server side, locate the file to be sent, denoted following as $YOURINPUTFILE, assume this to be file name following an absolute path
cd into the kafka directory, denoted as $KAFKA> 
Then Run the following commands

$KAFKA> ./gradlew jar
$KAFKA> ./gradlew srcJar
$KAFKA> bin/zookeeper-server-start.sh config/zookeeper.properties
$KAFKA> bin/kafka-server-start.sh config/server.properties
$KAFKA> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
$KAFKA> bin/kafka-file-producer.sh --broker-list localhost:9092 --topic test --input-file $YOURINPUTFILE

--------------------------------------------------------------------------------------------------------------

On the client side, locate the directory you want received file to be received. denoted as $YOUROUTPUTFILE
cd into the kafka directory, denoted as $KAFKA>

$KAFKA> bin/kafka-file-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning --output-file $YOUROUTPUTFILE


