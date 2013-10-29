This guide walks you through the process of creating a Spring application.

What you'll build
-----------------

You'll build a Spring application.


What you'll need
----------------

 - About 15 minutes
 - A favorite text editor or IDE
 - [JDK 6][jdk] or later
 - [Gradle 1.8+][gradle] or [Maven 3.0+][mvn]
 - You can also import the code from this guide as well as view the web page directly into [Spring Tool Suite (STS)][gs-sts] and work your way through it from there.

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[gradle]: http://www.gradle.org/
[mvn]: http://maven.apache.org/download.cgi
[gs-sts]: /guides/gs/sts


How to complete this guide
--------------------------

Like all Spring's [Getting Started guides](/guides/gs), you can start from scratch and complete each step, or you can bypass basic setup steps that are already familiar to you. Either way, you end up with working code.

To **start from scratch**, move on to [Set up the project](#scratch).

To **skip the basics**, do the following:

 - [Download][zip] and unzip the source repository for this guide, or clone it using [Git][u-git]:
`git clone https://github.com/spring-guides/draft-gs-template.git`
 - cd into `draft-gs-template/initial`.
 - Jump ahead to [Create a resource controller](#initial).

**When you're finished**, you can check your results against the code in `draft-gs-template/complete`.
[zip]: https://github.com/spring-guides/draft-gs-template/archive/master.zip
[u-git]: /understanding/Git


<a name="scratch"></a>
Set up the project
------------------

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Gradle](http://gradle.org) and [Maven](https://maven.apache.org) is included here. If you're not familiar with either, refer to [Building Java Projects with Gradle](/guides/gs/gradle/) or [Building Java Projects with Maven](/guides/gs/maven).

### Create the directory structure

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

    └── src
        └── main
            └── java
                └── hello


### Create a Gradle build file
Below is the [initial Gradle build file](https://github.com/spring-guides/draft-gs-template/blob/master/initial/build.gradle). But you can also use Maven. The pom.xml file is included [right here](https://github.com/spring-guides/draft-gs-template/blob/master/initial/pom.xml). If you are using [Spring Tool Suite (STS)][gs-sts], you can import the guide directly.

`build.gradle`
```gradle
buildscript {
    repositories {
        maven { url "http://repo.spring.io/libs-milestone" }
        mavenLocal()
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'

jar {
    baseName = 'draft-gs-template'
    version =  '0.1.0'
}

repositories {
    mavenCentral()
    maven { url "http://repo.spring.io/libs-milestone" }
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web:0.5.0.M5")
}

task wrapper(type: Wrapper) {
    gradleVersion = '1.8'
}
```
    
[gs-sts]: /guides/gs/sts    

> **Note:** This guide is using [Spring Boot](/guides/gs/spring-boot/).


<a name="initial"></a>
Create a resource controller
----------------------------

Create a new controller for your Spring application:

`src/main/java/hello/GreetingController.java`
```java
package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public @ResponseBody String greeting() {
        return "Hello World";
    }
}
```

> **Note:** The above example does not specify `GET` vs. `PUT`, `POST`, and so forth, because `@RequestMapping` maps all HTTP operations by default. Use `@RequestMapping(method=GET)` to narrow this mapping.


Make the application executable
-------------------------------

Although it is possible to package this service as a traditional [WAR][u-war] file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring's support for embedding the [Tomcat][u-tomcat] servlet container as the HTTP runtime, instead of deploying to an external instance.

### Create an Application class

`src/main/java/hello/Application.java`
```java
package hello;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

The `main()` method defers to the [`SpringApplication`] helper class, providing `Application.class` as an argument to its `run()` method. This tells Spring to read the annotation metadata from `Application` and to manage it as a component in the [Spring application context][u-application-context].

The `@ComponentScan` annotation tells Spring to search recursively through the `hello` package and its children for classes marked directly or indirectly with Spring's [`@Component`] annotation. This directive ensures that Spring finds and registers the `GreetingController`, because it is marked with `@Controller`, which in turn is a kind of `@Component` annotation.

The [`@EnableAutoConfiguration`] annotation switches on reasonable default behaviors based on the content of your classpath. For example, because the application depends on the embeddable version of Tomcat (tomcat-embed-core.jar), a Tomcat server is set up and configured with reasonable defaults on your behalf. And because the application also depends on Spring MVC (spring-webmvc.jar), a Spring MVC [`DispatcherServlet`] is configured and registered for you — no `web.xml` necessary! Auto-configuration is a powerful, flexible mechanism. See the [API documentation][`@EnableAutoConfiguration`] for further details.

### Build an executable JAR

Now that your `Application` class is ready, you simply instruct the build system to create a single, executable jar containing everything. This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

Below are the Gradle steps, but if you are using Maven, you can find the updated pom.xml [right here](https://github.com/spring-guides/draft-gs-template/blob/master/complete/pom.xml) and build it by typing `mvn clean package`.

Update your Gradle `build.gradle` file's `buildscript` section, so that it looks like this:

```groovy
buildscript {
    repositories {
        maven { url "http://repo.spring.io/libs-snapshot" }
        mavenLocal()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:0.5.0.M4")
    }
}
```

Further down inside `build.gradle`, add the following to the list of applied plugins:

```groovy
apply plugin: 'spring-boot'
```
You can see the final version of `build.gradle` [right here]((https://github.com/spring-guides/draft-gs-template/blob/master/complete/build.gradle).

The [Spring Boot gradle plugin][spring-boot-gradle-plugin] collects all the jars on the classpath and builds a single "über-jar", which makes it more convenient to execute and transport your service.
It also searches for the `public static void main()` method to flag as a runnable class.

Now run the following command to produce a single executable JAR file containing all necessary dependency classes and resources:

```sh
$ ./gradlew build
```

If you are using Gradle, you can run the JAR by typing:

```sh
$ java -jar build/libs/draft-gs-template-0.1.0.jar
```

If you are using Maven, you can run the JAR by typing:

```sh
$ java -jar target/draft-gs-template-0.1.0.jar
```

[spring-boot-gradle-plugin]: https://github.com/spring-projects/spring-boot/tree/master/spring-boot-tools/spring-boot-gradle-plugin

> **Note:** The procedure above will create a runnable JAR. You can also opt to [build a classic WAR file](/guides/gs/convert-jar-to-war/) instead.

Run the service
-------------------
If you are using Gradle, you can run your service at the command line this way:

```sh
$ ./gradlew clean build && java -jar build/libs/draft-gs-template-0.1.0.jar
```

> **Note:** If you are using Maven, you can run your service by typing `mvn clean package && java -jar target/draft-gs-template-0.1.0.jar`.


Logging output is displayed. The service should be up and running within a few seconds.


Test the application
--------------------

Now that the application is running, you can test it.


Summary
-------

Congratulations! You've just developed a Spring application! 


[u-rest]: /understanding/REST
[u-json]: /understanding/JSON
[u-view-templates]: /understanding/view-templates
[u-war]: /understanding/WAR
[u-tomcat]: /understanding/Tomcat
[u-application-context]: /understanding/application-context
[`@Controller`]: http://docs.spring.io/spring/docs/3.2.4.RELEASE/javadoc-api/org/springframework/stereotype/Controller.html
[`SpringApplication`]: http://docs.spring.io/spring-boot/docs/0.5.0.M5/api/org/springframework/boot/SpringApplication.html
[`@EnableAutoConfiguration`]: http://docs.spring.io/spring-boot/docs/0.5.0.M5/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html
[`@Component`]: http://docs.spring.io/spring/docs/3.2.4.RELEASE/javadoc-api/org/springframework/stereotype/Component.html
[`@ResponseBody`]: http://docs.spring.io/spring/docs/3.2.4.RELEASE/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html
[`DispatcherServlet`]: http://docs.spring.io/spring/docs/3.2.4.RELEASE/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html

