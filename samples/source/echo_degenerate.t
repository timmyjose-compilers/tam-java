! this starts the program
let 
  ! declaration
  var ch: Char;

  ! echo
  proc echo() ~
    ! start of the procedure
    begin
      ! while block
      while \eol() do
        begin
          get(var ch); ! read character
          put(ch) ! display character
        end
    end
! the let block
! needs an in clause
! as shown here
in
  ! procedure call
  echo()