CREATE OR REPLACE FUNCTION public.schema_upgrade () RETURNS bool AS
$BODY$
declare
    v_table_name varchar(255); 
    v_change_id varchar(255);
    v_change_required bool;
    v_successful bool;

begin
    -- Note: This is a function, so all will be dones as one transaction
    v_table_name := 'my_table';
    v_successful := true;

    -- Beginning of part to copy/past for each change
    if (v_successful) then
        v_change_id := '20160910jrichter01';
        select is_db_change_required(v_table_name, v_change_id) into v_change_required;

        if (v_change_required) then
            -- STEP START
            CREATE TABLE my_table (
                my_table_id int8 NOT NULL,               -- Unique identifier
                my_table_column_a varchar(10) NOT NULL,  -- Some generic column
                   PRIMARY KEY(my_table_id)
            );
            -- Include CREATE INDEXes here
            -- STEP END
            select ins_db_change_tracker(v_table_name, v_change_id) into v_successful;
            raise notice  'Processed tracked item: %, change Id: %',v_table_name, v_change_id;
        end if;
    end if;
    -- End of part to copy/paste for each change
    -- Beginning of part to copy/past for each change
    if (v_successful) then
        v_change_id := '20160910jrichter02';
        select is_db_change_required(v_table_name, v_change_id) into v_change_required;

        if (v_change_required) then
            -- STEP START
            ALTER TABLE my_table ADD my_table_column_b bool; -- New column sample
            -- STEP END
            select ins_db_change_tracker(v_table_name, v_change_id) into v_successful;
            raise notice  'Processed tracked item: %, change Id: %',v_table_name, v_change_id;
        end if;
    end if;
    -- End of part to copy/paste for each change    
    return v_successful;
 EXCEPTION
  WHEN OTHERS THEN
   v_successful := false;
   RAISE;    
   RETURN v_successful;
end;
$BODY$
LANGUAGE 'plpgsql';

select schema_upgrade();
