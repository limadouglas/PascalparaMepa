PROGRAM TESTE ;
    VAR N , K : INTEGER ;
        F1 , F2 , F3 : INTEGER ;
BEGIN
    READ ( N ) ;
    F1 := 0 ; F2 := 1 ; K := 1 ;
    WHILE K <= N DO
    BEGIN
        F3 := F1 + F2 ;
        F1 := F2 ;
        F2 := F3 ;
        K := K + 1 ;
    END;

    WRITE ( N , F1 ) ;

    IF ( N >= K ) THEN
           N := F1 + F2 * 2 ;
    ELSE
           N := F3 + K * 3 ;

END.