# Codota maven plugin
Maven plugin for [indexing java/scala code in Codota](https://www.codota.com/index-your-repo)

You can easily send your Java code for indexing by adding this simple maven plugin which collects your artifacts and uploads them to your secure private Codota account.

### Installing the plugin
* The plugin is available [here](http://assets.codota.com/codota-maven-plugin/codota-uploader-1.0.jar) 
* Install the codota plugin in your maven by running the following commands:
```
wget http://assets.codota.com/codota-maven-plugin/codota-uploader-1.0.jar
mvn install:install-file -Dfile=codota-uploader-1.0.jar -DgroupId=com.codota -DartifactId=uploader -Dversion=1.0 -Dpackaging=jar
```

Alternatively, you can clone this repo, build the from source and install the plugin:

    mvn clean package install

### Uploading your artifacts for analysis
To submit your code follow these simple steps:
* [Create your private account at Codota](https://www.codota.com/submit-repo/maven)
* Paste the following in your pom.xml under <plugins> - make sure to use the secure token you receive from Codota. Please make sure to keep your token private and secure.
```
<plugin>
    <groupId>com.codota</groupId>
    <artifactId>uploader</artifactId>
    <version>1.0</version>
    <configuration>
        <endpoint>https://upload.codota.com/artifacts</endpoint>
        <token>----------- *put your secure token here* -----------<token>
    </configuration>
    <executions>
        <execution>
            <id>codota-upload</id>
            <phase>package</phase>
            <!-- bind to the packaging phase -->
            <goals>
                <goal>upload</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
* Build your project including sources by running the following command in your project directory
```
mvn clean source:jar source:test-jar jar:test-jar package -DskipAssembly -DskipTests
```
  Indexing usually takes less than an hour - you'll get an email once it's ready
