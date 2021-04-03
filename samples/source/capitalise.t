let
  var s: array 5 of Char;
  const shift ~ ord('a') - ord('A');

  proc capitalisechar(var ch: Char) ~
    if (ord(ch) >= ord('a')) /\ (ord(ch) <= ord('z')) 
      then ch := chr(ord(ch) - shift)
      else ch := ch;

  proc capitalise(var s: array 5 of Char) ~
    let
      var i: Integer
    in
      begin
        i := 0;
        while i < 5 do
          begin
            capitalisechar(var s[i]);
            i := i + 1
          end
      end;

  proc readstring(var s: array 5 of Char) ~
    let
      var i: Integer
    in
      begin
        i := 0;
        while i < 5 do
          begin
            get(var s[i]);
            i := i + 1
          end
      end;

  proc displaystring(s: array 5 of Char) ~
    let 
      var i: Integer
    in
      begin
        i := 0;
        while i < 5 do
          begin
            put(s[i]);
            i := i + 1
          end;
        puteol()
      end
in 
  begin
    readstring(var s);
    displaystring(s);
    puteol();
    capitalise(var s);
    displaystring(s);
  end
