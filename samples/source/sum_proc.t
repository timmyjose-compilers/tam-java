let
  var x: Integer;
  var y: Integer;
  var s: Integer;

  proc add(a: Integer, b: Integer, var r: Integer) ~ r := a + b
in
  begin
    getint(var x);
    getint(var y);
    add(x, y, var s);
    putint(s)
  end