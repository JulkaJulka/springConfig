CREATE TABLE PASSENGER
   (	PAS_ID NUMBER,
   CONSTRAINT PASS_PK PRIMARY KEY (PAS_ID)
	LAST_NAME NVARCHAR2(20),
	NATIONALITY NVARCHAR2(20),
	DATE_OF_BIRTH DATE,
	PASSPORT_CODE NVARCHAR2(20),
	 CONSTRAINT PASS_PK PRIMARY KEY (PAS_ID)
   )