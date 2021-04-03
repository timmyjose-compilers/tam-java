let
  type Matrix ~ array 3 of array 4 of Integer;

  var mat: Matrix;

  proc displayprompt() ~
    begin
      put('E');
      put('n');
      put('t');
      put('e');
      put('r');
      put(' ');
      put('n');
      put('u');
      put('m');
      put('b');
      put('e');
      put('r');
      put(':');
      put(' ')
    end;

  proc initialisematrix(var m: Matrix) ~
    let
      var i: Integer;
      var j: Integer
    in
      begin
        i := 0;

        while i < 3 do
          begin
            j := 0;
            while j < 4 do
              begin
                displayprompt();
                getint(var m[i][j]);
                j := j + 1
              end;
            i := i + 1
          end
       end;

  proc displaymatrix(m: Matrix) ~
    let
      var i: Integer;
      var j: Integer
    in
      begin
        i := 0;

        while i < 3 do
          begin
            j := 0;
            while j < 4 do
              begin
                putint(m[i][j]);
                put(' ');
                j := j + 1
              end;
            i := i + 1;
            puteol()
          end
      end
in
  begin
    initialisematrix(var mat);
    displaymatrix(mat)
  end
    
