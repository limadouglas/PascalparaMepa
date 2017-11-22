Program teste ;
var a , b , c , d : integer ;
begin
       read ( a , b ) ;
       c := a + b ;
       while ( c >= 0 ) do
      begin
             c := c - 1 ;
             if ( c >= a ) then
                   d := d + c * 2;
            else
                   d := d + c * 3 ;
     end
    write ( a , b , d ) ;
end .
