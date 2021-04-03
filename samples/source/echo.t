let 
  var ch: Char;

  proc echo() ~
    begin
      while \eol() do
        begin
          get(var ch);
          put(ch)
        end
    end
in
  echo()