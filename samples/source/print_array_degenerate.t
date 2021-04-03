let







var arr: array 5 of Integer;
















proc println(x: Integer) ~

! testing testing testing

begin
      putint(x);
      puteol()
    end;

  proc iterate(proc f (n: Integer), arr: array 5 of Integer) ~
    let 
      var i: Integer
    in
      begin
        i := 0;
        while i < 5 do
          begin
            f(arr[i]);
            i := i + 1
          end
      end
in
  let
    var i: Integer
  in
    begin
      i := 0;
      while i < 5 do
        begin
          arr[i] := (i + 1) * 100;
          i :=  i + 1
        end;
      iterate(proc println, arr)
    ! end of the program
            ! here
    end