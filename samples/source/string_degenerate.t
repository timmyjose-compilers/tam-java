let
  ! this declares a String type - 
  ! Triangle does not have a built-in
  ! String type, and that's fine
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
        i := 0; ! the ; here is required
        while i < s.idx do
          begin
            put(s.buf[i]);
            i := i + 1
          end;
      end;

  proc readstring(var s: String) ~ ! read a string from the console
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
      ! readstring(!var s);
      readstring(var s);
      displaystring(s)
    end