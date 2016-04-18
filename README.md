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
* commons-codec jar
* mysql jdbc connector jar
* sqlite jdbc connector jar


Notes
------------
* This application can be configured to work with either mysql or sqlite database. It defaults to sqlite though.
* To configure it to use mysql, simply create a database named `farm` and toggle lines 45 and 46 of DBUtil.java under package model.db


Limitations
------------
* Currently, large files cannot be uploaded
* Uploaded files are not currently downloading properly afterwards 