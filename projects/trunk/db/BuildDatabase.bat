REM Creates/Upgrades database objects
echo off
set user_name=%1
set PGPASSWORD=%2
set database_name=%3
SET db_path=.
REM
REM DB change tracker functions and table
echo on
psql -U%user_name% -d%database_name% -f"%db_path%\public\code\ins_db_change_tracker.sql"
psql -U%user_name% -d%database_name% -f"%db_path%\public\code\is_db_change_required.sql"
psql -U%user_name% -d%database_name% -f"%db_path%\public\tables\db_change_tracker.sql"
REM
REM Tables
psql -U%user_name% -d%database_name% -f"%db_path%\public\tables\my_table.sql"
REM
echo off
REM Set password system variable back to none
SET PGPASSWORD=
echo on
