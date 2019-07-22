# Quakus Rest Pet Clinic

This project is based on [Spring Rest Pet Clinic](https://github.com/spring-petclinic/spring-petclinic-rest).

## Running pet clinic locally

```bash
./mvnw compile quarkus:dev
```

The application will run on `9966`. By default an `h2` in memory pre-populated database will be used. You may use [Spring Angular Client](https://github.com/spring-petclinic/spring-petclinic-angular).

## Todo

 * Write [tests](https://quarkus.io/guides/getting-started-testing)
 * [Enable Swagger](https://quarkus.io/guides/openapi-swaggerui-guide)
 * Provide alternative [repository](https://quarkus.io/guides/hibernate-orm-panache-guide) implementations
 * Add a [security](https://quarkus.io/guides/security-guide) layer

## Motivation

Explore [Quarkus](https://quarkus.io/), compare it to Spring, have fun.

## Contributing

In case anyone is interested in contributing, you are welcome. I would be especially interested in the items of the "Todo" list, in code reading/best practice/performance improvements and alternate implementations.

Please, open an issue and/or submit a pull request.

## Similar Works

 * https://github.com/jonathanvila/quarkus-petclinic
 * https://github.com/belyaev-andrey/quarkus-petclinic-reactjs
