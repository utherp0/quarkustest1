# ocphandle project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `ocphandle-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/ocphandle-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/ocphandle-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Pushing to OCP

You need to pre-login onto a cluster so that the kubeconfig is correct, and be in the namespace you want to deploy to. Then:

./mvnw clean package -Dquarkus.container-image.build=true -Dquarkus.kubernetes.deploy=true -Dquarkus.openshift.expose=true -Dquarkus.kubernetes-client.trust-certs=true

For a native build use:

mvn clean package -Dnative -Dquarkus.kubernetes.deploy=true -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=registry.access.redhat.com/quarkus/mandrel-20-rhel8:20.1 -Dquarkus.openshift.expose=true -Dquarkus.kubernetes-client.trust-certs=true
