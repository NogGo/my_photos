java -Dfile.encoding=UTF-8 -Duser.language=en -Duser.country=US ^
-jar lib/liquibase.jar ^
--logLevel=info ^
--classpath=lib/postgresql-42.2.5.jar ^
--driver=org.postgresql.Driver ^
--changeLogFile=install.xml ^
--url="jdbc:postgresql://localhost:5432/myphotos" ^
--username=myphotos ^
--password=myphotos ^
update