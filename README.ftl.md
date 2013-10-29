<#assign project_id="draft-gs-template">
<#assign spring_version="3.2.4.RELEASE">
<#assign spring_boot_version="0.5.0.M5">
This guide walks you through the process of creating a Spring application.

What you'll build
-----------------

You'll build a Spring application.


What you'll need
----------------

 - About 15 minutes
 - <@prereq_editor_jdk_buildtools/>


## <@how_to_complete_this_guide jump_ahead='Create a resource controller'/>


<a name="scratch"></a>
Set up the project
------------------

<@build_system_intro/>

<@create_directory_structure_hello/>


<@create_both_builds/>

<@bootstrap_starter_pom_disclaimer/>


<a name="initial"></a>
Create a resource controller
----------------------------

Create a new controller for your Spring application:

    <@snippet path="src/main/java/hello/GreetingController.java" prefix="complete"/>

> **Note:** The above example does not specify `GET` vs. `PUT`, `POST`, and so forth, because `@RequestMapping` maps all HTTP operations by default. Use `@RequestMapping(method=GET)` to narrow this mapping.


Make the application executable
-------------------------------

Although it is possible to package this service as a traditional [WAR][u-war] file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring's support for embedding the [Tomcat][u-tomcat] servlet container as the HTTP runtime, instead of deploying to an external instance.

### Create an Application class

    <@snippet path="src/main/java/hello/Application.java" prefix="complete"/>

The `main()` method defers to the [`SpringApplication`] helper class, providing `Application.class` as an argument to its `run()` method. This tells Spring to read the annotation metadata from `Application` and to manage it as a component in the [Spring application context][u-application-context].

The `@ComponentScan` annotation tells Spring to search recursively through the `hello` package and its children for classes marked directly or indirectly with Spring's [`@Component`] annotation. This directive ensures that Spring finds and registers the `GreetingController`, because it is marked with `@Controller`, which in turn is a kind of `@Component` annotation.

The [`@EnableAutoConfiguration`] annotation switches on reasonable default behaviors based on the content of your classpath. For example, because the application depends on the embeddable version of Tomcat (tomcat-embed-core.jar), a Tomcat server is set up and configured with reasonable defaults on your behalf. And because the application also depends on Spring MVC (spring-webmvc.jar), a Spring MVC [`DispatcherServlet`] is configured and registered for you — no `web.xml` necessary! Auto-configuration is a powerful, flexible mechanism. See the [API documentation][`@EnableAutoConfiguration`] for further details.

<@build_an_executable_jar_subhead/>

<@build_an_executable_jar_with_both/>

<@run_the_application_with_both module="service"/>

Logging output is displayed. The service should be up and running within a few seconds.


Test the application
--------------------

Now that the application is running, you can test it.


Summary
-------

Congratulations! You've just developed a Spring application! 


<@u_rest/>
<@u_json/>
<@u_view_templates/>
<@u_war/>
<@u_tomcat/>
<@u_application_context/>
[`@Controller`]: http://docs.spring.io/spring/docs/${spring_version}/javadoc-api/org/springframework/stereotype/Controller.html
[`SpringApplication`]: http://docs.spring.io/spring-boot/docs/${spring_boot_version}/api/org/springframework/boot/SpringApplication.html
[`@EnableAutoConfiguration`]: http://docs.spring.io/spring-boot/docs/${spring_boot_version}/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html
[`@Component`]: http://docs.spring.io/spring/docs/${spring_version}/javadoc-api/org/springframework/stereotype/Component.html
[`@ResponseBody`]: http://docs.spring.io/spring/docs/${spring_version}/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html
[`DispatcherServlet`]: http://docs.spring.io/spring/docs/${spring_version}/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html

