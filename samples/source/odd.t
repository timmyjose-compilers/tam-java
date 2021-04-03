let
  var n: Integer;

  func odd(n: Integer): Boolean ~
    (n // 2) \= 0
in
  begin
    getint(var n);
    if odd(n)
      then putint(1)
      else putint(2)
  end