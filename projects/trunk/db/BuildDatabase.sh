#!/bin/bash

if [ $# -lt 3 ]; then
    echo -e "\tUsage: $0 {username} {password} {databasename}"
    exit 1
fi

user_name=$1
PGPASSWORD=$2
datbase_name=$3

DB_PATH=`pwd`


# ChangeTracker functions and table
psql -U $user_name -d $database_name -f "${DB_PATH}/public/code/ins_db_change_tracker.sql"
psql -U $user_name -d $database_name -f "${DB_PATH}/public/code/is_db_change_required.sql"
psql -U $user_name -d $database_name -f "${DB_PATH}/public/tables/db_change_tracker.sql"

# Tables
psql -U $user_name  -d $database_name -f "${DB_PATH}/public/tables/my_table.sql"

# Reset password variable back to none
PGPASSWORD=''
