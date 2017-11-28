PROGRAM TESTE ;
VAR A , B , C , D : INTEGER ;
BEGIN
      READ ( A , B ) ;
      C := A + B ;
      WHILE ( C >= 0 ) DO
      BEGIN
             C := C - 1 ;
             IF ( C >= A ) THEN
                   D := D + C * 2 ;
            ELSE
                   D := D + C * 3 ;
     END;
    WRITE( A , B , D ) ;
END.