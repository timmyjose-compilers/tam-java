let
  proc iteratively(proc p(n: Integer), var arr: array 10 of Integer) ~
    let 
      var i: Integer
    in
      begin
        i := 0;
        while i < 10 do
          begin
            p(arr[i]);
            i := i + 1
          end
      end;

  proc readnums(var a: array 10 of Integer) ~
    let
      var i: Integer
    in
      begin
        i := 0;
        while i < 10 do
          begin
            getint(var a[i]);
            i := i + 1
          end
      end;

  proc putintln(n: Integer) ~
    begin
      putint(n);
      puteol()
    end;

  var a: array 10 of Integer
in
  begin
    readnums(var a);
    iteratively(proc putint, var a);
    puteol();
    iteratively(proc putintln, var a)
  end