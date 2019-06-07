#!/bin/sh

set -e
echo "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"
echo "mmmmmmmmmmmmmmmmmmmmmm Booting Thinkstep Online Users mmmmmmmmmmmmmmmmm"
echo "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"
echo "- Environment:"
echo "- spring_application_jpa_hibernate_ddl: $spring_application_jpa_hibernate_ddl"
echo "- spring_application_jpa_datasource_url: $spring_application_jpa_datasource_url"
echo "- spring_application_jpa_datasource_username: $spring_application_jpa_datasource_username"
echo "- spring_application_jpa_datasource_password: $spring_application_jpa_datasource_password"
echo "- logs_apache_folder: $logs_apache_folder"
echo "- logs_apache_scheduler_cronexp: $logs_apache_scheduler_cronexp"
echo "- logs_apache_regex: $logs_apache_regex"
echo "- $logs_apache_regex: $logs_level_com_thinkstep"
echo "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"

if [ $spring_application_jpa_hibernate_ddl ]; then
    echo "$spring_application_jpa_hibernate_ddl informed as env, using $spring_application_jpa_hibernate_ddl"
else
    echo "$spring_application_jpa_hibernate_ddl not informed as env, using default (create)"
    export spring_application_jpa_hibernate_ddl="create"
fi

if [ $spring_application_jpa_datasource_url ]; then
    echo "$spring_application_jpa_datasource_url informed as env, using $spring_application_jpa_datasource_url"
else
    echo "$spring_application_jpa_datasource_url not informed as env, using default (jdbc:mysql://127.0.0.1:3306/onlineusers)"
    export spring_application_jpa_datasource_url="jdbc:mysql://127.0.0.1:3306/onlineusers"
fi

if [ $spring_application_jpa_datasource_username ]; then
    echo "$spring_application_jpa_datasource_username informed as env, using $spring_application_jpa_datasource_username"
else
    echo "$spring_application_jpa_datasource_username not informed as env, using default (root)"
    export spring_application_jpa_datasource_username="root"
fi

if [ $spring_application_jpa_datasource_password ]; then
    echo "$spring_application_jpa_datasource_password informed as env, using $spring_application_jpa_datasource_password"
else
    echo "$spring_application_jpa_datasource_password not informed as env, using default (password)"
    export spring_application_jpa_datasource_password="password"
fi

if [ $logs_apache_folder ]; then
    echo "$logs_apache_folder informed as env, using $logs_apache_folder"
else
    echo "$logs_apache_folder not informed as env, using default (/thinkstep/apache-logs)"
    export logs_apache_folder="/thinkstep/apache-logs"
fi

if [ $logs_apache_scheduler_cronexp ]; then
    echo "$logs_apache_scheduler_cronexp informed as env, using $logs_apache_scheduler_cronexp"
else
    echo "$logs_apache_scheduler_cronexp not informed as env, using default (* */1 * * * *)"
    export logs_apache_scheduler_cronexp="* */1 * * * *"
fi

if [ $logs_apache_regex ]; then
    echo "$logs_apache_regex informed as env, using $logs_apache_regex"
else
    echo "$logs_apache_regex not informed as env, using default (^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)\\s?(\\S+)?\\s?(\\S+)?\" (\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?$)"
    export logs_apache_regex="^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)\\s?(\\S+)?\\s?(\\S+)?\" (\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?$"
fi

if [ $logs_level_com_thinkstep ]; then
    echo "$logs_level_com_thinkstep informed as env, using $logs_level_com_thinkstep"
else
    echo "$logs_level_com_thinkstep not informed as env, using default (INFO)"
    export logs_level_com_thinkstep="INFO"
fi


java -jar /var/lib/thinkstep/online-users-1.0.jar \
    --spring.application.jpa.hibernate.ddl-auto=$spring_application_jpa_hibernate_ddl-auto \
    --spring.application.jpa.datasource.url=$spring_application_jpa_datasource_url \
    --spring.application.jpa.datasource.username=${spring_application_jpa_datasource_username} \
    --spring.application.jpa.datasource.password=${spring_application_jpa_datasource_password} \
    --logs.apache.folder=${logs_apache_folder} \
    --logs.apache.scheduler.cronexp=${logs_apache_scheduler_cronexp} \
    --logs.apache.regex=${logs_apache_regex} \
    --logs.level.com.thinkstep=${logs_level_com_thinkstep}
