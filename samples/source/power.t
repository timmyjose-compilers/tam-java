let
  var m: Integer;
  var n: Integer;

  func power(a: Integer, b: Integer): Integer ~
    if b = 0
      then 1
      else a * power(a, b - 1)
in
  begin
    getint(var m);
    getint(var n);
    putint(power(m, n))
  end
