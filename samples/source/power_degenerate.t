let
  
  var m: Integer;
  

  var n: Integer;

  !!!!! Calculate the base raised to the exponent !!!!


  func power(a: Integer, b: Integer): Integer ~
    if b = 0
      then 1
      else a * power(a, b - 1)
in
  ! read and print the power
  begin getint(var m);
    getint(var n);
    putint(power(m, n))
  end
