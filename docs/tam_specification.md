This is the full specification of the Triangle Abstract Machine (TAM).

The TAM is a Stack Machine ideally suited for running programs in block-structured languages (which Triangle is).

## Storage and Registers

TAM has two separate stores - the code store which stores 32-bit read-only instructions, and the data store which stores 16-bit data words (read/write).

Each store is divided into a number of segments, each of which is pointed to by one or more registers. Data and instructions are always addressed relative to one of these
registers.

### Code Store

While a program is running, the segmentation of the code store remains fixed.

  * The Code Segment contains the program's instructions. Registers CB and CT respectively point to the base and top of the code segment. Register CP points to the next 
    instruction to be executed and is initialised with CB. 

  * The Primitive Segment contains "microcode" for the elementary arithmetic, logical, input-output, heap, and general-purpose operations. Registers PB and PT respectively 
    point to the base and top of the primitive segment.

### Data Store

While a program is running, the segmentation of the data store may vary. There are two basic segments in the Data Store - the Stack and the Heap.

  * The Stack grows from the low-address end of the Data Store, and registers SB and ST point to the base and top respectively of the stack. ST is initialised with SB.
    
  * The Heap grows from the high-address end of the Data Store, and registers HB and HT point to the base and top respectively of the heap. HT is initialised with HB.

Since the stack and the heap grow (and shrink) in opposite directions, storage exhaustion occurs when ST and HT cross over each other. 

#### The Stack and The Heap

The Stack itself consists of at least two segments:

  * The Global Segment which contains global data used by the program, and

  * Any number of frames, where a frame is an activation of a procedure or function (and so it contains all the data local to the activation itself). Calling a routine causes
    a new frame to be pushed on top, and returning from a routine causes the topmost frame to be poppd off, possibly leaving behind the results of activation on the top of the
    stack. 

    Register LB points to the base of the topmost frame. The topmost frame may expand or shrink, but the lower frames are temporarily fixed in size. Basically, the currently active
    frame (which is always the topmost frame) may expand or shrink freely.

Consider a program as follows:

```
  proc P() ~
    ...
    proc Q() ~
      ...
      proc R() ~
        ...
      ...
    proc S() ~
      ...
```

Consider now the basic flow during a hypothetical operation of the program:

  1. The main program calls procedure P. Register LB now points to the base of the topmost frame, which belongs to the current activation of procedure P.

  2. Procedure P now calls procedure S. Register LB now points to the topmost frame, which belongs to S. Register L1 now points to the underlying frame, which belongs to P.

  3. Procedure S now calls procedure Q. Register LB now points to the topmost frame, which belongs to Q. Register L1 still points to P, since procedure P is the surrounding scope for
     bothe S and Q.

  4. Procedure Q now calls procedure R. Register LB now points to the topmost frame, which belongs to R. Register L1 now points to R's enclosing scope, which is Q. Register L2 now
     points to the old L1 value, which is P.

Global, local, and nonlocal data can be accessed as follows:

```
  LOAD(n) d[SB] for any procedure to access global data at offset d

  LOAD(n) d[LB] for any procedure to access its local data at offset d

  LOAD(n) d[L1] for procedure R to access data local to Q at offset d, or for procedures Q and S to access P's local data at offset d

  LOAD(n) d[L2] for procedure R to access data local to P at offset d
```

In general:

```
  d[SB] for any routines to access global data
  
  d[LB] for any routine to access its own local data

  d[L1] for any routine to access its lexically-enclosing routine

  d[L2] for any routine to access its lexically-enclosing routine 2 levels up

  d[Ln] for any routine to access its lexically-enclosing routine n levels up

```

A frame represents an activation of a routine. A typical stack frame consists of the following components:

  * A static link which points to the underlying frame of the lexically-enclosing routine.

  * A dynamic link which points to the underlying frame based on the order of routine calls.

  * The return address of the instruction immediately following the call instruction that activated this routine.

There are 16 registers in the TAM architecture, each with its dedicated purpose:

Register   Mnemonic     Name                         Behaviour
-----------------------------------------------------------------------------------
  0          CB        Code Base           constant
  1          CT        Code Top            constant
  2          PB        Primitive Base      constant
  3          PT        Primitive Top       constant
  4          SB        Stack Base          constant
  5          ST        Stack Top           changed by most instructions
  6          HB        Heap Base           constant
  7          HT        Heap Top            changed by heap routines
  8          LB        Local Base          changed by call and return instructions
  9          L1        Local Base 1        L1 = content(LB)
  10         L2        Local Base 2        L2 = content(content(LB))
  11         L3        Local Base 3        L3 = content(content(content(LB)))
  12         L4        Local Base 4        L3 = content(content(content(content(LB))))
  13         L5        Local Base 5        L3 = content(content(content(content(content(LB)))))
  14         L5        Local Base 6        L3 = content(content(content(content(content(content(LB))))))
  15         CP        Code Pointer        changed by all instructions


## Instructions

TAM instructions have a fixed 32-bit format:

  * op - 4-bit opcode.
  * r  - 4-bit register field.
  * n  - 8-bit operand size (or other)
  * d  - 16-bit displacement (offset)

## Routines

Every TAM routine follows this basic protocol:

Suppose a procedure P requires d words of arguments and returns n words as result. The before the call to P, d words of arguments must be pushed onto the top of the stack. Just
after returning from P, n words of resul words myst be pushed onto the top of the stack (the d words of arguments having already been popped off). Note that both d and n may be 9.


