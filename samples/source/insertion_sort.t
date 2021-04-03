let
  var s: array 10 of Integer;

  proc readNumsSub(var a: array 10 of Integer, idx: Integer, stop: Integer) ~
    if idx = (stop - 1)
      then getint(var a[idx])
      else
        begin
          getint(var a[idx]);
          readNumsSub(var a, idx + 1, stop)
        end;

  proc readNums(var a: array 10 of Integer) ~
    readNumsSub(var a, 0, 10);

  proc displaySub(a: array 10 of Integer, idx: Integer, stop: Integer) ~
    if idx = (stop - 1)
      then putint(a[idx])
      else 
        begin
          putint(a[idx]);
          puteol();
          displaySub(a, idx + 1, stop)
        end;

  proc display(a: array 10 of Integer) ~
    displaySub(a, 0, 10);

  proc insertionSort(var a: array 10 of Integer) ~
    let
      var i: Integer;
      var key: Integer;
      var j: Integer
    in
      begin
        i := 1;
        while i < 10 do
          begin
           key := a[i];
           j := i - 1;

           while (j >= 0) /\ (a[j] > key) do
             begin
               a[j+1] := a[j];
               j := j - 1
             end;
           a[j+1] := key;
           i := i + 1;
          end
      end
in
  begin
    readNums(var s);
    puteol();
    puteol();
    display(s);
    puteol();
    puteol();
    insertionSort(var s);
    display(s)
  end