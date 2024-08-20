# Dockerfile for my voting simulator


# my uses java version 17
# Due to sanctions, in Iran I use this image
# which provides a version of java that might be even higher than 17
FROM docker.arvancloud.ir/openjdk

#Working directory
WORKDIR /voting-simulator

#copy my app jar file to the container
COPY build/libs/voiting-simulator-1.0-SNAPSHOT.jar voting-simulator.jar

# application will run on this port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "voting-simulator.jar"]


