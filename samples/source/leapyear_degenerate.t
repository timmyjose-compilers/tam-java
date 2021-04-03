let 
  type Date ~ record
                y: Integer,
                m: Integer,
                d: Integer
              end;

              var d: Date;

      proc   readdate(var d: Date) ~
    begin
        getint(var d.y);
      getint(var d.m);
      getint(   var d.d)
    end;

  func 


  leapyear(               d: Date): Boolean ~
    if ((d.y // 100 = 0) /\ (d.y // 400 = 0)) \/ (d.y // 4 = 0)
      then true
      else false
in
  


  begin
  

  readdate(var d);
  

     if leapyear(d)
        then putint(1)
      else putint(0)
  end