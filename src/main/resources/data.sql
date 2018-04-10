/*INSERT INTO HEXSPACE VALUES
  ("J1","JUNGLE",1),
  ("J2","JUNGLE",2),
  ("J3","JUNGLE",3),
  ("W1","RIVER",1),
  ("W2","RIVER",2),
  ("W3","RIVER",3),
  ("S1","SAND",1),
  ("S2","SAND",2),
  ("S3","SAND",3),
  ("S4","SAND",4),
  ("R1","RUBBLE",1),
  ("R2","RUBBLE",2),
  ("R3","RUBBLE",3),
  ("M","MOUNTAIN",1000),
  ("B1","BASECAMP",1),
  ("B2","BASECAMP",2),
  ("B3","BASECAMP",3),
  ("ST","STARTFIELD",1000),
  ("N","EMPTY",1000),
  ("EJ","ENDFIELDJUNGLE",1),
  ("EW","ENDFIELDRIVER",1),
  ("ED","ELDORADO",1000);

INSERT INTO TILE (ID,HEXSPACES) VALUES
  ("A",("J1","S1","J1","J1","J1","J1","ST","ST","ST","ST","J1","J1","J1","J1","W1","J1","B1","J1","J1","J1","S1","W1","J1","J1","J1","J1","J1","S1","M","M","J1","J1","J1","S1","J1","S1","W1"));
  ("B",("W1","J1","J1",'J1','J1','J1','ST','ST','ST','ST','J1','J1','W1','J1','J1','J1','W1','B1',
        'M','J1','J1','J1','J1','J1','J1','J1','J1','S1','J1','S1',
        'J1','S1','J1','W1','S1','J1',
        'J1')),
  ('C',('R1','W1','W1','R1','S1','W1','W1','W1','J1','J1','S1','S1','W1','W1','J1','J1','J1','R1',
        'W1','R1','R1','S1','S1','J1','R1','R1','S1','W1','S1','R1',
        'S1','W1','W1','W1','R1','S1',
        'M')),
  ('D',('W3','M','J1','J2','J1','J1','J2','J1','J1','J1','J1','J1','J2','J1','J1','J2','J1','M',
        'S1','S3','J1','W1','W1','W1','W1','W1','W1','W1','J1','S3',
        'M','J1','W2','W1','W2','J1',
        'M')),
  ('N',('J1','W1','W1','S1','S1','J1','J1','J1','J1','J1','J1','W1','W1','S1','S1','J1','J1','J1',
        'J1','W1','S2','S2','J1','J2','J1','W1','W1','S2','J1','J2',
        'W1','S3','J1','W1','S3','J1',
        'S4')),
  ('I',('J1','J1','J1','M','J1','J1','J1','W1','W2','W2','W2','S2','S1','S1','S1','J1','J1','J1',
        'J1','J2','M','J2','J1','W1','W1','R3','S2','S2','J1','M',
        'M','B3','M','M','J1','J1',
        'J2')),
  ('K',('J1','J2','J2','J2','J1','J1','B1','J2','J2','J1','J2','J2','J2','J1','J1','B1','J2','J2',
        'J1','J1','J1','J2','J1','S4','J1','J1','J1','J2','J1','W3',
        'J3','J3','J1','J3','J3','J1',
        'J1'));


INSERT INTO STRIP VALUES
  ('Q',('J2','R1','S1','S1','W1','J3','J2','W2','J1','J1','R3','J1',
          'R1','J2','S3','W1'));

INSERT INTO BOARD(BOARDID,TILES,TILESROTATION,TILESPOSITIONX, TILESPOSITIONY,
                  STRIP,STRIPROTATION,STRIPPOSITIONX,STRIPPOSITIONY,
                  BLOCKADEID,BLOCKADEPOSITIONX,BLOCKADEPOSITIONY,
                  ENDINGSPACES,ENDINGSPACEPOSITIONX,ENDINGSPACEPOSITIONY) VALUES
  //Default-Path
  (1,
    ('B','C'),(3,0,3,3,5),(4,5,11,17,16),(4,5,16,19,27),
    (),(),(),(),
    (1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4),(3,4,5,6,8,8,7,7,15,14,14,13,15,16,17,18),(8,8,8,8,13,14,15,16,16,17,18,19,23,23,23,23),
    ("EW","EW","EW"),(14,14,15),(30,31,31));

INSERT INTO BLOCKADE (ID, COLOR, STRENGTH, BLOCKADEID) VALUES
  ('BJ1','JUNGLE',1,1),
  ('BJ3','JUNGLE',3,2),
  ('BW1','RIVER',1,3),
  ('BS1','SAND',1,4),
  ('RB1', 'RUBBLE',1,5),
  ('BR2','RUBBLE',2,6);

*/
