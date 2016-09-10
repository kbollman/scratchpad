-- Note: This table has special create logic since it is used by all others
CREATE OR REPLACE FUNCTION public.schemaupgrade () RETURNS bool AS
$BODY$
BEGIN
    IF EXISTS (
        SELECT *
        FROM   pg_catalog.pg_tables 
        WHERE  schemaname = 'public'
        AND    tablename  = 'db_change_tracker'
        ) THEN
        RAISE NOTICE 'Verified db_change_tracker already exists.';
    ELSE
        CREATE TABLE public.db_change_tracker  ( 
            tracked_item	varchar(255) NOT NULL,
            change_id   	varchar(255) NOT NULL,
            update_time 	timestamptz NOT NULL,
            PRIMARY KEY(tracked_item, change_id)
        ); 
    END IF;
    RETURN true;
END;
$BODY$
LANGUAGE 'plpgsql';

select schemaupgrade();
