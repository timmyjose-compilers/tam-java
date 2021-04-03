let 
  var x: Integer;

  proc inc(var n: Integer) ~ n := n + 1
in
  begin
    getint(var x);
    inc(var x);
    putint(x)
  end
