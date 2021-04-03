let
  type Line ~
    record
      length: Integer,
      content: array 80 of Char
    end;

  proc getline(var l: Line) ~
    begin
      l.length := 0;
      while \eol() do
        begin
          get(var l.content[l.length]);
          l.length := l.length + 1;
        end;
    end;

  proc putline(l: Line) ~
    let
      var i: Integer
    in
      begin
        i := 0;
        while i < l.length do
          begin
            put(l.content[i]);
            i := i + 1
          end;
      end;

  proc putreversedline(l: Line) ~
    let
      var i: Integer
    in
      begin
        i := l.length - 1;
        while i >= 0 do
            begin
              put(l.content[i]);
              i := i - 1
            end;
      end;

  var currentline: Line
in
  begin
    getline(var currentline);
    putline(currentline);
    putreversedline(currentline)
  end