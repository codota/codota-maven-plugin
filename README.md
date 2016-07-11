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
* Build your project including sources and send to Codota for indexing by running the following command in your project directory
    
    ```
    mvn clean source:jar source:test-jar jar:test-jar package com.codota:uploader:1.0:upload -Dcodota.token={your_token_here} -Dcodota.endpoint=https://upload.codota.com/artifacts -DskipAssembly -DskipTests -Dmaven.javadoc.skip=true -Dgpg.skip
    ```
    
    Make sure to use the secure token you receive from Codota. Please make sure to keep your token private and secure.
    Indexing usually takes less than an hour - you'll get an email once it's ready
