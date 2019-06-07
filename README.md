# ThinkStep - OnlineUsers

[![Build Status](https://travis-ci.org/sonecabr/thinkstep-onlineusers.svg?branch=master)](https://travis-ci.org/sonecabr/thinkstep-onlineusers)

This project consume apache logs in apache_combined format and shows the consolidations through an rest Api 

# Dependencies:

  - OpenJDK8
  - Mysql 5.6+
  - Gradle (if you want to run from sources)
  - Docker (if you want to run from Docker image)
  - An apache log generator (or real apache logs) in combined format

# Modules

  - Web  - GET /api/onlineusers 
  - Collector - Collect content of apache log files from a local folder, ingest in a Mysql database)
  - Processor - Process consolidations regarding apache logs, available consolidations are:
        - unique users on past 5 seconds from now on
        - unique users on past 1 minute from now on
        - unique users on past 5 minutes from now on
        - unique users on past 30 minutes from now on
        - unique users on past 1 hour from now on
        - unique users on past 1 day from now on

# Build
To build the project gradle is required, so the comand is:
``` 
    gradle build bootJar
    or
    gradlew build bootJar
```
#Run
 - from jar binary
```
    java -jar  build/libs/online-users-1.0.jar {*options}
```
 * if an option is not informed, the default value will be applied
 # Available options
     --spring.application.jpa.hibernate.ddl-auto={create|create-auto|update|}
        - set the tables creation in boot time (default=create)
     --spring.application.jpa.datasource.url=jdbc:mysql://{mysql_host}:3306/{mysql_schema}
        - set the mysql connection string, (default=jdbc:mysql://127.0.0.1:3306/onlineusers)
     --spring.application.jpa.datasource.username={username}
        - set the user for mysql server (default=root)
     --spring.application.jpa.datasource.password={password}
        - set the password for mysql server (default=password)
      --logs.apache.folder={static path of apache logs folder}
        - set the path to scrape logs (default=/thinkstep/apache-logs)
      --logs.apache.scheduler.cronexp={cron expression to scrape logs }
        - set the scrape frequency of apache logs (default=* */1 * * * *)
      --logs.apache.regex={regex for log lines}
        - set the regex to serialize log lines (default=^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)\\s?(\\S+)?\\s?(\\S+)?\" (\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?$)
       --logs.level.com.thinkstep={LOG LEVEL}
        - set the desired log level (default=INFO)

 * from gradle
 ```
 gradle bootRun
 or
 gradlew bootRun
 ```
* Options are available in the file src/main/resources/application.yml
```
spring:
  application:
    name: thinkstep-test-online_users
  jpa:
    hibernate:
      ddl-auto: create
  datasource:
    url: jdbc:mysql://localhost:3306/onlineusers
    username: root
    password: password
  cache:
    cache-names: logs, consolidations
    jcache:
      config=classpath:ehcache:
        xml: ehcache.xml


logs:
  apache:
    folder: /thinkstep/apache-logs
    encoding: "UTF-8"
    initialOffset: 0
    scheduller:
      cronexp: "* */1 * * * *"
    regex: "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)\\s?(\\S+)?\\s?(\\S+)?\" (\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?$"

reactor:
  pool:
    size: 10

server:
  port: 8080
  error.whitelabel.enabled: true

logging:
  level:
    com:
     thinkstep: INFO


```


 * from Docker
 ```
 docker build -t thinkstep_challenge/onlineusers:1.0.0 .
 docker run --rm --name thinkstep_challenge -d -p 8080:8080 thinkstep_challenge/onlineusers:1.0.0
 or 
 docker run --rm --name thinkstep_challenge -d -p 8080:8080 sonecabr/thinkstep_challenge:1.0.0
 ```
 * Options are also available:
     -e spring_application_jpa.hibernate_ddl-auto={create|create-auto|update|}
        - set the tables creation in boot time (default=create)
     -e spring_application_jpa_datasource.url=jdbc:mysql://{mysql_host}:3306/{mysql_schema}
        - set the mysql connection string, (default=jdbc:mysql://127.0.0.1:3306/onlineusers)
     -e spring_application_jpa_datasource_username={username}
        - set the user for mysql server (default=root)
     -e spring_application_jpa_datasource_password={password}
        - set the password for mysql server (default=password)
     -e logs_apache_folder={static path of apache logs folder}
        - set the path to scrape logs (default=/thinkstep/apache-logs)
     -e logs_apache_scheduler_cronexp={cron expression to scrape logs }
        - set the scrape frequency of apache logs (default=* */1 * * * *)
     -e logs_apache_regex={regex for log lines}
        - set the regex to serialize log lines (default=^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)\\s?(\\S+)?\\s?(\\S+)?\" (\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?$)
     -e logs_level_com_thinkstep={LOG LEVEL}
        - set the desired log level (default=INFO)

# Usage
The consolidation of users is available thought http
```
GET localhost:8080/api/onlineusers
```     
## The response should be:
```
{
    "id": 9000,
    "timestamp": "2019-06-06T23:13:24.087Z",
    "past5Secons": 10,
    "past1Minute": 500,
    "past5Minutes": 750,
    "past30Minutes": 500,
    "past1Hour": 2000,
    "past1Day": 7979
}
```

# Database
Some tables should be created in your mysql-database:
    
    - log_file_metadata (support table to control log ingestion)
    - online_user_metric (consolidation results)
    - log_history (logs from apache)


# Author
Andre Rocha <devel.andrerocha@gmail.com>
