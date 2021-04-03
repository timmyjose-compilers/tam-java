let 
  type Date ~ record
                y: Integer,
                m: Integer,
                d: Integer
              end;

  proc displaydate(d: Date) ~
    begin
      puteol();
      puteol();
      putint(d.y);
      puteol();
      putint(d.m);
      puteol();
      putint(d.d)
    end;

 proc getdate(var d: Date) ~
    begin
      getint(var d.y);
      getint(var d.m);
      getint(var d.d)
    end;

  var d: Date
in
  begin
    getdate(var d);
    displaydate(d);
    d := { y ~ d.y + 1, m ~ d.m + 1, d ~ d.d + 1 };
    displaydate(d);
  end