There are two kinds of routines in TAM:

  * Code routines, and
  * Primitive routines

A code routine consists of instructions stored in the code segment. Control is transferred to the first instruction of the routine via a CALL or CALLI instruction. Control is 
then transferred back via a RETURN instruction. 

Consider the lifecycle of call of a typical procedure R:

  1. Immediately before the call, R's arguments (if any) must be at the stack top.

  2. The call instruction pushes a new frame on top of the arguments, and makes LB point to the base of this frame. The frame's static link is supplied by the value of register `n`
     in CALL(n) or the address of the closure at the stack top if invoked using CALLI.

     The frame's dynamic link is supplied by the old value of register LB. The frame's return address is the address of the instruction immediately following the call instruction.

  3. Immediately before return from R, the results must be available to be placed at the stack top. 

  4. The instruction RETURN(n) d pops the topmost frame, and replaces the d arguments at the current stack top with the n results of the routine call. LB is reset using the dynamic
     link of the popped frame, and control is transferred to the return address of the popped frame.

R access its arguments using negative displacements:

```
  LOAD(1) -d[LB] to access the first argument

  LOAD(1) -1[LB to access the dth argument]
```

A primitive routine is one that performs elementary arithmetic, logical, output, heap, or general-purpose operations. Each primitive routine has a fixed address in the Primitive
Segment of the Code Store. TAM traps every call to a primitive routine and performs the corresponding operation directly.

### Summary of TAM instructions

```
  Opcode   Mnemonic                                             Effect
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    0      LOAD(n) d[r]      Fetch an n-word object from the data address (d + register r), and push it onto the stack.

    1      LOADA   d[r]      Push the data address (d + register r) onto the stack.

    2      LOADI(n)          Pop a data address from the stack, load an n-word object from that address, and push it onto the stack.

    3      LOADL d           Push the 1-word literal d onto the stack.

    4      STORE(n) d[r]     Pop and n-word object from the stack, and store it at data address (d + register r).

    5      STOREI(n)         Pop an address from the stack, then pop an n-word object from the stack, and store it at that address.

    6      CALL(n) d[r]      Call the routine at code address (d + register r) using the addres in register n as the static link.

    7      CALLI             Pop a closure (static link + code address) from the stack, and then call the routine at that address.

    8      RETURN(n) d       Return from the current routine - pop and n-word result from the stack, then pop the topmost frame, then pop d-words of arguments, and then
                             finally push the result back onto the stack.

    9      -----             Unused

    10     PUSH d            Push d words (uninitialised) onto the stack.

    11     POP(n) d          Pop d n-word objects from the top of the stack.

    12     JUMP d[r]         Jump tot code address (d + register r).

    13     JUMPI             Pop a code address from the top of the stack, and then jump to that instruction.

    14     JUMPIF(n) d[r]    Pop a 1-word value from the stack, and then jump to code address (d + register r) iff that value equals n.

    15     HALT              Stop execution of the program.
```

### Summary of TAM primitive routines

```
  Address          Mnemonic         Arguments        Result                 Effect
  -------------------------------------------------------------------------------------
    PB + 1          id                w                w'         Set w' = w
    PB + 2          not               t                t'         Set t' = not t
    PB + 3          and               t1, t2           t'         Set t' = t1 and t2
    PB + 4          or                t1, t2           t'         Set t' = t1 or t2
    PB + 5          succ              i                i'         Set i' = i + 1
    PB + 6          pred              i                i'         Set i' = i - 1
    PB + 7          neg               i                i'         Set i' = -i
    PB + 8          add               i1, i2           i'         Set i' = i1 + i2
    PB + 9          sub               i1, i2           i'         Set i' = i1 - i2
    PB + 10         mult              i1, i2           i'         Set i' = i1 * i2
    PB + 11         div               i1, i2           i'         Set i' = i1 / i2
    PB + 12         mod               i1, i2           i'         Set i' = i1 % i2
    PB + 13         lt                i1, i2           t'         Set t' = true if i1 < i2
    PB + 14         le                i1, i2           t'         Set t' = true if i1 <= i2
    PB + 15         ge                i1, i2           t'         Set t' = true if i1 >= i2
    PB + 16         gt                i1, i2           t'         Set t' = true if i1 > i2
    PB + 17         eq                v1, v2, n        t'         Set t' = true if v1 = v2, where v1 and v2 are n-word values
    PB + 18         ne                v1, v2, n        t'         Set t' = true if v1 /= v2, where v1 and v2 are n-word values
    PB + 19         eol                  -             t'         Set t' = true if the next character to be read is an eol character
    PB + 20         eof                  -             t'         Set t' = true if there are no more characters to be read
    PB + 21         get                  a             _          Read a character, and store it in address a
    PB + 22         put                  c             _          Write the character c
    PB + 23         geteol               -             -          Read characters upto and including the next eol.
    PB + 24         puteol               -             -          Write an eol
    PB + 25         getint               a             -          Read an integer-literal, and store its value in address a
    PB + 26         putint               i             -          Write an integer literal whose value is i
    PB + 27         new                  n             a'         Set a' = starting address of the newly allocated n-word object on the heap
    PB + 28         dispose              n, a          -          Deallocate the n-word object at address a on the heap
```


