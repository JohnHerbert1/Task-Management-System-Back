-- src/main/resources/schema.sql
-- Este arquivo define a estrutura do banco de dados (DDL).

-- Dropar tabelas existentes para garantir um estado limpo (Opcional, se você não usar ddl-auto=create-drop)
-- No seu caso, com ddl-auto=create-drop, o Hibernate já fará isso, então estas linhas são redundantes.
-- No entanto, são úteis se você decidir não usar ddl-auto=create-drop em outro ambiente.
drop SEQUENCE if exists "Active_Type_ID_Active_Type_seq" CASCADE;
drop SEQUENCE if exists "Chat_Bot_ID_Chat_seq" CASCADE;
drop SEQUENCE if exists "Error_Logs_ID_Error_seq" CASCADE;
drop SEQUENCE if exists "Notification_ID_Notification_seq" CASCADE;
drop SEQUENCE if exists "Report_Task_ID_Report_seq" CASCADE;
drop SEQUENCE if exists "Schedule_ID_Schedule_seq" CASCADE;
drop SEQUENCE if exists "Schedule_Logs_ID_Log_seq" CASCADE;
drop SEQUENCE if exists "Schedule_Type_ID_Schedule_Type_seq" CASCADE;
drop SEQUENCE if exists "Task_ID_Task_seq" CASCADE;
drop SEQUENCE if exists "Task_Logs_ID_Log_seq" CASCADE;
drop SEQUENCE if exists "User_ID_User_seq" CASCADE;
drop SEQUENCE if exists "User_Logs_ID_Log_seq" CASCADE;
drop SEQUENCE if exists "User_Type_ID_User_Type_seq" CASCADE;
drop SEQUENCE if exists "user_logs_seq" CASCADE;

DROP TABLE IF EXISTS "Chat_Bot" CASCADE;
DROP TABLE IF EXISTS "User_Type" CASCADE;
DROP TABLE IF EXISTS "Active_Type" CASCADE;
DROP TABLE IF EXISTS "Schedule_Type" CASCADE;
DROP TABLE IF EXISTS "Notification" CASCADE;
DROP TABLE IF EXISTS "Task_Logs" CASCADE;
DROP TABLE IF EXISTS "Schedule_Logs" CASCADE;
DROP TABLE IF EXISTS "Error_Logs" CASCADE;
DROP TABLE IF EXISTS "User_Logs" CASCADE;
DROP TABLE IF EXISTS "Report_Task" CASCADE;
DROP TABLE IF EXISTS "Schedule" CASCADE;
DROP TABLE IF EXISTS "Task" CASCADE;
DROP TABLE IF EXISTS "User" CASCADE;

