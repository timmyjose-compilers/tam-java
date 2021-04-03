! This program reads in a line of text from the console
! and then displays the reversed line on standard output.
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
l.length := l.length + 1
end;
geteol() ! slurp a new line character
end;
proc putreversedline(l: Line) ~
let
var i: Integer
in
begin
i := l.length;
while i > 0 do
begin
i := i - 1;
put(l.content[i])
end;
puteol();
end;
var currentline: Line
in
while \eof() do
begin
getline(var currentline);
putreversedline(currentline)
end
! this marks the end of the source file