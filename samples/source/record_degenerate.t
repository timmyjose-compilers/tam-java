let 




  type Date ~ record
                y: Integer,
                m: Integer,
                d: Integer
              end;
  var 
  today
  : 
  Date
  ; var tomorrow: Date
in
  begin today := { y ~ 2021, m ~ 1, d ~ 12 };putint(today.y);putint(today.m);putint(today.d);tomorrow := { y ~ today.y, m ~ today.m, d ~ today.d + 1 };
    putint(tomorrow.y);putint(tomorrow.m);putint(tomorrow.d) end
