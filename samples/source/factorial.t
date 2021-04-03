let
  var n: Integer;

  func factorial(n: Integer): Integer ~
    if n <= 0 
      then 1
      else n * factorial(n - 1);

  proc readnumber(var n: Integer) ~
    getint(var n)
in
  begin
    readnumber(var n);
    puteol();
    puteol();
    putint(factorial(n))
  end