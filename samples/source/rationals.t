let
  type Rational ~
    record
      numerator: Integer,
      denominator: Integer
    end;

  func gcd(x: Integer, y: Integer): Integer ~
    if y = 0 
      then x
      else gcd(y, x // y);

  func makerational(n: Integer, d: Integer): Rational ~
    let
      const g ~ gcd(n, d)
    in
      { numerator ~ n / g, denominator ~ d / g };

  proc readrational(var r: Rational) ~
    begin
      getint(var r.numerator);
      getint(var r.denominator);
      r := makerational(r.numerator, r.denominator)
    end;

  proc displayrational(r: Rational) ~
    begin
      put('{');
      putint(r.numerator);
      put(',');
      putint(r.denominator);
      put('}');
      puteol();
    end;

  func addrational(r1: Rational, r2: Rational): Rational ~
    let
      const num ~ ((r1.numerator * r2.denominator) + (r1.denominator * r2.numerator));
      const denom ~ (r1.denominator * r2.denominator)
    in
      makerational(num, denom);

  func subrational(r1: Rational, r2: Rational): Rational ~
    let
      const num ~ ((r1.numerator * r2.denominator) - (r1.denominator * r2.numerator));
      const denom ~ (r1.denominator * r2.denominator)
    in
      makerational(num, denom);

  var a: Rational;
  var b: Rational;
  var c: Rational
in
  begin
    readrational(var a);
    displayrational(a);
    readrational(var b);
    displayrational(b);
    c := addrational(a, b);
    displayrational(c);
    c := subrational(a, b);
    displayrational(c);
  end