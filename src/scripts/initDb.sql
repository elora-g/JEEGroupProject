

DROP TABLE IF EXISTS sac_person;
DROP TABLE IF EXISTS sac_operation;
DROP TABLE IF EXISTS sac_messages;
DROP TABLE IF EXISTS sac_accounts;


CREATE TABLE sac_accounts(  account_id int NOT NULL IDENTITY,  account_customer_id varchar(128) ,  account_balance float ,  account_type varchar(128) ,  account_is_default int,  PRIMARY KEY (account_id));


CREATE TABLE sac_messages(  msg_id int NOT NULL IDENTITY,  msg_content varchar(128) ,  msg_from int ,  msg_to int ,  msg_created_at timestamp NULL ,  PRIMARY KEY (msg_id));


CREATE TABLE sac_operation(  ope_id int NOT NULL IDENTITY,  ope_type varchar(128) ,  ope_amount float NOT NULL ,  ope_description varchar(128) NOT NULL ,  ope_account_id int NOT NULL ,  ope_created_at timestamp NOT NULL ,  ope_updated_at timestamp NOT NULL ,  ope_dispute int NOT NULL ,  PRIMARY KEY (ope_id)) ;

CREATE TABLE sac_person (  person_id int NOT NULL IDENTITY,  person_external_id varchar(128) NOT NULL ,  person_firstname varchar(128) NOT NULL ,  person_lastname varchar(128) NOT NULL ,  person_email varchar(128) NOT NULL ,  person_password varchar(128) NOT NULL ,  person_dob varchar(128) NOT NULL ,  person_token varchar(128) ,  person_phone_number varchar(128) NOT NULL ,  person_created_At timestamp NOT NULL ,  person_updated_at timestamp NOT NULL ,  person_advisor_id int NOT NULL ,  person_is_advisor int NOT NULL ,  PRIMARY KEY (person_id));

