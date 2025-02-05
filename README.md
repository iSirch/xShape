# Genrate pom.xml, from parent directory:
```
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DgroupId=xshape -DartifactId=xshape -Dversion=0.1.3 -Dpackage=xshape
```
# Run code
```
mvn clean package  
mvn exec:java
```

# Envoyer au prof
```
mvn clean compile assembly:single
```