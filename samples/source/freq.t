let
  proc initialize(var arr: array 26 of Integer) ~
    let
      var i: Integer
    in
      begin
        i := 0;
        while i < 26 do
          begin
            arr[i] := 0;
            i := i + 1
          end
      end;

  proc increment(ch: Char, var freq: array 26 of Integer) ~
    let 
      var idx:Integer
    in
      begin
        idx := (ord(ch) - ord('a'));
        if (ord(ch) >= ord('a')) /\ (ord(ch) <= ord('z'))
          then freq[idx] := freq[idx] + 1
          else freq[0] := freq[0]
      end;

  proc calculatefreqs(var freq: array 26 of Integer) ~
    let
      var ch: Char
    in
      while \eol() do
        begin
          get(var ch);
          increment(ch, var freq);
        end;

  proc displayfreqs(freq: array 26 of Integer) ~
    let 
      var i: Integer
    in
      begin
        i := 0;
        while i < 26 do
          begin
            put(chr(ord('a') + i));
            put('=');
            put('>');
            putint(freq[i]);
            puteol();
            i := i + 1
          end;
      end
in
  let 
    var freq: array 26 of Integer
  in
    begin
      initialize(var freq);
      calculatefreqs(var freq);
      displayfreqs(freq)
    end