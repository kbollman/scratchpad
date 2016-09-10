CREATE OR REPLACE FUNCTION public.ins_db_change_tracker (in p_tracked_item varchar, in p_change_id varchar) RETURNS bool AS
$BODY$
BEGIN
    INSERT INTO db_change_tracker (tracked_item, change_id, update_time)
	VALUES (p_tracked_item, p_change_id, now()); 
    RETURN true;
EXCEPTION
  WHEN OTHERS THEN
   RAISE;    
   RETURN false;
END;
$BODY$
LANGUAGE 'plpgsql';
