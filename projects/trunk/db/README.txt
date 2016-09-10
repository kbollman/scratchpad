README.txt
09/10/2016


**************************
* One time initial setup *
**************************
1. Create User:
createuser -U postgres -P -d -s myuser
Enter password for new role: <<Enter password for myuser user, such as mypassword>>
Enter it again:
Password:                    <<Enter password for postgres user>>

2. Create mydatabase
Note: If mydatabase already exists, drop it (dropdb - Upostgres mydatabase)
createdb -U myuser mydatabase


********************************************
* Initial setup and every database upgrade *
********************************************
1. Add Tables and other DB objects 
Note: use .sh version for Linux (Windows version shown below)

BuildDatabase.bat myuser mypassword mydatabase
