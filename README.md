# StormTopology

Before running this topology, the following should be taken into account:
- The version for the Hadoop dependencies should coincide with the one that is installed on the computer/server that the program is run on
- The file that are in the "resources" folder should be replaced with the ones that are found in the hadoop installation folders
- The Cassandra keyspace and table must be created before
- Ensure that the following process are started:
    - from the storm installation folder:
        - nimbus
        - ui
        - supervisor
    - zookeeper
    - Cassandra service
- In order to create the jar that can be submitted to storm, the following commands must be used:
    - mvn clean
    - mvn compile
    - mvn package
- The jar that must be submitted to Storm is storm-topology-1.0-SNAPSHOT.jar (as this is the one that includes all the needed dependencies)