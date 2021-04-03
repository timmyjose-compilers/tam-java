let 
  type Date ~ 
    record
      m: Integer,
      d: Integer
    end;

  const xmas ~ { m ~ 12, d ~ 25 };
  var easter: Date;
  var holidays: array 3 of Date;

  proc displaydate(d: Date) ~
    begin
      putint(d.m);
      put('-');
      putint(d.d);
      puteol()
    end;

  proc readdate(var d: Date) ~
    begin
      getint(var d.m);
      getint(var d.d)
    end;

  proc readdates(var a: array 3 of Date) ~
    let
      var i: Integer
    in
      begin
        i := 0;
        while i < 3 do
          begin
            readdate(var a[i]);
            i := i + 1
          end
      end
in
  begin
    readdate(var easter);
    readdates(var holidays);
    displaydate(xmas);
    displaydate(easter);

    let 
      var i: Integer
    in
      begin
        i := 0;
        while i < 3 do
          begin
            displaydate(holidays[i]);
            i := i + 1
          end
      end;

    displaydate(holidays[0]);
  end