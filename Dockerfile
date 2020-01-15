FROM openjdk:11
RUN groupadd --gid 10001 fapp && \
    useradd --gid 10001 --uid 10001 --home-dir /fapp fapp
USER fapp:fapp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","dev.qrivi.fapp.server.ApplicationKt"]