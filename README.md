# Application Monitoring with Grafana and Opentelemetry

## About

Inspired by the [JavaLand Talk](https://meine.doag.org/events/javaland/2024/agenda/#agendaId.3830) by Fabian Stäber.

This project is a simple SpringBoot application. It provides access to a MySql database via a REST-API.
It serves as a demonstration for monitoring Java applications with OpenTelemetry and visualizing the results
with Grafana.

## Setup

First start a MySql database. You can use the included [docker compose file](complete/docker-compose.yml): `docker compose up -d`.
Next start the OpenTelemetry and Grafana [docker image](https://github.com/grafana/docker-otel-lgtm): `docker run -d -p 3000:3000 -p 4317:4317 -p 4318:4318 -ti grafana/otel-lgtm`.
Now the application can be started via `./gradlew bootRun`.

## Start the application

The application now exposes two REST-APIs:
- `http://localhost:8080/demo/add` allows you to add users with a `name` and `email`
- `http://localhost:8080/demo/all` returns all users saved in the database

You can also use the [included script](complete/scripts/generate-traffic.sh) to generate some traffic on the `all` endpoint.

## View the results

The OpenTelemetry results can be monitored in Grafana. Login to [http://localhost:3000](http://localhost:3000) with user `admin` and password `admin`.