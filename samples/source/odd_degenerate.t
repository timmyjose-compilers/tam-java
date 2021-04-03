let
  !!!!!!! declare an integer !!!!
  var n: Integer;

  func odd(n: Integer): Boolean ~
    ! the usual method
    (n // 2) \= 0
in
  begin
    getint(var n);
    if odd(n)
      then putint(1)
      else putint(2) ! if always needs an "else" part




  end !!!
  !!!