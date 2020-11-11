# Starter Structure

## api

This is the interface for the service, which should include the Request and Response payload objects.

Open question: Should we use Lombok, Immutables, or POJO?


## spi Service Provider Interface

This is an optional component, depending on the structure and domain of what is being built.
Assume you should have it, you should be able to justify why you don't.
This pattern forces you to think in terms of interfaces for the various components of your service.

An example may help.
Take Notifications as an example.
At a high level, as a consumer of this API, I just want to notify someone.
At the lower level, there may be different ways of notifying someone, configurable by the person, including email or SMS.
Email and SMS would be individual SPI implementations, because at the higher level of the service, I just want to track that the notification was sent out.

Further, there may be a future mechanism for notifications, which would only require a configuration capability for the user to select the new method, and a new SPI implementation registered with the service.

It also helps a lot with testability.

## persitence/api

Explicitly separating persistence into api and impl may seem like a bit of overkill to some.
Many prefer just using Spring JPA interfaces for the repositories.

That's fine as far as it goes, but occasionally we may have need for implementing other capabilities (e.g. C*).
Keeping with the interface-first design pattern preserves optionality and promotes testability.

The persistence model objects also live here in the persistence/api package.


## persistence/impl

Depending on the choice on the persistence/api side (i.e. Spring JPA), we may or may not need to provide any implementations.
However, it's still useful to have in cases where custom queries need to be implemented.


## impl

This is the main implementation package, which implements the service.
Ideally, it is a thin glue layer between the REST API and the SPI implementations.
Data validation, security (if not already delegated out to Service Mesh), logging and metrics capture should happen here.

Business logic should primarily reside behind the SPI interface(s) and the specific implementations  which execute the business logic.
This pattern easily accomodates plugin architectures at the SPI level, supported by factory objects (<--Ranbir;).


## service

The main Spring Boot Application, which contains whatever specific configuration classes and includes the components which make up the application (persistence, api, spi, spi-implementation).

SPI Implementations may be either internal or exteral.
Spring affords an easy way of dynamically constructing the Factory by auto-registering implementations of an interface via configuration beans based on packages included on the classpath.
