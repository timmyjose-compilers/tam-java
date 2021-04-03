let
  type Date ~ record
                y: Integer,
                m: Integer,
                d: Integer
              end;

  var d: Date;
  var months: array 12 of Integer;

  proc printmonths(ms: array 12 of Integer) ~
    let
      var i: Integer
    in
      begin
        i := 0;
        while i < 12 do
          begin
            putint(ms[i]);
            puteol();
            i := i + 1
          end
      end;
 proc readdate(var d: Date) ~



    begin
      getint(var d.y);
      getint(var d.m);
      getint(var d.d)
    end;

  func leap(y: Integer): Boolean ~ if ((y // 100 = 0) /\ (y // 400 = 0)) \/ (y // 4 = 0) then true else false;

  func getmonths(d: Date): array 12 of Integer ~
    [
    31, 
    if leap(d.y) then 29 else 28, 
      31, 
      30, 31, 30, 31, 31, 30, 31, 30, 31]
in
  begin
    readdate(var d);let
      var ms: array 12 of Integer
    in
      begin
        ms := getmonths(d);
        printmonths(ms)
      end end
