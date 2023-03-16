# spring-cloud-contract-poc

**_Consumer-Driven Contract Testing With Spring Cloud Contract_**
    Implement CDC testing with Spring Cloud Contract and two sample Spring 
    Boot applications to understand the communication between microservices.
    In this quick article, we'll explore writing producer and consumer side 
    test cases for Spring Cloud Contract through an HTTP interaction.


**_Introduction_**
    Spring Cloud Contract is a project that simply put, helps us write Consumer-Driven 
    Contracts (CDC).
    This ensures the contract between a Producer and a Consumer, 
    in a distributed system – for both HTTP-based and message-based interactions.

**_Producer/Provider_**
    The producer is a service that exposes an API (e.g. rest endpoint) or sends 
    a message (e.g. Kafka Producer which publishes the message to Kafka Topic)

**_Consumer_**
    The consumer is a service that consumes the API that is exposed by the 
    producer or listens to a message from the producer (e.g. Kafka Consumer which 
    consumes the message from Kafka Topic)

**_Contract_**
    The contract is an agreement between the producer and consumer how the 
    API/message will look like.
        What endpoints can we use?
        What input do the endpoints take?
        What does the output look like?

**_Consumer-Driven Contract_**
    Consumer-driven contract (CDD) is an approach where the consumer drives 
    the changes in the API of the producer.
    Consumer-driven contract testing is an approach to formalize above mentioned 
    expectations into a contract between each consumer-provider pair. 
    Once the contract is established between Provider and Consumer, 
    this ensures that the contract will not break suddenly.

**_Spring Cloud Contract_**
    Spring Cloud Contract is a project of spring-cloud that helps end-users in 
    successfully implementing the Consumer Driven Contracts (CDC) approach. 
    The Spring Cloud Contract Verifier is used as a tool that enables the development 
    of Consumer Driven Contracts. Spring Cloud Contract Verifier is used with 
    Contract Definition Language (DSL) written in Groovy or YAML.

**_What is contract testing?_**
    Contract testing is a set of automated tests that verify the interaction
    between two services (either through REST API or asynchronous message systems). 
    It is different from unit and integration tests (which verify the operation
    within one service) and end-to-end test (which aims to validate the operation 
    across UI and possibly multiple internal services).

**_Setup the producer_**
    add a  API endpoint to the producer:

<img alt="ProducerApi.png" height="300" src="D:\POCS\spring-cloud-contract-poc\producer-contract-poc\src\main\resources\readme-images\ProducerApi.png" title="Producer Api details" width="1000"/>
    
Now to generate a cloud contract for this API endpoint,
we need to add the following dependency:

<img alt="Producer-Verifier-dependency-details.png" height="300" src="D:\POCS\spring-cloud-contract-poc\producer-contract-poc\src\main\resources\readme-images\Producer-Verifier-dependency-details.png" title="Producer-Verifier-dependency-details" width="1000"/>

    Note that we can add a base test class IntegrationTestBase, which will later 
    be used by Spring to generate test classes.

Now, let's add a contract definition for this endpoint:

    find the contract test here (spring-cloud-contract-poc\producer-contract-poc\src\test\resources\contracts\shouldReturnExistingStudent.groovy)

<img alt="contract-defination-endpoint.png" height="300" src="D:\POCS\spring-cloud-contract-poc\producer-contract-poc\src\main\resources\readme-images\contract-defination-endpoint.png" title="contract-defination-endpoint.png" width="1000"/>
    
    Basically, this file defines the expected response that the consumer 
    can expect when calling our provider endpoint including the status, header,
    and the body. 
Now if we run the build, the test classes will be generated:

<img alt="contracts-generated-tests.png" height="300" src="D:\POCS\spring-cloud-contract-poc\producer-contract-poc\src\main\resources\readme-images\contracts-generated-tests.png" title="contracts-generated-tests.png" width="1000"/>

    Generated Test classes can be found inside /target/generated-test-sources/contracts**

So whenever someone introduces a breaking change to the endpoint, the test here would fail.

**_Setup the consumer_**
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-contract-wiremock</artifactId>
            <version>2.2.4.RELEASE</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-contract-stub-runner</artifactId>
            <version>2.2.4.RELEASE</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    To run the stubs, we need to add below dependency on the consumer side of consumer-contract-poc.

Let's implement another simple endpoint in the consumer, which actually calls our 
producer's endpoint.

<img alt="consumer-api.png" height="300" src="D:\POCS\spring-cloud-contract-poc\producer-contract-poc\src\main\resources\readme-images\consumer-api.png" title="consumer-api.png" width="1000"/>

and we write a test for this consumer's endpoint:

<img alt="consumer-api-tests.png" height="300" src="D:\POCS\spring-cloud-contract-poc\producer-contract-poc\src\main\resources\readme-images\consumer-api-tests.png" title="consumer-api-tests.png" width="1000"/>
    
    Firstly in the test, we simply call the consumer's endpoint,
    but as it depends on the producer, we need to tell Junit what we 
    expect from the producer. And how we can do that? Well, remember that 
    we have the contract defined above.

This piece of code tells Spring to look for the contract between the consumer 
and producer to get the correct response when we call the producer's endpoint. 
In the underlying, Spring runs a stub Wiremock server at the port 8080 and load 
the contract to determine what to return as the response.

Now, again when a developer changes some code in the producer, which will change 
the contract (e.g in this case to not return the Name or Age of the Student),
it will also cause the test here in the consumer to fail. 
Thus, the team can detect the issue way before it is deployed to production 
to fail silently.

Spring Cloud Contract's approach is more like a producer-driven contract 
(the contract is written and generated by the producer). 
In reality, a consumer-driven contract can be a better approach as only the 
consumer knows which fields are used and what format it expects. 
Any change if needed, should be driven by the consumer rather than the producer.