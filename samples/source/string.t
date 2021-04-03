let
  type String ~
    record
      buf: array 100 of Char,
      idx: Integer
    end;

  proc displaystring(s: String) ~
    let
      var i: Integer
    in
      begin
        puteol();
        i := 0;
        while i < s.idx do
          begin
            put(s.buf[i]);
            i := i + 1
          end;
      end;

  proc readstring(var s: String) ~
    while \eol() do
      begin
        get(var s.buf[s.idx]);
        s.idx := s.idx + 1;
      end
in
  let 
    var s: String
  in
    begin
      readstring(var s);
      displaystring(s)
    end