# ProjectFarm

Github
------
* https://github.com/tegaphilip/ProjectFarm

Contributors
------------
* Tega Oghenekohwo <tega.philip@gmail.com>
* Mr. Fuckner

Dependencies
------------
* commons-codec-1.10.jar (https://commons.apache.org/proper/commons-codec/download_codec.cgi)
* mysql-connector-java-5.1.34-bin.jar ( https://dev.mysql.com/downloads/connector/j/5.0.html)
* sqlite-jdbc-3.8.11.2.jar (https://bitbucket.org/xerial/sqlite-jdbc/downloads)


Notes
------------
* This application can be configured to work with either mysql or sqlite database. It defaults to sqlite though.
* To configure it to use mysql, simply create a database named `farm` and toggle lines 45 and 46 of DBUtil.java under package model.db


Limitations
------------
* Currently, large files cannot be uploaded
* Uploaded files are not currently downloading properly afterwards 