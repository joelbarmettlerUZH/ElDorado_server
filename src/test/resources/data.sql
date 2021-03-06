/*INSERT INTO HEXSPACE VALUES
  ('J1','JUNGLE',1),
  ('J2','JUNGLE',2),
  ('J3','JUNGLE',3),
  ('W1','RIVER',1),
  ('W2','RIVER',2),
  ('W3','RIVER',3),
  ('S1','SAND',1),
  ('S2','SAND',2),
  ('S3','SAND',3),
  ('S4','SAND',4),
  ('R1','RUBBLE',1),
  ('S2','RUBBLE',2),
  ('S3','RUBBLE',3),
  ('M','MOUNTAIN',1000),
  ('B1','BASECAMP',1),
  ('B2','BASECAMP',2),
  ('B4','BASECAMP',3),
  ('ST','STARTFIELD',1000),
  ('N','EMPTY',1000),
  ('EJ','ENDFIELDJUNGLE',1),
  ('EW','ENDFIELDRIVER',1),
  ('ED','ELDORADO',1000);

INSERT INTO TILE VALUES
  ('A',('J1','S1','J1','J1','J1','J1','ST','ST','ST','ST','J1','J1','J1','J1','W1','J1','B1','J1',
        'J1','J1','S1','W1','J1','J1','J1','J1','J1','S1','M','M',
        'J1','J1','J1','S1','J1','S1',
        'W1')),
  ('B',('W1','J1','J1','J1','J1','J1','ST','ST','ST','ST','J1','J1','W1','J1','J1','J1','W1','B1',
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
        'M'));


INSERT INTO STRIP VALUES
  ('Q',('J2','R1','S1','S1','W1','J3','J2','W2','J1','J1','R3','J1',
          'R1','J2','S3','W1'));

INSERT INTO BOARD(BOARDID,TILES,TILESROTATION,TILESPOSITIONX, TILESPOSITIONY,
                  STRIP,STRIPROTATION,STRIPPOSITIONX,STRIPPOSITIONY,
                  BLOCKADE,STARTINGSPACES,ENDINGSPACES,ENDINGSPACEPOSITION) VALUES
  (1,
    ('B','C'),(2,5),(4,8),(4,10),
    (),(),(),(),
    (((4,5,6,7,8),(8,8,7,7,6))),
    ((1,3),(1,4),(1,5),(1,6)),
    ('EJ','EJ','EJ'),((11,13),(12,12),(12,11)));

INSERT INTO BLOCKADE (ID, COLOR, STRENGTH, BLOCKADEID) VALUES
  ('BJ1','JUNGLE',1,1),
  ('BJ3','JUNGLE',3,2),
  ('BW1','RIVER',1,3),
  ('BS1','SAND',1,4),
  ('RB1', 'RUBBLE',1,5),
  ('BR2','RUBBLE',2,6);

*/
