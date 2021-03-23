# Challenge

## :computer: How to execute

To execute the solution it is only necessary to execute _mvn clean install_ and _mvn spring-boot:run_ in the folder _payment-verification_. Keep in mind that this should only be done after starting the docker-compose.yml.

## :memo: Notes

I decided to create two services, one for the offline payments and another for the online payments, because it will be easier in the future to add specific features to just one service. 

In each service there is a method to process the payments. These methods are Kafka listeners (one for the 'online' payments and other for the 'offline' payments) and everytime they receive a message they will do the complete flow to process that payment, ending with the payment being saved in the database. 

There are models of the Payment and Account that are the database entities, basically, and there is also two objects, PaymentJson and ErrorJson, that represent the request's body that is sent to the endpoints (payment and log, respectively).

About the logging of errors, I was not completely aware of what was expected from my part. I end up doing something that is not very complex or even correct, but that posts some errors to the log error service.

## :pushpin: Things to improve

The first thing to improve would be test coverage by adding integration tests. Unit tests are better than nothing, but the coverage they provide is not total. 

Adding a Dockerfile and a docker-compose.yml would be the next improvement. This is an indispensable feature nowadays considering that everything is run in a cloud provider. 

The logging of errors should be improved. Like I mentioned, this was a functionality that I did not understand completely, so, due to time constraints, I coded a very rudimentar solution that should be improved a lot.
